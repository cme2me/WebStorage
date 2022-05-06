package com.example.storage.controller;

import com.example.storage.dto.FileDTO;
import com.example.storage.model.ResponseData;
import com.example.storage.model.ResponseMessage;
import com.example.storage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<ResponseData> files = fileService.getAllFilesInStorage().map(fileDTO -> {
            String fileDownloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/allFiles/")
                    .path(fileDTO.getId())
                    .toUriString();
            return new ResponseData(
                    fileDTO.getName(),
                    fileDownloadURL,
                    (long) fileDTO.getData().length,
                    fileDTO.getFormat(),
                    fileDTO.getDate()
                    // время обновления, коммент
            );
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(files);
    }
    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getFileByID(@PathVariable String id) throws Exception {
        FileDTO fileDTO = fileService.getFileByID(id);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDTO.getName() + "")
                .body(fileDTO.getData());
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
}
