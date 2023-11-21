package com.boxingfever.service.impl;

import com.boxingfever.entity.Trainer;
import com.boxingfever.entity.TrainingClass;
import com.boxingfever.repository.ClassRepository;
import com.boxingfever.service.ClassService;
import com.boxingfever.service.NewClassRequest;
import com.boxingfever.service.UpdateClassRequest;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    ClassRepository classRepository;


    @Override
    public void createAndSaveClass(NewClassRequest newClassRequest) {
        validateClassRequest(newClassRequest);
        TrainingClass trainingClass = TrainingClass.builder()
                .className(newClassRequest.getClassName())
                .category(newClassRequest.getCategory())
                .description(newClassRequest.getDescription())
                .trainers((Set<Trainer>) newClassRequest.getTrainers())
                .durationInMinutes(newClassRequest.getDurationInMinutes())
                .build();
        classRepository.saveAndFlush(trainingClass);
    }

    @Override
    public void editClass(Long classID, UpdateClassRequest updateClassRequest) {
        // Retrieve the existing class from the database
        TrainingClass existingClass = classRepository.findById(classID)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        // Update the existing class with the new information
        existingClass.setClassName(updateClassRequest.getNewClassName());
        existingClass.setCategory(updateClassRequest.getCategory());
        existingClass.setDescription(updateClassRequest.getDescription());
        existingClass.setPlace(updateClassRequest.getPlace());
        existingClass.setDurationInMinutes(updateClassRequest.getDurationInMinutes());


        // Save the updated class
        classRepository.save(existingClass);
    }

    @Override
    public void deleteClass(Long classId) {
        TrainingClass existingClass = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        classRepository.delete(existingClass);
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
