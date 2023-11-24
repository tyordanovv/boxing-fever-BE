package com.boxingfever.controller;

import com.boxingfever.api.trainer.CreateTrainerRequest;
import com.boxingfever.api.user.UserInfoDto;
import com.boxingfever.entity.Trainer;
import com.boxingfever.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/trainer/")
public class TrainerController {
    private final TrainerService trainerService;

    public TrainerController(
            TrainerService trainerService
    ){
        this.trainerService = trainerService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Trainer> getTrainer(@PathVariable Long id){
        Trainer response = trainerService.getTrainerInfo(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = {"all"})
    public ResponseEntity<List<Trainer>> getTrainers(){
        List<Trainer> response = trainerService.getAllTrainers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<String> createTrainer(@RequestBody CreateTrainerRequest request){
        String response = trainerService.createTrainer(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        trainerService.deleteTrainer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
