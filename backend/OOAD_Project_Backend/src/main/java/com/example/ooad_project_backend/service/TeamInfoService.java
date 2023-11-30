package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.StudentInfo;
import com.example.ooad_project_backend.entity.TeamInfo;

import java.util.List;

public interface TeamInfoService extends IService<TeamInfo> {

    boolean createTeam(TeamInfo teamInfo);

    boolean joinTeam(TeamInfo teamInfo, Integer studentId);

    List<TeamInfo> findAllTeamInfoByProjectId(Integer projectId);

    List<StudentInfo> findStudentInfoByTeamId(Integer teamId);

    boolean leaveTeam(Integer teamId, Integer studentId);

    boolean deleteTeam(Integer teamId);

    List<TeamInfo> findAllTeamInfoByStudentId(Integer studentId);
}
