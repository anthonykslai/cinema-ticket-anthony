package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import java.util.logging.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */
    private static Logger log = Logger.getLogger(TicketServiceImpl.class.getName());

    InvalidPurchaseException invalidPurchaseException = new InvalidPurchaseException();
    TicketPaymentServiceImpl ticketPaymentService = new TicketPaymentServiceImpl();
    SeatReservationServiceImpl seatReservationService = new SeatReservationServiceImpl();

    @Override
    public int purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        boolean isValidAccount = invalidPurchaseException.AccountValidation(accountId);
        AtomicInteger noOfTickets = new AtomicInteger();

        if (isValidAccount) {
            List<TicketTypeRequest> ticketTypeRequestList = Arrays.asList(ticketTypeRequests);
            boolean containsAdult = invalidPurchaseException.TicketWithAdult(ticketTypeRequestList);
            if (containsAdult) {
                AtomicInteger totalAmount = new AtomicInteger();
                ticketTypeRequestList.forEach(ticketTypeRequest -> {
                    noOfTickets.addAndGet(ticketTypeRequest.getNoOfTickets());
                    int ticketAmount = ticketTypeRequest.getNoOfTickets() * ticketTypeRequest.getTicketType().getValue();
                    totalAmount.addAndGet(ticketAmount);
                });

                if(noOfTickets.get() <= 0){
                    log.info("Invalid seat reserved!");
                    return 0;
                }

                boolean isValidNoOfTickets = invalidPurchaseException.MaxPurchaseTickets(noOfTickets.get());

                if (isValidNoOfTickets) {
                    SeatReservation(accountId, ticketTypeRequests);

                    ticketPaymentService.makePayment(accountId, totalAmount.get());
                    return totalAmount.get();
                } else {
                    log.info("Number of Ticket is over purchased!");
                    return 0;
                }
            } else {
                //return no Adult
                log.info("Tickets not include Adult!");
                return 0;
            }
        }
        return 0;
    }

    public int SeatReservation(Long accountId, TicketTypeRequest... ticketTypeRequestList) {
        boolean isValidPurchase = invalidPurchaseException.MaxPurchaseTickets(ticketTypeRequestList.length);
        AtomicInteger seatReserved = new AtomicInteger();

        if (isValidPurchase) {
            Arrays.asList(ticketTypeRequestList).forEach(ticketTypeRequest -> {
                if (!TicketTypeRequest.Type.INFANT.equals(ticketTypeRequest.getTicketType())) {
                    seatReserved.getAndAdd(ticketTypeRequest.getNoOfTickets());
                }
            });



            seatReservationService.reserveSeat(accountId, seatReserved.get());
            return seatReserved.get();
        } else {
            //purchase too many tickets
            log.info("Too many tickets purchased!");
            return 0;
        }
    }

}
