package com.streaming.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    //MySQL Configuration
    @Bean(name = "mysqlDataSource")
    public DataSource mysqlDataSource(MysqlProperties mysqlProperties) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(mysqlProperties.getDriverClassName());
        dataSource.setJdbcUrl(mysqlProperties.getUrl());
        dataSource.setUsername(mysqlProperties.getUsername());
        dataSource.setPassword(mysqlProperties.getPassword());
        return dataSource;
    }

    @Bean(name = "mysqlJdbcTemplate")
    public JdbcTemplate mysqlJdbcTemplate(@Qualifier("mysqlDataSource") DataSource mysqlDataSource) {
        return new JdbcTemplate(mysqlDataSource);
    }

    //PostgreSQL configuration
    @Primary
    @Bean(name = "postgresDataSource")
    public DataSource postgresDataSource(PostgresProperties postgresProperties) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(postgresProperties.getDriverClassName());
        dataSource.setJdbcUrl(postgresProperties.getUrl());
        dataSource.setUsername(postgresProperties.getUsername());
        dataSource.setPassword(postgresProperties.getPassword());
        return dataSource;
    }

    @Bean(name = "postgresJdbcTemplate")
    public JdbcTemplate postgresJdbcTemplate(@Qualifier("postgresDataSource") DataSource postgresDataSource) {
        return new JdbcTemplate(postgresDataSource);
    }
}
