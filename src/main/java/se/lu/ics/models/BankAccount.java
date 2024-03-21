package se.lu.ics.models;

public class BankAccount {
    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    // Getter and setter methods
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    // Override toString method to return a string that represents the account
    @Override
    public String toString() {
        // Return a string that represents the account, for example:
        return (this.accountNumber + " (" + this.balance + " SEK)");
    }

    // Deposit amount to balance
    public void deposit(double amount) {
        balance += amount;
    }

    // Withdraw amount from balance
    public void withdraw(double amount) {
        balance -= amount;

    }

}
