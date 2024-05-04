package org.komissarov.lab1.console;

import lombok.NoArgsConstructor;
import org.komissarov.lab1.entities.Client;
import org.komissarov.lab1.services.SuperBank;

import java.util.Scanner;

@NoArgsConstructor
public class CreateClient implements IConsole {
    private String bankName;
    private String name;
    private String surname;
    private String passport;
    private String address;

    @Override
    public void request() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the bank");
        bankName = scanner.nextLine();
        System.out.println("Enter the name of the client:");
        name = scanner.nextLine();
        System.out.println("Enter the surname of the client:");
        surname = scanner.nextLine();
        System.out.println("Enter the passport of the client:");
        passport = scanner.nextLine();
        System.out.println("Enter the address of the client:");
        address = scanner.nextLine();
    }

    @Override
    public boolean execute(String message, SuperBank superBank) {
        if (message.equals("2")) {
            clear();
            System.out.println("Creating a client");
            request();
            superBank.getBanks().stream().filter(bank -> bank.getName().equals(bankName)).findFirst().ifPresent(bank -> bank.addClient(Client.builder().name(name).surname(surname).address(address).passport(passport).build()));
            return true;
        }
        return false;
    }
}
