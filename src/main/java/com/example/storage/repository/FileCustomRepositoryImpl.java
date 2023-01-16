package com.example.storage.repository;

import com.example.storage.model.FileEntity;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@EnableAutoConfiguration
public class FileCustomRepositoryImpl implements FileCustomRepository {
    private final EntityManager em;
    private CriteriaBuilder cb;
    private CriteriaQuery<FileEntity> cq;

    public FileCustomRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<FileEntity> findByName(String name) {
        cb = em.getCriteriaBuilder();
        cq = cb.createQuery(FileEntity.class);
        Root<FileEntity> file = cq.from(FileEntity.class);
        Predicate fileNamePredicate = cb.like(file.<String>get("name"), "%" + name + "%");
        cq.where(fileNamePredicate);
        TypedQuery<FileEntity> query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<FileEntity> findByFromDateAndToDate(LocalDateTime from, LocalDateTime to) {
        cb = em.getCriteriaBuilder();
        cq = cb.createQuery(FileEntity.class);
        Root<FileEntity> file = cq.from(FileEntity.class);
        List<Predicate> conditionsList = new ArrayList<>();
        Predicate onStart = cb.greaterThanOrEqualTo(file.get("date"), from);
        Predicate onEnd = cb.lessThanOrEqualTo(file.get("date"), to);
        conditionsList.add(onStart);
        conditionsList.add(onEnd);
        cq.where(conditionsList.toArray(new Predicate[]{}));
        TypedQuery<FileEntity> query = em.createQuery(cq);
        return query.getResultList();
    }

}
