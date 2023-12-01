package com.boxingfever.controller;

import com.boxingfever.api.classes.NewClassRequest;
import com.boxingfever.api.classes.TrainingClassDto;
import com.boxingfever.api.classes.UpdateClassRequest;
import com.boxingfever.entity.TrainingClass;
import com.boxingfever.mapper.TrainingClassMapper;
import com.boxingfever.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/class/")
public class ClassController {

    @Autowired
    ClassService classService;

    @GetMapping("{id}")
    public ResponseEntity<TrainingClass> getClass(@PathVariable Long id) {
        TrainingClass response = classService.getClassInfo(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("all")
    public List<TrainingClassDto> getAllTrainingClasses() {
        List<TrainingClass> trainingClasses = classService.getClasses();
        return TrainingClassMapper.INSTANCE.toDtoList(trainingClasses);
    }

        @PostMapping()
    public ResponseEntity<String> createTrainingClass(@RequestBody NewClassRequest request) {
        String response = classService.createClass(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateTrainingClass(@PathVariable Long id, @RequestBody UpdateClassRequest request) { // TODO just add id to the request
        String response = classService.updateClass(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTrainingClass(@PathVariable Long id) {
        classService.deleteClass(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
