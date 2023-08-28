package by.ladz.gs;

import by.ladz.gs.model.Ticket;
import by.ladz.gs.util.TicketUtil;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;


public class App {
    private static final String JSON_FILE_PATH = "src/main/resources/tickets.json";
    private static final String ORIGIN_CITY = "Владивосток";
    private static final String DESTINATION_CITY = "Тель-Авив";


    public static void main(String[] args) {
        List<Ticket> tickets = TicketUtil.parseJsonFileToTicketsList(JSON_FILE_PATH);

        HashMap<String, Duration> minFlightDurationByCarriers = TicketUtil.minFlightDurationByCarriers(ORIGIN_CITY, DESTINATION_CITY, tickets);
        minFlightDurationByCarriers.forEach((carrier,duration) ->
            System.out.printf("Минимальная продолжительность полета между городами %s и %s у компании-перевозчика " +
                    "%s составляет %d часов и %d минут.%n",
                    ORIGIN_CITY, DESTINATION_CITY, carrier, duration.toHours(), duration.toMinutesPart()));

        double avgMedianPricesDif = TicketUtil.calculateAvgMedianPriceDiff(ORIGIN_CITY, DESTINATION_CITY, tickets);
        System.out.printf("%nРазница между средней ценой и медианной ценой полета между городами %s и %s " +
                "составляет %.1f RUB%n", ORIGIN_CITY, DESTINATION_CITY, avgMedianPricesDif);

    }
}
