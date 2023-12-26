package com.example.ooad_project_backend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.ProjectInfo;
import com.example.ooad_project_backend.enums.UserType;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface ProjectMapper extends BaseMapper<ProjectInfo> {

        @Insert("insert into project(project_title, project_description, project_deadline, project_status, max_score, proportion, releaser, releaser_type, course_id, project_start_date, max_people_in_team, file_id, team_deadline) values(#{projectTitle}, #{projectDescription}, #{projectDeadline}, #{projectStatus}, #{maxScore}, #{proportion}, #{releaser}, #{releaserType}, #{courseId}, #{projectStartDate}, #{maxPeopleInTeam}, #{fileId}), #{teamDeadline}")
        boolean addProject(@Param("projectTitle") String projectTitle, @Param("projectDescription") String projectDescription, @Param("projectDeadline") Date projectDeadline, @Param("projectStatus") String projectStatus,
                        @Param("maxScore") Integer maxScore, @Param("proportion") Double proportion, @Param("releaser") Integer releaser, @Param("releaserType") UserType releaserType, @Param("courseId") Integer courseId, @Param("projectStartDate") Date projectStartDate, @Param("maxPeopleInTeam") Integer maxPeopleInTeam, @Param("fileId") Integer fileId, @Param("teamDeadline") Date teamDeadline);

        @Delete("delete from project where id = #{projectId}")
        boolean deleteProject(@Param("projectId") Integer projectId);

        @Delete("delete from file where id = #{fileId}")
        void deleteFileById(@Param("fileId") Integer fileId);

        @Select("select * from project where id = #{projectId}")
        ProjectInfo getProjectByProjectId(@Param("projectId") Integer projectId);

        @Update("update project set project_title = #{projectTitle}, project_description = #{projectDescription}, project_deadline = #{projectDeadline}, project_status = #{projectStatus}, max_score = #{maxScore}, proportion = #{proportion}, releaser = #{releaser}, releaser_type = #{releaserType}, course_id = #{courseId}, project_start_date = #{projectStartDate}, max_people_in_team = #{maxPeopleInTeam},file_id = #{fileId},team_deadline = #{teamDeadline} where id = #{projectId}")
        boolean updateProject(@Param("projectId") Integer projectId, @Param("projectTitle") String projectTitle, @Param("projectDescription") String projectDescription, @Param("projectDeadline") Date projectDeadline, @Param("projectStatus") String projectStatus,
                            @Param("maxScore") Integer maxScore, @Param("proportion") Double proportion, @Param("releaser") Integer releaser, @Param("releaserType") UserType releaserType, @Param("courseId") Integer courseId, @Param("projectStartDate") Date projectStartDate, @Param("maxPeopleInTeam") Integer maxPeopleInTeam, @Param("fileId") Integer fileId, @Param("TeamDeadline") Date teamDeadline);

        @Select("select * from project where course_id = #{courseId}")
        List<ProjectInfo> getProjectByCourseId(@Param("courseId") Integer courseId);

}

