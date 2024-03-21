package se.lu.ics.models;

import java.util.ArrayList;
import java.util.List;

public class PersonRegister {
    private List<Person> persons = new ArrayList<>();

    public void addPerson(Person person) {
        persons.add(person);
    }

    // Loop though persons in list and find person by identification number
    public Person findPerson(String identificationNumber) {
        for (Person person : persons) {
            if (identificationNumber.equals(person.getIdentificationNumber())) {
                return person;
            }
        }
        return null;
    }

    // Loop though persons in list and find account by account number
    public BankAccount findAccount(String accountNumber) {
        for (Person person : persons) {
            BankAccount account = person.findAccount(accountNumber);
            if (account != null) {
                return account;
            }
        }
        return null;
    }

    // Calculate total balance for all accounts in the whole person register
    public double calculateTotalBalance() {
        double totalBalance = 0;
        for (Person person : persons) {
            totalBalance += person.calculateTotalBalance();
        }
        return totalBalance;
    }

    // Calculate total balance of a single person
    public double calculateTotalBalance(String identificationNumber) {
        Person person = findPerson(identificationNumber);
        if (person != null) {
            return person.calculateTotalBalance();
        }
        return 0;
    }

    // Remove person from list
    public void removePerson(String identificationNumber) {
        Person person = findPerson(identificationNumber);
        if (person != null) {
            persons.remove(person);
        }
    }

    // Return all persons in the list
    public Person[] getAllPersons() {
        return persons.toArray(new Person[0]);
    }
}