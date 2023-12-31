package com.boxingfever.api.session;

import com.boxingfever.api.trainer.TrainerDto;

import java.util.Date;
import java.util.List;

public record SessionDto(
        Long id,
        Date startHour,
        Date endHour,
        int capacity,
        Date sessionDate,
        List<Long> trainees,
        List<TrainerDto> trainers,
        String className
)
{}
