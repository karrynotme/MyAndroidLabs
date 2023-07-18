package algonquin.cst2335.zhou0223;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.zhou0223.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.zhou0223.databinding.ReceiveMessageBinding;
import algonquin.cst2335.zhou0223.databinding.SentMessageBinding;


public class ChatRoom extends AppCompatActivity {
    ActivityChatRoomBinding binding;
    ChatRoomViewModel chatModel;
    ChatMessageDAO mDAO;
    private RecyclerView.Adapter myAdapter;
    ArrayList<ChatMessage> messages = new ArrayList<>();

    // Class for MyRowHolder
    class MyRowHolder extends RecyclerView.ViewHolder {
        //THis holds the message Text:
        TextView messageText;
        //This holds the time text
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(clk -> {
                int position = getAbsoluteAdapterPosition();
                ChatMessage selected = messages.get(position);
                chatModel.selectedMessage.postValue(selected);
            });
            messageText = itemView.findViewById(R.id.theMessage);
            timeText = itemView.findViewById(R.id.theTime);
        }
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        if (itemID == R.id.item_1 && binding.fragmentLayout != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
            ChatMessage message = chatModel.selectedMessage.getValue();
            long fragmentNo = message.id;
            TextView messageText = binding.fragmentLayout.findViewById(R.id.messageText);

            builder.setMessage("Do you want to delete the message: " + messageText.getText().toString());
            builder.setTitle("Question:");
            builder.setNegativeButton("No", (dialog, cl) -> {
            });
            builder.setPositiveButton("Yes", (dialog, cl) -> {
                //delete to database;
                Executor thread1 = Executors.newSingleThreadExecutor();
                thread1.execute(() -> {
                    mDAO.deleteMessage(message);//delete to database;
                });
                //mDAO.deleteMessage(m);
                messages.remove(message);
                myAdapter.notifyDataSetChanged();
                Snackbar.make(messageText, "You deleted message #" + fragmentNo, Snackbar.LENGTH_LONG)
                        .setAction("Undo", (cl2) -> {
                            messages.add((int) fragmentNo, message);
                            Executor thread2 = Executors.newSingleThreadExecutor();
                            thread2.execute(() -> {
                                mDAO.insertMessage(message);//add to database;
                            });
                            myAdapter.notifyItemInserted((int) fragmentNo);
                        })
                        .show();
            });
            builder.create().show();
        } else if (itemID == R.id.item_about) {
            Toast.makeText(this,"Version 1.0, created by Xuemin Zhou",Toast.LENGTH_LONG).show();
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_memu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);

        // Create database
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name")
                .allowMainThreadQueries()
                .build();
        // Create DAO
        mDAO = db.cmDAO();

        // Create chat view model
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        // Get all messages from the database at the beginning
        messages = chatModel.messages.getValue();
        if (messages == null) {
            chatModel.messages.setValue(messages = new ArrayList<>());
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                messages.addAll(mDAO.getAllMessages());
                runOnUiThread(() -> binding.recycleView.setAdapter(myAdapter));
            });
        }

        binding.sendButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage cm = new ChatMessage(binding.editText.getText().toString(), currentDateandTime, true);
            messages.add(cm);
            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.editText.setText("");

            Executor thread1 = Executors.newSingleThreadExecutor();
            thread1.execute(() -> {
                cm.id = (int) mDAO.insertMessage(cm);
            });
        });

        binding.receiveButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage cm = new ChatMessage(binding.editText.getText().toString(), currentDateandTime, false);
            messages.add(cm);
            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.editText.setText("");

            Executor thread1 = Executors.newSingleThreadExecutor();
            thread1.execute(() -> {
                cm.id = (int) mDAO.insertMessage(cm);
            });
        });

        myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(binding.getRoot());
                } else {
                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(binding.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.messageText.setText("");
                holder.timeText.setText("");
                ChatMessage chatMessage = messages.get(position);
                holder.messageText.setText(chatMessage.getMessage());
                holder.timeText.setText(chatMessage.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                ChatMessage obj = messages.get(position);
                if (obj.isSentButton() == 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };

        binding.recycleView.setAdapter(myAdapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        ChatRoomViewModel.selectedMessage.observe(this, (newMessageValue) -> {
            if (newMessageValue != null) {
                FragmentManager fMgr = getSupportFragmentManager();
                FragmentTransaction tx = fMgr.beginTransaction();
                MessageDetailsFragment chatFragment = new MessageDetailsFragment(newMessageValue);
                tx.replace(R.id.fragmentLayout, chatFragment);
                tx.addToBackStack("Anything here");
                tx.commit();
            }
        });
    }
}