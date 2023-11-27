package com.Doggo.DoggoEx.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class CatDto {
    @JsonIgnore
    private long id; // DB에서 사용할 ID, JSON 매핑 제외

    @JsonProperty("name")
    private String name;

    @JsonProperty("image_link")
    private String imageLink;

    @JsonProperty("family_friendly")
    private int familyFriendly;

    @JsonProperty("shedding")
    private int shedding;

    @JsonProperty("general_health")
    private int generalHealth;

    @JsonProperty("playfulness")
    private int playfulness;

    @JsonProperty("children_friendly")
    private int childrenFriendly;

    @JsonProperty("grooming")
    private int grooming;

    @JsonProperty("intelligence")
    private int intelligence;

    @JsonProperty("other_pets_friendly")
    private int otherPetsFriendly;

    @JsonProperty("min_weight")
    private int minWeight;

    @JsonProperty("max_weight")
    private int maxWeight;

    @JsonProperty("min_life_expectancy")
    private int minLifeExpectancy;

    @JsonProperty("max_life_expectancy")
    private int maxLifeExpectancy;

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("length")
    private String length;
}
