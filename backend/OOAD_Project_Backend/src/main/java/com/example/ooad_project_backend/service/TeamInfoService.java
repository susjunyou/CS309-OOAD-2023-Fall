package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.TeacherInfo;
import com.example.ooad_project_backend.entity.TeamInfo;

import java.util.List;

public interface TeamInfoService extends IService<TeamInfo> {

    boolean createTeam(TeamInfo teamInfo);

    boolean joinTeam(TeamInfo teamInfo, Integer studentId);

    List<TeamInfo> findAllTeamInfoByProjectId(Integer projectId);
}
