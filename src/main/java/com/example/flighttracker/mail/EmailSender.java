package com.example.flighttracker.mail;

import com.example.flighttracker.service.BatchJobExecutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import static com.example.flighttracker.util.DateTimeUtility.getCurrentDate;
import static com.example.flighttracker.util.DateTimeUtility.getCurrentHour;

@Component
public class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;
    private final BatchJobExecutionService batchJobExecutionService;

    @Autowired
    public EmailSender(JavaMailSender mailSender, MailProperties mailProperties,
                       BatchJobExecutionService batchJobExecutionService) {
        this.mailSender = mailSender;
        this.mailProperties = mailProperties;
        this.batchJobExecutionService = batchJobExecutionService;
    }

    public void sendEmail(String[] to, String subject, String body) {
        logger.info("Sending email to recipients {}", (Object) to);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        try {
            mailSender.send(message);
            logger.info("Sent email to recipients {} on {}", to, getCurrentDate());
            int hour = getCurrentHour();
            if (hour == 23) {
                batchJobExecutionService.cleanUpStaleData();
            }
        } catch (Exception e) {
            logger.error("Failed to send email on {} error is {}", getCurrentDate(), e.getMessage());
        }
    }

}
