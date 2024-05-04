package org.komissarov.lab1.console;

import lombok.NoArgsConstructor;
import org.komissarov.lab1.services.SuperBank;

@NoArgsConstructor
public class Welcome implements IConsole {

    @Override
    public void request() {
        System.out.println("Hello and welcome to the Super bank!");
        System.out.println("1 - Create a Bank");
        System.out.println("2 - Create a Client");
        System.out.println("3 - Create an Account");
        System.out.println("4 - Make a transaction");
        System.out.println("5 - Show all clients");
        System.out.println("6 - Show all accounts");
        System.out.println("7 - Exit");
    }

    @Override
    public boolean execute(String message, SuperBank superBank) {
        clear();
        request();
        return false;
    }
}
