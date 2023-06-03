package algonquin.cst2335.zhou0223;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.text.BreakIterator;

import algonquin.cst2335.zhou0223.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    protected ActivityMainBinding binding;

    //equivalent to        static void main(String args[])
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calling onCreate from parent class
        setContentView(R.layout.activity_main);
        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        Log.w("MainActivity", "In OnCreate() -Loading Widgets" );
        //loads an XML file on the page
        setContentView(  binding.getRoot()   );
        EditText et = findViewById(R.id.emailText);

        binding.loginButton.setOnClickListener( (v) -> {
            Log.w(TAG, "You clicked the button");
            //where to go:
            //leaving here
            //going to SecondActivity
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            // 将电子邮件地址作为额外的数据添加到Intent中
            nextPage.putExtra("EmailAddress", et.getText().toString());
            //go to another page
            startActivity(nextPage);
        } );

    }
    @Override //garbage collected, app is gone
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "In OnDestroy()");
    }

    @Override //now visible, not listening for clicks
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "In OnStart()");
    }

    @Override  //no longer visible on screen
    protected void onStop() {
        super.onStop();

        Log.e(TAG, "In OnStop()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "In OnResume()");
    }

    @Override //leaving the screen, no longer listening for input
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "In OnPause()");
    }
}