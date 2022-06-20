package com.example.storage.service;

import com.example.storage.dto.FileDTO;
import com.example.storage.mapper.MapperImpl;
import com.example.storage.model.FileModel;
import com.example.storage.repository.FileRepository;
import com.example.storage.repository.RepositorySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
@Slf4j
@Service
//todo логер, info methods, debug
public class FileService {

    private final FileRepository fileRepository;
    private final RepositorySpec specification;
    private final MapperImpl mapper;

    @Autowired
    public FileService(FileRepository fileRepository, RepositorySpec specification, MapperImpl mapper) {
        this.fileRepository = fileRepository;
        this.specification = specification;
        this.mapper = mapper;
    }

    public void putFile(MultipartFile file, String comment) {
        if (file.isEmpty()) {
            //todo кидать ошибку, а badRequest отлавливать в handler | done
            throw new RuntimeException();
        }
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())/*todo check for null | done?*/);
            FileModel fileModel = new FileModel(fileName, file.getContentType(), file.getBytes(), comment);
            fileRepository.save(fileModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //todo все ResponseEntity в контроллеры | +
    }

    public void updateFile(FileDTO fileDTO, String id) {

        FileModel fileDB = fileRepository.findById(UUID.fromString(id)).orElseThrow();

        if (StringUtils.hasLength(fileDTO.getName())) {
            fileDB.setName(fileDTO.getName());
        }
        if (StringUtils.hasLength(fileDTO.getComment())) {
            fileDB.setComment(fileDTO.getComment());
        }
        if (Objects.nonNull(fileDTO.getUpdatedDate())) {
            fileDB.setUpdatedDate(fileDTO.getUpdatedDate());
        }
        fileRepository.save(fileDB);
    }

    public FileModel downloadFileById(String id) {
        return fileRepository.findById(UUID.fromString(id)).orElseThrow();
    }

    public List<String> getFilesName() {
        //todo сделать запросом в базу через @Query в репозитории | +
        return fileRepository.findAllNames();
    }


    //TODO проверка exists и кидать ошибку, отлавливая в handler | +?
    public void deleteFileByID(UUID id) {
        if (!fileRepository.existsById(id)) {
            try {
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        fileRepository.deleteById(id);
        //todo try/catch
    }

    public Page<FileModel> findFilteredFiles(String name, String format, LocalDateTime from, LocalDateTime to) {
        Page<FileModel> all = fileRepository.findAll(specification.nameAndFormatAndDates(name, format, from, to), PageRequest.of(0, 2));

        return all;
        //todo сделать PageDTO, 3 параметра PageRequest, замаппить
    }

    public List<FileDTO> showAllFiles() {
        List<FileModel> files = fileRepository.findAll();
        return mapper.toFileDTOList(files);
    }
}
