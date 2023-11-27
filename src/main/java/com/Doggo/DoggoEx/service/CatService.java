package com.Doggo.DoggoEx.service;

import com.Doggo.DoggoEx.dto.CatDto;
import com.Doggo.DoggoEx.entity.Cat;
import com.Doggo.DoggoEx.repository.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CatService {

    private final CatRepository catRepository;
    private final RestTemplate restTemplate;

    @Value("${api.cat.url}")
    private String apiUrl;

    @Value("${api.cat.key}")
    private String apiKey;

    @Autowired
    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
        this.restTemplate = new RestTemplate();
    }

    public void insertCats() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        for (int grooming = 1; grooming <= 5; grooming++) {
            boolean moreData = true;
            int offset = 0;

            while (moreData) {
                String requestUrl = apiUrl + "?grooming=" + grooming + "&offset=" + offset;
                ResponseEntity<CatDto[]> responseEntity = restTemplate.exchange(
                        requestUrl, HttpMethod.GET, entity, CatDto[].class
                );

                CatDto[] response = responseEntity.getBody();

                if (response == null || response.length == 0) {
                    moreData = false;
                } else {
                    for (CatDto catDto : response) {
                        Cat cat = mapToCatEntity(catDto);
                        catRepository.save(cat);
                        System.out.println("Saved Cat: " + cat);
                    }
                    offset += response.length;
                }
            }
        }
    }

    private Cat mapToCatEntity(CatDto catDto) {

        Cat cat = new Cat();

        cat.setName(catDto.getName());
        cat.setImageLink(catDto.getImageLink());
        cat.setFamilyFriendly(catDto.getFamilyFriendly());
        cat.setShedding(catDto.getShedding());
        cat.setGeneralHealth(catDto.getGeneralHealth());
        cat.setPlayfulness(catDto.getPlayfulness());
        cat.setChildrenFriendly(catDto.getChildrenFriendly());
        cat.setGrooming(catDto.getGrooming());
        cat.setIntelligence(catDto.getIntelligence());
        cat.setOtherPetsFriendly(catDto.getOtherPetsFriendly());
        cat.setMinWeight(catDto.getMinWeight());
        cat.setMaxWeight(catDto.getMaxWeight());
        cat.setMinLifeExpectancy(catDto.getMinLifeExpectancy());
        cat.setMaxLifeExpectancy(catDto.getMaxLifeExpectancy());
        cat.setOrigin(catDto.getOrigin());
        cat.setLength(catDto.getLength());

        return cat;
    }
}
