package com.overseer.dao.impl;

import com.overseer.dao.PriorityStatusDao;
import com.overseer.model.PriorityStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;


/**
 * <p>
 * Implementation of {@link PriorityStatusDao} interface.
 * </p>
 */
@Transactional
@Repository
@RequiredArgsConstructor
public class PriorityStatusDaoImpl implements PriorityStatusDao {

    private static final String SELECT_PRIORITY_STATUS_BY_ID = "SELECT * FROM priority_status ps WHERE ps.id = :id";

    private static final String DELETE_PRIORITY_STATUS_BY_ID = "DELETE FROM priority_status ps WHERE ps.id = :id";

    private static final String SELECT_PRIORITY_STATUS_BY_NAME = "SELECT * FROM priority_status ps WHERE ps.name = :name";

    private static final String SELECT_ALL_PRIORITY_STATUSES = "SELECT * FROM priority_status";

    private static final String UPDATE_PRIORITY_STATUS = "UPDATE priority_status SET name =: name WHERE id = :id";

    private static final String INSERT_PRIORITY_STATUS = "INSERT INTO priority_status (name) VALUES (:name)";

    private final NamedParameterJdbcOperations jdbc;

    /**
     * {@inheritDoc} .
     */
    @Override
    public PriorityStatus save(PriorityStatus priorityStatus) {
        Assert.notNull(priorityStatus, "priorityStatus must not be null");
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(priorityStatus);
        if (priorityStatus.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(INSERT_PRIORITY_STATUS, sqlParameterSource, keyHolder, new String[]{"id"});
            long generatedId = keyHolder.getKey().longValue();
            priorityStatus.setId(generatedId);
        } else {
            jdbc.update(UPDATE_PRIORITY_STATUS, sqlParameterSource);
        }
        return priorityStatus;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public PriorityStatus findOne(Long id) {
        Assert.notNull(id, "ID must not be null");
        try {
            return jdbc.queryForObject(SELECT_PRIORITY_STATUS_BY_ID,
                    new MapSqlParameterSource("id", id),
                    BeanPropertyRowMapper.newInstance(PriorityStatus.class));
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(PriorityStatus priorityStatus) {
        Assert.notNull(priorityStatus, "priorityStatus must not be null");
        delete(priorityStatus.getId());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Long id) {
        Assert.notNull(id, "ID must not be null");
        jdbc.update(DELETE_PRIORITY_STATUS_BY_ID, new MapSqlParameterSource("id", id));
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean exists(Long id) {
        Assert.notNull(id, "ID must not be null");
        return findOne(id) != null;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<PriorityStatus> findAll() {
        return jdbc.query(SELECT_ALL_PRIORITY_STATUSES, BeanPropertyRowMapper.newInstance(PriorityStatus.class));
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public PriorityStatus findByName(String name) {
        Assert.notNull(name, "name must not be null");
        try {
            return jdbc.queryForObject(SELECT_PRIORITY_STATUS_BY_NAME,
                    new MapSqlParameterSource("name", name),
                    BeanPropertyRowMapper.newInstance(PriorityStatus.class));
        } catch (DataAccessException e) {
            return null;
        }
    }
}
