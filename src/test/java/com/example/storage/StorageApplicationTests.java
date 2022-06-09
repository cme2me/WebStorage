package com.example.storage;

import com.example.storage.repository.FileRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
class StorageApplicationTests {
    @MockBean
    FileRepository repository;
    private final MockMvc mockMvc;

    public StorageApplicationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }


    public void uploadFileSuccess() throws Exception {

    }
}
