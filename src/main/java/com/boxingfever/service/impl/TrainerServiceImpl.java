package com.boxingfever.service.impl;

import com.boxingfever.api.trainer.CreateTrainerRequest;
import com.boxingfever.api.trainer.UpdateTrainerRequest;
import com.boxingfever.entity.Trainer;
import com.boxingfever.entity.User;
import com.boxingfever.exception.APIException;
import com.boxingfever.repository.TrainerRepository;
import com.boxingfever.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TrainerServiceImpl implements TrainerService {
    @Autowired TrainerRepository trainerRepository;
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
    public void updateTrainer(UpdateTrainerRequest request) {
        Trainer trainer = trainerRepository.findById(request.id())
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "Trainer " + request.email() + " is not found!"));

        if (!request.email().equals("")){
            trainer.setEmail(request.email());
        }if (!request.name().equals("")){
            trainer.setName(request.name());
        }
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
        if (!isNotValid(request)) {
            Trainer trainer = new Trainer();
            trainer.setName(request.name());
            trainer.setEmail(request.email());
            trainerRepository.save(trainer);
            return "Trainer was successfully created!";
        } else {
            throw new APIException(HttpStatus.BAD_REQUEST, "Request body is invalid! No empty params are allowed!");
        }
    }

    private boolean isNotValid(CreateTrainerRequest request) {
        return request.name().equals("") || request.email().equals("");
    }
}
