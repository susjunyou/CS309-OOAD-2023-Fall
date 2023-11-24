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

    @Insert("INSERT INTO teacher (id,account, name, password, sex, tenure, phone_number, self_introduction, department, email) " +
            "VALUES (#{id},#{account}, #{name}, #{password}, #{sex}, #{tenure}, #{phoneNumber}, #{selfIntroduction}, #{department}, #{email})")
    void insertTeacherInfo(TeacherInfo teacherInfo);


    @Update("UPDATE teacher SET account=#{account},name = #{name}, password = #{password}, sex = #{sex}, " +
            " tenure = #{tenure}, phone_number = #{phoneNumber},self_introduction = #{selfIntroduction}, department= #{department},email=#{email} WHERE id = #{id}")
    void updateTeacherInfo(TeacherInfo teacherInfo);

    @Delete("DELETE FROM teacher WHERE id = #{id}")
    void deleteTeacherInfo(@Param("id") long id);

    @Select("SELECT * FROM teacher " +
            "WHERE (name LIKE CONCAT('%', #{search}, '%') OR department LIKE CONCAT('%', #{search}, '%'))")
    List<TeacherInfo> findByLikeName(@Param("search") String search);


}
