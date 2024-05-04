package org.komissarov.lab1.console;

import lombok.NoArgsConstructor;
import org.komissarov.lab1.entities.accounts.CreditAccount;
import org.komissarov.lab1.entities.accounts.DepitAccount;
import org.komissarov.lab1.entities.accounts.DepositAccount;
import org.komissarov.lab1.entities.accounts.IAccount;
import org.komissarov.lab1.services.SuperBank;

import java.util.Scanner;
import java.util.UUID;

@NoArgsConstructor
public class CreateAccount implements IConsole {
    private int accountType;
    private UUID clientId;
    private int days;

    @Override
    public void request() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the id of the client");
        clientId = UUID.fromString(scanner.nextLine());
        System.out.println("Chose the type of account:");
        System.out.println("1 - Credit");
        System.out.println("2 - Debit");
        System.out.println("3 - Deposit");

        accountType = scanner.nextInt();

        if (accountType == 3) {
            System.out.println("Enter the number of days");
            days = scanner.nextInt();
        }

    }

    @Override
    public boolean execute(String message, SuperBank superBank) {
        clear();
        System.out.println("Creating an account");
        request();

        if (accountType < 1 || accountType > 3) {
            System.out.println("Invalid account type");
            return false;
        }

        if (message.equals("3")) {
            IAccount account = switch (accountType) {
                case 1 -> new CreditAccount(clientId);
                case 2 -> new DepitAccount(clientId);
                case 3 -> new DepositAccount(clientId, days);
                default -> null;
            };

            superBank.getBanks().stream().filter(bank -> bank.getClients().stream().anyMatch(client -> client.getId().equals(clientId))).findFirst().ifPresent(bank -> bank.addAccount(account));

            return true;
        }
        return false;
    }
}
