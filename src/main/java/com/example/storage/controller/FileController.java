package com.example.storage.controller;

import com.example.storage.dto.FileDTO;
import com.example.storage.dto.ResponseMessage;
import com.example.storage.exceptions.FileException;
import com.example.storage.model.FileModel;
import com.example.storage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileController {

    private final
    FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) throws MultipartException {
        try {
            return fileService.putFile(file);
        } catch (Exception e) {
            throw new FileException("File is not uploaded");
        }
    }

    @GetMapping("/allFiles")
    public ResponseEntity<List<FileDTO>> getAllFiles() {
        List<FileDTO> files = fileService.getAllFilesInStorage().map(fileModel -> {
            String fileDownloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/file/")
                    .path(fileModel.getId())
                    .toUriString();
            return new FileDTO(
                    fileModel.getId(),
                    fileModel.getName(),
                    fileDownloadURL,
                    (long) fileModel.getData().length,
                    fileModel.getFormat(),
                    fileModel.getDate()
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

    @PutMapping("/file/update/{id}")
    public ResponseEntity<ResponseMessage> updateFileByID(@RequestBody FileDTO fileDTO, @PathVariable("id") String id) throws Exception {
        LocalDateTime updateTime = LocalDateTime.now();
        fileService.updateFile(fileDTO, id, updateTime);
        return ResponseEntity.ok().body(new ResponseMessage("Файл обновлен " + fileDTO.getFileName()));
    }
}
