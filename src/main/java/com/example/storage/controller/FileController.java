package com.example.storage.controller;

import com.example.storage.dto.FileDTO;
import com.example.storage.dto.FilesName;
import com.example.storage.dto.ResponseMessage;
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
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String comment) throws MultipartException {
        try {
            return fileService.putFile(file, comment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<FilesName>> getFileName() {
        return fileService.getFilesName();
    }

    @GetMapping("/files/info")
    public ResponseEntity<List<FileDTO>> getAllFiles() {
        List<FileDTO> files = fileService.getAllFilesInStorage().map(fileModel -> {
            String fileDownloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(fileModel.getId())
                    .toUriString();
            return new FileDTO(
                    fileModel.getId(),
                    fileModel.getName(),
                    fileDownloadURL,
                    (long) fileModel.getData().length,
                    fileModel.getFormat(),
                    fileModel.getDate(),
                    fileModel.getComment()
            );
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(files);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getFileByID(@PathVariable String id) {
        FileModel fileModel = fileService.getFileByID(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileModel.getName() + "")
                .body(fileModel.getData());
    }

    @PostMapping("/file/delete/{id}")
    public ResponseEntity<ResponseMessage> deleteFileByID(@PathVariable String id) {
        return fileService.deleteFileByID(id);
    }

    @PutMapping("/file/update/{id}")
    public ResponseEntity<ResponseMessage> updateFileByID(@RequestBody FileDTO fileDTO, @PathVariable("id") String id) {
        fileService.updateFile(fileDTO, id);
        return ResponseEntity.ok().body(new ResponseMessage("Файл обновлен " + fileDTO.getFileName()));
    }
}
