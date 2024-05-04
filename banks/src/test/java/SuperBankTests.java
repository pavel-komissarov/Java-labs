import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.komissarov.lab1.entities.Client;
import org.komissarov.lab1.entities.accounts.CreditAccount;
import org.komissarov.lab1.entities.accounts.DepitAccount;
import org.komissarov.lab1.entities.accounts.DepositAccount;
import org.komissarov.lab1.services.SuperBank;

import java.util.TreeMap;

/**
 * This class contains tests for the SuperBank class.
 */
public class SuperBankTests {
    final SuperBank superBank = new SuperBank();

    @Test
    public void testCreateBankAndClient() {
        // Arrange
        TreeMap<Double, Double> debitPercentage = new TreeMap<>();
        debitPercentage.put(0.0, 0.0);

        // Act
        superBank.addBank("TestBank", 0.3, 1000, 0.1, debitPercentage);
        Client client = Client.builder().name("Test").surname("Test").build();

        superBank.getBanks().stream()
                .filter(bank -> bank.getName().equals("TestBank"))
                .findFirst()
                .ifPresent(bank -> bank.addClient(client));

        // Assert
        Assertions.assertEquals(1, superBank.getBanks().size());
        Assertions.assertEquals(1, superBank.getBanks().get(0).getClients().size());
        Assertions.assertEquals("Test", superBank.getBanks().get(0).getClients().get(0).getName());
        Assertions.assertTrue(superBank.getBanks().get(0).getClients().get(0).isSuspicious());
    }

    @Test
    public void testCreateAccount() {
        // Arrange
        TreeMap<Double, Double> debitPercentage = new TreeMap<>();
        debitPercentage.put(0.0, 0.0);

        // Act
        Client client = Client.builder().name("Test").surname("Test").build();

        superBank.addBank("TestBank", 0.3, 1000, 0.1, debitPercentage);

        superBank.getBanks().stream()
                .filter(bank -> bank.getName().equals("TestBank"))
                .findFirst()
                .ifPresent(bank -> bank.addClient(client));

        superBank.getBanks().stream()
                .filter(bank -> bank.getName().equals("TestBank"))
                .findFirst()
                .ifPresent(bank -> bank.addAccount(new DepitAccount(client.getId())));

        // Assert
        Assertions.assertEquals(1, superBank.getBanks().size());
        Assertions.assertEquals(1, superBank.getBanks().get(0).getClients().size());
        Assertions.assertEquals(1, superBank.getBanks().get(0).getAccounts().size());
    }

    @Test
    public void testTransferDepit() {
        // Arrange
        TreeMap<Double, Double> debitPercentage = new TreeMap<>();
        debitPercentage.put(0.0, 0.0);

        // Act
        Client client = Client.builder().name("Test").surname("Test").address("test").passport("test").build();
        Client client2 = Client.builder().name("Test2").surname("Test2").address("test").passport("test").build();

        superBank.addBank("TestBank", 0.3, 1000, 0.1, debitPercentage);

        superBank.getBanks().stream()
                .filter(bank -> bank.getName().equals("TestBank"))
                .findFirst()
                .ifPresent(bank -> bank.addClient(client));

        superBank.getBanks().stream()
                .filter(bank -> bank.getName().equals("TestBank"))
                .findFirst()
                .ifPresent(bank -> bank.addClient(client2));

        superBank.getBanks().stream()
                .filter(bank -> bank.getName().equals("TestBank"))
                .findFirst()
                .ifPresent(bank -> bank.addAccount(new DepitAccount(client.getId())));

        superBank.getBanks().stream()
                .filter(bank -> bank.getName().equals("TestBank"))
                .findFirst()
                .ifPresent(bank -> bank.addAccount(new DepitAccount(client2.getId())));

        superBank.makeIncreaseTransaction(superBank.getBanks().get(0).getAccounts().get(0).getId(), 100);
        superBank.makeTransactionBetweenAccounts(
                superBank.getBanks().get(0).getAccounts().get(0).getId(),
                superBank.getBanks().get(0).getAccounts().get(1).getId(),
                100);

        // Assert
        Assertions.assertEquals(1, superBank.getBanks().size());
        Assertions.assertEquals(2, superBank.getBanks().get(0).getClients().size());
        Assertions.assertEquals(2, superBank.getBanks().get(0).getAccounts().size());
        Assertions.assertEquals(0, superBank.getBanks().get(0).getAccounts().get(0).getBalance());
        Assertions.assertEquals(100, superBank.getBanks().get(0).getAccounts().get(1).getBalance());

        // Act
        superBank.undoTransaction();

        // Assert
        Assertions.assertEquals(100, superBank.getBanks().get(0).getAccounts().get(0).getBalance());
        Assertions.assertEquals(0, superBank.getBanks().get(0).getAccounts().get(1).getBalance());
    }

