package com.example.storage.configuration;

import com.example.storage.dto.FileDTO;
import com.example.storage.model.FileModel;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
/*    @Bean
    public FileModel fileModel() {
        return new FileModel();
    }

    @Bean
    public FileDTO fileDTO() {
        return new FileDTO();
    }*/

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
