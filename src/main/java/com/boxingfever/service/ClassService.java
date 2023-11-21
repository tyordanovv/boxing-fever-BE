package com.boxingfever.service;

import com.boxingfever.entity.TrainingClass;

public interface ClassService {

    void createAndSaveClass(NewClassRequest newClassRequest);
    void editClass(Long classID, UpdateClassRequest updateClassRequest);
    void deleteClass(Long classId);
}
