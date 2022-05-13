package com.example.storage.service;

import com.example.storage.dto.ResponseMessage;
import com.example.storage.entity.repository.FileRepository;
import com.example.storage.exceptions.FileException;
import com.example.storage.model.FileModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@Service
public class FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void putFile(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        LocalDateTime date = LocalDateTime.now();
        FileModel fileModel = new FileModel(fileName, file.getContentType(), file.getBytes(), date);
        fileRepository.save(fileModel);
    }

    public ResponseEntity<ResponseMessage> msg() {
        try {
            String message = "";
            message = "Файл загружен";
            return ResponseEntity.ok().body(new ResponseMessage(message));
        } catch (Exception e) {
            throw new FileException("Размер превышен" + e);
        }
    }

    public FileModel getFileByID(String id) throws Exception {
        return fileRepository.findById(id).get();
    }

    public Stream<FileModel> getAllFilesInStorage() {
        return fileRepository.findAll().stream();
    }

    public void deleteFileByID(String id) {
        fileRepository.deleteById(id);
    }

    public FileModel updateFile(String id, MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        fileRepository.deleteById(id);
        LocalDateTime date = LocalDateTime.now();
        return fileRepository.save(new FileModel(fileName, file.getContentType(), file.getBytes(), date));
    }
}
