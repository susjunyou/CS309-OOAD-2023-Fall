package com.example.ooad_project_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.FileInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper extends BaseMapper<FileInfo> {


    @Insert("insert into file (file_name, file_type, file_data) values (#{fileName}, #{fileType}, #{fileData})")
    void insertFile(FileInfo fileInfo);



    @Select("select * from file where id = #{id}")
    FileInfo getFile(Integer id);

    @Select("select * from file order by id desc limit 1")
    FileInfo getLast();

    @Insert("insert into material (course_id, name,description, file_id) values (#{courseId}, #{name},#{description}, #{fileId})")
    boolean addMaterial(Integer courseId, String name,String description, Integer fileId);

    @Delete("delete from material where course_id = #{courseId} and id = #{id}")
    boolean deleteMaterial(Integer courseId, Integer id);

    @Update("update material set name = #{name},description = #{description},file_id = #{fileId} where course_id = #{courseId} and id = #{id}")
    boolean updateMaterial(Integer courseId, Integer id, String name,String description, Integer fileId);

    @Select("select file_id from material where id = #{id}")
    Integer getFileIdByMaterialId(Integer id);



}
