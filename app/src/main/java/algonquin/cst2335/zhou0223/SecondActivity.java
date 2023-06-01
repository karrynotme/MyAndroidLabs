package algonquin.cst2335.zhou0223;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import algonquin.cst2335.zhou0223.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

        ActivitySecondBinding binding;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            binding  = ActivitySecondBinding.inflate(getLayoutInflater());
            //loads the XML file  /res/layout/activity_second.xml
            setContentView(binding.getRoot());

            binding.goBackButton.setOnClickListener( (v)-> {

                //opposite of startActivity()
                finish(); //go back to previous
            });
        }
    }

