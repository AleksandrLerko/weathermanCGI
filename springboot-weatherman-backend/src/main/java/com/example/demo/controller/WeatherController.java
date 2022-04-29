package com.example.demo.controller;

import com.example.demo.dto.VisualCrossingApi;
import com.example.demo.dto.WeatherApiDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class WeatherController {
    private final WeatherApiDto weatherDto = new WeatherApiDto();
    private final VisualCrossingApi visualCrossingDto = new VisualCrossingApi();

    @RequestMapping("/api/weatherapi/{locationData}/{days}")
    public String getDataWeatherApi(@PathVariable String locationData, @PathVariable int days) throws JsonProcessingException {
        return weatherDto.getData(locationData, days);
    }

    @RequestMapping("/api/visualcrossing/{locationData}/{days}")
    public String getDataVisualCrossing(@PathVariable String locationData, @PathVariable int days) throws JsonProcessingException {
        return visualCrossingDto.getData(locationData, days);
    }
}
