package org.komissarov.lab1.services;

import lombok.Getter;
import org.komissarov.lab1.entities.Bank;
import org.komissarov.lab1.entities.accounts.IAccount;
import org.komissarov.lab1.entities.transactions.Transaction;
import org.komissarov.lab1.entities.transactions.betweenAccountsTransaction;
import org.komissarov.lab1.entities.transactions.decreaseTransaction;
import org.komissarov.lab1.entities.transactions.increaseTransaction;

import java.util.*;

/**
 * This class represents a super bank that manages multiple banks.
 * It provides the necessary methods for managing a super bank.
 */
@Getter
public class SuperBank {
    private final List<Bank> banks = new ArrayList<>();
    private final Stack<Transaction> transactions = new Stack<>();

    /**
     * Updates the credit rate percent of a bank.
     *
     * @param bankId            the ID of the bank
     * @param creditRatePercent the new credit rate percent
     */
    public void udtaeBankTermsCredit(String bankId, double creditRatePercent) {
        for (Bank bank : banks) {
            if (bank.getName().equals(bankId)) {
                bank.updateCreditPercentage(creditRatePercent);
            }
        }
    }

    /**
     * Updates the debit rate percent of a bank.
     *
     * @param bankId           the ID of the bank
     * @param debitRatePercent the new debit rate percent
     */
    public void updateBankTermsDebit(String bankId, SortedMap<Double, Double> debitRatePercent) {
        for (Bank bank : banks) {
            if (bank.getName().equals(bankId)) {
                bank.updateDebitPercentage(debitRatePercent);
            }
        }
    }

    /**
     * Updates the credit limit of a bank.
     *
     * @param bankId      the ID of the bank
     * @param creditLimit the new credit limit
     */
    public void updateBankTermsCreditLimit(String bankId, double creditLimit) {
        for (Bank bank : banks) {
            if (bank.getName().equals(bankId)) {
                bank.updateCreditLimit(creditLimit);
            }
        }
    }

    /**
     * Updates the commission of a bank.
     *
     * @param bankId     the ID of the bank
     * @param commission the new commission
     */
    public void updateBankTermsCommission(String bankId, double commission) {
        for (Bank bank : banks) {
            if (bank.getName().equals(bankId)) {
                bank.updateCommission(commission);
            }
        }
    }

    /**
     * Updates the time of all banks.
     *
     * @param days the number of days to update
     */
    public void updateTime(int days) {
        for (Bank bank : banks) {
            bank.updateTime(days);
        }
    }

    /**
     * Makes a transaction between two accounts.
     *
     * @param fromAccountId the ID of the account from which the amount will be deducted
     * @param toAccountId   the ID of the account to which the amount will be added
     * @param amount        the amount to be transacted
     */
    public void makeTransactionBetweenAccounts(UUID fromAccountId, UUID toAccountId, double amount) {
        IAccount fromAccount = null;
        Bank fromBank = null;

        for (Bank bank : banks) {
            for (IAccount account : bank.getAccounts()) {
                if (account.getId().equals(fromAccountId)) {
                    fromBank = bank;
                    fromAccount = account;
                    break;
                }
            }
        }

        IAccount toAccount = banks.stream().flatMap(bank -> bank.getAccounts().stream()).filter(account -> account.getId().equals(toAccountId)).findFirst().orElse(null);

        if (fromAccount != null && toAccount != null) {
            betweenAccountsTransaction transaction = new betweenAccountsTransaction(fromAccount, toAccount, amount);
            if (transaction.isExecuted(fromBank)) {
                transaction.execute();
                transactions.push(transaction);
            }
        }
    }

    /**
     * Makes a decrease transaction on an account.
     *
     * @param accountId the ID of the account
     * @param amount    the amount to be deducted
     */
    public void makeDecreaseTransaction(UUID accountId, double amount) {
        IAccount fromAccount = null;
        Bank fromBank = null;

        for (Bank bank : banks) {
            for (IAccount account : bank.getAccounts()) {
                if (account.getId().equals(accountId)) {
                    fromBank = bank;
                    fromAccount = account;
                }
            }
        }

        if (fromAccount != null) {
            decreaseTransaction transaction = new decreaseTransaction(amount, fromAccount);
            if (transaction.isExecuted(fromBank)) {
                transaction.execute();
                transactions.push(transaction);
            }
        }
    }

    /**
     * Makes an increase transaction on an account.
     *
     * @param accountId the ID of the account
     * @param amount    the amount to be added
     */
    public void makeIncreaseTransaction(UUID accountId, double amount) {
        IAccount fromAccount = null;
        Bank fromBank = null;

        for (Bank bank : banks) {
            for (IAccount account : bank.getAccounts()) {
                if (account.getId().equals(accountId)) {
                    fromBank = bank;
                    fromAccount = account;
                    break;
                }
            }
        }

        if (fromAccount != null) {
            increaseTransaction transaction = new increaseTransaction(amount, fromAccount);
            if (transaction.isExecuted(fromBank)) {
                transaction.execute();
                transactions.push(transaction);
            }
        }
    }

    /**
     * Undoes the last transaction.
     */
    public void undoTransaction() {
        if (!transactions.isEmpty()) {
            transactions.pop().undo();
        }
    }

    /**
     * Adds a bank to the super bank.
     *
     * @param bankName         the name of the bank
     * @param creditPercentage the credit percentage of the bank
     * @param creditLimit      the credit limit of the bank
     * @param commission       the commission of the bank
     * @param debitPercentage  the debit percentage of the bank
     */
    public void addBank(String bankName,
                        double creditPercentage,
                        double creditLimit,
                        double commission,
                        SortedMap<Double, Double> debitPercentage) {
        banks.add(new Bank(bankName, creditPercentage, creditLimit, commission, debitPercentage));
    }

    /**
     * Removes a bank from the super bank.
     *
     * @param bank the name of the bank
     */
    public void removeBank(String bank) {
        banks.removeIf(bank1 -> bank1.getName().equals(bank));
    }
}
