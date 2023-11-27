package com.Doggo.DoggoEx.controller;

import com.Doggo.DoggoEx.dto.DogDto;
import com.Doggo.DoggoEx.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dogs")
public class DogController {

    private final DogService dogService;

    @Autowired
    public DogController(DogService dogService) {
        this.dogService = dogService;
    }
    // 필요시에만 INSERT 하시오 , API 허용량 무셔
    @PostMapping("/insert")
    public ResponseEntity<?> insertDogs() {
        try {
            dogService.insertDogs();
            return ResponseEntity.ok("애견도감 테이블 insert");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

//    @GetMapping("/{name}")
//    public ResponseEntity<DogDto> getDogByName(@PathVariable String name) {
//        try {
//            DogDto dogDto = dogService.getDogByName(name);
//            return ResponseEntity.ok(dogDto);
//        } catch (Exception e) {
//            // 데이터가 조회되지 않았을때 발생하는 에러를 처리하기 위한 예외처리
//            return ResponseEntity.notFound().build();
//        }
//    }


}
