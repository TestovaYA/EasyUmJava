package org.example;

public interface ATMService {
    void deposit(Banknote Banknote, int quantity);
    void withdraw(int amount);
    int getCashInATM();
}
