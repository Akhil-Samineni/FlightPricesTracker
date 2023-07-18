package com.example.flighttracker.tasklet;

import com.example.flighttracker.mail.EmailSender;
import com.example.flighttracker.model.FlightSearch;
import com.example.flighttracker.service.FlightTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlightTrackerTasklet implements Tasklet {
    private static final Logger logger = LoggerFactory.getLogger(FlightTrackerTasklet.class);

    @Value("${mail.recipients.app}")
    private String mailRecipients;
    @Autowired
    FlightTrackerService flightTrackerService;

    @Autowired
    EmailSender emailSender;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        logger.info("Flight tracker task started");
        try {
            List<String> times = flightTrackerService.getLowestPricesAvailable();
            String[] recipients = mailRecipients.split(",");
            FlightSearch flightSearch = new FlightSearch();
            if (!times.isEmpty()) {
                emailSender.sendEmail(recipients, "Flight Times", flightSearch + " Results are " + times.toString());
            } else {
                logger.info("Flight tracker times not found");
            }
        } catch (Exception e) {
            logger.info("Flight task failed with error {}", e.getMessage());
        }
        return RepeatStatus.FINISHED;
    }
}
