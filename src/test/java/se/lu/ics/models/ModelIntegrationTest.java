package se.lu.ics.models;

public class ModelIntegrationTest {
    public static void main(String[] args) {
        // Create one instance of PersonRegister
        PersonRegister register = new PersonRegister();

        // Create two instances of Person
        Person person1 = new Person("123456789", "Simon", 25);
        Person person2 = new Person("987654321", "Mirnes", 30);

        // Adding them to the PersonRegister instance
        register.addPerson(person1);
        register.addPerson(person2);

        // Create and add two instances of BankAccount for each person
        BankAccount account1 = new BankAccount("ACC123", 500.00);
        BankAccount account2 = new BankAccount("ACC456", 5000.00);
        person1.addAccount(account1);
        person1.addAccount(account2);

        BankAccount account3 = new BankAccount("ACC789", 2000.00);
        BankAccount account4 = new BankAccount("ACC012", 3000.00);
        person2.addAccount(account3);
        person2.addAccount(account4);

        // Test all methods by invoking them and printing the result of the invocations
        System.out.println("Total balance for all persons: " + register.calculateTotalBalance());

        // Find person and account
        Person foundPerson = register.findPerson("123456789");
        if (foundPerson != null) {
            System.out.println(
                    "Found person: " + foundPerson.getName() + " ID: " + foundPerson.getIdentificationNumber());
        }

        BankAccount foundAccount = register.findAccount("ACC123");
        if (foundAccount != null) {
            System.out.println("Found account with balance: " + foundAccount.getBalance());
        }

        // Deposit into an account
        foundAccount.deposit(400.00);
        System.out.println("Balance after deposit: " + foundAccount.getBalance());

        // Withdraw from an account
        try {
            foundAccount.withdraw(250.00);
            System.out.println("Balance after withdrawal: " + foundAccount.getBalance());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        // Withdraw negative amount
        try {
            foundAccount.withdraw(-250.00);
            System.out.println("Balance after withdrawal: " + foundAccount.getBalance());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        // Withdraw string
        try {
            foundAccount.withdraw(300);
            System.out.println("Balance after withdrawal: " + foundAccount.getBalance());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        // Withdraw more than 50% of balance
        try {
            foundAccount.withdraw(375.00);
            System.out.println("Balance after withdrawal: " + foundAccount.getBalance());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        // Withdraw more than balance
        try {
            foundAccount.withdraw(1000.00);
            System.out.println("Balance after withdrawal: " + foundAccount.getBalance());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        // Test all business rules to make sure that none of them can be violated

        // Rule: Person cannot have more than 3 accounts
        try {
            person1.addAccount(new BankAccount("ACC999", 7000.00));
            person1.addAccount(new BankAccount("ACC888", 8000.00));
        } catch (IllegalStateException e) {
            System.out.println(
                    "Cannot add more than 3 accounts" + "| Current account number: " + person1.getNumberOfAccounts());
        }

        // Rule: Person must be 18 or older to have an account
        try {
            Person youngPerson = new Person("111222333", "Charlie", 16);
            youngPerson.addAccount(new BankAccount("ACC111", 100.00));
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
