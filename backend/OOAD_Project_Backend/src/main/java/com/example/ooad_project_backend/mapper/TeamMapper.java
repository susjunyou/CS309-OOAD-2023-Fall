package com.example.ooad_project_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.TeamInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeamMapper extends BaseMapper<TeamInfo> {

    @Insert("insert into team(team_name, leader, team_description, team_size, project_id)" +
            " VALUES (#{teamName}, #{leader}, #{teamDescription}, #{teamSize}, #{projectId})")
    void createTeam(TeamInfo teamInfo);

    @Insert("insert into team_student(team_id, student_id, project_id) VALUES (#{teamId}, #{studentId}, #{projectId})")
    void joinTeam(Integer teamId, Integer studentId, Integer projectId);

    @Select("select team_id from team_student where student_id = #{studentId} and project_id  = #{projectId}")
    Integer findTeamIdByProjectIdAndStudentId(Integer projectId, Integer studentId);

    @Select("select student_id from team_student where project_id = #{projectId} and team_id = #{teamId}")
    List<Integer> findStudentIdsByTeamIdAndProjectId(Integer teamId, Integer projectId);

    @Select("select * from team where project_id = #{projectId} and leader = #{leader}")
    TeamInfo findTeamInfoByProjectIdAndLeader(Integer projectId, Integer leader);

    @Select("select * from team where project_id = #{projectId}")
    List<TeamInfo> findTeamInfoByProjectId(Integer projectId);

    @Select("select student_id from team_student where team_id = #{teamId}")
    List<Integer> findStudentIdsByTeamId(Integer teamId);

}
