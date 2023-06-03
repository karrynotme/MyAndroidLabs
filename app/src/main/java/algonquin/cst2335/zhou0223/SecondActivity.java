package algonquin.cst2335.zhou0223;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import algonquin.cst2335.zhou0223.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

        //ActivitySecondBinding binding;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // 在接收变量的目标 Activity 的 onCreate() 方法中
            TextView welcomeTextView = findViewById(R.id.textView3); // 找到对应的 TextView
            setContentView(R.layout.activity_second);
            Intent fromPrevious = getIntent();
            String emailAddress = fromPrevious.getStringExtra("EmailAddress");
            String welcomeMessage = "Welcome Back " + emailAddress; // 构建欢迎消息
            welcomeTextView.setText(welcomeMessage); // 将欢迎消息设置给 TextView

            //binding  = ActivitySecondBinding.inflate(getLayoutInflater());
            //loads the XML file  /res/layout/activity_second.xml
            //setContentView(binding.getRoot());
            //binding.goBackButton.setOnClickListener( (v)-> {
                //opposite of startActivity()
               // finish(); //go back to previous
            //});
        }
    }

