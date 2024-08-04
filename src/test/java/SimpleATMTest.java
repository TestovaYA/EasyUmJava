
import org.example.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleATMTest {
    private SimpleATM atm;

    /**
     * Initializes the ATM instance before each test.
     * 50 + 50 + 100 + 100 + 100 + 100 + 500 = 1000
     */
    @BeforeEach
    void setUp() {
        atm = new SimpleATM();

        atm.deposit(new FiftyBanknote(), 2);
        atm.deposit(new HundredBanknote(), 4);
        atm.deposit(new FiveHundredBanknote(), 1);
    }

    /**
     * Tests the current cash balance in the ATM.
     */
    @Test
    void testGetCashInATM() {
        assertEquals(1000, atm.getCashInATM());
    }

    /**
     * Tests successful withdrawal with sufficient funds.
     */
    @Test
    void testWithdrawSuccessful() {
        int initialCash = atm.getCashInATM();
        atm.withdraw(300);
        assertEquals(initialCash - 300, atm.getCashInATM());
    }

    @Test
    void testWithdrawMultipleDenominations() {
        atm.withdraw(150);
        assertEquals(850, atm.getCashInATM());
    }

    /**
     * Tests withdrawal attempt that exceeds available funds.
     */
    @Test
    void testWithdrawInsufficientFunds() {
        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class, () -> atm.withdraw(5000));
        assertEquals("ATM does not have enough cash.", exception.getMessage());
    }

    /**
     * Tests withdrawal that cannot be dispensed as exact change.
     */
    @Test
    void testWithdrawExactChangeNotDispensed() {
        ExactChangeNotDispensedException exception = assertThrows(ExactChangeNotDispensedException.class, () -> atm.withdraw(135));
        assertEquals("Cannot dispense the exact amount requested.", exception.getMessage());
    }
}