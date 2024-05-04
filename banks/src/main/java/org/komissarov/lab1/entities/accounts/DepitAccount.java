package org.komissarov.lab1.entities.accounts;

import org.komissarov.lab1.entities.Bank;

import java.util.UUID;

/**
 * This class represents a debit account in a bank.
 * It implements the IAccount interface and provides the necessary methods for managing a debit account.
 */
public class DepitAccount implements IAccount {
    private final UUID id;
    private final UUID clientId;
    private double balance;

    /**
     * Constructs a new DepitAccount with the specified client ID.
     * The balance of the new account is initialized to 0.
     *
     * @param clientId the ID of the client who owns this account
     */
    public DepitAccount(UUID clientId) {
        this.balance = 0;
        this.id = UUID.randomUUID();
        this.clientId = clientId;
    }

    /**
     * Increases the balance of this account by the specified amount.
     *
     * @param amount the amount to add to the balance
     */
    @Override
    public void increaseBalance(double amount) {
        balance += amount;
    }

    /**
     * Decreases the balance of this account by the specified amount.
     *
     * @param amount the amount to subtract from the balance
     */
    @Override
    public void decreaseBalance(double amount) {
        balance -= amount;
    }

    /**
     * Returns the current balance of this account.
     *
     * @return the current balance
     */
    @Override
    public double getBalance() {
        return balance;
    }

    /**
     * Returns the ID of this account.
     *
     * @return the account ID
     */
    @Override
    public UUID getId() {
        return id;
    }

    /**
     * Returns the ID of the client who owns this account.
     *
     * @return the client ID
     */
    @Override
    public UUID getClientId() {
        return clientId;
    }

    /**
     * Counts the balance for a given number of days and a given bank.
     * The balance is increased by the product of the balance, the bank's commission,
     * and the number of days divided by 365.
     *
     * @param days the number of days
     * @param bank the bank
     */
    @Override
    public void countBalance(int days, Bank bank) {
        if (days <= 0) {
            throw new IllegalArgumentException("The number of days must be positive");
        }

        balance += balance * bank.getCommission() * days / 365;
    }
}
