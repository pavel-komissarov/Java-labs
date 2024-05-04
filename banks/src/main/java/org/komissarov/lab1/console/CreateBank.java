package org.komissarov.lab1.console;

import lombok.NoArgsConstructor;
import org.komissarov.lab1.services.SuperBank;

import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

@NoArgsConstructor
public class CreateBank implements IConsole {
    final SortedMap<Double, Double> debitPercentage = new TreeMap<>();
    double commission;
    private String bankName;
    private double creditPercentage;
    private double creditLimit;

    @Override
    public void request() {
        System.out.println("Enter the name of the bank");
        Scanner scanner = new Scanner(System.in);
        bankName = scanner.nextLine();
        System.out.println("Enter the credit percentage");
        creditPercentage = scanner.nextDouble();
        System.out.println("Enter the credit limit");
        creditLimit = scanner.nextDouble();
        System.out.println("Enter the commission");
        commission = scanner.nextDouble();
        System.out.println("How many debit percentages do you want to enter?");
        int numberOfEntries = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < numberOfEntries; i++) {
            System.out.println("Enter the debit percentage pair " + (i + 1));
            Double key = scanner.nextDouble();
            Double value = scanner.nextDouble();
            debitPercentage.put(key, value);
        }
    }

    @Override
    public boolean execute(String message, SuperBank superBank) {
        if (message.equals("1")) {
            clear();
            System.out.println("Creating a bank");
            request();
            superBank.addBank(bankName, creditPercentage, creditLimit, commission, debitPercentage);

            return true;
        }
        return false;
    }
}
