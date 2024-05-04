package org.komissarov.lab1.entities;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class represents a client in a bank.
 * It provides the necessary methods for managing a client.
 */
@Getter
@Builder
public class Client {
    @NonNull
    private final String name;
    @NonNull
    private final String surname;
    @NonNull
    @Default
    private final UUID id = UUID.randomUUID();
    @Default
    private final List<Notification> notifications = new ArrayList<>();
    @Default
    private String passport = null;
    @Default
    private String address = null;

    /**
     * Sets the passport of the client.
     *
     * @param clientPassport the passport of the client
     */
    public void withPassport(String clientPassport) {
        passport = clientPassport;
    }

    /**
     * Sets the address of the client.
     *
     * @param clientAddress the address of the client
     */
    public void withAddress(String clientAddress) {
        address = clientAddress;
    }

    /**
     * Checks if the client is suspicious.
     *
     * @return true if the client is suspicious, false otherwise
     */
    public boolean isSuspicious() {
        return noPassport() || noAddress();
    }

    /**
     * Checks if the client has no passport.
     *
     * @return true if the client has no passport, false otherwise
     */
    public boolean noPassport() {
        return passport == null;
    }

    /**
     * Checks if the client has no address.
     *
     * @return true if the client has no address, false otherwise
     */
    public boolean noAddress() {
        return address == null;
    }

    /**
     * Adds a notification to the client.
     *
     * @param notification the notification to be added
     */
    public void addNotification(Notification notification) {
        notifications.add(notification);
    }
}