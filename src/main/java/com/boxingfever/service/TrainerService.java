package com.boxingfever.service;

import com.boxingfever.api.trainer.CreateTrainerRequest;
import com.boxingfever.api.trainer.UpdateTrainerRequest;
import com.boxingfever.api.user.UpdateUserRequest;
import com.boxingfever.api.user.UserInfoDto;
import com.boxingfever.entity.Trainer;

import java.util.List;
import java.util.Set;

public interface TrainerService {
    Trainer getTrainerInfo(Long id);
    List<Trainer> getAllTrainers();
    void deleteTrainer(Long id);
    String createTrainer(CreateTrainerRequest request);
    Set<Trainer> getTrainersByIds(List<Long> trainerIds);
    void updateTrainer(UpdateTrainerRequest request);
}
