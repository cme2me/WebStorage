package com.example.storage.service;

import com.example.storage.config.StorageConfig;
import com.example.storage.entity.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;

@Service
@Slf4j
public class FileService {

    private final Path storagePath;

    @Autowired
    FileRepository fileStorageRepo;

    @Autowired
    public FileService(StorageConfig storageConfig) {
        this.storagePath = Paths.get(storageConfig.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.storagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String putFile(MultipartFile multipartFile, Long fileID, String fileFormat) throws Exception{
        String getOriginalFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String fileName = "";

        String extension = "";
        try {
            extension = getOriginalFileName.substring(getOriginalFileName.lastIndexOf("."));
        } catch (Exception e) {
            extension = "";
        }
        fileName = fileID + "_" + fileFormat + extension;
        Path currentLocation = this.storagePath.resolve(fileName);
        Files.copy(multipartFile.getInputStream(), currentLocation, StandardCopyOption.REPLACE_EXISTING);

        StorageConfig storage = fileStorageRepo.checkFileByID(fileID, fileFormat);
        if (storage != null) {
            storage.setFileFormat(multipartFile.getContentType());
            storage.setFileName(fileName);
            fileStorageRepo.save(storage);
        }else {
            StorageConfig newStorage = new StorageConfig();
            newStorage.setId(fileID);
            newStorage.setFileName(fileName);
            newStorage.setFileFormat(fileFormat);
            newStorage.setFileFormat(newStorage.getFileFormat());
            fileStorageRepo.save(newStorage);
        }
        return fileName;
    }

    public Resource loadFile(String fileName) throws Exception {
        try{
            Path fileLoc = this.storagePath.resolve(fileName).normalize();
            Resource resource = new UrlResource(fileLoc.toUri());
            if (resource.exists()) {
                return resource;
            }else{
                throw new FileAlreadyExistsException(fileName);
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException(fileName);
        }
    }
    public String getFileName(Long fileID, String fileType) {
        return fileStorageRepo.getUploadFilePath(fileID,fileType);
    }

}
