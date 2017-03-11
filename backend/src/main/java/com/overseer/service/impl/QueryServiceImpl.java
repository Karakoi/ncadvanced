package com.overseer.service.impl;

import com.overseer.service.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * {@inheritDoc}.
 */
@Service
@Slf4j
@PropertySource("classpath:queries.properties")
public class QueryServiceImpl implements QueryService {

    @Resource
    private Environment env;

    /**
     * {@inheritDoc}.
     */
    @Override
    public String getQuery(String name) {
        log.debug("Fetching sql query by name: {}", name);
        return this.env.getRequiredProperty(name);
    }
}
