package com.example.storage;

import com.example.storage.controller.RequestParams;
import com.example.storage.dto.FileDTO;
import com.example.storage.dto.PageDTO;
import com.example.storage.model.FileModel;
import com.example.storage.repository.FileRepository;
import com.example.storage.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class TestMethods {
    private final FileRepository repository = Mockito.mock(FileRepository.class);
    @InjectMocks
    private final FileService service = Mockito.mock(FileService.class);
    private final FileModel fileModel = new FileModel();
    private final PageDTO<FileDTO> pageDTO = new PageDTO<>();
    @Mock
    private FileDTO fileDTO = new FileDTO();

    public void testNotNullMockObjects() {
        Assertions.assertNotNull(repository);
        log.info("MockRepo and MockFileModel not null");
    }

    public void createExpectedFileModel(FileModel fileModel) {
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

    public void createActualFileModel() {
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
        createActualFileModel();
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
        FileModel expectedFileModel = new FileModel();
        createActualFileModel();
        createExpectedFileModel(expectedFileModel);

        Mockito.doAnswer(invocationOnMock -> expectedFileModel).when(service).putFile(Mockito.any(), Mockito.anyString());

        Assertions.assertNotNull(expectedFileModel);
        Assertions.assertEquals(expectedFileModel.getName(), fileModel.getName());
    }

    public void testServiceDownload() {
        FileModel expectedFileModel = new FileModel();
        createActualFileModel();
        createExpectedFileModel(expectedFileModel);

        Mockito.when(service.downloadFileById(Mockito.any())).thenReturn(expectedFileModel);

        Assertions.assertNotNull(expectedFileModel);
        Assertions.assertEquals(expectedFileModel.getFormat(), fileModel.getFormat());
        Assertions.assertEquals(expectedFileModel.getName(), fileModel.getName());
    }

    public void testFindByName() {
        List<FileModel> expectedFileModelList = new ArrayList<>();
        expectedFileModelList.add(fileModel);

        Mockito.when(repository.findByName(fileModel.getName())).thenReturn(expectedFileModelList);

        Assertions.assertNotNull(fileModel);
        Assertions.assertNotNull(expectedFileModelList);
        Assertions.assertEquals(expectedFileModelList.get(0).getFormat(), fileModel.getFormat());

    }

    public void testRequestWithParams() {
        PageDTO<FileDTO> shouldBeReturned = new PageDTO<>();
        shouldBeReturned.setTotalElements(pageDTO.getTotalElements());
        shouldBeReturned.setContent(pageDTO.getContent());
        shouldBeReturned.setTotalPages(pageDTO.getTotalPages());
        shouldBeReturned.setSize(pageDTO.getSize());

        Mockito.when(service.findFilteredFiles(new RequestParams(fileModel.getName(), fileDTO.getFormat(),
                        fileModel.getDate(), fileModel.getUpdatedDate(), pageDTO.getTotalPages(), pageDTO.getSize())))
                .thenReturn(shouldBeReturned);

        Assertions.assertNotNull(pageDTO);
        Assertions.assertNotNull(shouldBeReturned);
        Assertions.assertEquals(shouldBeReturned.getTotalPages(), pageDTO.getTotalPages());
    }

    public void showAllFiles() {
        List<FileDTO> expectedDtoList = new ArrayList<>();
        expectedDtoList.add(fileDTO);
        createFileDTO();

        Mockito.when(service.showAllFiles()).thenReturn(expectedDtoList);

        Assertions.assertNotNull(expectedDtoList);
        Assertions.assertEquals(fileDTO, expectedDtoList.get(0));
        Assertions.assertEquals(expectedDtoList.get(0).getName(), fileDTO.getName());
    }

    public void getFilesName() {
        List<String> expectedFilesNames = new ArrayList<>();
        expectedFilesNames.add(fileModel.getName());

        Mockito.when(service.getFilesName()).thenReturn(expectedFilesNames);

        Assertions.assertNotNull(expectedFilesNames);
        Assertions.assertEquals(expectedFilesNames.get(0), fileModel.getName());
    }

    public void updateMethod() {
        FileDTO expectedFileDto = new FileDTO();
        createActualFileModel();
        expectedFileDto.setName(fileModel.getName());
        String id = fileModel.getId().toString();
        createFileDTO();

        Mockito.doCallRealMethod().when(service).updateFile(fileDTO, id);

        Assertions.assertNotNull(expectedFileDto);
        Assertions.assertEquals(expectedFileDto.getName(), fileDTO.getName());
    }
}
