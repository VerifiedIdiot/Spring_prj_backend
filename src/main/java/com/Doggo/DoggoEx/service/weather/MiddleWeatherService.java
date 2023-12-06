package com.Doggo.DoggoEx.service.weather;

import com.Doggo.DoggoEx.enums.CityEnum;
import com.Doggo.DoggoEx.enums.RegionEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MiddleWeatherService extends WeatherAbstract {

    @Value("${api.weatherLocation.url}")
    private String weatherLocationUrl;
    @Value("${api.temperature7days.url}")
    private String temperature7daysUrl;

    @Value("${api.condition7days.url}")
    private String condition7daysUrl;

    @Value("${api.weatherApi.key}")
    private String weatherApiKey;

    public Map<String, String> getLocationCode() {
        HttpHeaders headers = createHeaders(weatherApiKey);
        String response = sendGetRequest(weatherLocationUrl, Collections.emptyMap(), headers);

        Map<String, String> locationCode = new HashMap<>();
        String[] lines = response.split("\n");
        for (String line : lines) {
            String[] parts = line.split("\\s+");
            if (parts.length >= 5) {
                String regId = parts[0];
                String regName = parts[4];
                locationCode.put(regName, regId);
//                System.out.println(regName+ " : " + regId);
            }
        }
//        System.out.println(locationAndRegCode);
        return locationCode;
    }

    public Map<String, List<List<String>>> getMiddleTemp(Map<String, String> locationCode) {
        HttpHeaders headers = createHeaders(weatherApiKey);
        Map<String, Integer> dateParams = middleDaysParam();

        Map<String, List<List<String>>> middleTemp = new HashMap<>();

        for (CityEnum city : CityEnum.values()) {
            String regCode = locationCode.get(city.name());

            Map<String, String> queryParams = middleQueryParams(regCode, dateParams);

            String response = sendGetRequest(temperature7daysUrl, queryParams, headers);


            String[] lines = response.split("\n");
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
            middleTemp.put(city.name(), cityWeather);
        }

        return middleTemp;
    }

    public Map<String, List<List<String>>> getMiddleCondition(Map<String, String> locationCode) {
        HttpHeaders headers = createHeaders(weatherApiKey);
        Map<String, Integer> dateParams = middleDaysParam();

        Map<String, List<List<String>>> middleCondition = new HashMap<>();

        for (RegionEnum region : RegionEnum.values()) {

            String regCode = locationCode.get(region.getRegionName());

            Map<String, String> queryParams = middleQueryParams(regCode, dateParams);

            String response = sendGetRequest(condition7daysUrl, queryParams, headers);

            String[] lines = response.split("\n");
//
//          System.out.println(Arrays.toString(lines));
            String[] filterLines = Arrays.copyOfRange(lines, 2, lines.length - 1);

//            System.out.println(Arrays.toString(filterLines));

            List<List<String>> regionCondition = new ArrayList<>();

            for (String index : filterLines) {
                Pattern pattern = Pattern.compile("[^\\s\"]+|\"([^\"]*)\"");
                Matcher matcher = pattern.matcher(index);

                List<String> fields = new ArrayList<>();
                while (matcher.find()) {
                    if (matcher.group(1) != null) {
                        // 큰따옴표로 묶인 부분을 추가합니다.
                        fields.add(matcher.group(1));
                    } else {
                        // 큰따옴표로 묶이지 않은 부분을 추가합니다.
                        fields.add(matcher.group());
                    }
                }

                // 날짜, 날씨 조건, 강수량 필드를 추출합니다.
                // 큰따옴표로 묶인 부분을 고려하여 적절한 인덱스 값을 사용합니다.
                String date = fields.get(2); // 날짜 추출
                String condition = fields.get(fields.size() - 2); // 날씨 조건
                String rain = fields.get(fields.size() - 1); // 강수량

                List<String> dailyCondition = new ArrayList<>();
                dailyCondition.add(date);
                dailyCondition.add(condition);
                dailyCondition.add(rain);

                regionCondition.add(dailyCondition);
            }
            middleCondition.put(region.name(), regionCondition);

        }

        return middleCondition;
    }

    public Map<String, List<List<String>>> getCompleteMiddle(Map<String, List<List<String>>> middleTemp,
                                                             Map<String, List<List<String>>> middleCondition) {
        // 최종적으로 통합된 날씨 데이터를 저장할 맵을 생성합니다.
        Map<String, List<List<String>>> completeMiddleWeather = new HashMap<>();

        // 지역별 도시 매핑 정보를 가져옵니다. 이 맵은 각 지역과 그 지역에 속한 도시들의 리스트를 매핑합니다.
        Map<String, List<String>> regionToCities = EnumMapper.getRegionToCitiesMap();

        // middleTemp 맵의 각 키(도시 이름)에 대해 반복합니다.
        // middleTemp의 Key 값은 수원 : [[날짜,최저,최고],[날짜,최저,최고],[날짜,최저,최
        for (String city : middleTemp.keySet()) {
            // 특정 도시의 온도 데이터 리스트를 가져옵니다. 이 데이터는 "날짜, 최저온도, 최고온도"의 형식으로 구성된 리스트의 리스트입니다.
            List<List<String>> tempData = middleTemp.get(city);

            // 도시별로 날짜를 키로 하고 날씨 데이터를 값으로 하는 LinkedHashMap을 생성합니다.
            // LinkedHashMap은 삽입된 순서대로 키와 값을 유지합니다.
            Map<String, List<String>> cityWeatherMap = new LinkedHashMap<>();

            // 각 도시의 온도 데이터를 순회합니다.
            for (List<String> dailyTemp : tempData) {
                // 각 항목에서 날짜를 추출합니다. 날짜는 YYYYMMDD 형식입니다.
                String date = dailyTemp.get(0);

                // 날짜, 최저온도, 최고온도를 리스트에 저장하고, 추가적인 날씨 정보(오전/오후 날씨 조건과 강수량)를 위한 빈 문자열을 추가합니다.
                List<String> weatherData = new ArrayList<>(Arrays.asList(date, dailyTemp.get(1), "", "", dailyTemp.get(2), "", ""));
                cityWeatherMap.put(date, weatherData);
            }

            // 해당 도시가 속한 지역의 날씨 조건 데이터를 추가합니다.
            for (Map.Entry<String, List<String>> regionEntry : regionToCities.entrySet()) {
                // 현재 반복중인 지역 이름을 가져옵니다.
                String region = regionEntry.getKey();
                // 현재 지역에 속한 도시 리스트를 가져옵니다.
                List<String> cities = regionEntry.getValue();

                // 현재 처리 중인 도시가 해당 지역에 속하는 경우, 해당 지역의 날씨 조건 데이터를 처리합니다.
                if (cities.contains(city)) {
                    // 현재 지역의 날씨 조건 데이터를 가져옵니다. 이 데이터는 "날짜와 시간, 날씨 조건, 강수량"의 형식으로 구성된 리스트의 리스트입니다.
                    List<List<String>> conditionData = middleCondition.get(region);

                    if (conditionData != null) {
                        // 각 날씨 조건 데이터에 대해 반복합니다.
                        for (List<String> dailyCondition : conditionData) {
                            // 날짜와 시간을 추출합니다. 이 값은 YYYYMMDDHHMM 형식입니다.
                            String dateTime = dailyCondition.get(0);
                            // 날짜 부분만 추출합니다 (YYYYMMDD 형식).
                            String date = dateTime.substring(0, 8);
                            // 시간 부분을 추출합니다 (HHMM 형식).
                            String time = dateTime.substring(8, 12);

                            // cityWeatherMap에 해당 날짜의 데이터가 이미 있는 경우, 오전/오후 데이터를 추가합니다.
                            if (cityWeatherMap.containsKey(date)) {
                                List<String> dayAndNightCondition = cityWeatherMap.get(date);

                                // 오전 데이터 (0000 시) 또는 오후 데이터 (1200 시)에 따라 날씨 조건과 강수량을 추가합니다.
                                if (time.equals("0000")) {
                                    // 오전 날씨 조건과 강수량을 설정합니다.
                                    dayAndNightCondition.set(2, dailyCondition.get(2)); // 오전 강수량
                                    dayAndNightCondition.set(3, dailyCondition.get(1)); // 오전 날씨 조건
                                } else if (time.equals("1200")) {
                                    // 오후 날씨 조건과 강수량을 설정합니다.

                                    dayAndNightCondition.set(5, dailyCondition.get(2)); // 오후 강수량
                                    dayAndNightCondition.set(6, dailyCondition.get(1)); // 오후 날씨 조건
                                }
                            }
                        }
                    }
                    break; // 해당 지역에 대한 처리가 완료되면, 반복문을 빠져나와 다음 도시를 처리합니다.
                }
            }
            // 최종적으로 통합된 도시별 날씨 데이터를 completeMiddleWeather 맵에 추가합니다.
            completeMiddleWeather.put(city, new ArrayList<>(cityWeatherMap.values()));
        }

        return completeMiddleWeather;
    }



}
