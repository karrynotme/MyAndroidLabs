package com.example.ericsandroidlabs.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
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

        variableBinding.theButton.setOnClickListener(click -> {
            model.editText.postValue(variableBinding.myeditText.getText().toString());
        });

        model.editText.observe(this, s -> {
            String editTextValue = s != null ? s : "";
            variableBinding.myeditText.setText(editTextValue);
            variableBinding.theText.setText(editTextValue);
        });

        //
        model.isSelected.observe(this, selected -> {
            Toast.makeText(this,"The value now is :"+selected.toString(),Toast.LENGTH_SHORT).show();
                    variableBinding.myCheckbox.setChecked(selected);
                    variableBinding.mySwitch.setChecked(selected);
                    variableBinding.myRadioButton.setChecked(selected);
                });
        variableBinding.myCheckbox.setOnCheckedChangeListener((btn, isChecked) -> {
                model.isSelected.postValue(isChecked);
            });
        variableBinding.mySwitch.setOnCheckedChangeListener((btn, isChecked) -> {
            model.isSelected.postValue(isChecked);
        });
        variableBinding.myRadioButton.setOnCheckedChangeListener((btn, isChecked) -> {
            model.isSelected.postValue(isChecked);
        });
        variableBinding.myImageButton.setOnClickListener((click) -> {
            Toast.makeText(this,"the width ="+variableBinding.myImageButton.getWidth()+"and height ="+variableBinding.myImageButton.getHeight(),Toast.LENGTH_SHORT).show();
        });
    }
}