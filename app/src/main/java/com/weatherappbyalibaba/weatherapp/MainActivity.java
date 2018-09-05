package com.weatherappbyalibaba.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

public class MainActivity extends AppCompatActivity {
TextView temp ,dateTextView,weatherDescTextView,cityTextView;
    ImageView weatherImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         temp = (TextView)findViewById(R.id.temperature);
        dateTextView = (TextView) findViewById(R.id.date);
        weatherDescTextView = (TextView) findViewById(R.id.description);
        cityTextView = (TextView) findViewById(R.id.city);


        //    declared weather icons here

 //        weatherImageView = (ImageView) findViewById(R.id.WeatherIconView);
//        weatherImageView.setImageResource(R.drawable.icon_clearsky);

        dateTextView.setText(getCurrentDate());

        //image view
    //    ImageView mImageView;


                    // it can only takes and shows the weather of Peshawar
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Peshawar,pk&appid=ac70f630d68d6568db4a2c10922d63c0&units=imperial";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                   //    temp.setText("Response: " + response.toString());
                        Log.v("Weather","Response: " + response.toString());
                        try {

                            // temperature shown here is in kelvin
                            JSONObject obj = response.getJSONObject("main");
                            String myTemp = Integer.toString( (int) Math.round(obj.getDouble("temp")-32)*5/9);

                            temp.setText(myTemp);

                          //  JSONObject mainJSONObject = responseObject.getJSONObject("main");

                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject firstWeatherObject = weatherArray.getJSONObject(0);

                         //   String temp = Integer.toString((int) Math.round(mainJSONObject.getDouble("temp")));
                            String weatherDescription = firstWeatherObject.getString("description");
                            String city = response.getString("name");

                          //  tempTextView.setText(temp);
                            weatherDescTextView.setText(weatherDescription);
                            cityTextView.setText(city);
//
//String weatherIcon = firstWeatherObject.getString("icon");
//                          int iconResourceId = getResources().getIdentifier("icon_" + weatherDescription.replace(" ", ""), "drawable", getPackageName());
////                            int iconResourceId = getResources().getIdentifier(weatherIcon, "drawable", getPackageName());
//                            weatherImageView.setImageResource(iconResourceId);






                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

// Access the RequestQueue through your singleton class.
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);
    }
    private String getCurrentDate ()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("(dd.MM.yyyy)");
        String formattedDate = dateFormat.format(calendar.getTime());

        return formattedDate;
    }
}
