package uk.gov.dwp.uc.pairtest.exception;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class InvalidPurchaseException extends RuntimeException {

    private final int maxTicket = 20;

    public boolean TicketWithAdult(List<TicketTypeRequest> ticketTypeRequests) {
        AtomicBoolean containsAdult = new AtomicBoolean(false);

        ticketTypeRequests.forEach(ticketTypeRequest ->{
            if(TicketTypeRequest.Type.ADULT.equals(ticketTypeRequest.getTicketType()))
                containsAdult.set(true);
        });
        return containsAdult.get();
    }

    public boolean MaxPurchaseTickets(int noOfTickets) {
        if (noOfTickets > maxTicket)
            return false;
        else
            return true;
    }

    public boolean AccountValidation(long accountId) {
        if (accountId > 0)
            return true;
        else
            return false;

    }
}
