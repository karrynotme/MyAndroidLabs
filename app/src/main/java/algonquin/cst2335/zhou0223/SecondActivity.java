package algonquin.cst2335.zhou0223;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import algonquin.cst2335.zhou0223.databinding.ActivitySecondBinding;


public class SecondActivity extends AppCompatActivity {
    private ActivitySecondBinding variableBinding;
    private String TAG = "SecondActivity";
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            variableBinding = ActivitySecondBinding.inflate(getLayoutInflater());
            setContentView(variableBinding.getRoot());
            SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

            Intent fromPrevious = getIntent();
            String emailAddress = fromPrevious.getStringExtra("EmailAddress");
            variableBinding.textView3.setText("Welcome back"+emailAddress);
            //TextView welcomeTextView = findViewById(R.id.textView3);
            //welcomeTextView.setText("Welcome Back " + emailAddress);

            //set initial phone number
            variableBinding.editTextPhone.setText(prefs.getString("PhoneNumber", ""));
            // get picture if exist
            File file = new File( getFilesDir(), "Picture.png");

            if(file.exists()) {
                Bitmap theImage = BitmapFactory.decodeFile(file.getAbsolutePath());
                variableBinding.imageView.setImageBitmap(theImage);
            }

            //set phone call
            variableBinding.changeNumber.setOnClickListener((v)-> {
                Log.e(TAG, "You clicked the Call button");
                Intent phoneCall = new Intent(Intent.ACTION_DIAL);
                String phoneNo = variableBinding.editTextPhone.getText().toString();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("PhoneNumber", phoneNo);
                editor.apply();
                phoneCall.setData(Uri.parse("tel:" + phoneNo));
                startActivity(phoneCall);
            });

            //set camera function
            ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
//                        Log.e(TAG, "You clicked the camera");
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                FileOutputStream fOut = null;

                                Intent data = result.getData();
                                Bitmap thumbnail = data.getParcelableExtra("data");

                                variableBinding.imageView.setImageBitmap(thumbnail);
                                try { fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                    fOut.flush();
                                    fOut.close();
                                }
                                catch (FileNotFoundException e)
                                { e.printStackTrace();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
            //set camera listener
            variableBinding.changePictureButton.setOnClickListener((v)-> {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraResult.launch(cameraIntent);
            });
        }
    protected void onPause(){
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("Mydata",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PhoneNumber",variableBinding.editTextPhone.getText().toString());
        editor.apply();
    }
    }
