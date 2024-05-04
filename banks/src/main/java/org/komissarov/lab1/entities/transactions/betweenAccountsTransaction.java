package org.komissarov.lab1.entities.transactions;

import org.komissarov.lab1.entities.Bank;
import org.komissarov.lab1.entities.accounts.CreditAccount;
import org.komissarov.lab1.entities.accounts.DepositAccount;
import org.komissarov.lab1.entities.accounts.IAccount;

/**
 * This class represents a transaction between two accounts.
 * It extends the Transaction class and provides the necessary methods for executing and undoing a transaction.
 */
public class betweenAccountsTransaction extends Transaction {
    private final IAccount fromAccount;
    private final IAccount toAccount;

    /**
     * Constructs a new betweenAccountsTransaction with the specified source account, destination account, and amount.
     *
     * @param fromAccount the account from which the amount will be deducted
     * @param toAccount   the account to which the amount will be added
     * @param amount      the amount to be transferred
     */
    public betweenAccountsTransaction(IAccount fromAccount, IAccount toAccount, double amount) {
        super(amount);
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    /**
     * Returns the source account of this transaction.
     *
     * @return the source account
     */
    public IAccount getFromAccount() {
        return fromAccount;
    }

    /**
     * Returns the destination account of this transaction.
     *
     * @return the destination account
     */
    public IAccount getToAccount() {
        return toAccount;
    }

    /**
     * Executes this transaction by deducting the amount from the source account
     * and adding it to the destination account.
     */
    @Override
    public void execute() {
        fromAccount.decreaseBalance(getAmount());
        toAccount.increaseBalance(getAmount());
    }

    /**
     * Undoes this transaction by adding the amount back to the source account
     * and deducting it from the destination account.
     */
    @Override
    public void undo() {
        fromAccount.increaseBalance(getAmount());
        toAccount.decreaseBalance(getAmount());
    }

    /**
     * Checks if this transaction has been executed.
     *
     * @param bank the bank in which the transaction is being performed
     * @return true if the transaction has been executed, false otherwise
     */
    @Override
    public boolean isExecuted(Bank bank) {
        if (bank.getClients().stream().filter(client -> client.getId().equals(fromAccount.getClientId())).findFirst().get().isSuspicious()) {
            return false;
        }

        if (fromAccount instanceof CreditAccount) {
            return !(bank.getCreditLimit() < getAmount());
        }

        if (fromAccount instanceof DepositAccount) {
            return !(((DepositAccount) fromAccount).getDays() > 0);
        }

        if (fromAccount.getBalance() < getAmount()) {
            return false;
        }

        return !(fromAccount.getBalance() < getAmount());
    }
}
