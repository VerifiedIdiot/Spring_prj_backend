package com.Doggo.DoggoEx.controller;

import com.Doggo.DoggoEx.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cats")
public class CatController {

    private final CatService catService;

    @Autowired
    public CatController(CatService catService) {
        this.catService = catService;
    }
    // 필요시에만 INSERT 하시오 , API 허용량 무셔
//    @GetMapping("/insert")
//    public ResponseEntity<?> catInsert() {
//        catService.insertCats();
//        return ResponseEntity.ok("DONE BXXCH");
//    }


}
