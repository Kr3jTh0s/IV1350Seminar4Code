package src.test.java.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.processSale.integration.Account;
import src.main.java.processSale.model.dto.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Account} class.
 */
class AccountTest {
    private Account account;

    /**
     * Sets up a new Account instance before each test.
     */
    @BeforeEach
    void setUp() {
        account = new Account();
    }

    /**
     * Tests recording a sale in the accounting system.
     */
    @Test
    void testAccountSale() {
        SaleSummaryDTO summary = new SaleSummaryDTO(null, null, null);

        assertDoesNotThrow(() -> account.accountSale(summary), "Recording a sale should not throw an exception.");
    }

    /**
     * Tests recording a sale with a null SaleSummaryDTO.
     */
    @Test
    void testAccountSaleWithNullSummary() {
        assertDoesNotThrow(() -> account.accountSale(null), "Recording a sale with a null summary should not throw an exception.");
    }

    /**
     * Tests recording a sale with valid SaleSummaryDTO data.
     */
    @Test
    void testAccountSaleWithValidSummary() {
        TimeOfSaleDTO time = new TimeOfSaleDTO("2023-05-01_14:30");
        BoughtItemsDTO boughtItems = new BoughtItemsDTO(new HashMap<>());
        PaymentInfoDTO paymentInfo = new PaymentInfoDTO(100.0, 20.0, 80.0, 10.0);
        SaleSummaryDTO summary = new SaleSummaryDTO(time, boughtItems, paymentInfo);

        assertDoesNotThrow(() -> account.accountSale(summary), "Recording a sale with valid data should not throw an exception.");
    }
}