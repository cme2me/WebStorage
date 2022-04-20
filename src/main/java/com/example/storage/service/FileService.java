package com.example.storage.service;

import com.example.storage.api.models.File;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private List<File> files = new ArrayList<>();
    private long ID = 0;
    {
        files.add(new File(ID++, 1L, 2L, "крапива", "exe", 123L, "хороший файл" ));
    }
    public List<File> showFiles() {
        return files;
    }
    public void saveFile(File file) {
        file.setId(++ID);
        files.add(file);
    }
    public void deleteFile(Long id) {
        files.removeIf(file -> file.getId().equals(id));
    }
    public File getFileById(Long id) {
        for (File file : files) {
            if (file.getId().equals(id)) return file;
            else {
                throw new IllegalArgumentException("Файл не найден");
            }
        }
        return null;
    }
}
