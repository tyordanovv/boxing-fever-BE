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
import java.util.Set;

import static io.micrometer.common.util.StringUtils.isBlank;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    ClassRepository classRepository;

    @Autowired
    TrainerRepository trainerRepository;

    @Override
    public String createClass(NewClassRequest request) {
        validateClassRequest(request);

        List<Trainer> trainers = trainerRepository.findAllById(request.getTrainers());


        TrainingClass trainingClass = TrainingClass.builder()
                .className(request.getClassName())
                .category(TrainingClassEnums.valueOf(request.getCategory()))
                .description(request.getDescription())
                .trainers((Set<Trainer>) trainers)
                .durationInMinutes(request.getDurationInMinutes())
                .build();
        classRepository.saveAndFlush(trainingClass);
        return "Training Class has been successfully created!";
    }

    @Override
    public String updateClass(Long classID, UpdateClassRequest updateClassRequest) {
        // Retrieve the existing class from the database
        TrainingClass existingClass = classRepository.findById(classID)
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "Class with id = " + classID + " was not found!"));

        List<Trainer> trainers = trainerRepository.findAllById(updateClassRequest.getTrainers()); // TODO INFO Trainers should be fetched from the database and not passed in the JSON or saved as Long

        validateUpdateClassRequest(updateClassRequest);
        existingClass.setClassName(updateClassRequest.getNewClassName());
        existingClass.setCategory(TrainingClassEnums.valueOf(String.valueOf(updateClassRequest.getCategory())));
        existingClass.setDescription(updateClassRequest.getDescription());
        existingClass.setTrainers((Set<Trainer>) trainers); // TODO not sure if this casting works, should be tested
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
        int durationInMinutes = newClassRequest.getDurationInMinutes();

        if (durationInMinutes <= 0) {
            throw new IllegalArgumentException("Duration in minutes must be a positive integer");
        }

        // Validate trainers
        if (newClassRequest.getTrainers() == null || newClassRequest.getTrainers().isEmpty()) {
            throw new IllegalArgumentException("Trainers list cannot be empty");
        }

        for (Long trainerId : newClassRequest.getTrainers()) {
            Optional<Trainer> trainer = trainerRepository.findById(trainerId);
            if (trainer.isEmpty()) {
                throw new TrainerNotFoundException(trainerId); // Handle invalid trainer ID
            }
        }
    }

    //TODO idea was if attribute is empty just dont set it to ""
    //now you throw a message if user leaves smth empty
    //this works too but could be improved
    private void validateUpdateClassRequest(UpdateClassRequest updateClassRequest) {
        // Validate class name
        if (isBlank(updateClassRequest.getNewClassName())) {
            throw new IllegalArgumentException("Class name cannot be blank"); // TODO IllegalArgumentException is very bad practice for web serves
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
        if (updateClassRequest.getPlace().isEmpty()) {
            updateClassRequest.setPlace("Wielandgasse 11");
        }

        // Validate duration in minutes
        int durationInMinutes = updateClassRequest.getDurationInMinutes();

        if (durationInMinutes <= 0) {
            throw new IllegalArgumentException("Duration in minutes must be a positive integer");
        }

        // Validate trainers
        if (updateClassRequest.getTrainers() == null || updateClassRequest.getTrainers().isEmpty()) {
            throw new IllegalArgumentException("Trainers list cannot be empty");
        }

        for (Long trainerId : updateClassRequest.getTrainers()) {
            Optional<Trainer> trainer = trainerRepository.findById(trainerId);
            if (trainer.isEmpty()) {
                throw new TrainerNotFoundException(trainerId); // TODO use ResourceNotFoundException or APIException
            }
        }
    }
}
