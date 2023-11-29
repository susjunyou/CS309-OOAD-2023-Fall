package com.example.ooad_project_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.CourseInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CourseInfoMapper extends BaseMapper<CourseInfo> {
    @Select("select * from course where course_id = #{courseId}")
    CourseInfo findCourseInfoByCourseId(Integer courseId);

}
