package com.price.shared.dto;

import java.util.ArrayList;

public class VenueDTO {

    private String venue_id;
    private ArrayList<TicketBasePrices> ticketBasePrices;
    private Double min_ticket_price;
    private Double max_ticket_price;

    public String getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(String venue_id) {
        this.venue_id = venue_id;
    }

    public ArrayList<TicketBasePrices> getTicketBasePrices() {
        return ticketBasePrices;
    }

    public void setTicketBasePrices(ArrayList<TicketBasePrices> ticketBasePrices) {
        this.ticketBasePrices = ticketBasePrices;
    }

    public Double getMin_ticket_price() {
        return min_ticket_price;
    }

    public void setMin_ticket_price(Double min_ticket_price) {
        this.min_ticket_price = min_ticket_price;
    }

    public Double getMax_ticket_price() {
        return max_ticket_price;
    }

    public void setMax_ticket_price(Double max_ticket_price) {
        this.max_ticket_price = max_ticket_price;
    }
}
