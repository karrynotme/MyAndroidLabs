package algonquin.cst2335.zhou0223;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.zhou0223.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.zhou0223.databinding.ReceiveMessageBinding;
import algonquin.cst2335.zhou0223.databinding.SentMessageBinding;

class MyRowHolder extends RecyclerView.ViewHolder {
    TextView theMessage;
    TextView theTime;

    public MyRowHolder(@NonNull View itemView) {
        super(itemView);
        theMessage = itemView.findViewById(R.id.theMessage);
        theTime = itemView.findViewById(R.id.theTime);
    }
}

public class ChatRoom extends AppCompatActivity {
    ActivityChatRoomBinding binding;
    ChatRoomViewModel chatModel;
    private RecyclerView.Adapter<MyRowHolder> myAdapter;
    ArrayList<ChatMessage> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        if (messages == null) {
            chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());
        }
        setContentView(binding.getRoot());

        binding.sendButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            messages.add( new ChatMessage(binding.sendButton.getText().toString(), currentDateandTime, true));

            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.sendButton.setText("");
        });

        binding.receiveButton.setOnClickListener(click -> {
            String message = binding.receiveButton.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage chatMessage = new ChatMessage(message, currentDateandTime, false);
            messages.add(chatMessage);
            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.receiveButton.setText("");
        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 1) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());
                } else {
                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.theMessage.setText("");
                holder.theTime.setText("");
                ChatMessage chatMessage = messages.get(position);
                holder.theMessage.setText(chatMessage.getMessage());
                holder.theTime.setText(chatMessage.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            public int getItemViewType(int position){
                if(messages.get(position).isSentButton){
                    return 0;
                }
                else{
                    return 1;
                }
            }
        });

        binding.recycleView.setAdapter(myAdapter);
    }
}
