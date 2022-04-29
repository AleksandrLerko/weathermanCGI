package com.example.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class Forecast {
    public Location location = new Location();
    public CurrentForecast currentForecast = new CurrentForecast();
    public List<DailyForecast> dailyForecast = new ArrayList<>();
}
