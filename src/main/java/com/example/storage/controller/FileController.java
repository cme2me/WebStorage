package com.example.storage.controller;

import com.example.storage.model.FileModel;
import com.example.storage.dto.ResponseData;
import com.example.storage.dto.ResponseMessage;
import com.example.storage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:8080")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            fileService.putFile(file);
            message = "Файл загружен " + file.getOriginalFilename();
            return ResponseEntity.ok().body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Файл не был загружен " + file.getOriginalFilename();
            return ResponseEntity.badRequest().body(new ResponseMessage(message));
        }
    }

    @GetMapping("/allFiles")
    public ResponseEntity<List<ResponseData>> getAllFiles() {
        List<ResponseData> files = fileService.getAllFilesInStorage().map(fileModel -> {
            String fileDownloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/allFiles/")
                    .path(fileModel.getId())
                    .toUriString();
            return new ResponseData(
                    fileModel.getName(),
                    fileDownloadURL,
                    (long) fileModel.getData().length,
                    fileModel.getFormat(),
                    fileModel.getDate()
                    // время обновления, коммент
            );
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(files);
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getFileByID(@PathVariable String id) throws Exception {
        FileModel fileModel = fileService.getFileByID(id);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileModel.getName() + "")
                .body(fileModel.getData());
    }

    @PostMapping("/file/delete/{id}")
    public ResponseEntity<ResponseMessage> deleteFileByID(@PathVariable String id) {
        String message = "";
        try {
            fileService.deleteFileByID(id);
            message = "файл успешно удален";
            return ResponseEntity.ok().body(new ResponseMessage(message));
        } catch (Exception e) {
            e.printStackTrace();
            message = "файл не удален";
            return ResponseEntity.badRequest().body(new ResponseMessage(message));
        }
    }

    @PostMapping("/file/update/{id}")
    public ResponseEntity<ResponseMessage> updateFileByID(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        try {
            FileModel fileModel = fileService.updateFile(id, file);
            return ResponseEntity.ok().body(new ResponseMessage("Файл успешно обновлен"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body(new ResponseMessage("Файл не был обновлен"));
    }
}
