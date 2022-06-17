package com.example.storage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StorageApplicationTests {

    @Autowired
    private TestRepositoryMethods testRepositoryMethods;

    /*@Test
    @DisplayName("Test save method")
    public void saveFile() throws IOException {
        testLogic.testFileSave();
    }*/

    @Test
    @DisplayName("Test download method")
    public void downloadFile() {

    }

    @Test
    @DisplayName("Test get all files")
    public void findAllFiles() throws IOException {
        testRepositoryMethods.testFindAll();
    }

    @Test
    @DisplayName("Test save repo method")
    public void testCreateFile() throws IOException {
        testRepositoryMethods.createFile();
    }
}
