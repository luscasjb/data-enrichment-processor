package com.streaming.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
public class PersonRepository {

    private final JdbcTemplate postgresJdbcTemplate;

    public PersonRepository(@Qualifier("postgresJdbcTemplate") JdbcTemplate postgresJdbcTemplate) {
        this.postgresJdbcTemplate = postgresJdbcTemplate;
    }

    public Optional<String> findPersonNameById(Integer personId) {
        try {
            String sql = "SELECT name FROM public.people WHERE id = ?";
            return Optional.ofNullable(postgresJdbcTemplate.queryForObject(sql, String.class, personId));
        } catch (EmptyResultDataAccessException e) {
            log.error("Person name not found for ID: {}", personId);
            return Optional.empty();
        }
    }
}