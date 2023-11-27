package com.Doggo.DoggoEx.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

@Entity
@Table(name = "CAT_TB")
@Getter @Setter
@NoArgsConstructor
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



}
