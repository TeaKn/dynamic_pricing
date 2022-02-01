package com.price.service;

import com.price.shared.dto.TicketDTO;
import com.price.shared.dto.VenueDTO;

// TODO: FIND DATABASE BACKED CORE INTERFACE SPRING TO MAKE THIS WORK, pust zdj to
// TODO: ticket service je kot service class, ON IZ DATA LAYERA DOBI CONFIGURACIJO,
// DATA CONFIG
// GOOGLE API DATA
// ČE JE POTREBEN WEATHER API
// POL PA VSE TE PODATKE PUŠNEŠ V KALKULATOR

// TODO: V KONTROLERJU KLIČEM, V SERVICE SE RAČUNA, KONTROLER DOBI REZULTAT IN GA VRNE

public interface TicketService {

    VenueDTO insert(VenueDTO venue);

}
