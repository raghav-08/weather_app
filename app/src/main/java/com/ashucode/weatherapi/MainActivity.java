package com.ashucode.weatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText etcity;
    TextView tvResult;

    private final String url = "https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    private final String appid = "c85220924d5c89ec0999f048fdab502a";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etcity = findViewById(R.id.etcity);
        tvResult = findViewById(R.id.tvResult);
    }
    public void getWeatherDetails(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weather myweather = retrofit.create(weather.class);
        Call<Example> exampleCall = myweather.getweather(etcity.getText().toString().trim(),appid);
        exampleCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(response.code() == 404)
                {
                    Toast.makeText(MainActivity.this, "Please Enter Valid City Name", Toast.LENGTH_SHORT).show();
                }
                else if(!response.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                }
                Example mydata = response.body();
                Main main = mydata.getMain();
                Double temp = main.getTemp();
                Integer pressure = main.getPressure();
                Integer humidity = main.getHumidity();
                Integer temperature = (int)(temp-273.15);

                tvResult.setText("Temperature: "+String.valueOf(temperature)+"°C\n"+"Pressure: "+String.valueOf(pressure)+"hpa\n"+
                "Humidity: "+String.valueOf(humidity)+"%");
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

                Toast.makeText(MainActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        }

    }