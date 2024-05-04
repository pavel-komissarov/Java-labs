package org.komissarov.lab1.console;

import lombok.NoArgsConstructor;
import org.komissarov.lab1.services.SuperBank;

import java.util.Scanner;
import java.util.UUID;

@NoArgsConstructor

public class MakeTransaction implements IConsole {
    private UUID toAccountId;
    private UUID fromAccountId;
    private double amount;

    @Override
    public void request() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the id of the account to transfer to");
        toAccountId = UUID.fromString(scanner.nextLine());
        System.out.println("Enter the id of the account to transfer from");
        fromAccountId = UUID.fromString(scanner.nextLine());
        System.out.println("Enter the amount to transfer");
        amount = scanner.nextDouble();
    }

    @Override
    public boolean execute(String message, SuperBank superBank) {
        if (message.equals("4")) {
            clear();
            System.out.println("Making a transaction");
            request();
            superBank.makeTransactionBetweenAccounts(toAccountId, fromAccountId, amount);
        }
        return false;
    }
}
