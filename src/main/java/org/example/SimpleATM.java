package org.example;

import java.util.Map;
import java.util.TreeMap;

public class SimpleATM implements ATMService {
    private final TreeMap<Banknote, Integer> cash = new TreeMap<>((a, b) -> Integer.compare(b.getDenomination(), a.getDenomination())); // Order by denomination descending
    private int totalCash = 0;

    /**
     * Deposits a specified quantity of banknotes into the ATM.
     *
     * @param banknote the banknote to deposit
     * @param quantity the number of banknotes to deposit
     */
    @Override
    public void deposit(Banknote banknote, int quantity) {
        cash.put(banknote, cash.getOrDefault(banknote, 0) + quantity);
        totalCash += banknote.getDenomination() * quantity;
    }

    /**
     * Withdraws a specified amount of money from the ATM, attempting to dispense
     * the minimum number of banknotes.
     *
     * @param amount the amount of money to withdraw
     * @throws InsufficientFundsException if the ATM does not have enough cash
     * @throws ExactChangeNotDispensedException if the exact amount cannot be dispensed
     */
    @Override
    public void withdraw(int amount) throws InsufficientFundsException, ExactChangeNotDispensedException {
        if (amount > totalCash) {
            throw new InsufficientFundsException("ATM does not have enough cash.");
        }

        Map<Banknote, Integer> withdrawnNotes = new TreeMap<>((a, b) -> Integer.compare(b.getDenomination(), a.getDenomination())); // Order by denomination descending

        for (Map.Entry<Banknote, Integer> entry : cash.entrySet()) {
            Banknote banknote = entry.getKey();
            int denomination = banknote.getDenomination();
            int available = entry.getValue();

            while (amount >= denomination && available > 0) {
                withdrawnNotes.put(banknote, withdrawnNotes.getOrDefault(banknote, 0) + 1);
                amount -= denomination;
                cash.put(banknote, available - 1);
                available--;
            }
        }

        if (amount > 0) {
            // Rollback
            for (Map.Entry<Banknote, Integer> entry : withdrawnNotes.entrySet()) {
                cash.put(entry.getKey(), cash.get(entry.getKey()) + entry.getValue());
            }
            throw new ExactChangeNotDispensedException("Cannot dispense the exact amount requested.");
        }

        for (Map.Entry<Banknote, Integer> entry : withdrawnNotes.entrySet()) {
            System.out.println("Dispensed " + entry.getValue() + " banknote(s) of denomination " + entry.getKey().getDenomination());
        }

        totalCash -= withdrawnNotes.entrySet().stream()
                .mapToInt(entry -> entry.getValue() * entry.getKey().getDenomination())
                .sum();

    }

    /**
     * Returns the total amount of cash currently in the ATM.
     *
     * @return the total cash in the ATM
     */
    @Override
    public int getCashInATM() {
        return totalCash;
    }
}