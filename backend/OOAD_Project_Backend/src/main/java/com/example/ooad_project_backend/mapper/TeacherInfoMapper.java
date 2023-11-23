package com.example.ooad_project_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.TeacherInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherInfoMapper extends BaseMapper<TeacherInfo> {
    @Select("select * from teacher where account = #{account}")
    TeacherInfo findTeacherInfoByAccount(String account);

    @Select("select * from teacher where name = #{name}")
    TeacherInfo findByName(@Param("name") String name);

    @Select("select * from teacher")
    List<TeacherInfo> getAll();

    @Update("UPDATE teacher SET account=#{#account},name = #{name}, password = #{password}, sex = #{sex}, age = #{age}, level = #{level}," +
            " tenure = #{tenure}, phone_number = #{phone_number},selfIntroduction = #{self_Introduction}, department= #{department},email=#{email},  WHERE id = #{id}")
    void updateTeacherInfo(TeacherInfo teacherInfo);

    @Delete("DELETE FROM teacher WHERE id = #{id}")
    void deleteTeacherInfo(@Param("id") long id);

    @Select("SELECT * FROM teacher " +
            "WHERE (name LIKE CONCAT('%', #{search}, '%') OR department LIKE CONCAT('%', #{search}, '%'))")
    List<TeacherInfo> findByLikeName(@Param("search") String search);


}
