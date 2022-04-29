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

public class VisualCrossingApi {

    private final String apiKey = "C73QNA7LVUBBG8DWB3B9B7JSL";

    public String getData(String cityName, int days) throws JsonProcessingException {
        Forecast forecast = new Forecast();
        Location location = new Location();
        CurrentForecast currentForecast = new CurrentForecast();
        List<DailyForecast> dailyForecastList = new ArrayList<>();
        //List<HourlyForecast> hourlyForecastList = new ArrayList<>();

        String res = "";

        try {
            String apiUrl = String.format("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s/next%sdays?unitGroup=metric&key=%s&contentType=json", cityName, days, apiKey);
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

                System.out.println(infoString);

                res = String.valueOf(infoString);

                // JSON Object

                JSONObject jsonObj = new JSONObject(String.valueOf(infoString));
                JSONArray dayArray = jsonObj.getJSONArray("days");
                System.out.println(dayArray);

                //Get the first JSON object in the JSON array

                // Location
                location.localTime = (jsonObj.getJSONObject("currentConditions").getString("datetime"));
                location.cityName = (jsonObj.getString("address"));
                location.countryName = (jsonObj.getString("resolvedAddress").split(",")[1]);
                location.longitude = (jsonObj.getFloat("longitude"));
                location.latitude = (jsonObj.getFloat("latitude"));

                // CurrentForecast
                currentForecast.currentTemperatureCelsius = (jsonObj.getJSONObject("currentConditions").getFloat("temp"));
                float windSpeed = 0.0f;
                try {
                    windSpeed = (jsonObj.getJSONObject("currentConditions").getFloat("windspeed"));
                } catch (Exception e){
                    System.out.println("was exception in current forecast");
                    windSpeed = 0.0f;
                }
                currentForecast.windKph = windSpeed;
                currentForecast.precipitation = (jsonObj.getJSONObject("currentConditions").getFloat("precip"));
                currentForecast.conditions = (jsonObj.getJSONObject("currentConditions").getString("conditions"));

                for (int i = 0; i < dayArray.length(); i++) {
                    // DailyForecast
                    DailyForecast dailyForecast = new DailyForecast();
                    dailyForecast.dateTime = (dayArray.getJSONObject(i).getString("datetime"));
                    JSONObject dailyObj = dayArray.getJSONObject(i);
                    dailyForecast.minimumTemperatureCelsius = (dailyObj.getFloat("tempmin"));
                    dailyForecast.maximumTemperatureCelsius = (dailyObj.getFloat("tempmax"));
//                    dailyForecast.dailyChanceOfRain = (dailyObj.getFloat("daily_chance_of_rain"));
//                    dailyForecast.dailyChanceOfSnow = (dailyObj.getFloat("snow"));
                    dailyForecast.conditions = (dailyObj.getString("conditions"));
                    float windSpeedDaily = 0.0f;
                    try {
                        windSpeedDaily = (dailyObj.getFloat("windspeed"));
                    } catch (Exception e){
                        System.out.println("was exception in daily's forecast");
                        windSpeedDaily = 0.0f;
                    }
                    dailyForecast.maxWindKph = windSpeedDaily;
                    dailyForecast.precipitation = (dailyObj.getFloat("precip"));

                    JSONArray hourArray = dayArray.getJSONObject(i).getJSONArray("hours");
                    // HourlyForecast
                    for (int j = 0; j < hourArray.length(); j++) {
                        HourlyForecast hourlyForecast = new HourlyForecast();

                        JSONObject hourObj = hourArray.getJSONObject(j);
                        //system.out.println(j);

                        hourlyForecast.dateTime = (hourObj.getString("datetime")).split(":")[0] + ":" +
                                (hourObj.getString("datetime")).split(":")[1];
                        hourlyForecast.hourTemperatureCelsius = (hourObj.getFloat("temp"));
//                        hourlyForecast.chanceOfRain = (hourObj.getFloat("chance_of_rain"));
//                        hourlyForecast.chanceOfSnow = (hourObj.getFloat("snow"));
                        hourlyForecast.conditions = (hourObj.getString("conditions"));
                        float windSpeedHourly = 0.0f;
                        try {
                            windSpeedHourly = (hourObj.getFloat("windspeed"));
                        } catch (Exception e){
                            System.out.println("was exception in hour's forecast");
                            windSpeedHourly = 0.0f;
                        }
                        hourlyForecast.windKph = windSpeedHourly;
                        hourlyForecast.precipitation = (hourObj.getFloat("precip"));

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
