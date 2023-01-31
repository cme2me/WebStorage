package com.example.storage;

import com.example.storage.controller.RequestParams;
import com.example.storage.dto.FileDTO;
import com.example.storage.dto.PageDTO;
import com.example.storage.mapper.EntityMapper;
import com.example.storage.model.FileEntity;
import com.example.storage.repository.FileRepository;
import com.example.storage.repository.RepositorySpec;
import com.example.storage.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class TestMethods {
    private final FileEntity fileEntity = new FileEntity();
    private final PageDTO<FileDTO> pageDTO = new PageDTO<>();
    private final FileDTO fileDTO = new FileDTO();

    private final FileRepository repository = Mockito.mock(FileRepository.class);

    private final RepositorySpec spec = Mockito.mock(RepositorySpec.class);

    private final EntityMapper mapper = Mockito.mock(EntityMapper.class);

    private final FileService service = new FileService(repository, spec, mapper);

    public void testNotNullMockObjects() {
        Assertions.assertNotNull(repository);
        log.info("MockRepo and MockFileModel not null");
    }

    public void createExpectedFileEntity(FileEntity fileEntity) {
        byte[] fileBytes = new byte[]{32, 4, 12, 66, 86, 34};
        fileEntity.setId(UUID.fromString("4644765f-38db-4baf-a8e9-ae4704a2d6cd"));
        fileEntity.setData(fileBytes);
        fileEntity.setFormat("text/txt");
        fileEntity.setComment("testComment");
        fileEntity.setUpdatedDate(LocalDateTime.now());
        fileEntity.setDate(LocalDateTime.now());
        fileEntity.setName("testName");
        Assertions.assertNotNull(fileEntity.getData());
        Assertions.assertNotNull(fileEntity.getId());
        Assertions.assertNotNull(fileEntity.getComment());
        Assertions.assertNotNull(fileEntity.getDate());
        Assertions.assertNotNull(fileEntity.getFormat());
        Assertions.assertNotNull(fileEntity.getUpdatedDate());
        Assertions.assertNotNull(fileEntity.getName());
    }

    public void createActualFileEntity() {
        byte[] fileBytes = new byte[]{32, 4, 12, 66, 86, 34};
        fileEntity.setId(UUID.fromString("4644765f-38db-4baf-a8e9-ae4704a2d6cd"));
        fileEntity.setData(fileBytes);
        fileEntity.setFormat("text/txt");
        fileEntity.setComment("testComment");
        fileEntity.setUpdatedDate(LocalDateTime.now());
        fileEntity.setDate(LocalDateTime.now());
        fileEntity.setName("testName");
        Assertions.assertNotNull(fileEntity.getData());
        Assertions.assertNotNull(fileEntity.getId());
        Assertions.assertNotNull(fileEntity.getComment());
        Assertions.assertNotNull(fileEntity.getDate());
        Assertions.assertNotNull(fileEntity.getFormat());
        Assertions.assertNotNull(fileEntity.getUpdatedDate());
        Assertions.assertNotNull(fileEntity.getName());
    }

    public void initRequestParams() {
        createFileDTO();
        List<FileDTO> fileDTOS = new ArrayList<>();
        fileDTOS.add(fileDTO);
        pageDTO.setTotalPages(4);
        pageDTO.setTotalElements(4);
        pageDTO.setSize(2);
        pageDTO.setContent(fileDTOS);
    }

    public void createFileDTO() {
        createActualFileEntity();
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileEntity.getId().toString())
                .toUriString();
        fileDTO.setSize(fileEntity.getData().length);
        fileDTO.setFormat(fileEntity.getFormat());
        fileDTO.setComment(fileEntity.getComment());
        fileDTO.setId(fileEntity.getId());
        fileDTO.setName(fileEntity.getName());
        fileDTO.setDate(fileEntity.getDate());
        fileDTO.setUpdatedDate(fileEntity.getUpdatedDate());
        fileDTO.setDownloadURL(downloadURL);
        Assertions.assertNotNull(fileDTO);
        Assertions.assertNotNull(fileDTO.getName());
        Assertions.assertNotNull(fileDTO.getDate());
        Assertions.assertNotNull(fileDTO.getComment());
        Assertions.assertNotNull(fileDTO.getUpdatedDate());
        Assertions.assertNotNull(fileDTO.getId());
        Assertions.assertNotNull(fileDTO.getFormat());
        Assertions.assertNotNull(fileDTO.getDownloadURL());
        Assertions.assertEquals(fileDTO.getName(), fileEntity.getName());
        Assertions.assertEquals(fileDTO.getFormat(), fileEntity.getFormat());
        Assertions.assertEquals(fileDTO.getSize(), fileEntity.getData().length);
    }

    public void testServiceSave() {
        FileEntity expectedFileEntity = new FileEntity();
        createActualFileEntity();
        createExpectedFileEntity(expectedFileEntity);

        Mockito.when(repository.save(null)).thenReturn(RuntimeException.class);

        Assertions.assertEquals(expectedFileEntity.getName(), fileEntity.getName());
    }

    public void testServiceDownload() {
        FileEntity expectedFileEntity = new FileEntity();
        createActualFileEntity();
        createExpectedFileEntity(expectedFileEntity);

        service.downloadFileById(fileEntity.getId().toString());
        Mockito.when(service.downloadFileById(Mockito.any())).thenReturn(expectedFileEntity);

        Assertions.assertNotNull(expectedFileEntity);
        Assertions.assertEquals(expectedFileEntity.getFormat(), fileEntity.getFormat());
        Assertions.assertEquals(expectedFileEntity.getName(), fileEntity.getName());
    }

    public void testFindByName() {
        List<FileEntity> expectedFileEntityList = new ArrayList<>();
        expectedFileEntityList.add(fileEntity);

        Mockito.when(repository.findByName(fileEntity.getName())).thenReturn(expectedFileEntityList);

        Assertions.assertNotNull(fileEntity);
        Assertions.assertNotNull(expectedFileEntityList);
        Assertions.assertEquals(expectedFileEntityList.get(0).getFormat(), fileEntity.getFormat());

    }

    public void testRequestWithParams() {
        PageDTO<FileDTO> shouldBeReturned = new PageDTO<>();
        shouldBeReturned.setTotalElements(pageDTO.getTotalElements());
        shouldBeReturned.setContent(pageDTO.getContent());
        shouldBeReturned.setTotalPages(pageDTO.getTotalPages());
        shouldBeReturned.setSize(pageDTO.getSize());

        Mockito.when(service.findFilteredFiles(new RequestParams(fileEntity.getName(), fileDTO.getFormat(),
                        fileEntity.getDate(), fileEntity.getUpdatedDate(), pageDTO.getTotalPages(), pageDTO.getSize())))
                .thenReturn(shouldBeReturned);

        Assertions.assertNotNull(pageDTO);
        Assertions.assertNotNull(shouldBeReturned);
        Assertions.assertEquals(shouldBeReturned.getTotalPages(), pageDTO.getTotalPages());
    }

    public void showAllFiles() {
        service.showAllFiles();

        Mockito.verify(repository).findAll();


    }

    public void getFilesName() {
        List<String> expectedFilesNames = new ArrayList<>();
        expectedFilesNames.add(fileEntity.getName());

        Mockito.when(service.getFilesName()).thenReturn(expectedFilesNames);

        Assertions.assertNotNull(expectedFilesNames);
        Assertions.assertEquals(expectedFilesNames.get(0), fileEntity.getName());
    }

    public void updateMethod() {
        FileDTO expectedFileDto = new FileDTO();
        createActualFileEntity();
        expectedFileDto.setName(fileEntity.getName());
        String id = fileEntity.getId().toString();
        createFileDTO();

        Mockito.doReturn(expectedFileDto).when(service).updateFile(fileDTO, id);

        Assertions.assertNotNull(expectedFileDto);
        Assertions.assertEquals(expectedFileDto.getName(), fileDTO.getName());
    }

    public void downloadZip() throws IOException {
        List<UUID> uuidsList = new ArrayList<>();
        createActualFileEntity();
        uuidsList.add(fileEntity.getId());
        StreamingResponseBody srb = Mockito.mock(StreamingResponseBody.class);


    }
}
