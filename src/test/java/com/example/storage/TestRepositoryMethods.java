package com.example.storage;

import com.example.storage.controller.FileController;
import com.example.storage.controller.RequestParams;
import com.example.storage.dto.FileDTO;
import com.example.storage.mapper.EntityMapper;
import com.example.storage.mapper.EntityMapperImpl;
import com.example.storage.model.FileModel;
import com.example.storage.repository.FileRepository;
import com.example.storage.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class TestRepositoryMethods {

    private final FileRepository repository = Mockito.mock(FileRepository.class);
    private final FileService service = Mockito.mock(FileService.class);
    private FileModel fileModel = new FileModel();
    private FileDTO fileDTO = new FileDTO();
    @Mock
    private FileController controller = new FileController(this.service);
    @Mock
    private EntityMapper mapper = new EntityMapperImpl();


    public void testNotNullMockObjects() {
        Assertions.assertNotNull(repository);
        Assertions.assertNotNull(fileModel);
        log.info("MockRepo and MockFileModel not null");
    }


    public void createTestFile() {
        byte[] fileBytes = new byte[]{32, 4, 12, 66, 86, 34};
        fileModel.setId(UUID.fromString("4644765f-38db-4baf-a8e9-ae4704a2d6cd"));
        fileModel.setData(fileBytes);
        fileModel.setFormat("text/txt");
        fileModel.setComment("testComment");
        fileModel.setUpdatedDate(LocalDateTime.now());
        fileModel.setDate(LocalDateTime.now());
        fileModel.setName("testName");
        Assertions.assertNotNull(fileModel.getData());
        Assertions.assertNotNull(fileModel.getId());
        Assertions.assertNotNull(fileModel.getComment());
        Assertions.assertNotNull(fileModel.getDate());
        Assertions.assertNotNull(fileModel.getFormat());
        Assertions.assertNotNull(fileModel.getUpdatedDate());
        Assertions.assertNotNull(fileModel.getName());
    }

    public void createFileDTO() {
        createTestFile();
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileModel.getId().toString())
                .toUriString();
        fileDTO.setSize(fileModel.getData().length);
        fileDTO.setFormat(fileModel.getFormat());
        fileDTO.setComment(fileModel.getComment());
        fileDTO.setId(fileModel.getId());
        fileDTO.setName(fileModel.getName());
        fileDTO.setDate(fileModel.getDate());
        fileDTO.setUpdatedDate(fileModel.getUpdatedDate());
        fileDTO.setDownloadURL(downloadURL);
        Assertions.assertNotNull(fileDTO);
        Assertions.assertNotNull(fileDTO.getName());
        Assertions.assertNotNull(fileDTO.getDate());
        Assertions.assertNotNull(fileDTO.getComment());
        Assertions.assertNotNull(fileDTO.getUpdatedDate());
        Assertions.assertNotNull(fileDTO.getId());
        Assertions.assertNotNull(fileDTO.getFormat());
        Assertions.assertNotNull(fileDTO.getDownloadURL());
        Assertions.assertEquals(fileDTO.getName(), fileModel.getName());
        Assertions.assertEquals(fileDTO.getFormat(), fileModel.getFormat());
        Assertions.assertEquals(fileDTO.getSize(), fileModel.getData().length);
    }

    public void testServiceSave() {
        createTestFile();
        service.putFile(new MockMultipartFile(fileModel.getName(), fileModel.getData()), fileModel.getComment());
        repository.save(fileModel);
        Assertions.assertEquals("testName", fileModel.getName());
        System.out.println(repository.save(fileModel));
        Mockito.when(repository.save(fileModel)).thenReturn(fileModel);
        log.info("File uploaded");
    }

    public void testServiceDelete() {
        createTestFile();
        Assertions.assertNotNull(fileModel.getId());
        Mockito.when(service.downloadFileById(Mockito.any())).thenReturn(fileModel);
        service.deleteFileByID(fileModel.getId());
        log.info("File deleted");
    }

    public void testServiceUpdate() {
        createTestFile();
        createFileDTO();
        String id = String.valueOf(fileModel.getId());
        Assertions.assertNotNull(id);
        Assertions.assertNotNull(fileDTO);
        service.updateFile(fileDTO, id);
        log.info("File updated");
    }

    public void testRepoFindByName() {
        createTestFile();
        String fileName = fileModel.getName();
        List<FileModel> fileModelList = new ArrayList<>();
        repository.findByName(fileName);
        Assertions.assertEquals("testName", fileName);
        Mockito.when(repository.findByName(fileName)).thenReturn(fileModelList);
    }

    public void testDownloadLink() {
        createTestFile();
        String id = fileModel.getId().toString();
        Mockito.when(repository.findById(fileModel.getId())).thenReturn(Optional.of(fileModel));
        Optional<FileModel> optionalEntity = repository.findById(fileModel.getId());
        repository.save(fileModel);
        FileModel fileModelActual = service.downloadFileById(id);
        Optional<FileModel> optionalFileModel = repository.findById(UUID.fromString(id));
        Assertions.assertEquals(fileModel, fileModelActual);
    }
}
