package com.example.ooad_project_backend.service;

import com.example.ooad_project_backend.entity.ProjectInfo;
import com.example.ooad_project_backend.enums.UserType;

import java.sql.Date;
import java.util.List;

public interface ProjectInfoService {
    boolean addProject(String projectTitle, String projectDescription, Date projectDeadline, String projectStatus,
                       Integer maxScore, Double proportion, Integer releaser, UserType releaserType, Integer courseId,Date projectStartDate,Integer maxPeopleInTeam,Integer fileId,Date TeamDeadline);
    boolean deleteProject(Integer projectId);
    boolean updateProject(Integer projectId, String projectTitle, String projectDescription, Date projectDeadline, String projectStatus,
                          Integer maxScore, Double proportion, Integer releaser, UserType releaserType, Integer courseId, Date projectStartDate, Integer maxPeopleInTeam,Integer fileId,Date TeamDeadline);
    List<ProjectInfo> getProjectByCourseId(Integer courseId);

}
