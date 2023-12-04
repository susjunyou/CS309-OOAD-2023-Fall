package com.example.ooad_project_backend.service;

import com.example.ooad_project_backend.entity.AssignmentInfo;

import java.util.List;

public interface AssignmentInfoService {
    List<AssignmentInfo> findAssignmentInfoByCourseId(Integer courseId);
}
