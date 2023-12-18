package com.example.ooad_project_backend.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ooad_project_backend.entity.FileInfo;
import com.example.ooad_project_backend.mapper.FileMapper;
import com.example.ooad_project_backend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImp extends ServiceImpl<FileMapper, FileInfo> implements FileService {
    @Autowired
    private FileMapper fileMapper;


    @Override
    public FileInfo insertFile(FileInfo fileInfo) {
        fileMapper.insertFile(fileInfo);
        return fileMapper.getLast();
    }

    @Override
    public FileInfo getFile(Integer id) {
        return fileMapper.getFile(id);
    }

    @Override
    public boolean addMaterial(Integer courseId, String name, String description, Integer fileId) {
        return fileMapper.addMaterial(courseId, name, description, fileId);
    }

    @Override
    public boolean deleteMaterial(Integer courseId, Integer id) {
        //先删除文件
        FileInfo fileInfo = fileMapper.getFile(id);
        fileMapper.deleteById(id);
        return fileMapper.deleteMaterial(courseId, id);
    }


    //这里的id是material的id
    @Override
    public boolean updateMaterial(Integer courseId, Integer id, String name,String description, Integer fileId) {
        Integer fileId1=fileMapper.getFileIdByMaterialId(id);
        //删除原来的file
        fileMapper.deleteById(fileId1);
        //更新material
        return fileMapper.updateMaterial(courseId, id, name, description, fileId);


    }

}
