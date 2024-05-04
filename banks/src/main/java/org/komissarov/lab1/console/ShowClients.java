package org.komissarov.lab1.console;

import lombok.NoArgsConstructor;
import org.komissarov.lab1.entities.Bank;
import org.komissarov.lab1.entities.Client;
import org.komissarov.lab1.services.SuperBank;

@NoArgsConstructor
public class ShowClients implements IConsole {
    @Override
    public void request() {

    }

    @Override
    public boolean execute(String message, SuperBank superBank) {
        if (message.equals("5")) {
            clear();
            System.out.println("Showing all clients");
            for (Bank bank : superBank.getBanks()) {
                for (Client client : bank.getClients()) {
                    System.out.println(client.getName() + ' ' + client.getId());
                }
            }

            return true;
        }
        return false;
    }
}
