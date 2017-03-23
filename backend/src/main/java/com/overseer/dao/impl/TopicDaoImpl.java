package com.overseer.dao.impl;

import com.overseer.dao.TopicDao;
import com.overseer.model.Message;
import com.overseer.model.Role;
import com.overseer.model.Topic;
import lombok.val;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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

import static com.overseer.util.ValidationUtil.validate;

/**
 * <p>
 * Implementation of {@link TopicDao} interface.
 * </p>
 */
@Repository
public class TopicDaoImpl extends CrudDaoImpl<Topic> implements TopicDao {

    /**
     * {@inheritDoc}.
     */
    /*@Override
    public List<Topic> findUserTopics(Long userId) {
        Assert.notNull(userId, "user id must not be null");
        return this.jdbc().query(this.queryService().getQuery("topic.findUserTopics"),
                new MapSqlParameterSource("userId", userId),
                this.getMapper());
    }*/
    @Override
    public List<Topic> findUserTopics(Long userId) {
        Assert.notNull(userId, "user id must not be null");
        /*TopicObjectExtractor topicObjectExtractor = new TopicObjectExtractor();*/
        Map<Long, Topic> map = new HashMap<>();
        return this.jdbc().query(this.queryService().getQuery("topic.findUserTopics"),
                new MapSqlParameterSource("userId", userId),
                new ResultSetExtractor<List<Topic>>() {
                    @Override
                    public List<Topic> extractData(ResultSet resultSet) throws SQLException, DataAccessException {


                        while(resultSet.next()){
                            Long id = resultSet.getLong("id");
                            Topic topic = map.get(id);

                            if(topic == null){
                                topic = new Topic();
                                topic.setId(resultSet.getLong("id"));
                                topic.setTitle(resultSet.getString("title"));
                                map.put(id, topic);
                            }
                            Role role = new Role(resultSet.getString("name"));
                            role.setId(resultSet.getLong("id"));
                            topic.getRole().add(role);
                        }
                        return new ArrayList<Topic>(map.values());
                    }
                });
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

    /*@Override
    public Topic save(Topic entity) {
        validate(entity);
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(entity);
        String insertQueryForTopic = this.getInsertQuery();
        String insertQueryForTopicRoles = this.getInsertRoleQuery(); // TODO: 23.03.2017
        if (entity.isNew()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.jdbc.update(insertQuery, sqlParameterSource, keyHolder, new String[]{"id"});
            long generatedId = keyHolder.getKey().longValue();
            entity.setId(generatedId);
        } else {
            this.jdbc.update(insertQuery, sqlParameterSource);
        }
        return entity;
    }*/

    @Override
    protected String getInsertQuery() {
        return this.queryService().getQuery("topic.insert");
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
        return this.queryService().getQuery("topic.fetchPage");
    }

    @Override
    protected String getCountQuery() {
        return this.queryService().getQuery("topic.count");
    }

    /*@Override
    protected String getFindByNameQuery() {
        return this.queryService().getQuery("topic.findByName");
    }*/ // todo ???

    @Override
    protected RowMapper<Topic> getMapper() {
        return (resultSet, rowNum) -> {
            /*Role role = new Role(resultSet.getString("name"));
            role.setId(resultSet.getLong("id"));

            Topic topic = new Topic(resultSet.getString("title"), role);
            topic.setId(resultSet.getLong("id"));

            return topic;*/
            ArrayList<Role> arrayList = new ArrayList();
            arrayList.add(new Role("admin"));
            return new Topic("QQQ", arrayList);
        };
    }

    @Override
    public Topic findByName(String name) {
        return null; // TODO: 23.03.2017
    }

    @Override
    public List<Topic> findAll() {
        return null; // TODO: 23.03.2017
    }

    /*private class TopicObjectExtractor implements ResultSetExtractor {

        @Override
        public Map<Long, Topic> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Long, Topic> map = new HashMap<>();

            while(resultSet.next()){
                Long id = resultSet.getLong("id");
                Topic topic = map.get(id);

                if(topic == null){
                    topic = new Topic();
                    topic.setId(resultSet.getLong("id"));
                    topic.setTitle(resultSet.getString("title"));
                }

                Role role = new Role(resultSet.getString("name"));
                role.setId(resultSet.getLong("id"));
                topic.getRole().add(role);
            }
            return map;
        }
    }*/

    /*public class PersonRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int line) throws SQLException {
            TopicObjectExtractor extractor = new TopicObjectExtractor();
            return extractor.extractData(rs);
        }

    }*/

    /*@Override
    protected RowMapper<Topic> getMapper() {
        return new RowMapper<Topic>() {
            @Override
            public Object mapRow(ResultSet rs, int line) throws SQLException {
                TopicObjectExtractor extractor = new TopicObjectExtractor();
                return extractor.extractData(rs);
            }
        };
    }*/
}