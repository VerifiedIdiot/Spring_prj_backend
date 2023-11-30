package com.Doggo.DoggoEx.dto;


import com.Doggo.DoggoEx.dto.View.Views;
import com.Doggo.DoggoEx.entity.Cat;
import com.Doggo.DoggoEx.entity.Weather;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.util.Map;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherDto {

    // @JsonIgnore 원래는 제외 대상이었는데 로직변경으로 변동됨
    private long id;

    // JsonView로 명시해 놓으면 RestController 측 메서드에 해당 어노테이션을
    // 명시해놓을시 해당 필드만 Json 직렬화 처리됨 개꿀띠

    private Map<String, String> region;

    private int weatherDate;

    private int minTemperature;

    private int maxTemperature;

    private int weatherCondition;

    private int rainPercent;



    public Weather toEntity() {
        return Weather.builder()
                .region(this.getRegion())
                .weatherDate(this.getWeatherDate())
                .minTemperature((this.getMinTemperature()))
                .maxTemperature(this.getMaxTemperature())
                .weatherCondition(this.getWeatherCondition())
                .rainPercent(this.getRainPercent())
                .build();
    }
}
