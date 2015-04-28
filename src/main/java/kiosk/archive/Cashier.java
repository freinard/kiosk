package kiosk.archive;

public interface Cashier {

    void charge(CreditCard card) throws InsufficientFundsException;

    void reset();
}
