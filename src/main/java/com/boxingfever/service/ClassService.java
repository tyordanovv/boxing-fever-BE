package com.boxingfever.service;

import com.boxingfever.api.classes.NewClassRequest;
import com.boxingfever.api.classes.UpdateClassRequest;
import com.boxingfever.entity.TrainingClass;

import java.util.List;

public interface ClassService {

    String createClass(NewClassRequest newClassRequest);
    String updateClass(Long classID, UpdateClassRequest updateClassRequest);
    void deleteClass(Long classId);
    TrainingClass getClassByName(String name);
    List<TrainingClass> getClasses();
    TrainingClass getClassInfo(Long id);
}
