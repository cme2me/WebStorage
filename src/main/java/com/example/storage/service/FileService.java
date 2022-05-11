package com.example.storage.service;

import com.example.storage.model.FileModel;
import com.example.storage.entity.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public FileModel putFile(MultipartFile file) throws Exception{
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        LocalDateTime date = LocalDateTime.now();
        FileModel fileModel = new FileModel(fileName, file.getContentType(), file.getBytes(), date);
        return fileRepository.save(fileModel);
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
