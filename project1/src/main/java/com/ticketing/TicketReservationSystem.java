package com.ticketing;

/**
 *  Launches the ticket reservation system
 *
 * @author diem pham
 * CS2336.002
 * Netid: dtp160130
 *
 */
public class TicketReservationSystem {

    public static void main(String []args) throws Exception {
        TicketSystem ticketSystem = new TicketSystem();
        ticketSystem.run();
    }
}