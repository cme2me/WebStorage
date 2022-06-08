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

import javax.transaction.Transactional;
import java.util.List;

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
        return fileService.putFile(file, comment);
    }

    @GetMapping("/files")
    public ResponseEntity<List<FilesName>> getFileName() {
        return fileService.getFilesName();
    }

    @GetMapping("/files/info")
    public ResponseEntity<List<FileDTO>> getAllInformationAboutFiles() {
        return fileService.showAllFiles();
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
    @Transactional
    @GetMapping("api/files/{name}")
    public ResponseEntity<List<FileModel>> findFilesByName(@PathVariable("name") String name) {
        return fileService.findFilesByName(name);
    }
}
