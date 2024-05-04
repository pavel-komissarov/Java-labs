package org.komissarov.lab1.console;

import lombok.NoArgsConstructor;
import org.komissarov.lab1.entities.Bank;
import org.komissarov.lab1.entities.accounts.IAccount;
import org.komissarov.lab1.services.SuperBank;

@NoArgsConstructor
public class ShowAccounts implements IConsole {
    @Override
    public void request() {

    }

    @Override
    public boolean execute(String message, SuperBank superBank) {
        if (message.equals("6")) {
            clear();
            System.out.println("Showing all accounts");
            for (Bank bank : superBank.getBanks()) {
                for (IAccount account : bank.getAccounts()) {
                    System.out.println(account.getId() + " " + account.getBalance());
                }
            }
        }
        return false;
    }
}
