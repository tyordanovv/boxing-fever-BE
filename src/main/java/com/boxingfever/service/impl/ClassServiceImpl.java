package com.boxingfever.service.impl;

import com.boxingfever.entity.Trainer;
import com.boxingfever.entity.TrainingClass;
import com.boxingfever.exception.APIException;
import com.boxingfever.exception.TrainerNotFoundException;
import com.boxingfever.repository.ClassRepository;
import com.boxingfever.repository.TrainerRepository;
import com.boxingfever.service.ClassService;
import com.boxingfever.api.classes.NewClassRequest;
import com.boxingfever.api.classes.UpdateClassRequest;
import com.boxingfever.types.TrainingClassEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static io.micrometer.common.util.StringUtils.isBlank;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    ClassRepository classRepository;

    @Autowired
    TrainerRepository trainerRepository;

    @Override
    public String createClass(NewClassRequest newClassRequest) {
        validateClassRequest(newClassRequest);
        TrainingClass trainingClass = TrainingClass.builder()
                .className(newClassRequest.getClassName())
                .category(TrainingClassEnums.valueOf(newClassRequest.getCategory()))
                .description(newClassRequest.getDescription())
                .trainers(newClassRequest.getTrainers())
                .durationInMinutes(newClassRequest.getDurationInMinutes())
                .build();
        classRepository.saveAndFlush(trainingClass);
        return "Training Class has been successfully created!";
    }

    @Override
    public String updateClass(Long classID, UpdateClassRequest updateClassRequest) {
        // Retrieve the existing class from the database
        TrainingClass existingClass = classRepository.findById(classID)
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "Class with id = " + classID + " was not found!"));

        validateUpdateClassRequest(updateClassRequest);
        existingClass.setClassName(updateClassRequest.getNewClassName());
        existingClass.setCategory(TrainingClassEnums.valueOf(String.valueOf(updateClassRequest.getCategory())));
        existingClass.setDescription(updateClassRequest.getDescription());
        existingClass.setTrainers(updateClassRequest.getTrainers());
        existingClass.setDurationInMinutes(updateClassRequest.getDurationInMinutes());


        // Save the updated class
        classRepository.saveAndFlush(existingClass);
        return "Successfully updated the training class!";
    }

    @Override
    public void deleteClass(Long classId) {
        classRepository.deleteById(classId);
    }

    @Override
    public TrainingClass getClassByName(String name) {
        return classRepository.findByClassName(name)
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "Class with name " + name + " was not found!"));
    }

    @Override
    public List<TrainingClass> getClasses() {
        return classRepository.findAll();
    }

    @Override
    public TrainingClass getClassInfo(Long id) {
        return classRepository.findById(id).orElseThrow(() -> new APIException(
           HttpStatus.BAD_REQUEST, "Training Class with id " + id + " is not found!"
        ));
    }

    private void validateClassRequest(NewClassRequest newClassRequest) {
        // Validate class name
        if (isBlank(newClassRequest.getClassName())) {
            throw new IllegalArgumentException("Class name cannot be blank");
        }

        // Validate category
        try {
            TrainingClassEnums.valueOf(String.valueOf(newClassRequest.getCategory()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid category: " + newClassRequest.getCategory(), e);
        }

        // Validate description
        if (isBlank(newClassRequest.getDescription())) {
            throw new IllegalArgumentException("Description cannot be blank");
        }

        // Validate place
        if (newClassRequest.getPlace().isEmpty()) {
            newClassRequest.setPlace("Wielandgasse 11");
        }

        // Validate duration in minutes
        Integer durationInMinutes = newClassRequest.getDurationInMinutes();

        if (durationInMinutes == null || durationInMinutes <= 0) {
            throw new IllegalArgumentException("Duration in minutes must be a positive integer");
        }

        // Validate trainers
        if (newClassRequest.getTrainers() == null || newClassRequest.getTrainers().isEmpty()) {
            throw new IllegalArgumentException("Trainers list cannot be empty");
        }

        for (Long trainerId : newClassRequest.getTrainers()) {
            Optional<Trainer> trainer = trainerRepository.findById(trainerId);
            if (!trainer.isPresent()) {
                throw new TrainerNotFoundException(trainerId); // Handle invalid trainer ID
            }
        }
    }

    private void validateUpdateClassRequest(UpdateClassRequest updateClassRequest) {
        // Validate class name
        if (isBlank(updateClassRequest.getNewClassName())) {
            throw new IllegalArgumentException("Class name cannot be blank");
        }

        // Validate category
        try {
            TrainingClassEnums.valueOf(String.valueOf(updateClassRequest.getCategory()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid category: " + updateClassRequest.getCategory(), e);
        }

        // Validate description
        if (isBlank(updateClassRequest.getDescription())) {
            throw new IllegalArgumentException("Description cannot be blank");
        }

        // Validate place
        if (updateClassRequest.getPlace().isEmpty() || updateClassRequest.getPlace() == null) {
            updateClassRequest.setPlace("Wielandgasse 11");
        }

        // Validate duration in minutes
        Integer durationInMinutes = updateClassRequest.getDurationInMinutes();

        if (durationInMinutes == null || durationInMinutes <= 0) {
            throw new IllegalArgumentException("Duration in minutes must be a positive integer");
        }

        // Validate trainers
        if (updateClassRequest.getTrainers() == null || updateClassRequest.getTrainers().isEmpty()) {
            throw new IllegalArgumentException("Trainers list cannot be empty");
        }

        for (Long trainerId : updateClassRequest.getTrainers()) {
            Optional<Trainer> trainer = trainerRepository.findById(trainerId);
            if (!trainer.isPresent()) {
                throw new TrainerNotFoundException(trainerId); // Handle invalid trainer ID
            }
        }
    }
}
