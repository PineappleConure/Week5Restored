package algonquin.cst2335.wang0467;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import android.util.Log;
import algonquin.cst2335.wang0467.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    protected String cityName;
    protected RequestQueue queue;
    protected ImageView weatherIconImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(this);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        weatherIconImageView = binding.icon;

        binding.forcastButton.setOnClickListener(click -> {
            cityName = binding.cityTextField.getText().toString();
            String stringURL = null;
            try {
                stringURL = "https://api.openweathermap.org/data/2.5/weather?q="
                        + URLEncoder.encode(cityName, "UTF-8")
                        + "&appid=2fb132343b663b89e1d0ca8a36aeedf5&units=metric";
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                    (response) -> {
                        try {
                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject position0 = weatherArray.getJSONObject(0);
                            String iconName = position0.getString("icon");
                            String fileName = iconName + ".png";
                            File file = new File(getFilesDir(), fileName);

                            if (!file.exists()) {
                                String imageUrl = "http://openweathermap.org/img/w/" + iconName + ".png";
                                ImageRequest imgReq = new ImageRequest(imageUrl, bitmap -> {
                                    weatherIconImageView.setImageBitmap(bitmap);
                                    try (FileOutputStream fOut = openFileOutput(fileName, MODE_PRIVATE)) {
                                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                    } catch (IOException e) {
                                        Log.e("MainActivity", "Error saving image", e);
                                    }
                                }, 1024, 1024, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565,
                                        error -> Log.e("MainActivity", "Image Request Error: " + error.getMessage()));

                                queue.add(imgReq);
                            } else {
                                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                weatherIconImageView.setImageBitmap(bitmap);
                            }

                            JSONObject mainObject = response.getJSONObject("main");
                            double current = mainObject.getDouble("temp");
                            double min = mainObject.getDouble("temp_min");
                            double max = mainObject.getDouble("temp_max");
                            int humidity = mainObject.getInt("humidity");

                            binding.temp.setText("The current temperature is " + current);
                            binding.minTemp.setText("The min temperature is " + min);
                            binding.maxTemp.setText("The max temperature is " + max);
                            binding.humidity.setText("The humidity is " + humidity);
                            binding.description.setText(position0.getString("description"));

                            binding.temp.setVisibility(View.VISIBLE);
                            binding.minTemp.setVisibility(View.VISIBLE);
                            binding.maxTemp.setVisibility(View.VISIBLE);
                            binding.humidity.setVisibility(View.VISIBLE);
                            binding.description.setVisibility(View.VISIBLE);
                            binding.icon.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            Log.e("MainActivity", "JSON parsing error", e);
                        }
                    },
                    error -> Log.e("MainActivity", "Request Error: " + error.getMessage())
            );
            queue.add(request);
        });
    }
}
