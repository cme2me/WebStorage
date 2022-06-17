package com.example.storage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/*@Entity
@Table(name = "storage_files")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SeconModel {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")

    private UUID id;

    @OneToOne
    private FileModel fileModel;
    //todo понять как происходит бесконечная рекурсия при связи двух сущностей из-за toString() || ???
}*/
