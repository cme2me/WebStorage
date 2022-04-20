package com.example.storage.controller;

import com.example.storage.api.models.File;
import com.example.storage.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    @GetMapping("/api")
    public List<File> files(FileService fileService) {
        return fileService.showFiles();
    }
    @GetMapping("api/files/{id}")
    public File filesInfo(@PathVariable Long id) {
        return fileService.getFileById(id);
    }
    @PostMapping("api/files/add")
    public String addFile(@RequestParam File file) {
        fileService.saveFile(file);
        return "redirect:/api";
    }
    //@PostMapping("/files/remove/{id}") if doesn't work
    @DeleteMapping("api/files/remove/{id}")
    public String removeFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return "redirect:/api";
    }
}
