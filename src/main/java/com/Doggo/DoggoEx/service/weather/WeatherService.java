package com.Doggo.DoggoEx.service.weather;

import com.Doggo.DoggoEx.dto.WeatherDto;
import com.Doggo.DoggoEx.dto.WeatherEnum;
import com.Doggo.DoggoEx.repository.WeatherRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.*;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;


    @Value("${api.weatherLocation.url}")
    private String weatherLocationUrl;
    @Value("${api.temperature7days.url}")
    private String temperature7daysUrl;

    @Value("${api.weatherApi.key}")
    private String weatherApiKey;

    @Autowired
    public WeatherService() {
        this.restTemplate = new RestTemplate();
    }

    // 기상청에서 지역별 코드를 구해오는 메서드
    public Map<String, String> getWeatherLocation() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("authKey", weatherApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                weatherLocationUrl, HttpMethod.GET, entity, String.class);
        // 줄바꿈 기준으로 index에 집어넣는다
        String[] lines = response.getBody().split("\n");
        Map<String, String> locationAndRegCode = new HashMap<>();

        for (String line : lines) {
            // 혹시 모를 2칸이상의 공백을 대비해 \\s+ 정규 표현식 사용
            //  공백이 아무리 길어도 하나의 공백으로 취급한다
            String[] targets = line.split("\\s+");
            if (targets.length >= 5) {
                String regId = targets[0];
                String regName = targets[4];
                locationAndRegCode.put(regName, regId);
            }
        }
//        System.out.println(locationAndRegCode);
        return locationAndRegCode;
    }
    // 가져온 지역별 코드를 활용해서 중기별 최저온도 , 최고온도를 구하는 메서드 ,
    public Map<String, List<List<String>>> getWeatherTemp(Map<String, String> locationAndRegCode) {
        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();
        // 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        // 포맷 적용
        String Today = now.format(formatter);
        // 내일과 7일 후 날짜 계산
        int tomorrow = Integer.parseInt(Today) + 1;
        int sevenDaysAfter = Integer.parseInt(Today) + 7;

        // HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("authKey", weatherApiKey); // 이 부분은 실제 weatherApiKey 변수 값에 따라 달라집니다.
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 결과를 저장할 맵
        Map<String, List<List<String>>> weeklyTemperature = new HashMap<>();

        // WeatherEnum의 모든 값을 순회
        for (WeatherEnum city : WeatherEnum.values()) {
            // 해당 도시의 코드 가져오기 , city의 값과 일치하는 locationAndRegCode의 key를 매핑
            String regCode = locationAndRegCode.get(city.name());
//            System.out.println(regCode);
            // UriComponentsBuilder를 사용하여 URL 구성
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(temperature7daysUrl)
                    .queryParam("reg", regCode) // 도시 코드를 쿼리 파라미터로 설정
                    .queryParam("tmef1", tomorrow)
                    .queryParam("tmef2", sevenDaysAfter)
                    .queryParam("help", 0);

            // HttpEntity 생성
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // RestTemplate을 사용하여 HTTP 요청 수행
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.toUriString(), HttpMethod.GET, entity, String.class);

            String[] lines = response.getBody().split("\n");

            String[] filterLines = Arrays.copyOfRange(lines, 2, lines.length - 1);



            List<List<String>> cityWeather = new ArrayList<>();

            for (String index : filterLines) {
                String[] targets = index.split("\\s+");

                List<String> dailyTemp = new ArrayList<>();

                String date = targets[2].substring(0,8); // 날짜 추출
                String minTemp = targets[6]; // 최저날씨
                String maxTemp = targets[7]; // 최고날씨

                dailyTemp.add(date);
                dailyTemp.add(minTemp);
                dailyTemp.add(maxTemp);
                cityWeather.add(dailyTemp);

            }
            weeklyTemperature.put(city.name(), cityWeather);
        }

        return weeklyTemperature;
    }


//    public Map<String, List<List<String>>> getWeatherCondition(Map<String, String> locationAndRegCode) {
//
//    }


}