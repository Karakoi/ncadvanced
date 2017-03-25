package com.overseer.service.impl.builder;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class SqlQueryBuilder {

    private final StringBuilder builder;

    public SqlQueryBuilder() {
        this.builder = new StringBuilder();
    }

    public SqlQueryBuilder where() {
        builder.append(" WHERE ");
        return this;
    }

    public SqlQueryBuilder and() {
        builder.append(" AND ");
        return this;
    }

    public SqlQueryBuilder or() {
        builder.append(" OR ");
        return this;
    }

    public SqlQueryBuilder isNull(String field) {
        builder.append(field).append(" IS NULL ");
        return this;
    }

    public SqlQueryBuilder equal(String field, String value) {
        builder.append(format("%s = '%s'", field, value));
        return this;
    }

    public SqlQueryBuilder equalDate(String field, String value) {
        builder.append(format("%s::timestamp::date = '%s'", field, value));
        return this;
    }

    public SqlQueryBuilder like(String field, String value) {
        builder.append(format("lower(%s) LIKE lower('%%%s%%')", field, value));
        return this;
    }

    public SqlQueryBuilder like(String[] fields, String value) {
        builder.append(format("lower(%s) LIKE lower('%%%s%%')", concat(fields), value));
        return this;
    }

    public SqlQueryBuilder limit(int value) {
        builder.append(format(" LIMIT %s", value));
        return this;
    }

    private String concat(String... values) {
        String formattedValues = Arrays.stream(values).filter(s -> !s.isEmpty()).collect(Collectors.joining(", "));
        return format("concat_ws(' ', %s)", formattedValues);
    }

    public String build() {
        return builder.append(";").toString();
    }
}