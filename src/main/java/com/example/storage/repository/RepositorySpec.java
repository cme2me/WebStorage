package com.example.storage.repository;

import com.example.storage.model.FileModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class RepositorySpec {
    public Specification<FileModel> nameAndFormatAndDates(String name, String format, LocalDateTime from, LocalDateTime to) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), from));
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), to));
            predicates.add(criteriaBuilder.equal(root.get("format"), format));
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }


}
