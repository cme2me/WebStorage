package com.example.storage.controller;

import com.example.storage.model.ResponseData;
import com.example.storage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class FileController {

    private final FileService fileService;
    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
    @PostMapping("/upload")
    public ResponseData responseData(@RequestParam("file")MultipartFile file, @RequestParam("fileID") Long fileID, @RequestParam("fileType") String fileFormat) {
        String fileName = null;
        try {
            fileName = fileService.putFile(file, fileID, fileFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String fileDownloadURI = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(fileName).toUriString();
        return new ResponseData(fileName,fileDownloadURI, file.getContentType(), file.getSize());
    }
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFiles(@RequestParam("fileID") Long fileID, @RequestParam("fileType") String fileFormat,
                                                  HttpServletRequest httpServletRequest) {
        String fileName = fileService.getFileName(fileID, fileFormat);
        Resource resource = null;
        if (fileName != null && !fileName.isEmpty()) {
            try {
                resource = fileService.loadFile(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String contentInFIle = null;
        try {
            contentInFIle = httpServletRequest.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (contentInFIle == null) {
            contentInFIle = "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentInFIle))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);

    }
}
