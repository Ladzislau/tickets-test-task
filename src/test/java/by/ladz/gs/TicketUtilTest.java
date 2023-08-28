package by.ladz.gs;

import by.ladz.gs.model.Ticket;
import by.ladz.gs.util.TicketUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TicketUtilTest {

    List<Ticket> sampleTickets;

    @BeforeEach
    void setUp() {
        Ticket ticket1 = new Ticket("VVO", "Владивосток", "TLV", "Тель-Авив",
                LocalDate.of(2023, 8, 1), LocalTime.of(12, 0),
                LocalDate.of(2023, 8, 1), LocalTime.of(17, 0),
                "SU", 0, 10000.0);

        Ticket ticket2 = new Ticket("VVO", "Владивосток", "TLV", "Тель-Авив",
                LocalDate.of(2023, 8, 1), LocalTime.of(13, 0),
                LocalDate.of(2023, 8, 1), LocalTime.of(19, 0),
                "S7", 1, 12000.0);

        Ticket ticket3 = new Ticket("VVO", "Владивосток", "TLV", "Тель-Авив",
                LocalDate.of(2023, 8, 1), LocalTime.of(14, 0),
                LocalDate.of(2023, 8, 1), LocalTime.of(20, 0),
                "TK", 2, 11000.0);

        Ticket ticket4 = new Ticket("VVO", "Владивосток", "TLV", "Тель-Авив",
                LocalDate.of(2023, 8, 1), LocalTime.of(15, 0),
                LocalDate.of(2023, 8, 1), LocalTime.of(20, 0),
                "S7", 0, 14000.0);

        Ticket ticket5 = new Ticket("VVO", "Владивосток", "PRS", "Париж",
                LocalDate.of(2023, 8, 1), LocalTime.of(15, 0),
                LocalDate.of(2023, 8, 1), LocalTime.of(20, 0),
                "S7", 0, 14000.0);

        sampleTickets = List.of(ticket1, ticket2, ticket3, ticket4, ticket5);
    }

    @Test
    public void testParseJsonFileToTicketsList() {
        List<Ticket> tickets = TicketUtil.parseJsonFileToTicketsList("src/main/resources/tickets.json");
        assertNotNull(tickets);
        assertFalse(tickets.isEmpty());
    }

    @Test
    public void testParseJsonFileToTicketsListException() {
        assertThrows(RuntimeException.class, () -> TicketUtil.parseJsonFileToTicketsList("nonexistent.json"));
    }

    @Test
    public void testMinFlightDurationByCarriers() {
        HashMap<String, Duration> minFlightDurationByCarriers = new HashMap<>();
        minFlightDurationByCarriers.put("S7", Duration.ofHours(5));
        minFlightDurationByCarriers.put("SU", Duration.ofHours(5));
        minFlightDurationByCarriers.put("TK", Duration.ofHours(6));

        HashMap<String, Duration> result = TicketUtil.minFlightDurationByCarriers("Владивосток", "Тель-Авив", sampleTickets);
        assertEquals(minFlightDurationByCarriers, result);
    }

    @Test
    public void testCalculateAvgMedianPriceDiff() {
        //avgPrice = 11_750          medianPrice = 11_500
        double expectedResult = 250;

        double result = TicketUtil.calculateAvgMedianPriceDiff("Владивосток", "Тель-Авив", sampleTickets);
        assertEquals(expectedResult, result, 0.001);
    }

    @Test
    public void testSelectTicketsForRoute() {
        ArrayList<Ticket> expectedResult = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            expectedResult.add(sampleTickets.get(i));
        }

        List<Ticket> result = TicketUtil.selectTicketsForRoute("Владивосток", "Тель-Авив", sampleTickets);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectTicketsForRouteException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            TicketUtil.selectTicketsForRoute("Москва", "Лондон", sampleTickets);
        });

        String expectedErrorMessage = "Не найдено билетов для направления: Москва -> Лондон";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

}