    @Test
    public void testTransferCredit() {
        // Arrange
        TreeMap<Double, Double> debitPercentage = new TreeMap<>();
        debitPercentage.put(0.0, 0.0);

        // Act
        Client client = Client.builder().name("Test").surname("Test").address("test").passport("test").build();
        Client client2 = Client.builder().name("Test2").surname("Test2").address("test").passport("test").build();

        superBank.addBank("TestBank", 0.3, 1000, 0.1, debitPercentage);

        superBank.getBanks().stream()
                .filter(bank -> bank.getName().equals("TestBank"))
                .findFirst()
                .ifPresent(bank -> bank.addClient(client));

        superBank.getBanks().stream()
                .filter(bank -> bank.getName().equals("TestBank"))
                .findFirst()
                .ifPresent(bank -> bank.addClient(client2));

        superBank.getBanks().stream()
                .filter(bank -> bank.getName().equals("TestBank"))
                .findFirst()
                .ifPresent(bank -> bank.addAccount(new CreditAccount(client.getId())));

        superBank.getBanks().stream()
                .filter(bank -> bank.getName().equals("TestBank"))
                .findFirst()
                .ifPresent(bank -> bank.addAccount(new CreditAccount(client2.getId())));

        superBank.makeIncreaseTransaction(superBank.getBanks().get(0).getAccounts().get(0).getId(), 100);
        superBank.makeTransactionBetweenAccounts(
                superBank.getBanks().get(0).getAccounts().get(0).getId(),
                superBank.getBanks().get(0).getAccounts().get(1).getId(),
                100);

        // Assert
        Assertions.assertEquals(1, superBank.getBanks().size());
        Assertions.assertEquals(2, superBank.getBanks().get(0).getClients().size());
        Assertions.assertEquals(2, superBank.getBanks().get(0).getAccounts().size());
        Assertions.assertEquals(0, superBank.getBanks().get(0).getAccounts().get(0).getBalance());
        Assertions.assertEquals(100, superBank.getBanks().get(0).getAccounts().get(1).getBalance());

        // Act
        superBank.undoTransaction();

        // Assert
        Assertions.assertEquals(100, superBank.getBanks().get(0).getAccounts().get(0).getBalance());
        Assertions.assertEquals(0, superBank.getBanks().get(0).getAccounts().get(1).getBalance());
    }

    @Test
    public void testDepositAndTime() {
        // Arrange
        TreeMap<Double, Double> depositPercentage = new TreeMap<>();
        depositPercentage.put(29.0, 0.5);

        // Act
        superBank.addBank("TestBank", 0.3, 1000, 0.1, depositPercentage);

        Client client = Client.builder().name("Test").surname("Test").address("test").passport("test").build();

        superBank.getBanks().stream()
                .filter(bank -> bank.getName().equals("TestBank"))
                .findFirst()
                .ifPresent(bank -> bank.addClient(client));

        superBank.getBanks().stream()
                .filter(bank -> bank.getName().equals("TestBank"))
                .findFirst()
                .ifPresent(bank -> bank.addAccount(new DepositAccount(client.getId(), 30)));

        superBank.makeIncreaseTransaction(superBank.getBanks().get(0).getAccounts().get(0).getId(), 45);

        superBank.updateTime(30);

        // Assert
        Assertions.assertEquals(46, (int) superBank.getBanks().get(0).getAccounts().get(0).getBalance());
        Assertions.assertEquals(0, ((DepositAccount) superBank.getBanks().get(0).getAccounts().get(0)).getDays());
    }

    @Test
    public void testNotifyClients() {
        // Arrange
        TreeMap<Double, Double> depositPercentage = new TreeMap<>();
        depositPercentage.put(30.0, 0.5);

        // Act
        superBank.addBank("TestBank", 0.3, 1000, 0.1, depositPercentage);

        Client client = Client.builder().name("Test").surname("Test").address("test").passport("test").build();

        superBank.getBanks().stream()
                .filter(bank -> bank.getName().equals("TestBank"))
                .findFirst()
                .ifPresent(bank -> bank.addClient(client));

        superBank.getBanks().stream()
                .filter(bank -> bank.getName().equals("TestBank"))
                .findFirst()
                .ifPresent(bank -> bank.addNotifyClient(client.getId()));

        superBank.getBanks().stream()
                .filter(bank -> bank.getName().equals("TestBank"))
                .findFirst()
                .ifPresent(bank -> bank.updateCreditPercentage(0.5));

        // Assertion
        Assertions.assertEquals(1, superBank.getBanks().get(0).getClients().get(0).getNotifications().size());
        Assertions.assertEquals("Credit percentage has been updated", superBank.getBanks().get(0).getClients().get(0).getNotifications().get(0).getNotification());
    }
}
