package uk.gov.dwp.uc.pairtest.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Immutable Object
 */

public class TicketTypeRequest {

    private int noOfTickets;
    private Type type;

    public TicketTypeRequest(Type type, int noOfTickets) {
        this.type = type;
        this.noOfTickets = noOfTickets;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public Type getTicketType() {
        return type;
    }

    public enum Type {
        ADULT(20), CHILD(10) , INFANT(0);

        private static Map map = new HashMap<>();
        private final int amount;

        Type(int amount) {
            this.amount = amount;
        }
        static {
            for (Type pageType : Type.values()) {
                map.put(pageType.amount, pageType);
            }
        }

        public static Type valueOf(int pageType) {
            return (Type) map.get(pageType);
        }

        public int getValue() {
            return amount;
        }
    }

}
