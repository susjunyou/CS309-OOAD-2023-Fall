package com.example.ooad_project_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.FileInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper extends BaseMapper<FileInfo> {


    @Insert("insert into file (file_name, file_type, file_data) values (#{fileName}, #{fileType}, #{fileBytes})")
    void insertFile(FileInfo fileInfo);

    @Select("select * from file where id = #{id}")
    FileInfo getFile(Integer id);


}
