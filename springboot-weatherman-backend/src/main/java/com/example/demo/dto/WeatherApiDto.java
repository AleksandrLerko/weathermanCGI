package com.example.demo.dto;

import com.example.demo.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WeatherApiDto {

    private final String apiKey = "79f4a81978e24b6c80a161644221704";

    public String getData(String cityName, int days) throws JsonProcessingException {
        Forecast forecast = new Forecast();
        Location location = new Location();
        CurrentForecast currentForecast = new CurrentForecast();
        List<DailyForecast> dailyForecastList = new ArrayList<>();
        //List<HourlyForecast> hourlyForecastList = new ArrayList<>();

        String res = "";

        try {
            String apiUrl = String.format("http://api.weatherapi.com/v1/forecast.json?key=%s&q=%s&days=%o&aqi=no&alerts=no", apiKey, cityName, days);
            URL url = new URL(apiUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200){
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder infoString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()){
                    infoString.append(scanner.nextLine());
                }

                scanner.close();

                //System.out.println(infoString);

                //res = String.valueOf(infoString);

                // JSON Object

                JSONObject jsonObj = new JSONObject(String.valueOf(infoString));
                JSONArray dayArray = jsonObj.getJSONObject("forecast").getJSONArray("forecastday");
                System.out.println(dayArray);

                //Get the first JSON object in the JSON array

                // Location
                location.localTime = (jsonObj.getJSONObject("location").getString("localtime"));
                location.cityName = (jsonObj.getJSONObject("location").getString("name"));
                location.countryName = (jsonObj.getJSONObject("location").getString("country"));
                location.longitude = (jsonObj.getJSONObject("location").getFloat("lon"));
                location.latitude = (jsonObj.getJSONObject("location").getFloat("lat"));

                // CurrentForecast
                currentForecast.currentTemperatureCelsius = (jsonObj.getJSONObject("current").getFloat("temp_c"));
                currentForecast.windKph = (jsonObj.getJSONObject("current").getFloat("wind_kph"));
                currentForecast.precipitation = (jsonObj.getJSONObject("current").getFloat("precip_mm"));
                currentForecast.conditions = (jsonObj.getJSONObject("current").getJSONObject("condition").getString("text"));

                for (int i = 0; i < dayArray.length(); i++) {
                    // DailyForecast
                    DailyForecast dailyForecast = new DailyForecast();
                    dailyForecast.dateTime = (dayArray.getJSONObject(i).getString("date"));
                    JSONObject dailyObj = dayArray.getJSONObject(i).getJSONObject("day");
                    dailyForecast.minimumTemperatureCelsius = (dailyObj.getFloat("mintemp_c"));
                    dailyForecast.maximumTemperatureCelsius = (dailyObj.getFloat("maxtemp_c"));
//                    dailyForecast.dailyChanceOfRain = (dailyObj.getFloat("daily_chance_of_rain"));
//                    dailyForecast.dailyChanceOfSnow = (dailyObj.getFloat("daily_chance_of_snow"));
                    dailyForecast.conditions = (dailyObj.getJSONObject("condition").getString("text"));
                    dailyForecast.maxWindKph = (dailyObj.getFloat("maxwind_kph"));
                    dailyForecast.precipitation = (dailyObj.getFloat("totalprecip_mm"));

                    JSONArray hourArray = dayArray.getJSONObject(i).getJSONArray("hour");
                    // HourlyForecast
                    for (int j = 0; j < hourArray.length(); j++) {
                        HourlyForecast hourlyForecast = new HourlyForecast();

                        JSONObject hourObj = hourArray.getJSONObject(j);
                        //system.out.println(j);

                        hourlyForecast.dateTime = (hourObj.getString("time")).split(" ")[1];
                        hourlyForecast.hourTemperatureCelsius = (hourObj.getFloat("temp_c"));
//                        hourlyForecast.chanceOfRain = (hourObj.getFloat("chance_of_rain"));
//                        hourlyForecast.chanceOfSnow = (hourObj.getFloat("chance_of_snow"));
                        hourlyForecast.conditions = (hourObj.getJSONObject("condition").getString("text"));
                        hourlyForecast.windKph = (hourObj.getFloat("wind_kph"));
                        hourlyForecast.precipitation = (hourObj.getFloat("precip_mm"));

                        dailyForecast.hourlyForecastList.add(hourlyForecast);
                    }
                    dailyForecastList.add(dailyForecast);
                }


                //System.out.println(jsonObj.getJSONObject("location"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        forecast.location = location;
        forecast.currentForecast = currentForecast;
        forecast.dailyForecast = dailyForecastList;

        res = new ObjectMapper().writeValueAsString(forecast);

        return res;
    }
}
