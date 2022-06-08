package com.example.storage.repository;

import com.example.storage.model.FileModel;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
@Component
@EnableAutoConfiguration
public class FileCustomRepositoryImpl implements FileCustomRepository{
    private final EntityManager em;

    public FileCustomRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<FileModel> findByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(FileModel.class);
        Root<FileModel> file = cq.from(FileModel.class);
        Predicate fileNamePredicate = cb.like(file.<String>get("name"), "%"+name+"%");
        cq.where(fileNamePredicate);
        TypedQuery<FileModel> query = em.createQuery(cq);
        return query.getResultList();
    }
}
