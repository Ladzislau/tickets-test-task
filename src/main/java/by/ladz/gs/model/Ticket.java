package by.ladz.gs.model;

import by.ladz.gs.json.DateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Ticket {
    @JsonProperty("origin")
    private String origin;
    @JsonProperty("origin_name")
    private String originName;
    @JsonProperty("destination")
    private String destination;
    @JsonProperty("destination_name")
    private String destinationName;
    @JsonProperty("departure_date")
    @JsonDeserialize(using = DateTimeDeserializer.LocalDateDeserializer.class)
    private LocalDate departureDate;
    @JsonProperty("departure_time")
    @JsonDeserialize(using = DateTimeDeserializer.LocalTimeDeserializer.class)
    private LocalTime departureTime;
    @JsonProperty("arrival_date")
    @JsonDeserialize(using = DateTimeDeserializer.LocalDateDeserializer.class)
    private LocalDate arrivalDate;
    @JsonProperty("arrival_time")
    @JsonDeserialize(using = DateTimeDeserializer.LocalTimeDeserializer.class)
    private LocalTime arrivalTime;
    @JsonProperty("carrier")
    private String carrier;
    @JsonProperty("stops")
    private int stops;
    @JsonProperty("price")
    private double price;

    public Ticket() {
    }

    public Ticket(String origin, String originName, String destination, String destinationName, LocalDate departureDate,
                  LocalTime departureTime, LocalDate arrivalDate, LocalTime arrivalTime, String carrier, int stops, double price) {
        this.origin = origin;
        this.originName = originName;
        this.destination = destination;
        this.destinationName = destinationName;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.carrier = carrier;
        this.stops = stops;
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public int getStops() {
        return stops;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Duration getFlightDuration() {
        LocalDateTime departureDateTime = LocalDateTime.of(departureDate, departureTime);
        LocalDateTime arrivalDateTime = LocalDateTime.of(arrivalDate, arrivalTime);
        return Duration.between(departureDateTime, arrivalDateTime);
    }
}
