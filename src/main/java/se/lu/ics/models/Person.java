package se.lu.ics.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Person {
    private String identificationNumber;
    private String name;
    private int age;
    private List<BankAccount> customerBankAccounts = new ArrayList<>();

    @Override // Override toString method to return a string that represents the person
    public String toString() {
        return "Person:\n" +
                "ID: " + identificationNumber + "\n" +
                "Name: " + name + "\n" +
                "Age: " + age + "\n";
    }

    public Person(String identificationNumber, String name, int age) {
        this.identificationNumber = identificationNumber;
        this.name = name;
        this.age = age;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public void addAccount(BankAccount account) {
        customerBankAccounts.add(account);
    }

    // Loop through back accounts and remove account if account number matches

    public void removeAccount(String accountNumber) {
        for (BankAccount account : customerBankAccounts) {
            if (accountNumber.equals(account.getAccountNumber())) {
                customerBankAccounts.remove(account);
                return;
            }
        }
    }

    // Loop through bank accounts and find account by account number
    public BankAccount findAccount(String accountNumber) {
        for (BankAccount account : customerBankAccounts) {
            if (accountNumber.equals(account.getAccountNumber())) {
                return account;
            }
        }
        return null;
    }

    // Loop through bank accounts and calculate total balance
    public double calculateTotalBalance() {
        double totalBalance = 0.0;
        for (BankAccount account : customerBankAccounts) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }

    public int getNumberOfAccounts() {
        return customerBankAccounts.size();
    }

    public List<BankAccount> getCustomerBankAccounts() {
        return customerBankAccounts;
    }

    public String getAccountsAsString() {
        return customerBankAccounts.stream()
                .map(BankAccount::toString) // Convert each account to a string
                .collect(Collectors.joining(", "));
    }
}