package com.Doggo.DoggoEx.service;

import com.Doggo.DoggoEx.dto.DogDto;
import com.Doggo.DoggoEx.entity.Dog;
import com.Doggo.DoggoEx.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class DogService {

    private final DogRepository dogRepository;
    private final RestTemplate restTemplate;

    @Value("${api.dog.url}")
    private String apiUrl;

    @Value("${api.dog.key}")
    private String apiKey;

    // 생성자를 통한 DogRepository 주입 및 RestTemplate 초기화
    @Autowired
    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
        this.restTemplate = new RestTemplate();
    }







    // 외부 API를 호출하여 데이터를 받아오고, 이를 Dog 엔티티로 변환 후 저장하는 메소드
    public void insertDogs() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        // 개 짖음 level은 1~5 가 존재하고 모든 견종 정보는 각 레벨별로 페이지 끝까지 조회하면 다 가져올 수 있다.
        // 2중 순회 , (1차 for문 개 짖음 1~5 , 이후 while로 페이지마다 조회할 시 빈값이나 , length면 false로 while문 종료
        for (int barking = 1; barking <= 5; barking++) {
            boolean moreData = true;
            int offset = 0;

            while (moreData) {
                String requestUrl = apiUrl + "?barking=" + barking + "&offset=" + offset;
                ResponseEntity<DogDto[]> responseEntity = restTemplate.exchange(
                        requestUrl, HttpMethod.GET, entity, DogDto[].class
                );

                DogDto[] response = responseEntity.getBody();

                if (response == null || response.length == 0) {
                    moreData = false;
                } else {
                    for (DogDto dogDto : response) {
                        Dog dog = mapToDogEntity(dogDto);
                        dogRepository.save(dog);
                        System.out.println("강아지들 : " + dog);
                    }
                    offset += response.length;
                }
            }
        }
    }
    // DogDto 객체를 받아 Dog 엔티티로 매핑하는 메소드



    private Dog mapToDogEntity(DogDto dogDto) {


        Dog dog = new Dog();

        dog.setName(dogDto.getName());
        dog.setImageLink(dogDto.getImageLink());
        dog.setGoodWithChildren(dogDto.getGoodWithChildren());
        dog.setGoodWithOtherDogs(dogDto.getGoodWithOtherDogs());
        dog.setShedding(dogDto.getShedding());
        dog.setGrooming(dogDto.getGrooming());
        dog.setDrooling(dogDto.getDrooling());
        dog.setCoatLength(dogDto.getCoatLength());
        dog.setGoodWithStrangers(dogDto.getGoodWithStrangers());
        dog.setPlayfulness(dogDto.getPlayfulness());
        dog.setProtectiveness(dogDto.getProtectiveness());
        dog.setTrainability(dogDto.getTrainability());
        dog.setEnergy(dogDto.getEnergy());
        dog.setBarking(dogDto.getBarking());
        dog.setMinLife(dogDto.getMinLifeExpectancy());
        dog.setMaxLife(dogDto.getMaxLifeExpectancy());
        dog.setMinHeightMale(dogDto.getMinHeightMale());
        dog.setMaxHeightMale(dogDto.getMaxHeightMale());
        dog.setMinHeightFemale(dogDto.getMinHeightFemale());
        dog.setMaxHeightFemale(dogDto.getMaxHeightFemale());
        dog.setMinWeightMale(dogDto.getMinWeightMale());
        dog.setMaxWeightMale(dogDto.getMaxWeightMale());
        dog.setMinWeightFemale(dogDto.getMinWeightFemale());
        dog.setMaxWeightFemale(dogDto.getMaxWeightFemale());

        return dog;
    }
}
