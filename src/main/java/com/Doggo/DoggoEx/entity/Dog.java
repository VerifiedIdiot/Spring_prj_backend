package com.Doggo.DoggoEx.entity;
import com.Doggo.DoggoEx.dto.DogDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DOG_TB")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Dog {
    @Id
    @Column(name = "dog_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dog_seq")
    private Long id;
    @Column(unique = true)
    private String name;

    private String imageLink;

    private int goodWithChildren;

    private int goodWithOtherDogs;

    private int shedding;

    private int grooming;

    private int drooling;

    private int coatLength;

    private int goodWithStrangers;

    private int playfulness;

    private int protectiveness;

    private int trainability;

    private int energy;

    private int barking;

    private int minLife;

    private int maxLife;

    private int minHeightMale;

    private int maxHeightMale;

    private int minHeightFemale;

    private int maxHeightFemale;

    private int minWeightMale;

    private int maxWeightMale;

    private int minWeightFemale;

    private int maxWeightFemale;

    public static Dog toEntity(DogDto dto) {
        return Dog.builder()
//                .id(dto.getId())
                .name(dto.getName())
                .imageLink(dto.getImageLink())
                .goodWithChildren(dto.getGoodWithChildren())
                .goodWithOtherDogs(dto.getGoodWithOtherDogs())
                .shedding(dto.getShedding())
                .grooming(dto.getGrooming())
                .drooling(dto.getDrooling())
                .coatLength(dto.getCoatLength())
                .goodWithStrangers(dto.getGoodWithStrangers())
                .playfulness(dto.getPlayfulness())
                .protectiveness(dto.getProtectiveness())
                .trainability(dto.getTrainability())
                .energy(dto.getEnergy())
                .barking(dto.getBarking())
                .minLife(dto.getMinLifeExpectancy())
                .maxLife(dto.getMaxLifeExpectancy())
                .maxHeightMale(dto.getMaxHeightMale())
                .maxHeightFemale(dto.getMaxHeightFemale())
                .maxWeightMale(dto.getMaxWeightMale())
                .maxWeightFemale(dto.getMaxWeightFemale())
                .minHeightMale(dto.getMinHeightMale())
                .minHeightFemale(dto.getMinHeightFemale())
                .minWeightMale(dto.getMinWeightMale())
                .minWeightFemale(dto.getMinWeightFemale())
                .build();
    }




//    private Date regDate;
//
//
//
//    @PrePersist // DB에 INSERT 되기 전에 실행되는 메소드
//    public void prePersist() {
//        regDate = new Date();
//    }
}
