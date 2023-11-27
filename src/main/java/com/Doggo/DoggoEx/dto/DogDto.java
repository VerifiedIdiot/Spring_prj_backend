package com.Doggo.DoggoEx.dto;

import com.Doggo.DoggoEx.entity.Dog;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
// JsonProperty는 개발자가 해당 제이슨 객체의 대응되는 dto 변수를 직접 명시하여
// 서비스영역에서 빈 배열이나 빈 맵에 제이슨 객체를 넣었다가 dto에 일일이 전달하는 공정을 줄여줌

// JsonNaming 어노테이션을 사용하는 경우 , JSON 객체의 스네이크 표기법에 맞춰 dto 변수명을 카멜 케이스로 지정해 놓으면
// 자바가 알아서 스네이크 <> 카멜표기로 일치하는것을 판별하여 매핑해줌 !!
// 그러나 직관적이게 내비두고 싶어서 JsonProperty를 사용하였음
public class DogDto {
    @JsonIgnore
    private long id; // DB에서 사용할 ID, JSON 매핑 제외

    @JsonProperty("name")
    private String name;

    @JsonProperty("image_link")
    private String imageLink;

    @JsonProperty("good_with_children")
    private int goodWithChildren;

    @JsonProperty("good_with_other_dogs")
    private int goodWithOtherDogs;

    @JsonProperty("shedding")
    private int shedding;

    @JsonProperty("grooming")
    private int grooming;

    @JsonProperty("drooling")
    private int drooling;

    @JsonProperty("coat_length")
    private int coatLength;

    @JsonProperty("good_with_strangers")
    private int goodWithStrangers;

    @JsonProperty("playfulness")
    private int playfulness;

    @JsonProperty("protectiveness")
    private int protectiveness;

    @JsonProperty("trainability")
    private int trainability;

    @JsonProperty("energy")
    private int energy;

    @JsonProperty("barking")
    private int barking;

    @JsonProperty("min_life_expectancy")
    private int minLifeExpectancy;

    @JsonProperty("max_life_expectancy")
    private int maxLifeExpectancy;

    @JsonProperty("max_height_male")
    private int maxHeightMale;

    @JsonProperty("max_height_female")
    private int maxHeightFemale;

    @JsonProperty("max_weight_male")
    private int maxWeightMale;

    @JsonProperty("max_weight_female")
    private int maxWeightFemale;

    @JsonProperty("min_height_male")
    private int minHeightMale;

    @JsonProperty("min_height_female")
    private int minHeightFemale;

    @JsonProperty("min_weight_male")
    private int minWeightMale;

    @JsonProperty("min_weight_female")
    private int minWeightFemale;




}
