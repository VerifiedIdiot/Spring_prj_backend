package com.Doggo.DoggoEx.entity;

import com.Doggo.DoggoEx.dto.CatDto;
import lombok.*;


import javax.persistence.*;

@Entity
@Table(name = "CAT_TB")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Cat {
    @Id
    @Column(name = "cat_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cat_seq")
    private Long id;
    @Column(unique = true)
    private String name;

    private String imageLink;

    private String origin;

    private String length;

    private int intelligence;

    private int familyFriendly;

    private int childrenFriendly;

    private int strangerFriendly;

    private int otherPetsFriendly;

    private int shedding;

    private int grooming;

    private int generalHealth;

    private int playfulness;

    private int minWeight;

    private int maxWeight;

    private int minLifeExpectancy;

    private int maxLifeExpectancy;

    public CatDto toDto() {
        return CatDto.builder()
                .id(this.getId())
                .name(this.getName())
                .imageLink(this.getImageLink())
                .origin((this.getOrigin()))
                .length(this.getLength())
                .intelligence(this.getIntelligence())
                .familyFriendly(this.getFamilyFriendly())
                .childrenFriendly(this.getChildrenFriendly())
                .strangerFriendly(this.getStrangerFriendly())
                .otherPetsFriendly(this.getOtherPetsFriendly())
                .shedding(this.getShedding())
                .grooming(this.getGrooming())
                .generalHealth(this.getGeneralHealth())
                .playfulness(this.getPlayfulness())
                .minWeight(this.getMinWeight())
                .maxWeight(this.getMaxWeight())
                .minWeight(this.getMinWeight())
                .maxWeight(this.getMaxWeight())
                .build();
    }

}
