package com.overseer.dao.impl;

import com.overseer.dao.ProgressStatusDao;
import com.overseer.model.ProgressStatus;
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
 * Implementation of {@link ProgressStatusDao} interface.
 * </p>
 */
@Transactional
@Repository
@RequiredArgsConstructor
public class ProgressStatusDaoImpl implements ProgressStatusDao {
    private static final String SELECT_PROGRESS_STATUS_BY_ID = "SELECT * FROM progress_status ps WHERE ps.id = :id";

    private static final String SELECT_PROGRESS_STATUS_BY_NAME = "SELECT * FROM progress_status ps WHERE ps.name = :name";

    private static final String SELECT_ALL_PROGRESS_STATUSES = "SELECT * FROM progress_status";

    private static final String INSERT_PROGRESS_STATUS = "INSERT INTO progress_status (name) VALUES (:name)";

    private static final String UPDATE_PROGRESS_STATUS = "UPDATE progress_status SET name =: name WHERE id = :id";

    private static final String DELETE_PROGRESS_STATUS_BY_ID = "DELETE FROM progress_status ps WHERE ps.id = :id";

    private final NamedParameterJdbcOperations jdbc;

    /**
     * {@inheritDoc}.
     */
    @Override
    public ProgressStatus save(ProgressStatus progressStatus) {
        Assert.notNull(progressStatus, "progressStatus must not be null");
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(progressStatus);
        if (progressStatus.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(INSERT_PROGRESS_STATUS, sqlParameterSource, keyHolder, new String[]{"id"});
            long generatedId = keyHolder.getKey().longValue();
            progressStatus.setId(generatedId);
        } else {
            jdbc.update(UPDATE_PROGRESS_STATUS, sqlParameterSource);
        }
        return progressStatus;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public ProgressStatus findOne(Long id) {
        Assert.notNull(id, "ID must not be null");
        try {
            return jdbc.queryForObject(SELECT_PROGRESS_STATUS_BY_ID,
                    new MapSqlParameterSource("id", id),
                    BeanPropertyRowMapper.newInstance(ProgressStatus.class));
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(ProgressStatus progressStatus) {
        Assert.notNull(progressStatus, "progressStatus must not be null");
        delete(progressStatus.getId());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Long id) {
        Assert.notNull(id, "ID must not be null");
        jdbc.update(DELETE_PROGRESS_STATUS_BY_ID, new MapSqlParameterSource("id", id));
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
    public List<ProgressStatus> findAll() {
        return jdbc.query(SELECT_ALL_PROGRESS_STATUSES, BeanPropertyRowMapper.newInstance(ProgressStatus.class));
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public ProgressStatus findByName(String name) {
        Assert.notNull(name, "name must not be null");
        try {
            return jdbc.queryForObject(SELECT_PROGRESS_STATUS_BY_NAME,
                    new MapSqlParameterSource("name", name),
                    BeanPropertyRowMapper.newInstance(ProgressStatus.class));
        } catch (DataAccessException e) {
            return null;
        }
    }
}
