package org.komissarov.lab1.entities.transactions;

import lombok.Getter;
import org.komissarov.lab1.entities.Bank;
import org.komissarov.lab1.entities.accounts.CreditAccount;
import org.komissarov.lab1.entities.accounts.DepositAccount;
import org.komissarov.lab1.entities.accounts.IAccount;

/**
 * This class represents an increase transaction on an account.
 * It extends the Transaction class and provides the necessary methods for executing and undoing a transaction.
 */
@Getter
public class decreaseTransaction extends Transaction {
    private final IAccount account;

    /**
     * Constructs a new increaseTransaction with the specified account and amount.
     *
     * @param amount  the amount to be added
     * @param account the account to which the amount will be added
     */
    public decreaseTransaction(double amount, IAccount account) {
        super(amount);
        this.account = account;
    }

    /**
     * Executes this transaction by adding the amount to the account.
     */
    @Override
    public void execute() {
        account.decreaseBalance(getAmount());
    }

    /**
     * Undoes this transaction by deducting the amount from the account.
     */
    @Override
    public void undo() {
        account.increaseBalance(getAmount());
    }

    /**
     * Checks if this transaction has been executed.
     *
     * @param bank the bank in which the transaction is being performed
     * @return true if the transaction has been executed, false otherwise
     */
    @Override
    public boolean isExecuted(Bank bank) {
        if (bank.getClients().stream().filter(client -> client.getId().equals(account.getClientId())).findFirst().get().isSuspicious()) {
            return false;
        }

        if (account instanceof CreditAccount) {
            return !(bank.getCreditLimit() < getAmount());
        }

        if (account instanceof DepositAccount) {
            return !(((DepositAccount) account).getDays() > 0);
        }

        return !(account.getBalance() < getAmount());
    }
}
