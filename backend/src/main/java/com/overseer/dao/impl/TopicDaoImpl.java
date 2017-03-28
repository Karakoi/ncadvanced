package com.overseer.dao.impl;

import static com.overseer.util.ValidationUtil.validate;

import com.overseer.dao.TopicDao;
import com.overseer.model.Message;
import com.overseer.model.Role;
import com.overseer.model.Topic;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Implementation of {@link TopicDao} interface.
 * </p>
 */
@Repository
public class TopicDaoImpl extends CrudDaoImpl<Topic> implements TopicDao {

    @Autowired
    private RoleDaoImpl roleDao;

    @Override
    public Topic findOne(Long id) {
        Assert.notNull(id, "id must not be null");
        String findOneQuery = this.getFindOneQuery();
        try {
            return jdbc().query(findOneQuery,
                    new MapSqlParameterSource("id", id),
                    new TopicOneObjectExtractor());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Topic save(Topic entity) {
        validate(entity);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("title", entity.getTitle());
        parameterSource.addValue("description", entity.getDescription());
        String insertQuery = this.getInsertQuery();

        // inserting topic in db
        if (entity.isNew()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.jdbc().update(insertQuery, parameterSource, keyHolder, new String[]{"id"});
            long generatedId = keyHolder.getKey().longValue();
            entity.setId(generatedId);
        } else {
            this.jdbc().update(insertQuery, parameterSource);
            parameterSource = new MapSqlParameterSource("topic_id", entity.getId());
            this.jdbc().update(getDeleteAllByTopicIdQuery(), parameterSource);
        }

        // inserting roles as topic_to_role db table
        List<Role> rolesOfEntity = entity.getRoles();
        for (int i = 0; i < rolesOfEntity.size(); i++) {
            Role currentRoleWithoutId = rolesOfEntity.get(i);
            Role currentRoleWithId = roleDao.findByName(currentRoleWithoutId.getName());
            currentRoleWithoutId.setId(currentRoleWithId.getId());
            saveTopicRole(currentRoleWithId.getId(), entity.getId());
        }

        return entity;
    }

    /**
     * This method save role for topic.
     * @param roleId id of role.
     * @param topicId id of topic.
     */
    private void saveTopicRole(Long roleId, Long topicId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("role_id", roleId);
        parameterSource.addValue("topic_id", topicId);
        String insertQuery = this.getInsertQueryForRole();
        this.jdbc().update(insertQuery, parameterSource);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Topic> findUserTopics(Long userId) {
        Assert.notNull(userId, "user id must not be null");
        return this.jdbc().query(this.queryService().getQuery("topic.findUserTopics"),
                new MapSqlParameterSource("userId", userId),
                new TopicObjectsExtractor());
    }

    @Override
    public List<Topic> fetchPage(int pageSize, int pageNumber) {
        Assert.state(pageNumber > 0, "Must be greater then 0");
        val parameterSource = new MapSqlParameterSource("limit", pageSize);
        parameterSource.addValue("offset", pageSize * (pageNumber - 1));
        String findAllQuery = this.getFindAllQuery() + this.getFetchPageQuery();
        return this.jdbc().query(findAllQuery, parameterSource, new TopicObjectsExtractor());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Message saveTopicMessage(Message message) {
        Assert.notNull(message, "message must not be null");
        val parameterSource = new MapSqlParameterSource("text", message.getText());
        parameterSource.addValue("senderId", message.getSender().getId());
        parameterSource.addValue("topicId", message.getTopic().getId());
        parameterSource.addValue("dateAndTime", message.getDateAndTime());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbc().update(this.queryService().getQuery("topic.saveTopicMessage"),
                parameterSource,
                keyHolder, new String[]{"id"});
        long generatedId = keyHolder.getKey().longValue();
        message.setId(generatedId);
        return message;
    }

    @Override
    protected String getInsertQuery() {
        return this.queryService().getQuery("topic.insert");
    }

    private String getInsertQueryForRole() {
        return this.queryService().getQuery("topic_to_role.insert");
    }

    private String getDeleteAllByTopicIdQuery() {
        return this.queryService().getQuery("topic_to_role.delete_all_by_topic_id");
    }

    private String getFetchPageQuery() {
        return this.queryService().getQuery("topic.fetchPage");
    }

    @Override
    protected String getFindOneQuery() {
        return this.queryService().getQuery("topic.findOne");
    }

    @Override
    protected String getDeleteQuery() {
        return this.queryService().getQuery("topic.delete");
    }

    @Override
    protected String getExistsQuery() {
        return this.queryService().getQuery("topic.exists");
    }

    @Override
    protected String getFindAllQuery() {
        return this.queryService().getQuery("topic.findAll");
    }

    @Override
    protected String getCountQuery() {
        return this.queryService().getQuery("topic.count");
    }

    @Override
    protected RowMapper<Topic> getMapper() {
        return (resultSet, rowNum) -> {
            return null; // in this class we use ResultSetExtractor instead of RowMapper
        };
    }

    /**
     * Inner class that allow us to get a list of {@link Topic} from ResultSet object from database.
     */
    private class TopicObjectsExtractor implements ResultSetExtractor<List<Topic>> {
        @Override
        public List<Topic> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Long, Topic> map = new HashMap<>();

            while (resultSet.next()) {
                Long id = resultSet.getLong("topic_id");
                Topic topic = map.get(id);

                if (topic == null) {
                    topic = new Topic();
                    topic.setId(resultSet.getLong("topic_id"));
                    topic.setTitle(resultSet.getString("title"));
                    topic.setDescription(resultSet.getString("description"));
                    map.put(id, topic);
                }
                Role role = new Role(resultSet.getString("name"));
                role.setId(resultSet.getLong("role_id"));
                topic.getRoles().add(role);
            }
            return new ArrayList<Topic>(map.values());
        }
    }

    /**
     * Inner class that allow us to get a {@link Topic} from ResultSet object from database.
     */
    private class TopicOneObjectExtractor implements ResultSetExtractor<Topic> {
        @Override
        public Topic extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Topic topic = new Topic();
            boolean haveGeneralTopicData = false; // true, if we already get all topics fields except roles

            while (resultSet.next()) {
                Long id = resultSet.getLong("topic_id");
                if (!haveGeneralTopicData) {
                    topic.setId(id);
                    topic.setTitle(resultSet.getString("title"));
                    topic.setDescription(resultSet.getString("description"));
                    haveGeneralTopicData = true;
                }
                Role role = new Role(resultSet.getString("name"));
                role.setId(resultSet.getLong("role_id"));
                topic.getRoles().add(role);
            }
            if (!haveGeneralTopicData) {
                return null;
            }
            return topic;
        }
    }
}