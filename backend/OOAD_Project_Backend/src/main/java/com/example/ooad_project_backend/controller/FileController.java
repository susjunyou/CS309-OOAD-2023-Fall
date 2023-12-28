package com.example.ooad_project_backend.controller;


import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.FileInfo;
import com.example.ooad_project_backend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/course")
@CrossOrigin
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/file")
    public Result getFile(Integer id) {
        FileInfo file = fileService.getFile(id);
        return file == null ? Result.error() : Result.success(file);
    }


    @GetMapping("/fileContent")
    public Result getFileContent(Integer id) {
        FileInfo file = fileService.getFile(id);
        byte[] fileData = file.getFileData();
        String result = new String(fileData, StandardCharsets.UTF_8);
        return Result.success(result);
    }

    @PostMapping("/addMaterial")
    public Result addMaterial(Integer courseId, String name, String description, MultipartFile file) {
        FileInfo fileInfo = new FileInfo();
        try {
            if (file != null) {
                fileInfo.setFileName(file.getOriginalFilename());
                fileInfo.setFileType(file.getContentType());
                fileInfo.setFileData(file.getBytes());
                fileInfo = fileService.insertFile(fileInfo);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileService.addMaterial(courseId, name, description, fileInfo.getId()) ? Result.success() : Result.error("1", "提交失败");
    }

    //这里的id是material的id
    @GetMapping("/deleteMaterial")
    public Result deleteMaterial(Integer courseId, Integer id) {
        return fileService.deleteMaterial(courseId, id) ? Result.success() : Result.error("1", "删除失败");
    }


    //传入新文件
    @GetMapping("/updateMaterial")
    public Result updateMaterial(Integer courseId, Integer id, String name, String description, MultipartFile file) {
        FileInfo fileInfo = new FileInfo();
        try {
            if (file != null) {
                fileInfo.setFileName(file.getOriginalFilename());
                fileInfo.setFileType(file.getContentType());
                fileInfo.setFileData(file.getBytes());
                fileInfo = fileService.insertFile(fileInfo);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileService.updateMaterial(courseId, id, name, description, fileInfo.getId()) ? Result.success() : Result.error("1", "提交失败");
    }


}
