package activities;

import org.junit.jupiter.api.Test;

import activities.BankAccount;
import activities.NotEnoughFundsException;
// import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Activity2 {
    @Test
    void notEnoughFunds() {
        BankAccount account = new BankAccount(9);
        assertThrows(NotEnoughFundsException.class, () -> account.withdraw(10));
    }
    @Test
    void enoughFunds() {
        BankAccount account = new BankAccount(100);
        // Assertion for no exceptions
        assertDoesNotThrow(() -> account.withdraw(100));
    }
}
