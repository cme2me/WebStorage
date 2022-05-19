package com.example.storage.service;

import com.example.storage.dto.FileDTO;
import com.example.storage.dto.ResponseMessage;
import com.example.storage.exceptions.FileException;
import com.example.storage.model.FileModel;
import com.example.storage.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public ResponseEntity<ResponseMessage> putFile(MultipartFile file) {
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            LocalDateTime date = LocalDateTime.now();
            FileModel fileModel = new FileModel(fileName, file.getContentType(), file.getBytes(), date, date);
            fileRepository.save(fileModel);
            return ResponseEntity.ok().body(new ResponseMessage("File uploaded"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public FileModel updateFile(FileDTO fileDTO, String id, LocalDateTime changedTime) {
        FileModel fileDB = fileRepository.findById(id).get();
        if (Objects.nonNull(fileDTO.getFileName()) && !"".equalsIgnoreCase(fileDTO.getFileName())) {
            fileDB.setName(fileDTO.getFileName());
        }
        if (Objects.nonNull(fileDTO.getUploadDate()) && !"".equalsIgnoreCase(String.valueOf(fileDTO.getUploadDate()))) {

        }
        if (Objects.nonNull(fileDTO.getChangeDate()) && !"".equalsIgnoreCase(String.valueOf(fileDTO.getChangeDate()))) {
            fileDB.setUpdatedDate(LocalDateTime.now());
        }
        return fileRepository.save(fileDB);
    }

    public FileModel getFileByID(String id) {
        return fileRepository.findById(id).get();
    }

    public Stream<FileModel> getAllFilesInStorage() {
        return fileRepository.findAll().stream();
    }

    public ResponseEntity<ResponseMessage> deleteFileByID(String id) {
        try {
            fileRepository.deleteById(id);
            return ResponseEntity.ok().body(new ResponseMessage("File deleted"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
