package by.ladz.gs.json;
import by.ladz.gs.model.Ticket;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TicketWrapper {
    @JsonProperty("tickets")
    private List<Ticket> tickets;

    public List<Ticket> getTickets() {
        return tickets;
    }
}
