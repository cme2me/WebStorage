package com.example.storage.repository;

import com.example.storage.model.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileModel, UUID>, JpaSpecificationExecutor<FileModel> /*todo сделать спеку на фильтр*/, FileCustomRepository {
    List<FileModel> findByName(String name);
}
