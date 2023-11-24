package com.example.ooad_project_backend.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ooad_project_backend.entity.StudentInfo;
import com.example.ooad_project_backend.entity.TeamInfo;
import com.example.ooad_project_backend.mapper.ProjectInfoMapper;
import com.example.ooad_project_backend.mapper.StudentInfoMapper;
import com.example.ooad_project_backend.mapper.TeamMapper;
import com.example.ooad_project_backend.service.TeamInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamInfoServiceImp extends ServiceImpl<TeamMapper, TeamInfo> implements TeamInfoService {

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private ProjectInfoMapper projectInfoMapper;

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Override
    public boolean createTeam(TeamInfo teamInfo) {
        Integer maxTeamSize = projectInfoMapper.findMaxPeopleInTeamById(teamInfo.getProjectId());
        // 判断队伍人数是否超过最大人数
        if (maxTeamSize < teamInfo.getTeamSize()) {
            return false;
        }
        try {
            teamMapper.createTeam(teamInfo);
        } catch (Exception e) {
            return false;
        }
        TeamInfo team = teamMapper.findTeamInfoByProjectIdAndLeader(teamInfo.getProjectId(), teamInfo.getLeader());
        teamMapper.joinTeam(team.getTeamId(), teamInfo.getLeader(), teamInfo.getProjectId());
        return true;
    }

    @Override
    public boolean joinTeam(TeamInfo teamInfo, Integer studentId) {
        // 判断是否加入该项目的其他队伍
        if (teamMapper.findTeamIdByProjectIdAndStudentId(teamInfo.getProjectId(), studentId) != null) {
            return false;
        }
        // 判断这个team是否已经满员
        if (teamMapper.findStudentIdsByTeamIdAndProjectId(teamInfo.getTeamId(), teamInfo.getProjectId()).size() >= teamInfo.getTeamSize()) {
            return false;
        }
        try {
            teamMapper.joinTeam(teamInfo.getTeamId(), studentId, teamInfo.getProjectId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<TeamInfo> findAllTeamInfoByProjectId(Integer projectId) {
        return teamMapper.findTeamInfoByProjectId(projectId);
    }

    @Override
    public List<StudentInfo> findStudentInfoByTeamId(Integer teamId) {
        List<Integer> studentIds = teamMapper.findStudentIdsByTeamId(teamId);
        List<StudentInfo> studentInfos = new ArrayList<>();
        for (int i = 0; i < studentIds.size(); i++) {
            studentInfos.add(studentInfoMapper.findStudentInfoById(studentIds.get(i)));
        }
        return studentInfos;
    }
}
