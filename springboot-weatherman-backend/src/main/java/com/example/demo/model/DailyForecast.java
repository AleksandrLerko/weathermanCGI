package com.example.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

public class DailyForecast extends BaseModel{
    public String dateTime = "";

    public float minimumTemperatureCelsius = 0.0f;
    public float maximumTemperatureCelsius = 0.0f;

//    public float dailyChanceOfRain = 0.0f;
//    public float dailyChanceOfSnow = 0.0f;

    public float maxWindKph = 0.0f;

    public List<HourlyForecast> hourlyForecastList = new ArrayList<>();
}
