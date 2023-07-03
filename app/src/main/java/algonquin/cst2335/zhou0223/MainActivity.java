package algonquin.cst2335.zhou0223;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import algonquin.cst2335.zhou0223.data.MainActivityViewModel;
import algonquin.cst2335.zhou0223.databinding.ActivityMainBinding;
import algonquin.cst2335.zhou0223.databinding.ReceiveRowLayoutBinding;
import algonquin.cst2335.zhou0223.databinding.SentRowLayoutBinding;


public class MainActivity extends AppCompatActivity {
    /** This is a javadoc comment */
    /*   This is a normal comment */
    /**
     * This holds the "Hello world" text view
     */
    protected ArrayList<ChatMessage> theWords;
    protected RecyclerView recyclerView;
    protected TextView theText;
    /**
     * This holds the "Click me" button
     */
    protected Button myButton;
    /**
     * This holds the edit text for typing into
     */
    protected EditText theEditText;
    RecyclerView.Adapter myAdapter;
    MainActivityViewModel model;

    //equivalent to        static void main(String args[])
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calling onCreate from parent class
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        //loads an XML file on the page
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(MainActivityViewModel.class);
       //all new messages
        theWords = model.theWords;
        //theText = findViewById(R.id.textView);
        recyclerView = binding.theRecycleView;
        myButton = binding.button;
        theEditText = binding.theEditText;

        myButton.setOnClickListener(click -> {
            String input = theEditText.getText().toString();
            String back = theEditText.getText().toString();
            int type = 2;
            //insert into ArrayList
            theWords.add(new ChatMessage(input,back,type));
            //update the rows
            myAdapter.notifyDataSetChanged();
            theEditText.setText("");
        });

        //for  recycler view:
        recyclerView.setAdapter(new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //this inflates the row layout
                //load the sent_row_layout
                if (viewType == 1) {
                    SentRowLayoutBinding binding =
                            SentRowLayoutBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(binding.getRoot());
                } else {
                    ReceiveRowLayoutBinding binding =
                            ReceiveRowLayoutBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(binding.getRoot());
                }
            }

            public  int getItemViewType(int position){
                if (position % 2 == 0 )
                    return 1;
                else //add
                    return 2;
            }
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                // this initializes the row to data
                ChatMessage atThisRow = theWords.get(position);
                //puts the string
                holder.theWord.setText(atThisRow.message);
            }
            @Override
            public int getItemCount() {
                //how many rows there are:
                return theWords.size();
            }
        });
    }

    protected class MyRowHolder extends RecyclerView.ViewHolder {
        //put variables for what is on a single row;
        TextView theWord;
        TextView theTime;

        //this view is row
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            //this holds the message Text;
            theWord = itemView.findViewById((R.id.theMessage));
            //this holds the time text
            theTime = itemView.findViewById((R.id.theTime));
        }
    }
}

