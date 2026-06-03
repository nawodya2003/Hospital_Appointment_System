package com.example.hospital.service.impl;

import com.example.hospital.service.DatabaseResetService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Implementation of database reset logic.
 */
@Service
public class DatabaseResetServiceImpl implements DatabaseResetService {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseResetServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void resetDatabase() {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        try {
            jdbcTemplate.execute("TRUNCATE TABLE appointments");
            jdbcTemplate.execute("TRUNCATE TABLE doctors");
            jdbcTemplate.execute("TRUNCATE TABLE patients");
        } finally {
            jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
    }
}
