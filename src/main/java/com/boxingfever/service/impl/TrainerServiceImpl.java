package com.boxingfever.service.impl;

import com.boxingfever.api.trainer.CreateTrainerRequest;
import com.boxingfever.entity.Trainer;
import com.boxingfever.exception.APIException;
import com.boxingfever.repository.TrainerRepository;
import com.boxingfever.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepository;

    public TrainerServiceImpl (
            TrainerRepository trainerRepository
    ){
        this.trainerRepository = trainerRepository;
    }
    @Override
    public Trainer getTrainerInfo(Long id) {
        return trainerRepository
                .findById(id)
                .orElseThrow(() -> new APIException(
                        HttpStatus.BAD_REQUEST,
                        "Trainer with id = " + id + " is not found!"
                ));
    }

    @Override
    public Set<Trainer> getTrainersByIds(List<Long> trainerIds) {
        return new HashSet<>(trainerRepository.findAllById(trainerIds));
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    @Override
    public void deleteTrainer(Long id) {
        trainerRepository.deleteById(id);
    }

    @Override
    public String createTrainer(CreateTrainerRequest request) {
        if (!isValid(request)) {
            Trainer trainer = new Trainer();
            trainer.setName(request.name());
            trainer.setEmail(request.email());
            trainerRepository.save(trainer);
            return "Trainer was successfully created!";
        } else {
            throw new APIException(HttpStatus.BAD_REQUEST, "Request body is invalid! No empty params are allowed!");
        }
    }

    private boolean isValid(CreateTrainerRequest request) {
        return request.name().equals("") || request.email().equals("");
    }
}
