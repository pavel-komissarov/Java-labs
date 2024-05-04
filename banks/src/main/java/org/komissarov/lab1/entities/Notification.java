package org.komissarov.lab1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class represents a notification for a client in a bank.
 * It provides the necessary methods for managing a notification.
 */
@Data
@AllArgsConstructor
public class Notification {
    private String notification;
}
