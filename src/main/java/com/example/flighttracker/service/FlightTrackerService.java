package com.example.flighttracker.service;

import com.example.flighttracker.model.FlightSearch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.flighttracker.util.ConstantsUtil.*;
import static com.example.flighttracker.util.DateTimeUtility.getCurrentDate;
import static com.example.flighttracker.util.DateTimeUtility.getCurrentHour;

@Component
public class FlightTrackerService {
    private static final Logger logger = LoggerFactory.getLogger(FlightTrackerService.class);

    public Map<String, List<String>> getLowestPricesAvailable(List<FlightSearch> flightSearches) {
        logger.info("Getting lowest prices for {} at hour {} from {} to {}", getCurrentDate(), getCurrentHour(), startTime, endTime);
        Map<String, List<String>> prices = new HashMap<>();
        flightSearches.forEach(flightSearch -> {
            prices.put(flightSearch.getAirLines(), new ArrayList<>());
            List<String> availableLowestPrices = new ArrayList<>();
            String url = flightSearch.getSearchURL();
            try {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                WebDriver driver = new ChromeDriver(options);
                driver.get(url);
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    logger.error("Error while thread sleeping {}", e.getMessage());
                }
                if (flightSearch.getAirLines().equalsIgnoreCase(QATAR)) {
                    WebElement appRoot = driver.findElement(By.xpath("//app-root"));
                    availableLowestPrices.addAll(extractFares(appRoot.getText()));
                } else if (flightSearch.getAirLines().equalsIgnoreCase(GOOGLE_FLIGHTS)) {
                    availableLowestPrices.addAll(extractFares(driver.getPageSource()));
                }
                prices.get(flightSearch.getAirLines()).addAll(availableLowestPrices);
            } catch (Exception e) {
                logger.info("Error while getting available times for {} at hour {} from {} to {} Error is {}", getCurrentDate(), getCurrentHour(), startTime, endTime, e.getMessage());
            }
            if (availableLowestPrices.isEmpty()) {
                logger.info("No available times for {} at hour {} from {} to {}", getCurrentDate(), getCurrentHour(), startTime, endTime);
            }
        });

        return prices;
    }

    public static List<String> extractFares(String text) {
        List<String> fares = new ArrayList<>();
        Pattern pattern = Pattern.compile("[₹$][0-9,]+");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String fare = matcher.group();
            fares.add(fare);
        }

        return fares;
    }

}
