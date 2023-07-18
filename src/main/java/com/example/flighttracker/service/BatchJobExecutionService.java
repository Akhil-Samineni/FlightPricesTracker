package com.example.flighttracker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BatchJobExecutionService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(BatchJobExecutionService.class);

    public void cleanUpStaleData() {
        logger.info("Cleaning up data from batch job tables");
        try {
            jdbcTemplate.execute("DELETE FROM BATCH_JOB_EXECUTION where STATUS = 'COMPLETED'");
            logger.info("Cleaned up data from batch job tables");
        } catch (Exception e) {
            logger.info("Error cleaning up data from batch job tables Error is {}", e.getMessage());
        }
    }
}
