package com.example.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class HourlyForecast extends BaseModel {
    public String dateTime = "";

    public float hourTemperatureCelsius = 0.0f;

//    public float chanceOfRain = 0.0f;
//    public float chanceOfSnow = 0.0f;

    public float windKph = 0.0f;
}
