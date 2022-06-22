package com.example.storage;

import com.example.storage.mapper.EntityMapper;
import com.example.storage.model.FileModel;
import com.example.storage.repository.FileRepository;
import com.example.storage.repository.RepositorySpec;
import com.example.storage.service.FileService;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Component
public class TestRepositoryMethods {
    @InjectMocks
    @Autowired
    private FileService service;
    private FileModel fileModel;
    private FileRepository repository = Mockito.mock(FileRepository.class);
    private ModelBuilder builder;
    private RepositorySpec specification;
    private EntityMapper mapper;

    public void init() {
        service = new FileService(this.repository, this.specification, this.mapper);
    }

    /* public void testFileSave() throws IOException {
        init();
        byte[] fileBytes = Files.readAllBytes(Paths.get("src/test/resources/1.txt"));
        FileModel modelToSave = FileModel.builder()
                .id("123442")
                .name("1.txt")
                .comment("testFileComment")
                .date(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .format("text/txt")
                .data(fileBytes)
                .build();
        when(repository.save(any(FileModel.class))).thenReturn(modelToSave);
        MultipartFile file = new MockMultipartFile("1.txt", "1.txt", "text/txt", fileBytes);
        when(service.putFile(file, modelToSave.getComment())).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
        Assertions.assertEquals("1.txt", modelToSave.getName());
        Assertions.assertEquals("1.txt", file.getName());
        Assertions.assertEquals(modelToSave.getFormat(), file.getContentType());
        Assertions.assertEquals(modelToSave.getData(), file.getBytes());
        Assertions.assertNotNull(modelToSave.getDate());
        Assertions.assertNotNull(modelToSave.getUpdatedDate());
        Assertions.assertNotNull(modelToSave.getComment());
        Assertions.assertNotNull(modelToSave);
        Assertions.assertNotNull(file);
        System.out.println(modelToSave);
    }*/

    public void testFindAll() throws IOException {
        init();
        byte[] fileBytes = Files.readAllBytes(Paths.get("src/test/resources/1.txt"));
        FileModel createdModel = new FileModel();
        createdModel.setId(UUID.fromString("123442"));
        createdModel.setDate(LocalDateTime.now());
        createdModel.setComment("testComment");
        createdModel.setName("testFileName");
        createdModel.setUpdatedDate(LocalDateTime.now());
        createdModel.setData(fileBytes);
        createdModel.setFormat("text/txt");
        List<FileModel> filesList = new ArrayList<>();
        filesList.add(createdModel);

        when(repository.findAll()).thenReturn(filesList);
        Assertions.assertNotNull(filesList);
        Assertions.assertEquals(filesList.get(0), createdModel);
        System.out.println(filesList);
    }

    public void createFile() throws IOException {
        init();
        byte[] fileBytes = Files.readAllBytes(Paths.get("src/test/resources/1.txt"));
        FileModel createdModel = new FileModel();
        createdModel.setId(UUID.fromString("123442"));
        createdModel.setDate(LocalDateTime.now());
        createdModel.setComment("testComment");
        createdModel.setName("testFileName");
        createdModel.setUpdatedDate(LocalDateTime.now());
        createdModel.setData(fileBytes);
        createdModel.setFormat("text/txt");

        when(repository.save(any(FileModel.class))).thenReturn(fileModel);
        Assertions.assertNotNull(createdModel);
        System.out.println(createdModel);


    }

}
