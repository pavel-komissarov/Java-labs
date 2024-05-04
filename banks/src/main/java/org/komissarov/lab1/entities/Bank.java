package org.komissarov.lab1.entities;

import lombok.Getter;
import org.komissarov.lab1.entities.accounts.IAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.UUID;

/**
 * This class represents a bank.
 * It provides the necessary methods for managing a bank.
 */
@Getter
public class Bank {
    private final String name;
    private final List<Client> clients = new ArrayList<>();
    private final List<UUID> notifyClients = new ArrayList<>();
    private final List<IAccount> accounts = new ArrayList<>();
    private SortedMap<Double, Double> depositPercentage;
    private double creditPercentage;
    private double creditLimit;
    private double commission;

    /**
     * Constructs a new Bank with the specified parameters.
     *
     * @param bankName         the name of the bank
     * @param creditPercentage the credit percentage of the bank
     * @param creditLimit      the credit limit of the bank
     * @param commission       the commission of the bank
     * @param debitPercentage  the debit percentage of the bank
     */
    public Bank(String bankName,
                double creditPercentage,
                double creditLimit,
                double commission,
                SortedMap<Double, Double> debitPercentage) {
        name = bankName;
        this.creditPercentage = creditPercentage;
        this.creditLimit = creditLimit;
        this.commission = commission;
        this.depositPercentage = debitPercentage;
    }

    /**
     * Adds a client to the bank.
     *
     * @param client the client to be added
     */
    public void addClient(Client client) {
        clients.add(client);
    }

    /**
     * Adds a client to the notify list of the bank.
     *
     * @param clientId the ID of the client to be added
     */
    public void addNotifyClient(UUID clientId) {
        notifyClients.add(clientId);
    }

    /**
     * Updates the credit percentage of the bank and notifies the clients.
     *
     * @param creditPercentage the new credit percentage
     */
    public void updateCreditPercentage(double creditPercentage) {
        this.creditPercentage = creditPercentage;
        notifyClients("Credit percentage has been updated");
    }

    /**
     * Updates the credit limit of the bank and notifies the clients.
     *
     * @param creditLimit the new credit limit
     */
    public void updateCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
        notifyClients("Credit limit has been updated");
    }

    /**
     * Updates the commission of the bank and notifies the clients.
     *
     * @param commission the new commission
     */
    public void updateCommission(double commission) {
        this.commission = commission;
        notifyClients("Commission has been updated");
    }

    /**
     * Updates the debit percentage of the bank and notifies the clients.
     *
     * @param debitPercentage the new debit percentage
     */
    public void updateDebitPercentage(SortedMap<Double, Double> debitPercentage) {
        this.depositPercentage = debitPercentage;
        notifyClients("Debit percentage has been updated");
    }

    /**
     * Notifies the clients of the bank with a message.
     *
     * @param message the message to be sent to the clients
     */
    public void notifyClients(String message) {
        for (UUID clientId : notifyClients) {
            Notification notification = new Notification(message);
            clients.stream()
                    .filter(client -> client.getId().equals(clientId))
                    .forEach(client -> client.addNotification(notification));
        }
    }

    /**
     * Updates the time of the bank.
     *
     * @param days the number of days to update
     */
    public void updateTime(int days) {
        accounts.forEach(account -> account.countBalance(days, this));
    }

    /**
     * Adds an account to the bank.
     *
     * @param account the account to be added
     */
    public void addAccount(IAccount account) {
        accounts.add(account);
    }

    /**
     * Removes an account from the bank.
     *
     * @param account the account to be removed
     */
    public void removeAccount(IAccount account) {
        accounts.remove(account);
    }
}