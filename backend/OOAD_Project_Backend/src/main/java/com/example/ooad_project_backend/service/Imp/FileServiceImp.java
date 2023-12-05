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
}
