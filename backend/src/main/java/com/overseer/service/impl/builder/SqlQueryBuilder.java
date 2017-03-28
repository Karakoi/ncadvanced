package com.overseer.service.impl.builder;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Implementation of builder pattern, which provides methods to build a sql query and avoid multiple string concat repeating.
 * In our case, is used for search by the administrator required users or requests in data table by ajax for the specified
 *  search parameters.
 */
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
        builder.append(format(" %s IS NULL ", field));
        return this;
    }

    public SqlQueryBuilder notNull(String field) {
        builder.append(format(" %s IS NOT NULL ", field));
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

    public SqlQueryBuilder is(String field) {
        builder.append(field);
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