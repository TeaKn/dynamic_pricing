package com.price.ui.model.request;

public class TicketRequestModel {

    private String venue;
    private String start_time;
    private String end_time;
    private String ticket_id;
    private RulingParam rulingParam;

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public RulingParam getRulingParam() {
        return rulingParam;
    }

    public void setRulingParam(RulingParam rulingParam) {
        this.rulingParam = rulingParam;
    }
}
