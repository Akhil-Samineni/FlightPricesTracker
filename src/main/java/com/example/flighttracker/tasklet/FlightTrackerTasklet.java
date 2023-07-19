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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.flighttracker.util.ConstantsUtil.GOOGLE_FLIGHTS;
import static com.example.flighttracker.util.ConstantsUtil.QATAR;

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
            List<FlightSearch> flightSearches = new ArrayList<>();
            flightSearches.add(getFlight(QATAR, "2024-04-24", "2023-10-24", "HYD", "DFW"));
            flightSearches.add(getFlight(GOOGLE_FLIGHTS, "2024-04-24", "2023-10-24", "HYD", "DFW"));
            Map<String, List<String>> times = flightTrackerService.getLowestPricesAvailable(flightSearches);
            String[] recipients = mailRecipients.split(",");
            FlightSearch flightSearch = new FlightSearch();
            if (!times.isEmpty()) {
                emailSender.sendEmail(recipients, "Flight Times", flightSearch + " Results are " + times);
            } else {
                logger.info("Flight tracker times not found");
            }
        } catch (Exception e) {
            logger.info("Flight task failed with error {}", e.getMessage());
        }
        return RepeatStatus.FINISHED;
    }

    private FlightSearch getFlight(String airline, String returnDate, String departureDate,
                                   String fromStation, String toStation) {
        FlightSearch flightSearch = new FlightSearch();
        flightSearch.setAirLines(airline);
        flightSearch.setReturnDate(returnDate);
        flightSearch.setDepartureDate(departureDate);
        flightSearch.setFromStation(fromStation);
        flightSearch.setToStation(toStation);
        if (airline.equalsIgnoreCase(QATAR)) {
            flightSearch.setSearchURL(String.format("https://www.qatarairways.com/app/booking/flight-selection?widget=QR&searchType=F&addTaxToFare=Y&minPurTime=0&upsellCallId=&allowRedemption=Y&tripType=R&selLang=en&flexibleDate=false&returning=%s&adults=2&children=0&ofw=0&teenager=0&infants=0&promoCode=&bookingClass=E&fromStation=%s&from=&toStation=%s&to=&departingHidden=&departing=%s", flightSearch.getReturnDate(), flightSearch.getFromStation(), flightSearch.getToStation(), flightSearch.getDepartureDate()));
        } else if (airline.equalsIgnoreCase(GOOGLE_FLIGHTS)) {
            flightSearch.setSearchURL("https://www.google.com/travel/flights/search?tfs=CBwQAhoeEgoyMDIzLTEwLTI0agcIARIDSFlEcgcIARIDREZXGh4SCjIwMjQtMDQtMjJqBwgBEgNERldyBwgBEgNIWURAAUABSAFwAYIBCwj___________8BmAEB");
        }
        return flightSearch;
    }
}
