package com.example.storage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StorageApplicationTests {

    @Autowired
    private TestMethods testMethods;

    @Test
    @DisplayName("Test MockRepo and MockFileModel not null")
    public void testRepoAndModelNotNull() {
        testMethods.testNotNullMockObjects();
    }

    @Test
    @DisplayName("Test save file")
    public void testServiceUpload() {
        testMethods.testServiceSave();
    }

    @Test
    @DisplayName("Test download file")
    public void testServiceDownload() {
        testMethods.testServiceDownload();
    }

    @Test
    @DisplayName("Test find by name")
    public void testFindByName() {
        testMethods.testFindByName();
    }

    @Test
    @DisplayName("Test request with params")
    public void testRequestWithParams() {
        testMethods.testRequestWithParams();
    }

    @Test
    @DisplayName("Test show all files")
    public void testShowAllFiles() {
        testMethods.showAllFiles();
    }

    @Test
    @DisplayName("Test get file names")
    public void testGetFilesName() {
        testMethods.getFilesName();
    }

    @Test
    @DisplayName("Test update file")
    public void testUpdateMethod() {
        testMethods.updateMethod();
    }
}
