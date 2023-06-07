package algonquin.cst2335.zhou0223;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import algonquin.cst2335.zhou0223.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private SharedPreferences prefs;

    //equivalent to        static void main(String args[])
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calling onCreate from parent class
        setContentView(R.layout.activity_main);
        Log.w("MainActivity", "In OnCreate() -Loading Widgets");

        EditText et = findViewById(R.id.emailText);
        prefs = getSharedPreferences("Mydata", Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("LoginName", "");
        et.setText(emailAddress);
    }
        public void sendMessage(View view) {
            TextView editText = findViewById(R.id.emailText);
            String newEmailAddress = editText.getText().toString();

            SharedPreferences prefs = getSharedPreferences("Mydata", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("LoginName", newEmailAddress);
            editor.apply();
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            nextPage.putExtra("EmailAddress", editText.getText().toString());
            startActivity(nextPage);
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