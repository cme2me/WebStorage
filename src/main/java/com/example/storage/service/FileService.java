package com.example.storage.service;

import com.example.storage.dto.FileDTO;
import com.example.storage.entity.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public FileDTO putFile(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Long uploadDate = Time.now();
        FileDTO fileDTO = new FileDTO(fileName, file.getContentType(), file.getBytes(), uploadDate);
        return fileRepository.save(fileDTO);
    }

    public FileDTO getFileByID(String id) throws Exception {
        return fileRepository.findById(id).get();
    }

    public Stream<FileDTO> getAllFilesInStorage() {
        return fileRepository.findAll().stream();
    }

    public void deleteFileByID(String id) {
        fileRepository.deleteById(id);
    }
}
