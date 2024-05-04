package org.komissarov.lab1.entities.transactions;


import org.komissarov.lab1.entities.Bank;

/**
 * This abstract class represents a transaction on an account.
 * It provides the necessary methods for executing and undoing a transaction.
 */
public abstract class Transaction {
    private final double amount;

    /**
     * Constructs a new Transaction with the specified amount.
     *
     * @param amount the amount to be transacted
     */
    public Transaction(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.amount = amount;
    }

    /**
     * Returns the amount of this transaction.
     *
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Executes this transaction.
     */
    public abstract void execute();

    /**
     * Undoes this transaction.
     */
    public abstract void undo();

    /**
     * Checks if this transaction has been executed.
     *
     * @param bank the bank in which the transaction is being performed
     * @return true if the transaction has been executed, false otherwise
     */
    public abstract boolean isExecuted(Bank bank);
}