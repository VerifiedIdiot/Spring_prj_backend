package com.Doggo.DoggoEx.service.weather;

import com.Doggo.DoggoEx.dto.WeatherDto;
import com.Doggo.DoggoEx.entity.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.Doggo.DoggoEx.repository.WeatherRepository;

import java.util.List;
import java.util.Map;

@Service
public class WeatherDataSaveService {

    @Autowired
    private WeatherRepository weatherRepository;


    public void saveWeatherData(Map<String, List<List<String>>> weatherData) {
        for (Map.Entry<String, List<List<String>>> entry : weatherData.entrySet()) {
            for (List<String> data : entry.getValue()) {
                // data 리스트에서 필요한 정보를 추출하여 WeatherDto 객체 생성
                WeatherDto weatherDto = new WeatherDto();
                weatherDto.setRegion(entry.getKey());
                weatherDto.setWeatherDate(Integer.parseInt(data.get(0))); // 예: "20231205"
                weatherDto.setMorningTemperature(Integer.parseInt(data.get(1)));
                weatherDto.setMorningRainPercent(Integer.parseInt(data.get(2)));
                weatherDto.setMorningWeatherCondition(data.get(3));
                weatherDto.setAfternoonTemperature(Integer.parseInt(data.get(4)));
                weatherDto.setAfternoonRainPercent(Integer.parseInt(data.get(5)));
                weatherDto.setAfternoonWeatherCondition(data.get(6));

                // DTO 객체를 엔티티 객체로 변환하고 저장
                Weather weather = weatherDto.toEntity();
                weatherRepository.saveAndFlush(weather);
            }
        }
    }
}
