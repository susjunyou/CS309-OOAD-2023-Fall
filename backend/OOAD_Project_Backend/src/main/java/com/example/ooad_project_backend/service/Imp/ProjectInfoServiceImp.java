package com.example.ooad_project_backend.service.Imp;

import com.example.ooad_project_backend.entity.ProjectInfo;
import com.example.ooad_project_backend.enums.UserType;
import com.example.ooad_project_backend.mapper.ProjectMapper;
import com.example.ooad_project_backend.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ProjectInfoServiceImp implements ProjectInfoService {

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public boolean addProject(String projectTitle, String projectDescription, Date projectDeadline, String projectStatus,
                              Integer maxScore, Double proportion, Integer releaser, UserType releaserType, Integer courseId, Date projectStartDate, Integer maxPeopleInTeam, Integer fileId, Date teamDeadline) {
        return projectMapper.addProject(projectTitle, projectDescription, projectDeadline, projectStatus,
                maxScore, proportion, releaser, releaserType, courseId, projectStartDate, maxPeopleInTeam, fileId, teamDeadline);
    }

    @Override
    public boolean deleteProject(Integer projectId) {
        Integer fileId = projectMapper.getProjectByProjectId(projectId).getFileId();
        if (fileId != null) {
            projectMapper.deleteFileById(fileId);
        }
        return projectMapper.deleteProject(projectId);
    }

    @Override
    public boolean updateProject(Integer projectId, String projectTitle, String projectDescription, Date projectDeadline, String projectStatus,
                                 Integer maxScore, Double proportion, Integer releaser, UserType releaserType, Integer courseId, Date projectStartDate, Integer maxPeopleInTeam, Integer fileId, Date teamDeadline) {
//        return projectMapper.updateProject(projectId, projectTitle, projectDescription, projectDeadline, projectStatus,
//                maxScore, proportion, releaser, releaserType, courseId, projectStartDate, maxPeopleInTeam);
        Integer oldFileId = projectMapper.getProjectByProjectId(projectId).getFileId();
        if (oldFileId != null) {
            projectMapper.deleteFileById(oldFileId);
        }
        return projectMapper.updateProject(projectId, projectTitle, projectDescription, projectDeadline, projectStatus,
                maxScore, proportion, releaser, releaserType, courseId, projectStartDate, maxPeopleInTeam, fileId, teamDeadline);
    }

    @Override
    public List<ProjectInfo> getProjectByCourseId(Integer courseId) {
        return projectMapper.getProjectByCourseId(courseId);
    }


}
