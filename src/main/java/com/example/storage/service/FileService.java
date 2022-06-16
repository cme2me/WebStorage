package com.example.storage.service;

import com.example.storage.dto.FileDTO;
import com.example.storage.dto.FilesName;
import com.example.storage.dto.ResponseMessage;
import com.example.storage.model.FileModel;
import com.example.storage.repository.FileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final ModelMapper mapper;

    @Autowired
    public FileService(FileRepository fileRepository, ModelMapper mapper) {
        this.fileRepository = fileRepository;
        this.mapper = mapper;
    }

    public ResponseEntity<ResponseMessage> putFile(MultipartFile file, String comment) {
        if (!file.isEmpty()) {
            try {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                LocalDateTime date = LocalDateTime.now();
                FileModel fileModel = new FileModel(fileName, file.getContentType(), file.getBytes(), date, date, comment);
                fileRepository.save(fileModel);
                return ResponseEntity.ok().body(new ResponseMessage("File uploaded"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.badRequest().body(new ResponseMessage("Missing uploading file"));
    }

    public FileModel updateFile(FileDTO fileDTO, String id) {
        FileModel fileDB = fileRepository.findById(id).get();
        if (Objects.nonNull(fileDTO.getFileName()) && !"".equalsIgnoreCase(fileDTO.getFileName())) {
            fileDB.setName(fileDTO.getFileName());
        }
        if (Objects.nonNull(fileDTO.getComment())) {
            fileDB.setComment(fileDTO.getComment());
        }
        if (Objects.nonNull(fileDTO.getChangeDate())) {
            fileDB.setUpdatedDate(fileDTO.getChangeDate());
        }
        return fileRepository.save(fileDB);
    }

    public FileModel downloadFileById(String id) {
        return fileRepository.findById(id).get();
    }

    public ResponseEntity<List<FilesName>> getFilesName() {
        List<FilesName> filesName = getAllFilesInStorage().map(fileModel -> new FilesName(fileModel.getName())).collect(Collectors.toList());
        return ResponseEntity.ok().body(filesName);
    }

    public Stream<FileModel> getAllFilesInStorage() {
        return fileRepository.findAll().stream();
    }

    public Stream<FileModel> getFilteredFilesInStorage(String name) {
        return fileRepository.findByName(name).stream();
    }

    public Stream<FileModel> getFilteredFilesInStorageByDate(LocalDateTime from, LocalDateTime to) {
        return fileRepository.findByFromDateAndToDate(from, to).stream();
    }

    public ResponseEntity<List<FileDTO>> showAllFiles() {
        List<FileDTO> files = getAllFilesInStorage().map(fileModel -> {
            String fileDownloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(fileModel.getId())
                    .toUriString();
            return new FileDTO(
                    fileModel.getId(),
                    fileModel.getName(),
                    fileDownloadURL,
                    (long) fileModel.getData().length,
                    fileModel.getFormat(),
                    fileModel.getDate(),
                    fileModel.getComment(),
                    fileModel.getUpdatedDate()
            );
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(files);
    }

    public ResponseEntity<List<FileDTO>> doSmtng() {
        List<FileDTO> dtos = fileRepository.findAll().stream().map(fileModel -> mapper.map(fileModel, FileDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(dtos);
    }

    public ResponseEntity<?> deleteFileByID(String id) {
        try {
            fileRepository.deleteById(id);
            return ResponseEntity.ok().body(new ResponseMessage("File deleted"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public ResponseEntity<List<FileDTO>> findFilesByName(String name) {
        List<FileDTO> files = getFilteredFilesInStorage(name).map(fileModel -> {
            String fileDownloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(fileModel.getId())
                    .toUriString();
            return new FileDTO(
                    fileModel.getId(),
                    fileModel.getName(),
                    fileDownloadURL,
                    (long) fileModel.getData().length,
                    fileModel.getFormat(),
                    fileModel.getDate(),
                    fileModel.getComment(),
                    fileModel.getUpdatedDate()
            );
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(files);
    }

    public ResponseEntity<List<FileDTO>> findFilesByDates(LocalDateTime from, LocalDateTime to) {
        List<FileDTO> files = getFilteredFilesInStorageByDate(from, to).map(fileModel -> {
            String fileDownloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(fileModel.getId())
                    .toUriString();
            return new FileDTO(
                    fileModel.getId(),
                    fileModel.getName(),
                    fileDownloadURL,
                    (long) fileModel.getData().length,
                    fileModel.getFormat(),
                    fileModel.getDate(),
                    fileModel.getComment(),
                    fileModel.getUpdatedDate()
            );
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(files);
    }
}
