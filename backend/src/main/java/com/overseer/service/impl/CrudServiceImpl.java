package com.overseer.service.impl;

import com.overseer.dao.CrudDao;
import com.overseer.exception.entity.EntityAlreadyExistsException;
import com.overseer.exception.entity.NoSuchEntityException;
import com.overseer.model.AbstractEntity;
import com.overseer.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Basic implementation of {@link CrudService} interface.
 *
 * @param <T> entity type.
 */
@Transactional
@Slf4j
public abstract class CrudServiceImpl<T extends AbstractEntity> implements CrudService<T, Long> {
    protected static final short DEFAULT_PAGE_SIZE = 20;

    private CrudDao<T, Long> crudDao;

    CrudServiceImpl(CrudDao<T, Long> crudDao) {
        this.crudDao = crudDao;
    }

    protected CrudServiceImpl() {
    }

    @Override
    public T create(T entity) throws EntityAlreadyExistsException {
        Assert.notNull(entity);
        if (!entity.isNew()) {
            throw new EntityAlreadyExistsException("Failed to perform create operation. Id was not null: " + entity);
        }
        return this.crudDao.save(entity);
    }

    @Override
    public T update(T entity) throws NoSuchEntityException {

        Assert.notNull(entity);
        if (entity.isNew()) {
            throw new NoSuchEntityException("Failed to perform update operation. Id was null: " + entity);
        }
        return this.crudDao.save(entity);
    }

    @Override
    public T findOne(Long id) throws NoSuchEntityException {
        Assert.notNull(id, "id must not be null");
        log.debug("Searching for entity with id: {}", id);
        T entity = this.crudDao.findOne(id);
        if (entity == null) {
            throw new NoSuchEntityException("Failed to retrieve entity with id " + id);
        }
        return entity;
    }

    @Override
    public void delete(T entity) {
        Assert.notNull(entity, "entity must not be null");
        this.crudDao.delete(entity);
    }

    @Override
    public void delete(Long id) {
        Assert.notNull(id, "id must not be null");
        log.debug("Deleting entity with id: {}", id);
        this.crudDao.delete(id);
    }

    @Override
    public boolean exists(Long id) {
        Assert.notNull(id, "id must not be null");
        log.debug("Checking if entity with id: {} exists", id);
        return this.crudDao.exists(id);
    }

    @Override
    public List<T> fetchPage(int pageNumber) {
        val list = this.crudDao.fetchPage(DEFAULT_PAGE_SIZE, pageNumber);
        log.debug("Fetched {} entities for page number: {}", list.size(), pageNumber);
        return list;
    }

    @Override
    public Long getCount() {
        val count = crudDao.count();
        log.debug("Counted {} entities", count);
        return count;
    }
}
