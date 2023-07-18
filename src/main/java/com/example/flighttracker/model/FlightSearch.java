package com.example.flighttracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightSearch {
    String airLines = "QATAR";
    String returnDate = "2024-04-24";
    String departureDate = "2023-10-26";
    String fromStation = "HYD";
    String toStation = "DFW";
}
