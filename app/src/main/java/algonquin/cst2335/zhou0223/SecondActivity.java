package algonquin.cst2335.zhou0223;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;

import algonquin.cst2335.zhou0223.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {
    private ActivitySecondBinding binding;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        TextView welcomeTextView = findViewById(R.id.textView3);
        welcomeTextView.setText("Welcome Back " + emailAddress);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                    }

                    public void onActivityResuilt(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null && data.hasExtra("data")) {
                                Bitmap thumbnail = data.getParcelableExtra("data");
                                if (thumbnail != null) {
                                    ImageView profileImg = findViewById(R.id.imageView);
                                    profileImg.setImageBitmap(thumbnail);
                                    FileOutputStream fOut = null;
                                    try {
                                        fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                                        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                        fOut.flush();
                                        fOut.close();
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                    }
                });

    }
}
