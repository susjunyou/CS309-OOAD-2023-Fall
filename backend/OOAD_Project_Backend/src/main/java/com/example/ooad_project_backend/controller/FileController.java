package com.example.ooad_project_backend.controller;


import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.FileInfo;
import com.example.ooad_project_backend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
