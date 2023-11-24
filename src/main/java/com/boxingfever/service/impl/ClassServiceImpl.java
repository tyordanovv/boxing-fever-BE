package com.boxingfever.service.impl;

import com.boxingfever.entity.Trainer;
import com.boxingfever.entity.TrainingClass;
import com.boxingfever.exception.APIException;
import com.boxingfever.repository.ClassRepository;
import com.boxingfever.service.ClassService;
import com.boxingfever.api.classes.NewClassRequest;
import com.boxingfever.api.classes.UpdateClassRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    ClassRepository classRepository;


    @Override
    public void createClass(NewClassRequest newClassRequest) {
        validateClassRequest(newClassRequest);
        TrainingClass trainingClass = TrainingClass.builder()
                .name(newClassRequest.getClassName())
                .category(newClassRequest.getCategory())
                .description(newClassRequest.getDescription())
                .trainers((Set<Trainer>) newClassRequest.getTrainers())
                //TODO this is not fully correct, first you need to fetch the trainers
                // and then set them in the training class otherwise you can set
                // a corrupted reference to the trainer, which will lead to DB error
                .durationInMinutes(newClassRequest.getDurationInMinutes())
                .build();
        classRepository.save(trainingClass);
    }

    @Override
    public void editClass(Long classID, UpdateClassRequest updateClassRequest) {
        // Retrieve the existing class from the database
        TrainingClass existingClass = classRepository.findById(classID)
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "Class with id = " + classID + " was not found!"));

        // Update the existing class with the new information
        //TODO when you update an existing object check if the set value is not null
        // eg -> if the attribute is not null or ""empty string then set the new value
        // otherwise you may delete an existing value
        existingClass.setName(updateClassRequest.getNewClassName());
        existingClass.setCategory(updateClassRequest.getCategory());
        existingClass.setDescription(updateClassRequest.getDescription());
        existingClass.setPlace(updateClassRequest.getPlace());
        existingClass.setDurationInMinutes(updateClassRequest.getDurationInMinutes());


        // Save the updated class
        classRepository.save(existingClass);
    }

    @Override
    public void deleteClass(Long classId) {
        classRepository.deleteById(classId);
    }

    @Override
    public TrainingClass getClassByName(String name) {
        return classRepository.findByName(name)
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "Class with name " + name + " was not found!"));
    }

    @Override
    public List<TrainingClass> getClasses() {
        return classRepository.findAll();
    }

    private void validateClassRequest(NewClassRequest newClassRequest) {
        if (newClassRequest.getPlace().isEmpty()) {
            newClassRequest.setPlace("Wielandgasse 11");
        }
        if (newClassRequest.getDescription().isEmpty()) {
            throw new RuntimeException("Please enter the discription of the training class");
        }
    }
}
