package com.Doggo.DoggoEx.entity;


import com.Doggo.DoggoEx.dto.MemberDto;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
@ToString
@Table(name = "member_tb")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_profile_seq")
    private Long id;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<PetProfile> petProfiles;
    @Column(unique = true , nullable = false)
    private String memberEmail;
    @Column(nullable = false)
    private String memberPassword;
    private String memberImage;
    @Column(nullable = false)
    private String memberTel;
    @Column(nullable = false)
    private String memberGender;
    @Column(nullable = false)
    private String memberName;
    @Column(nullable = false)
    private LocalDate memberBirth;
    @Column(nullable = false)
    private String memberAddress;
    private LocalDateTime regDate;
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Sale> sales;
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Diary> diarys;
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Quest> quests;


    @PrePersist
    protected void prePersist() {
        regDate = LocalDateTime.now();
    }

    private String memberGrade;

    public MemberDto toDto() {
        return MemberDto.builder()
                .id(this.getId())
                .memberEmail(this.getMemberEmail())
                .memberPassword(this.getMemberPassword())
                .memberImage(this.getMemberImage())
                .memberTel((this.getMemberTel()))
                .memberGender(this.getMemberGender())
                .memberName(this.getMemberName())
                .memberBirth(this.getMemberBirth())
                .memberAddress(this.getMemberAddress())
                .regDate(this.getRegDate())
                .memberGrade(this.getMemberGrade())
                .build();
    }

}