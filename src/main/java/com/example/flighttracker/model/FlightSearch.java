package com.example.flighttracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightSearch {
    private String airLines;
    private String returnDate;
    private String departureDate;
    private String fromStation;
    private String toStation;
    private String searchURL;
}
