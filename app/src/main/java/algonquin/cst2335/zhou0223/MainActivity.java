package algonquin.cst2335.zhou0223;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import algonquin.cst2335.zhou0223.databinding.ActivityMainBinding;
import algonquin.cst2335.zhou0223.databinding.SentRowLayoutBinding;


/**This page represents the first page loaded
 * @author Min
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {
    /** This is a javadoc comment */
    /*   This is a normal comment */
    /**
     * This holds the "Hello world" text view
     */
    protected ArrayList<String> theWords;
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

    //equivalent to        static void main(String args[])
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calling onCreate from parent class
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        //loads an XML file on the page
        setContentView(binding.getRoot());
        theWords = new ArrayList<>();
        //theText = findViewById(R.id.textView);
        recyclerView = binding.theRecycleView;
        myButton = binding.button;
        theEditText = binding.theEditText;

        myButton.setOnClickListener(click -> {
            String input = theEditText.getText().toString();
            theWords.add(input);
            myAdapter.notifyDataSetChanged();//update the rows
            theEditText.setText("");
        });

        //for  recycler view:
        recyclerView.setAdapter(new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //this inflates the row layout
                //load the sent_row_layout
                SentRowLayoutBinding binding = SentRowLayoutBinding.inflate(getLayoutInflater());
                return new MyRowHolder (binding.getRoot());
            }
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                // this initializes the row to data
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

