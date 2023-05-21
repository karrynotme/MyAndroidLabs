package com.example.ericsandroidlabs.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ericsandroidlabs.data.MainViewModel;
import com.example.ericsandroidlabs.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private MainViewModel model;

    //equivalent to        static void main(String args[])
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calling onCreate from parent class,do what the parent does
        model = new ViewModelProvider(this).get(MainViewModel.class);//initialize

        //this has all the ids predefined
        com.example.ericsandroidlabs.databinding.ActivityMainBinding variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        //loads the screen
        setContentView(variableBinding.getRoot());
        //old way of getting widgets
        TextView theText = variableBinding.theText;
        Button myButton = variableBinding.theButton;
        EditText myEdit = variableBinding.theEdit;
        CheckBox myCheckbox = variableBinding.myCheckbox;
        Switch mySwitch = variableBinding.mySwitch;
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


        theText.setText( model.theText );
        myButton.setText( model.buttonText );
        myEdit.setText( model.editText );

        myButton.setOnClickListener((v) -> {
                    model.theText = "You clicked the button";
                    model.buttonText = "Something new here";
                    model.editText = "My edit text";
                    //this gets run when you click the button
                    theText.setText(model.theText);
                    myButton.setText(model.buttonText);
                    myEdit.setText(model.editText);

                });
        }
    }