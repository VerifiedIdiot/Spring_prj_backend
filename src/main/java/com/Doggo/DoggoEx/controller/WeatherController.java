package com.Doggo.DoggoEx.controller;
import com.Doggo.DoggoEx.service.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

// 해당 컨트롤러는 파이썬 코드를 읽어내는 파이썬인터프리터를 호출하는 스크립트임
// 해당 파이썬 코드는 기상청과 환경관리공단에서 화면에 노출되어있는 날씨/기후 정보들을 수집해옴

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;
    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("/insert")
    public ResponseEntity<?> getForcast() {
        try {
            Map<String, String> locationAndRegCode = weatherService.getWeatherLocation();
            Map<String, List<List<String>>> weeklyTemperature = weatherService.getWeatherTemp(locationAndRegCode);
            return ResponseEntity.ok(weeklyTemperature);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

}
