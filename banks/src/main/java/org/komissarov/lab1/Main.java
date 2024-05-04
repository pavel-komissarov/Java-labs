package org.komissarov.lab1;

import org.komissarov.lab1.console.*;
import org.komissarov.lab1.services.SuperBank;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome to the bank!");

        SuperBank superBank = new SuperBank();

        ArrayList<IConsole> consoles = new ArrayList<IConsole>();
        consoles.add(new ShowClients());
        consoles.add(new ShowAccounts());
        consoles.add(new CreateBank());
        consoles.add(new CreateClient());
        consoles.add(new CreateAccount());
        consoles.add(new MakeTransaction());

        IConsole welcome = new Welcome();

        while (true) {
            welcome.request();
            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();
            for (IConsole console : consoles) {
                if (console.execute(message, superBank)) {
                    break;
                }
            }
        }


    }
}