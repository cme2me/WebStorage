package com.example.storage.repository;

import com.example.storage.model.FileModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CustomFileRepositoryImpl implements CustomFileRepository{
    private EntityManager entityManager;
    @Autowired
    public CustomFileRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<FileModel> filterByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<FileModel> filesRoot = cq.from(FileModel.class);
        Predicate fileNamePredicate = cb.equal(filesRoot.get("name"), name);
        cq.where(fileNamePredicate);
        TypedQuery<FileModel> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
