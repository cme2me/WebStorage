package com.example.storage.service;

import com.example.storage.controller.RequestParams;
import com.example.storage.dto.FileDTO;
import com.example.storage.dto.PageDTO;
import com.example.storage.mapper.EntityMapper;
import com.example.storage.model.FileEntity;
import com.example.storage.repository.FileRepository;
import com.example.storage.repository.RepositorySpec;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
@AllArgsConstructor
//todo логер, info methods, debug
public class FileService {
    private final FileRepository fileRepository;
    private final RepositorySpec specification;
    private final EntityMapper mapper;

    public void putFile(MultipartFile file, String comment) {
        if (file.isEmpty()) {
            log.error("File is empty");
            throw new RuntimeException();
        }
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())/*todo check for null | done?*/);
            FileEntity fileEntity = new FileEntity(fileName, file.getContentType(), file.getBytes(), comment);
            fileRepository.save(fileEntity);
            log.info("File uploaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //todo все ResponseEntity в контроллеры | +
    }

    public void updateFile(FileDTO fileDTO, String id) {

        FileEntity fileDB = fileRepository.findById(UUID.fromString(id)).orElseThrow();

        if (StringUtils.hasLength(fileDTO.getName())) {
            fileDB.setName(fileDTO.getName());
        }
        if (StringUtils.hasLength(fileDTO.getComment())) {
            fileDB.setComment(fileDTO.getComment());
        }
        if (Objects.nonNull(fileDTO.getUpdatedDate())) {
            fileDB.setUpdatedDate(fileDTO.getUpdatedDate());
        }
        log.info("File updated");
        fileRepository.save(fileDB);
    }

    public FileEntity downloadFileById(String id) {
        log.info("File downloaded");
        return fileRepository.findById(UUID.fromString(id)).orElseThrow();
    }

    public List<String> getFilesName() {
        //todo сделать запросом в базу через @Query в репозитории | +
        return fileRepository.findAllNames();
    }

    //TODO проверка exists и кидать ошибку, отлавливая в handler | +?
    public void deleteFileByID(UUID id) {
        if (fileRepository.existsById(id)) {
            fileRepository.deleteById(id);
            log.info("File deleted");
        } else {
            log.error("Such file ID does not exist!");
            throw new IllegalArgumentException();
        }
    }

    public PageDTO<FileDTO> findFilteredFiles(RequestParams requestParams) {
        Page<FileEntity> fileModelPage = fileRepository.findAll(specification.nameAndFormatAndDates(requestParams.getName(),
                requestParams.getFormat(), requestParams.getFrom(), requestParams.getTo()), PageRequest.of(requestParams.getPage(), requestParams.getSize()));
        return mapper.toPageDTO(fileModelPage);
        //todo сделать PageDTO, 3 параметра PageRequest, замаппить | +
    }

    public List<FileDTO> showAllFiles() {
        List<FileEntity> files = fileRepository.findAll();
        return mapper.toFileDTOList(files);
    }

    public StreamingResponseBody downloadZipped(List<UUID> id) {
        List<FileEntity> fileEntity = fileRepository.findAllById(id);

        return outputStream -> {
            try (ZipOutputStream zout = new ZipOutputStream(outputStream)) {
                for (FileEntity model : fileEntity) {
                    Path fileWriteBytes = Files.write(Paths.get(model.getName()), model.getData());
                    FileInputStream fis = new FileInputStream(fileWriteBytes.toFile().getName());
                    ZipEntry zipEntry = new ZipEntry(fileWriteBytes.toFile().getName());
                    zout.putNextEntry(zipEntry);
                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zout.write(bytes, 0, length);
                    }
                    fis.close();
                }
                zout.closeEntry();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        };
    }
}
