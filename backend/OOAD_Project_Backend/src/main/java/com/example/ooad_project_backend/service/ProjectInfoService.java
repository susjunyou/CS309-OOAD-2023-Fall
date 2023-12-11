package com.example.ooad_project_backend.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.PostInfo;
import com.example.ooad_project_backend.entity.ProjectInfo;
import com.example.ooad_project_backend.entity.ReplyInfo;
import com.example.ooad_project_backend.enums.UserType;

import java.sql.Date;
import java.util.List;

public interface ProjectInfoService {
    boolean addProject(String projectTitle, String projectDescription, Date projectDeadline, String projectStatus,
                       Integer maxScore, Double proportion, String releaser, UserType releaserType, Integer courseId,Date projectStartDate,Integer maxPeopleInTeam);
    boolean deleteProject(Integer projectId);
    boolean updateProject(Integer projectId, String projectTitle, String projectDescription, Date projectDeadline, String projectStatus,
                          Integer maxScore, Double proportion, String releaser, UserType releaserType, Integer courseId, Date projectStartDate, Integer maxPeopleInTeam);
    List<ProjectInfo> getProjectByCourseId(Integer courseId);

}
