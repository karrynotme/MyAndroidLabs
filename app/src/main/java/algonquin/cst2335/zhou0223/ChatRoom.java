package algonquin.cst2335.zhou0223;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
    //class for MyRowHolder
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(clk -> {
                int position = getAbsoluteAdapterPosition();
                MyRowHolder newRow = (MyRowHolder) myAdapter.onCreateViewHolder(null,myAdapter.getItemViewType(position));
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );

                builder.setMessage("Do you want to delete the message: "+ messageText.getText());
                builder.setTitle("Question:");
                builder.setNegativeButton("No",(dialog,cl)->{});
                builder.setPositiveButton("Yes",(dialog,cl)->{
                    ChatMessage m = messages.get(position);
                    //mDAO.deleteMessage(m);
                    messages.remove(position);
                    myAdapter.notifyItemRemoved(position);
                    Snackbar.make(messageText, "You deleted message #"+position, Snackbar.LENGTH_LONG)
                            .setAction("Undo", (cl2) ->{
                                messages.add(position, m);
                                myAdapter.notifyItemInserted(position);
                            })
                            .show();
                });
                builder.create().show();
            });
            messageText = itemView.findViewById(R.id.theMessage);
            timeText = itemView.findViewById(R.id.theTime);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        //Create database
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        //Create DAO
        mDAO = db.cmDAO();
        //Create chat view model
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        //get all messages from the database at the beginning
        if(messages == null)
        {
            chatModel.messages.setValue(messages = new ArrayList<>());
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->{
                messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database
                runOnUiThread( () ->  binding.recycleView.setAdapter( myAdapter )); //You can then load the RecyclerView
            });
    }

        setContentView(binding.getRoot());

        binding.sendButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            //Create chat object
            messages.add( new ChatMessage(binding.editText.getText().toString(), currentDateandTime, true));
            //Notify insert
            myAdapter.notifyItemInserted(messages.size() - 1);
            //Clear the previous text
            binding.editText.setText("");
        });

        binding.receiveButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            //Create chat object
            messages.add( new ChatMessage(binding.editText.getText().toString(), currentDateandTime, false));
            //Notify insert
            myAdapter.notifyItemInserted(messages.size() - 1);
            //Clear the previous text
            binding.editText.setText("");
        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(),parent, false);
                    return new MyRowHolder(binding.getRoot());
                } else {
                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater(),parent, false);
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

            public int getItemViewType(int position){
                ChatMessage obj = messages.get(position);
                if(obj.isSentButton() == 0){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }
}


