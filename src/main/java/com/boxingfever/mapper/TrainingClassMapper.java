package com.boxingfever.mapper;

import com.boxingfever.api.classes.TrainingClassDto;
import com.boxingfever.entity.Trainer;
import com.boxingfever.entity.TrainingClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TrainingClassMapper {

    TrainingClassMapper INSTANCE = Mappers.getMapper(TrainingClassMapper.class);

    @Mapping(source = "className", target = "name")
    @Mapping(source = "trainers", target = "trainersIds", qualifiedByName = "trainerToId")
    TrainingClassDto toDto(TrainingClass trainingClass);

    List<TrainingClassDto> toDtoList(List<TrainingClass> trainingClasses);

    @Named("trainerToId")
    default Long trainerToId(Trainer trainer) {
        return trainer.getId();
    }
}