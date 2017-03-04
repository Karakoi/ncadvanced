package com.overseer.service.impl;

import com.overseer.dao.CrudDao;
import com.overseer.exception.entity.EntityAlreadyExistsException;
import com.overseer.exception.entity.NoSuchEntityException;
import com.overseer.model.AbstractEntity;
import com.overseer.service.CrudService;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@NoArgsConstructor
@Transactional
public abstract class CrudServiceImpl<T extends AbstractEntity> implements CrudService<T, Long> {

    private CrudDao<T, Long> crudDao;

    CrudServiceImpl(CrudDao<T, Long> crudDao) {
        this.crudDao = crudDao;
    }

    @Override
    public T create(T entity) throws EntityAlreadyExistsException {
        Assert.notNull(entity);
        if (entity.getId() != null) {
            throw new EntityAlreadyExistsException("Failed to perform create operation. Id was not null: " + entity);
        }
        return this.crudDao.save(entity);
    }

    @Override
    public T update(T entity) throws NoSuchEntityException {
        Assert.notNull(entity);
        if (entity.getId() != null) {
            throw new EntityAlreadyExistsException("Failed to perform update operation. Id was null: " + entity);
        }
        return this.crudDao.save(entity);
    }

    @Override
    public T findOne(Long id) throws NoSuchEntityException {
        Assert.notNull(id);
        T entity = this.crudDao.findOne(id);
        if (entity == null) {
            throw new NoSuchEntityException("Failed to retrieve entity with id " + id);
        }
        return entity;
    }

    @Override
    public void delete(T entity) {
        Assert.notNull(entity);
        this.crudDao.delete(entity);
    }

    @Override
    public void delete(Long id) {
        Assert.notNull(id);
        this.crudDao.delete(id);
    }

    @Override
    public boolean exists(Long id) {
        Assert.notNull(id);
        return this.crudDao.exists(id);
    }

    @Override
    public List<T> findAll() {
        return this.crudDao.findAll();
    }
}
