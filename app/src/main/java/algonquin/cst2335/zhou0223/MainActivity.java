package algonquin.cst2335.zhou0223;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import algonquin.cst2335.zhou0223.databinding.ActivityMainBinding;

/**
 * The main activity java class of the activity_main.xml
 * @author Min
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    protected String cityName;
    protected RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(this);
        ActivityMainBinding binding = ActivityMainBinding.inflate( getLayoutInflater() );
        setContentView(binding.getRoot());

        binding.forecastButton.setOnClickListener(click -> {
            cityName = binding.cityTextField.getText().toString();
            String stringURL = null;
            try {
                stringURL = "https://api.openweathermap.org/data/2.5/weather?q="
                        + URLEncoder.encode(cityName, "UTF-8")
                        + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            //this goes in the button click handler:
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                    (successfulResponse )->{
                        try {
                            JSONArray weather = successfulResponse.getJSONArray("weather");
                            JSONObject data = successfulResponse.getJSONObject("main");
                            binding.temp.setText("The current temperature is " + data.getString("temp"));
                            binding.maxTemp.setText("The max temperature is " + data.getString("temp_max"));
                            binding.minTemp.setText("The min temperature is " + data.getString("temp_min"));
                            binding.humitidy.setText("The humidity is " + data.getString("humidity") + "%");
                            binding.description.setText(weather.getJSONObject(0).getString("description"));

                            String iconName = weather.getJSONObject(0).getString("icon");
                            String pictureURL = "http://openweathermap.org/img/w/"
                                    + iconName
                                    + ".png";
                            String pathname = getFilesDir() + "/" + iconName + ".png";
                            File f = new File(pathname);
                            if(f.exists()){
                                binding.icon.setImageBitmap(BitmapFactory.decodeFile(pathname));
                            }
                            else {
                                ImageRequest imgReq = new ImageRequest(pictureURL, new Response.Listener<Bitmap>() {
                                    @Override
                                    public void onResponse(Bitmap bitmap) {
                                        binding.icon.setImageBitmap(bitmap);
                                    }
                                }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {
                                    int j = 0;
                                });
                                queue.add(imgReq);
                            }
                        }
                        catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }, //gets called if it is successful
                    (error) ->
                    {  int i =0;   }
            );//gets called if there is an error
            queue.add(request);//run the web query
        });
    }
}