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
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
    public TrainingClass createClass(NewClassRequest request) {
        validateClassRequest(request);

        List<Trainer> trainers = trainerRepository.findAllById(request.getTrainers());
        if (trainers.isEmpty()) {
            throw new ApplicationContextException("There are no trainers available");
        }
        Set<Trainer> trainersSet = new HashSet<>(trainers);


        TrainingClass trainingClass = TrainingClass.builder()
                .className(request.getClassName())
                .category(TrainingClassEnums.valueOf(request.getCategory()))
                .description(request.getDescription())
                .place(request.getPlace())
                .trainers(trainersSet)
                .durationInMinutes(request.getDurationInMinutes())
                .build();
        classRepository.saveAndFlush(trainingClass);
        return trainingClass;
    }

    @Override
    public TrainingClass updateClass(Long classID, UpdateClassRequest updateClassRequest) {
        // Retrieve the existing class from the database
        TrainingClass existingClass = classRepository.findById(classID)
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "Class with id = " + classID + " was not found!"));

        List<Trainer> trainers = trainerRepository.findAllById(updateClassRequest.getTrainers());
        if (trainers.isEmpty()) {
            throw new ApplicationContextException("There are no trainers available");
        }
        Set<Trainer> trainersSet = new HashSet<>(trainers);

        existingClass.setClassName(updateClassRequest.getNewClassName());
        existingClass.setCategory(TrainingClassEnums.valueOf(String.valueOf(updateClassRequest.getCategory())));
        existingClass.setDescription(updateClassRequest.getDescription());
        existingClass.setPlace(updateClassRequest.getPlace());
        existingClass.setTrainers(trainersSet); // TODO not sure if this casting works, should be tested
        existingClass.setDurationInMinutes(updateClassRequest.getDurationInMinutes());


        // Save the updated class
        classRepository.saveAndFlush(existingClass);
        return existingClass;
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
            throw new ApplicationContextException("Class name cannot be blank");
        }

        // Validate category
        try {
            TrainingClassEnums.valueOf(String.valueOf(newClassRequest.getCategory()));
        } catch (IllegalArgumentException e) {
            throw new ApplicationContextException("Invalid category: " + newClassRequest.getCategory(), e);
        }

        // Validate description
        if (isBlank(newClassRequest.getDescription())) {
            throw new ApplicationContextException("Description cannot be blank");
        }

        // Validate place
        if (newClassRequest.getPlace().isEmpty()) {
            newClassRequest.setPlace("Wielandgasse 11");
        }

        // Validate duration in minutes
        int durationInMinutes = newClassRequest.getDurationInMinutes();

        if (durationInMinutes <= 0) {
            throw new ApplicationContextException("Duration in minutes must be a positive integer");
        }

        // Validate trainers
        if (newClassRequest.getTrainers() == null || newClassRequest.getTrainers().isEmpty()) {
            throw new ApplicationContextException("Trainers list cannot be empty");
        }

        for (Long trainerId : newClassRequest.getTrainers()) {
            Optional<Trainer> trainer = trainerRepository.findById(trainerId);
            if (trainer.isEmpty()) {
                throw new TrainerNotFoundException(trainerId); // Handle invalid trainer ID
            }
        }
    }


    private void validateUpdateRequest(UpdateClassRequest updateRequest) {
        // Validate class name
        if (isBlank(updateRequest.getNewClassName())) {
            throw new ApplicationContextException("Class name cannot be blank");
        }

        // Validate category
        try {
            TrainingClassEnums.valueOf(String.valueOf(updateRequest.getCategory()));
        } catch (IllegalArgumentException e) {
            throw new ApplicationContextException("Invalid category: " + updateRequest.getCategory(), e);
        }

        // Validate description
        if (isBlank(updateRequest.getDescription())) {
            throw new ApplicationContextException("Description cannot be blank");
        }

        // Validate place
        if (updateRequest.getPlace().isEmpty()) {
            updateRequest.setPlace("Wielandgasse 11");
        }

        // Validate duration in minutes
        int durationInMinutes = updateRequest.getDurationInMinutes();

        if (durationInMinutes <= 0) {
            throw new ApplicationContextException("Duration in minutes must be a positive integer");
        }

        // Validate trainers
        if (updateRequest.getTrainers() == null || updateRequest.getTrainers().isEmpty()) {
            throw new ApplicationContextException("Trainers list cannot be empty");
        }

        for (Long trainerId : updateRequest.getTrainers()) {
            Optional<Trainer> trainer = trainerRepository.findById(trainerId);
            if (trainer.isEmpty()) {
                throw new TrainerNotFoundException(trainerId); // Handle invalid trainer ID
            }
        }
    }
}
