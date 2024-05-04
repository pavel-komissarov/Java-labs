package org.komissarov.lab1.entities.accounts;

import org.komissarov.lab1.entities.Bank;

import java.util.UUID;

/**
 * This interface represents a bank account.
 * It provides the necessary methods for managing a bank account.
 */
public interface IAccount {

    /**
     * Increases the balance of this account by the specified amount.
     *
     * @param amount the amount to add to the balance
     */
    void increaseBalance(double amount);

    /**
     * Decreases the balance of this account by the specified amount.
     *
     * @param amount the amount to subtract from the balance
     */
    void decreaseBalance(double amount);

    /**
     * Returns the current balance of this account.
     *
     * @return the current balance
     */
    double getBalance();

    /**
     * Returns the ID of this account.
     *
     * @return the account ID
     */
    UUID getId();

    /**
     * Returns the ID of the client who owns this account.
     *
     * @return the client ID
     */
    UUID getClientId();

    /**
     * Counts the balance for a given number of days and a given bank.
     *
     * @param days the number of days
     * @param bank the bank
     */
    void countBalance(int days, Bank bank);
}
