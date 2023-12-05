package com.Doggo.DoggoEx.controller;
import com.Doggo.DoggoEx.service.weather.CompleteWeatherService;
import com.Doggo.DoggoEx.service.weather.MiddleWeatherService;
import com.Doggo.DoggoEx.service.weather.ShortWeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final MiddleWeatherService middleWeatherService;
    private final ShortWeatherService shortWeatherService;

    private final CompleteWeatherService completeWeatherService;

    public WeatherController(MiddleWeatherService middleWeatherService, ShortWeatherService shortWeatherService, CompleteWeatherService completeWeatherService) {
        this.middleWeatherService = middleWeatherService;
        this.shortWeatherService = shortWeatherService;
        this.completeWeatherService = completeWeatherService;
    }

    @PostMapping("/insert")
    public ResponseEntity<?> getForcasts() {
        try {
            // 단기예보
            Map<String, String> locationCode = shortWeatherService.getLocationCode();
            Map<String, List<List<String>>> completeShort = shortWeatherService.completeShort(locationCode);
            // 중기예보
            Map<String, List<List<String>>> middleTemp = middleWeatherService.getMiddleTemp(locationCode);
            Map<String, List<List<String>>> middleCondition = middleWeatherService.getMiddleCondition(locationCode);
            Map<String, List<List<String>>> completeMiddle = middleWeatherService.getCompleteMiddle(middleTemp,middleCondition);
            // 단기예보 + 중기예보
            Map<String, List<List<String>>> completeWeather = completeWeatherService.getCompleteWeather(completeShort, completeMiddle);
            return ResponseEntity.ok(completeWeather);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

}
