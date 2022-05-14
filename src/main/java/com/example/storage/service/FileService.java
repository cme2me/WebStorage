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

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void putFile(MultipartFile file) throws Exception {
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            LocalDateTime date = LocalDateTime.now();
            FileModel fileModel = new FileModel(fileName, file.getContentType(), file.getBytes(), date, date);
            fileRepository.save(fileModel);
        }catch (Exception e) {
            throw new FileException("Файл не был загружен");
        }
    }

    public void updateFile(FileDTO fileDTO) {
        Optional<FileModel> fileModel = fileRepository.findById(fileDTO.getId());
        fileModel.get().setName(fileDTO.getFileName());
    }

    public ResponseEntity<ResponseMessage> msg() {
        String message = "";
        message = "Файл загружен";
        return ResponseEntity.ok().body(new ResponseMessage(message));
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
}
