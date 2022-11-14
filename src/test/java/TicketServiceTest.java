import org.junit.Test;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import static org.junit.Assert.assertEquals;

public class TicketServiceTest {
    TicketServiceImpl ticketService = new TicketServiceImpl();

    @Test
    public void purchaseOneAdultTicket() {
        System.out.println("Purchase one adult ticket");
        TicketTypeRequest ticketTypeRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        int amount = ticketService.purchaseTickets(1000L, ticketTypeRequest);
        assertEquals(20, amount);
    }

    @Test
    public void purchaseTwoTicketsWithAdultAndChild() {
        System.out.println("Purchase one adult ticket and one child ticket");
        TicketTypeRequest adultTicketTypeRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest childTicketTypeRequest = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);

        int amount = ticketService.purchaseTickets(1000L, adultTicketTypeRequest, childTicketTypeRequest);
        assertEquals(50, amount);
    }

    @Test
    public void purchaseTwoTicketsWithAdultAndChildAndInfant() {
        System.out.println("Purchase one adult ticket and one child ticket");
        TicketTypeRequest adultTicketTypeRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest childTicketTypeRequest = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);
        TicketTypeRequest infantTicketTypeRequest = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);

        int amount = ticketService.purchaseTickets(1000L, adultTicketTypeRequest, childTicketTypeRequest, infantTicketTypeRequest);
        assertEquals(50, amount);
    }

    @Test
    public void purchaseTenTickets() {
        System.out.println("Purchase ten adult tickets");
        TicketTypeRequest adultTicketTypeRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 10);

        int amount = ticketService.purchaseTickets(1000L, adultTicketTypeRequest);
        assertEquals(200, amount);
    }

    @Test
    public void purchaseTicketsWithOutAdult() {
        System.out.println("Purchase one child ticket without child");
        TicketTypeRequest childTicketTypeRequest = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);

        int amount = ticketService.purchaseTickets(1000L, childTicketTypeRequest);
        assertEquals(0, amount);
    }

    @Test
    public void purchaseTicketsOverTwenty() {
        System.out.println("Purchase over 20 tickets");
        TicketTypeRequest adultTicketTypeRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 21);
        int amount = ticketService.purchaseTickets(1000L, adultTicketTypeRequest);
        assertEquals(0, amount);
    }

    @Test
    public void purchaseOneAdultTicketWithoutSeat() {
        System.out.println("Purchase one adult ticket without seat");
        TicketTypeRequest ticketTypeRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 0);
        int amount = ticketService.purchaseTickets(1000L, ticketTypeRequest);
        assertEquals(0, amount);
    }

    @Test
    public void purchaseOneAdultTicketWithInvalidSeat() {
        System.out.println("Purchase one adult ticket without seat");
        TicketTypeRequest ticketTypeRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, -1);
        int amount = ticketService.purchaseTickets(1000L, ticketTypeRequest);
        assertEquals(0, amount);
    }

    @Test
    public void seatReservationForTwoAdults() {
        System.out.println("Seat Reservation for 2 Adults");
        TicketTypeRequest adultTicketTypeRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        int noOfSeats = ticketService.SeatReservation(1000L, adultTicketTypeRequest);
        assertEquals(2, noOfSeats);
    }

    @Test
    public void seatReservationForTwoAdultsAndOneChild() {
        System.out.println("Seat Reservation for 2 Adults and one child");
        TicketTypeRequest adultTicketTypeRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest childTicketTypeRequest = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);
        int noOfSeats = ticketService.SeatReservation(1000L, adultTicketTypeRequest, childTicketTypeRequest);
        assertEquals(3, noOfSeats);
    }

    @Test
    public void seatReservationForTwoAdultsAndOneInfant() {
        System.out.println("Seat Reservation for 2 Adults and one infant");
        TicketTypeRequest adultTicketTypeRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest childTicketTypeRequest = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);
        int noOfSeats = ticketService.SeatReservation(1000L, adultTicketTypeRequest, childTicketTypeRequest);
        assertEquals(2, noOfSeats);
    }

}
