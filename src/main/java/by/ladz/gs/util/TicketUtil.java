package by.ladz.gs.util;

import by.ladz.gs.model.Ticket;
import by.ladz.gs.json.TicketWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class TicketUtil {
    private static final double DEFAULT_AVERAGE_PRICE = 0.0;

    public static List<Ticket> parseJsonFileToTicketsList(String jsonFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        TicketWrapper wrapper;
        try {
            wrapper = objectMapper.readValue(new File(jsonFilePath), TicketWrapper.class);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения JSON файла: " + e.getMessage(), e);
        }
        return wrapper.getTickets();
    }

    public static HashMap<String, Duration> minFlightDurationByCarriers(String originCity, String destinationCity,
                                                                        List<Ticket> tickets) {
        HashMap<String, Duration> ticketsWithMinFlightTimeByCarrier = new HashMap<>();
        List<Ticket> ticketsMatchingRoute = selectTicketsForRoute(originCity, destinationCity, tickets);

        Set<String> carriers = ticketsMatchingRoute.stream()
                .map(Ticket::getCarrier)
                .collect(Collectors.toSet());

        carriers.forEach(carrier -> {
            Optional<Ticket> ticketWithMinFlightDuration = ticketsMatchingRoute.stream()
                    .filter(ticket -> ticket.getCarrier().equals(carrier))
                    .min(Comparator.comparing(Ticket::getFlightDuration));
            if (ticketWithMinFlightDuration.isPresent()) {
                Duration flightDuration = ticketWithMinFlightDuration.get().getFlightDuration();
                ticketsWithMinFlightTimeByCarrier.put(carrier, flightDuration);
            }
        });

        return ticketsWithMinFlightTimeByCarrier;
    }

    public static double calculateAvgMedianPriceDiff(String originCity, String destinationCity,
                                                     List<Ticket> tickets) {
        List<Ticket> ticketsMatchingRoute = selectTicketsForRoute(originCity, destinationCity, tickets);

        List<Double> prices = new ArrayList<>(ticketsMatchingRoute.stream()
                .map(Ticket::getPrice)
                .sorted()
                .toList());

        DoubleStream priceStream = prices.stream().mapToDouble(Double::doubleValue);

        double averagePrice = priceStream.average().orElse(DEFAULT_AVERAGE_PRICE);
        double medianPrice;
        int size = prices.size();

        if (size % 2 == 0) {
            double middleBeforeMedian = prices.get(size / 2 - 1);
            double middleAfterMedian = prices.get(size / 2);
            medianPrice = (middleBeforeMedian + middleAfterMedian) / 2;
        } else {
            medianPrice = prices.get(size / 2);
        }

        return Math.abs(averagePrice - medianPrice);
    }

    public static List<Ticket> selectTicketsForRoute(String originCity, String destinationCity, List<Ticket> tickets) {
        List<Ticket> ticketsMatchingRoute = tickets.stream()
                .filter(ticket ->
                        (originCity.equals(ticket.getOriginName()) &&
                                destinationCity.equals(ticket.getDestinationName())
                        ||
                        (originCity.equals(ticket.getOrigin()) &&
                                destinationCity.equals(ticket.getDestination()))
                        ))
                .toList();

        if(ticketsMatchingRoute.isEmpty()){
            throw new RuntimeException("Не найдено билетов для направления: " + originCity + " -> " + destinationCity);
        }

        return ticketsMatchingRoute;
    }
}
