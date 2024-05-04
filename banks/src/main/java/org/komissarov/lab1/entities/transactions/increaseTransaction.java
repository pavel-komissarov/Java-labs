package org.komissarov.lab1.entities.transactions;

import lombok.Getter;
import org.komissarov.lab1.entities.Bank;
import org.komissarov.lab1.entities.accounts.IAccount;

/**
 * fff
 * This abstract class represents a transaction on an account.
 * It provides the necessary methods for executing and undoing a transaction.
 */
@Getter
public class increaseTransaction extends Transaction {
    final IAccount account;

    /**
     * Constructs a new Transaction with the specified amount.
     *
     * @param amount the amount to be transacted
     */
    public increaseTransaction(double amount, IAccount account) {
        super(amount);
        this.account = account;
    }

    /**
     * Executes this transaction.
     */
    @Override
    public void execute() {
        account.increaseBalance(getAmount());
    }

    /**
     * Undoes this transaction.
     */
    @Override
    public void undo() {
        account.decreaseBalance(getAmount());
    }

    /**
     * Checks if this transaction has been executed.
     *
     * @param bank the bank in which the transaction is being performed
     * @return true if the transaction has been executed, false otherwise
     */
    @Override
    public boolean isExecuted(Bank bank) {
        return true;
    }
}
