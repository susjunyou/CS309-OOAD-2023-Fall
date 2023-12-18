package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.FileInfo;

public interface FileService extends IService<FileInfo> {

    FileInfo insertFile(FileInfo fileInfo);

    FileInfo getFile(Integer id);

    boolean addMaterial(Integer courseId, String name,String description, Integer fileId);

    boolean deleteMaterial(Integer courseId, Integer id);

    boolean updateMaterial(Integer courseId, Integer id, String name,String description, Integer fileId);

}
