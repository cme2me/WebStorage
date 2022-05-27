package com.example.storage.specification;

import com.example.storage.model.FileModel;
import org.springframework.data.jpa.domain.Specification;

public class FileSpecification {
    public static Specification<FileModel> chooseFileTypes(String fileType) {
        return null;
    }
}
