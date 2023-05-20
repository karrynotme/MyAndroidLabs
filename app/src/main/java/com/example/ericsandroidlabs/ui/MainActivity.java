
package com.example.ericsandroidlabs.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ericsandroidlabs.R;
import com.example.ericsandroidlabs.data.MainActivityViewModel;
import com.example.ericsandroidlabs.databinding.ActivityMainBinding;

import java.text.CollationElementIterator;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding variableBinding;
    private MainActivityViewModel model;
    //equivalent to        static void main(String args[])
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calling onCreate from parent class
        model = new ViewModelProvider(this).get(MainActivityViewModel.class);
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        variableBinding.myText.setText(model.editString);
        variableBinding.myButton.setOnClickListener( click  ->{
            variableBinding.myText.setText("Your Edit Text has" + model.editString);

        });


        //loads an XML file on the page
        //setContentView(  R.layout.activity_main   );

        //look for something that id as "theText", return that object
        //TextView theText = findViewById( R.id.theText );
        //will search XML for something with id as theButton
        //Button myButton = findViewById( R.id.theButton ); //same as getElementId in javascript
        //EditText theEdit = findViewById(R.id.theEditText);
        CheckBox myCheckbox = variableBinding.myCheckbox;
        Switch mySwitch = variableBinding.mySwitch;
        //onCheckedChanged
        myCheckbox.setOnClickedChangedListener( (a,b) -> myText.setText("The CheckBox is ON?" +b));

        //when only 1 line of code between {}
        mySwitch.setOnClickedChangeListener((a,b) -> myText.setText("The Switch is ON?"+ b));

        myText.setText( model.theText );//save it offscreen
        myButton.setText( model.buttonText );
        //myButton.setOnClickListener(new View.OnClickListener() {
            // provide the missing function:
           // @Override
           // public void onClick(View v) {
              //  String words = theEdit.getText().toString();//return what's in the EditText
                //change what is in the textView
                //theText.setText(words);

            }
       // });
    //}
}

