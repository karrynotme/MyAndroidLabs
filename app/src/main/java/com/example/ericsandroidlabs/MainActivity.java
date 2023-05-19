package com.example.ericsandroidlabs;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    //equivalent to        static void main(String args[])
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calling onCreate from parent class


        //loads an XML file on the page
        setContentView(  R.layout.activity_main   );


        //look for something that id as "theText", return that object
        TextView theText = findViewById( R.id.theText );
        //will search XML for something with id as theButton
        Button myButton = findViewById( R.id.theButton ); //same as getElementId in javascript
        EditText theEdit = findViewById(R.id.theEditText);
        myButton.setOnClickListener(new View.OnClickListener() {
            // provide the missing function:
                     @Override
                 public void onClick(View v) {
                         String words = theEdit.getText().toString();//return what's in the EditText
                         //change what is in the textView
                         theText.setText(words);
                     }
             });
    }
}