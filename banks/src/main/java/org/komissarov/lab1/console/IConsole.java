package org.komissarov.lab1.console;

import org.komissarov.lab1.services.SuperBank;

/**
 * This interface represents a console.
 * It provides the necessary methods for managing a console.
 */
public interface IConsole {

    /**
     * Initiates a request for console input.
     * This method is responsible for prompting the user
     * for input in the console.
     */
    void request();

    /**
     * Executes a command in the console.
     *
     * @param message   the command to execute
     * @param superBank the SuperBank instance to use for executing the command
     * @return true if the command was executed successfully, false otherwise
     */
    boolean execute(String message, SuperBank superBank);

    /**
     * Clears the console.
     */
    default void clear() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }
}
