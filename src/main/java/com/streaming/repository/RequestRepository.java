package com.streaming.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
public class RequestRepository {

    private final JdbcTemplate mysqlJdbcTemplate;

    public RequestRepository(@Qualifier("mysqlJdbcTemplate") JdbcTemplate mysqlJdbcTemplate) {
        this.mysqlJdbcTemplate = mysqlJdbcTemplate;
    }

    public Optional<String> findLatestStatus(Long requestId) {
        try {
            String sql = "SELECT status FROM people_movements WHERE request_id = ? ORDER BY id DESC LIMIT 1";
            return Optional.ofNullable(mysqlJdbcTemplate.queryForObject(sql, String.class, requestId));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No movement found for request ID: {}", requestId);
            return Optional.empty();
        }
    }

    public Optional<Integer> findPersonIdByRequestId(Long requestId) {
        try {
            String sql = "SELECT person_id FROM people_requests WHERE id = ?";
            return Optional.ofNullable(mysqlJdbcTemplate.queryForObject(sql, Integer.class, requestId));
        } catch (EmptyResultDataAccessException e) {
            log.error("Person ID not found for request ID: {}", requestId);
            return Optional.empty();
        }
    }
}