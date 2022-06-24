package com.example.storage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StorageApplicationTests {

    @Autowired
    private TestRepositoryMethods testRepositoryMethods;

    @Test
    @DisplayName("Test create model method")
    public void testCreateModel() {
        testRepositoryMethods.createTestFile();
    }

    @Test
    @DisplayName("Test MockRepo and MockFileModel not null")
    public void testRepoAndModelNotNull() {
        testRepositoryMethods.testNotNullMockObjects();
    }

    @Test
    @DisplayName("Test save file")
    public void testServiceUpload() {
        testRepositoryMethods.testServiceSave();
    }

    @Test
    @DisplayName("Test delete file")
    public void testServiceDelete() {
        testRepositoryMethods.testServiceDelete();
    }

    @Test
    @DisplayName("Test update file")
    public void testServiceUpdate() {
        testRepositoryMethods.testServiceUpdate();
    }

    @Test
    @DisplayName("Test find by name")
    public void testFindByName() {
        testRepositoryMethods.testRepoFindByName();
    }

    @Test
    @DisplayName("Test download file")
    public void testGetDownloadLink() {
        testRepositoryMethods.testDownloadLink();
    }
}
