package com.example.ooad_project_backend.mapper;


import com.example.ooad_project_backend.entity.ProjectInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProjectInfoMapper {

    @Select("select * from project where course_id = #{courseId}")
    List<ProjectInfo> findProjectInfoByCourseId(Integer courseId);
}