package com.price.ui.model.request;

import com.fasterxml.jackson.databind.node.DoubleNode;

import java.util.ArrayList;

public class Venue {

    private String venue_id;
    private ArrayList<TicketBasePrices> ticketBasePrices;
    private Double min_ticket_price;
    private Double max_ticket_price;

    public static class TicketBasePrices {
        private Double adult;
        private Double child;
        private Double young;
        private Double young_adult;

        public Double getAdult() {
            return adult;
        }

        public void setAdult(Double adult) {
            this.adult = adult;
        }

        public Double getChild() {
            return child;
        }

        public void setChild(Double child) {
            this.child = child;
        }

        public Double getYoung() {
            return young;
        }

        public void setYoung(Double young) {
            this.young = young;
        }

        public Double getYoung_adult() {
            return young_adult;
        }

        public void setYoung_adult(Double young_adult) {
            this.young_adult = young_adult;
        }
    }

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
