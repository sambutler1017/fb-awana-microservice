package com.awana.sql.abstracts;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.opengamma.elsql.ElSqlBundle;
import com.opengamma.elsql.ElSqlConfig;

/**
 * Abstract class for building the DAO classes and running queries against the
 * database.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Service
public abstract class AbstractSqlDao extends AbstractSqlGlobals {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSqlDao.class);
    private final NamedParameterJdbcTemplate template;
    private final ElSqlBundle bundle;

    public AbstractSqlDao() {
        this.template = null;
        this.bundle = null;
    }

    public AbstractSqlDao(NamedParameterJdbcTemplate template, ElSqlConfig config) {
        this.template = template;
        this.bundle = ElSqlBundle.of(config, this.getClass());
    }

    /**
     * Gets the default template for the datasource.
     * 
     * @return {@link NamedParameterJdbcTemplate} object.
     */
    protected NamedParameterJdbcTemplate getTemplate() {
        return template;
    }

    /**
     * Does a get on the database for a single record. It will return the top most
     * record if multiple rows are returned.
     * 
     * @param <T>    The object type of the method to cast the rows too.
     * @param sql    The sql to run against the database.
     * @param params Params to be inserted into the query.
     * @param mapper The mapper to return the data as.
     * @return Object of the returned data.
     */
    protected <T> T get(String sql, MapSqlParameterSource params, RowMapper<T> mapper) {
        return getTemplate().queryForObject(sql, params, mapper);
    }

    /**
     * Does a get on the database for a single record. It will return the top most
     * record if multiple rows are returned.
     * 
     * @param <T>    The object type of the method to cast the rows too.
     * @param sql    The sql to run against the database.
     * @param mapper The mapper to return the data as.
     * @return Object of the returned data.
     */
    protected <T> T get(String sql, RowMapper<T> mapper) {
        return get(sql, new MapSqlParameterSource(), mapper);
    }

    /**
     * Does a get on the database for a single record. It will return the top most
     * record if multiple rows are returned. It will return the type of the passed
     * in class.
     * 
     * @param <T>    The object type of the method to cast the rows too.
     * @param sql    The sql to run against the database.
     * @param params Params to be inserted into the query.
     * @param clazz  The class to map the data as.
     * @return Object of the returned data.
     */
    protected <T> T get(String sql, MapSqlParameterSource params, Class<T> clazz) {
        return getTemplate().queryForObject(sql, params, clazz);
    }

    /**
     * Does a get on the database for a single record. It will return the top most
     * record if multiple rows are returned. It will return the type of the passed
     * in class.
     * 
     * @param <T>   The object type of the method to cast the rows too.
     * @param sql   The sql to run against the database.
     * @param clazz The class to map the data as.
     * @return Object of the returned data.
     */
    protected <T> T get(String sql, Class<T> clazz) {
        return get(sql, new MapSqlParameterSource(), clazz);
    }

    /**
     * Wraps the get query in an optional. If the result set returns an empty data,
     * it will log a warning and return an empty optional object.
     * 
     * @param <T>    The object type of the method to cast the rows too.
     * @param sql    The sql to run against the database.
     * @param params Params to be inserted into the query.
     * @param mapper The mapper to return the data as.
     * @return Object of the returned data.
     */
    protected <T> Optional<T> getForOptional(String sql, MapSqlParameterSource params, RowMapper<T> mapper) {
        try {
            return Optional.of(get(sql, params, mapper));
        }
        catch(EmptyResultDataAccessException e) {
            LOGGER.warn("Query returned empty result");
            return Optional.empty();
        }
    }

    /**
     * Wraps the get query in an optional. If the result set returns an empty data,
     * it will log a warning and return an empty optional object.
     * 
     * @param <T>    The object type of the method to cast the rows too.
     * @param sql    The sql to run against the database.
     * @param mapper The mapper to return the data as.
     * @return Object of the returned data.
     */
    protected <T> Optional<T> getForOptional(String sql, RowMapper<T> mapper) {
        return getForOptional(sql, mapper);
    }

    /**
     * Wraps the get query in an optional. If the result set returns an empty data,
     * it will log a warning and return an empty optional object.
     * 
     * @param <T>    The object type of the method to cast the rows too.
     * @param sql    The sql to run against the database.
     * @param params Params to be inserted into the query.
     * @param clazz  The class to map the data as.
     * @return Object of the returned data.
     */
    protected <T> Optional<T> getForOptional(String sql, MapSqlParameterSource params, Class<T> clazz) {
        try {
            return Optional.of(get(sql, params, clazz));
        }
        catch(EmptyResultDataAccessException e) {
            LOGGER.warn("Query returned empty result");
            return Optional.empty();
        }
    }

    /**
     * Wraps the get query in an optional. If the result set returns an empty data,
     * it will log a warning and return an empty optional object.
     * 
     * @param <T>   The object type of the method to cast the rows too.
     * @param sql   The sql to run against the database.
     * @param clazz The class to map the data as.
     * @return Object of the returned data.
     */
    protected <T> Optional<T> getForOptional(String sql, Class<T> clazz) {
        return getForOptional(sql, clazz);
    }

    /**
     * Querys the database for a page of data. It will return the data as a list of
     * the called object.
     * 
     * @param <T>    The object type of the method to cast the rows too.
     * @param sql    The sql to run against the database.
     * @param params Params to be inserted into the query.
     * @param mapper The mapper to return the data as.
     * @return List of the returned data.
     */
    protected <T> List<T> getPage(String sql, MapSqlParameterSource params, RowMapper<T> mapper) {
        return getTemplate().query(sql, params, mapper);
    }

    /**
     * Querys the database for a page of data. It will return the data as a list of
     * the called object.
     * 
     * @param <T>    The object type of the method to cast the rows too.
     * @param sql    The sql to run against the database.
     * @param mapper The mapper to return the data as.
     * @return List of the returned data.
     */
    protected <T> List<T> getPage(String sql, RowMapper<T> mapper) {
        return getTemplate().query(sql, new MapSqlParameterSource(), mapper);
    }

    /**
     * Querys the database for a page of data. It will return the data as a list of
     * the called object.
     * 
     * @param <T>   The object type of the method to cast the rows too.
     * @param sql   The sql to run against the database.
     * @param clazz The class to map the data as.
     * @return List of the returned data.
     */
    protected <T> List<T> getPage(String sql, Class<T> clazz) {
        return getTemplate().queryForList(sql, new MapSqlParameterSource(), clazz);
    }

    /**
     * Does an insertion into the database with the given sql and params. It will
     * also get the auto incremented id of the table with the key holder.
     * 
     * @param sql       The sql to run against the database.
     * @param params    Params to be inserted into the query.
     * @param keyHolder used to get the auto increment id.
     */
    protected int post(String sql, MapSqlParameterSource params, KeyHolder keyHolder) {
        return getTemplate().update(sql, params, keyHolder);
    }

    /**
     * Does an insertion into the database with the given sql and params.
     * 
     * @param sql    The sql to run against the database.
     * @param params Params to be inserted into the query.
     */
    protected int post(String sql, MapSqlParameterSource params) {
        return getTemplate().update(sql, params);
    }

    /**
     * Performs a delete on the database for the given sql.
     * 
     * @param sql    The sql to run against the database.
     * @param params Params to be inserted into the query.
     */
    protected int delete(String sql, MapSqlParameterSource params) {
        return getTemplate().update(sql, params);
    }

    /**
     * Performs an update against the database.
     * 
     * @param sql    The sql to run against the database.
     * @param params Params to be inserted into the query.
     */
    protected int update(String sql, MapSqlParameterSource params) {
        return getTemplate().update(sql, params);
    }

    /**
     * Will execute the given sql string against the active database.
     * 
     * @param sql The sql to be run.
     */
    protected void execute(String sql) {
        getTemplate().update(sql, new MapSqlParameterSource());
    }

    /**
     * Gets the sql fragement for the given name and filters out any parameters that
     * don't exist in the fragment.
     * 
     * @param name   The name of the sql fragement.
     * @param params The params to filter out of the query.
     * @return {@link String} of the filtered query.
     */
    protected String getSql(String name, SqlParameterSource params) {
        return bundle.getSql(name, params).trim();
    }

    /**
     * Gets the raw sql fragement string for the given name.
     * 
     * @param name The name of the sql fragement.
     * @return {@link String} of the sql fragment.
     */
    protected String getSql(String name) {
        return bundle.getSql(name).trim();
    }

    /**
     * Creates a new {@link MapSqlParameterSource} object with the given name and
     * value.
     * 
     * @param name  Name of the map to store the value under.
     * @param value What value to store in the map.
     * @return {@link MapSqlParameterSource} instance
     */
    protected MapSqlParameterSource parameterSource(String name, Object value) {
        return new MapSqlParameterSource(name, value);
    }
}
