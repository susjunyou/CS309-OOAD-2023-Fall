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

import java.io.Console;
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
    public boolean updateTeamInfo(TeamInfo teamInfo) {
        teamMapper.updateTeamInfo(teamInfo);
        return true;
    }

    @Override
    public boolean updateTeamLeader(Integer teamId, Integer studentId) {
        teamMapper.updateTeamLeader(teamId, studentId);
        return true;
    }

    @Override
    public boolean createTeam(TeamInfo teamInfo) {
        Integer maxTeamSize = projectInfoMapper.findMaxPeopleInTeamById(teamInfo.getProjectId());
        // 判断队伍人数是否超过最大人数
        if (maxTeamSize < teamInfo.getTeamSize()) {
            return false;
        }
        // 检查创建队伍的人是否位于其他的队伍
        Integer teamId = teamMapper.findTeamIdByProjectIdAndStudentId(teamInfo.getProjectId(), teamInfo.getLeader());
        if (teamId != null) {
            return false;
        }
        if (teamInfo.getLeader() == null || teamInfo.getTeamSize() == null || teamInfo.getProjectId() == null || teamInfo.getTeamName() == null) {
            return false;
        }
        teamMapper.createTeam(teamInfo);
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
        for (Integer studentId : studentIds) {
            studentInfos.add(studentInfoMapper.findStudentInfoById(studentId));
        }
        return studentInfos;
    }

    @Override
    public TeamInfo findTeamInfoByTeamId(Integer teamId) {
        return teamMapper.findTeamInfoByTeamId(teamId);
    }

    @Override
    public boolean leaveTeam(Integer teamId, Integer studentId) {
        teamMapper.leaveTeam(teamId, studentId);
        return true;
    }

    @Override
    public boolean deleteTeam(Integer teamId) {
        teamMapper.deleteTeamInTeamStudent(teamId);
        teamMapper.deleteTeamInTeam(teamId);
        return true;
    }

    @Override
    public List<TeamInfo> findAllTeamInfoByStudentId(Integer studentId) {
        return teamMapper.findTeamInfoByStudentId(studentId);
    }

    @Override
    public boolean requestJoinTeam(Integer teamId, Integer studentId) {

        teamMapper.requestJoinTeam(teamId, studentId);
        return true;
    }

    @Override
    public List<StudentInfo> getRequestsJoinTeam(Integer teamId) {
        List<Integer> studentIds = teamMapper.getRequestsStudentIdByTeamId(teamId);
        List<StudentInfo> studentInfos = new ArrayList<>();
        for (Integer studentId : studentIds) {
            studentInfos.add(studentInfoMapper.findStudentInfoById(studentId));
        }
        return studentInfos;
    }

    @Override
    public List<TeamInfo> getInvitesJoinTeam(Integer studentId) {
        List<Integer> teamIds = teamMapper.getInvitesJoinTeam(studentId);
        List<TeamInfo> teamInfos = new ArrayList<>();
        for (Integer teamId : teamIds) {
            teamInfos.add(teamMapper.findTeamInfoByTeamId(teamId));
        }
        return teamInfos;
    }

    @Override
    public boolean manageTeamRequest(Integer requestId, boolean isAccepted) {
        if (isAccepted) {
            Integer studentId = teamMapper.findStudentIdByRequestId(requestId);
            Integer teamId = teamMapper.findTeamIdByRequestId(requestId);
            teamMapper.joinTeam(teamId, studentId, teamMapper.findProjectIdByTeamId(teamId));
            teamMapper.deleteRequest(requestId);
        } else {
            teamMapper.deleteRequest(requestId);
        }
        return true;
    }

    @Override
    public boolean manageInvite(Integer id, boolean isAccepted) {
        if (isAccepted) {
            Integer studentId = teamMapper.findStudentIdByInviteId(id);
            Integer teamId = teamMapper.findTeamIdByInviteId(id);
            teamMapper.joinTeam(teamId, studentId, teamMapper.findProjectIdByTeamId(teamId));
            teamMapper.deleteInvite(id);
        } else {
            teamMapper.deleteInvite(id);
        }
        return true;
    }

    @Override
    public boolean inviteStudent(Integer teamId, Integer studentId) {
        TeamInfo teamInfo = teamMapper.findTeamInfoByTeamId(teamId);
        // 判断是否加入该项目的其他队伍
        if (teamMapper.findTeamIdByProjectIdAndStudentId(teamInfo.getProjectId(), studentId) != null) {
            return false;
        }
        // 判断这个team是否已经满员
        if (teamMapper.findStudentIdsByTeamIdAndProjectId(teamInfo.getTeamId(), teamInfo.getProjectId()).size() >= teamInfo.getTeamSize()) {
            return false;
        }
        try {
            teamMapper.addInviteJoinTeam(teamId, studentId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
