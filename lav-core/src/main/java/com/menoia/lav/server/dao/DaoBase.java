package com.menoia.lav.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.google.inject.persist.Transactional;

/**
 * Base class containing basic functions to database operations. It's an
 * abstract class and needs to be extended by a DAO class.
 * 
 * @author gmenoia
 * 
 * @param <T> Entity class
 */
public abstract class DaoBase<T> {

    private Class<T> entityClass;
    private EntityManager em;

    public DaoBase(EntityManager em, Class<T> entityClass) {

        this.em = em;
        this.entityClass = entityClass;
    }
    
    public Class<T> getEntityClass() {
        return entityClass;
    }

    @Transactional
    public T save(T entity) {

        em.persist(entity);
        return entity;
    }

    @Transactional
    public T update(T entity) {

        em.merge(entity);
        return entity;
    }

    @Transactional
    public List<T> findAll() {

        CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

    @Transactional
    public T get(Long id) {

        return em.find(entityClass, id);
    }

    @Transactional
    public void remove(Long id) {

        T p = get(id);
        em.remove(p);
    }

    @Transactional
    public Long countAll() {

        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(entityClass)));
        return em.createQuery(cq).getSingleResult();
    }

}
