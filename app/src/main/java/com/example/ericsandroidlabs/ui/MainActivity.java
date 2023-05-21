package com.example.ericsandroidlabs.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ericsandroidlabs.data.MainViewModel;
import com.example.ericsandroidlabs.databinding.ActivityMainBinding;



public class MainActivity extends AppCompatActivity {
    private MainViewModel model;
    private ActivityMainBinding variableBinding;

    //equivalent to        static void main(String args[])
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calling onCreate from parent class,do what the parent does
        model = new ViewModelProvider(this).get(MainViewModel.class);//initialize

        //this has all the ids predefined
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        //loads the screen
        setContentView(variableBinding.getRoot());
        //old way of getting widgets
        TextView theText = variableBinding.theText;
        Button myButton = variableBinding.theButton;
        EditText myEdit = variableBinding.theEdit;
        CheckBox myCheckbox = variableBinding.myCheckbox;
        Switch mySwitch = variableBinding.mySwitch;
        RadioButton myRadioButton = variableBinding.myRadioButton;
        ImageButton myImageButton = variableBinding.myImageButton;
        //TextView theText = findViewById(R.id.theText );
        //Button myButton = findViewById( R.id.theButton );
        //EditText myEdit = findViewById( R.id.theEdit );
        //CheckBox myCheckbox = findViewById( R.id.myCheckbox );
        
        //anCheckedChanged()
        myCheckbox.setOnCheckedChangeListener( (a,b) ->{
            theText.setText("The checkbox is on?" +b);
        });
        //when only 1 line of code between{}
        mySwitch.setOnCheckedChangeListener( (a, b) ->{
            theText.setText("The switch is on?" +b);
        });

        myRadioButton.setOnCheckedChangeListener( (a, b) ->{
            theText.setText("The myRadioButton is on?" +b);
        });


        theText.setText( model.theText );
        myButton.setText( model.buttonText );
        myEdit.setText( model.editText );
        myImageButton.setContentDescription(model.imageText);


        myButton.setOnClickListener((v) -> {
            model.theText = "You clicked the button";
            model.buttonText = "Something new here";
            model.editText = "My edit text";
            model.imageText = "My image text";

            //this gets run when you click the button
            theText.setText(model.theText);
            myButton.setText(model.buttonText);
            myEdit.setText(model.editText);
            myImageButton.setContentDescription(model.imageText);

            // Show the Toast
            Context context = getApplicationContext();
            CharSequence text = "Button clicked!";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
                });
        }
}