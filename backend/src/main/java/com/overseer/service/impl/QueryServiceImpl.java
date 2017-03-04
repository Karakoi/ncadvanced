package com.overseer.service.impl;

import com.overseer.service.QueryService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * {@inheritDoc}.
 */
@Service
@PropertySource("classpath:queries.properties")
public class QueryServiceImpl implements QueryService {

    @Resource
    private Environment env;

    /**
     * {@inheritDoc}.
     */
    @Override
    public String getQuery(String name) {
        return this.env.getRequiredProperty(name);
    }
}
