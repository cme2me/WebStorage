package com.example.storage.entity.repository;

import com.example.storage.dto.FileDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileDTO, String> {

}
