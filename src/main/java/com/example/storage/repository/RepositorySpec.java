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
            if (name != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if (from != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), from));
            }
            if (to != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), to));
            }
            if (format != null) {
                predicates.add(criteriaBuilder.equal(root.get("format"), format));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }


}
