package se.lu.ics.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import se.lu.ics.models.BankAccount;
import se.lu.ics.models.Person;
import se.lu.ics.models.PersonRegister;

public class MainViewController {

    private ObservableList<Person> observablePersonList = FXCollections.observableArrayList();

    private PersonRegister personRegister = new PersonRegister();

    @FXML
    private TableView<Person> tableView;

    // TableColumns
    @FXML
    private TableColumn<Person, String> accountsColumn;

    @FXML
    private TableColumn<Person, String> accountidColumn;

    @FXML
    private TableColumn<Person, Integer> ageColumn;

    @FXML
    private TableColumn<Person, String> idColumn;

    @FXML
    private TableColumn<Person, String> nameColumn;

    // Buttons

    @FXML
    private Button buttonAddAccount;

    @FXML
    private Button buttonCustomerAdd;

    @FXML
    private Button buttonDeposit;

    @FXML
    private Button buttonWithdraw;

    // MenuItems
    @FXML
    private MenuItem buttonTest;

    @FXML
    private MenuItem buttonAboutUs;

    @FXML
    private MenuItem buttonCustomerFind;

    @FXML
    private MenuItem buttonCustomerRemove;

    @FXML
    private MenuItem buttonDisplayAccountInfo;

    @FXML
    private MenuItem buttonExitProgram;

    @FXML
    private MenuItem buttonPrintAllPersonsInfo;

    @FXML
    private MenuItem buttonShowTotalBalanceAllAccounts;

    // Labels

    @FXML
    private Label labelAccountHolder;

    @FXML
    private Label labelAccountId;

    @FXML
    private Label labelAccountToUse;

    @FXML
    private Label labelAmount;

    @FXML
    private Label labelCustomerAge;

    @FXML
    private Label labelCustomerIdentificationNumber;

    @FXML
    private Label labelCustomerName;

    @FXML
    private Label labelInitialBalance;

    @FXML
    private Label labelTotalBalance;

    // TextFields and TextAreas

    @FXML
    private TextArea textAreaAllPersonsInfo;

    @FXML
    private TextField textFieldAccountHolder;

    @FXML
    private TextField textFieldAccountId;

    @FXML
    private TextField textFieldAccountToUse;

    @FXML
    private TextField textFieldAmount;

    @FXML
    private TextField textFieldCustomerAge;

    @FXML
    private TextField textFieldCustomerIdentificationNumber;

    @FXML
    private TextField textFieldCustomerName;

    @FXML
    private TextField textFieldInitialBalance;

    @FXML
    private TextField textFieldTotalBalance;

    @FXML
    private TextField textFieldFindPersonByID;

    @FXML
    private TextField textFieldPersonIdForAccountInfo;

    @FXML // Exit button
    private void handleExitButtonAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void initialize() {
        // Initialize the columns in the TableView
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("identificationNumber"));
        accountsColumn.setCellValueFactory(new PropertyValueFactory<>("accountsAsString"));

        // Populate the observable list with data from PersonRegister
        observablePersonList.addAll(personRegister.getAllPersons());

        // Bind the observable list to the TableView
        tableView.setItems(observablePersonList);

        // Set an empty Label as the placeholder for the TableView
        tableView.setPlaceholder(new Label("Awaiting input..."));
    }

    @FXML // Adding Add person button
    private void handleButtonCustomerAddAction(ActionEvent event) {
        String identificationNumber = textFieldCustomerIdentificationNumber.getText();
        String name = textFieldCustomerName.getText();
        String ageText = textFieldCustomerAge.getText();

        if (identificationNumber.isEmpty() || name.isEmpty() || ageText.isEmpty()) {
            textAreaAllPersonsInfo.setText("All fields must be completed.");
            return;
        }

        // Additional check for valid age (numeric and positive)
        int age;
        try {
            age = Integer.parseInt(ageText);
            if (age <= 0) {
                throw new NumberFormatException("Age must be positive.");
            }
        } catch (NumberFormatException error) {
            // Check if the caught exception is due to a non-positive age
            if (error.getMessage().equals("Age must be positive.")) {
                textAreaAllPersonsInfo.setText("The age entered must be a positive number.");
            } else {
                // If the exception is for a non-numeric value
                textAreaAllPersonsInfo.setText("The age entered must be a number.");
            }
            return;
        }

        // If all checks pass, proceed with adding the person
        Person person = new Person(identificationNumber, name, age);
        personRegister.addPerson(person);
        observablePersonList.add(person); 
        tableView.refresh(); 

        // Update TextArea
        textAreaAllPersonsInfo
                .setText("Person successfully added to system." + "\n" + "Name: " + person.getName() + "\n"
                        + "Age: " + person.getAge() + "\n" + "ID number: " + person.getIdentificationNumber());

        // Clear textFields
        textFieldCustomerIdentificationNumber.clear();
        textFieldCustomerName.clear();
        textFieldCustomerAge.clear();
    }

    // Remove customer button by identification number
    @FXML
    private void handleButtonCustomerRemoveAction(ActionEvent event) {

        Stage newStage = new Stage();
        newStage.getIcons().add(new Image("gigaLogoIcon.png"));
        newStage.setTitle("Enter ID to Remove");

        VBox layout = new VBox(10);

        layout.setPadding(new Insets(20, 20, 20, 20));

        // Create a text field for inputting the ID
        TextField textFieldRemovePersonByID = new TextField();
        textFieldRemovePersonByID.setPromptText("Enter ID number here");

        // Create a submit button.
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String identificationNumber = textFieldRemovePersonByID.getText();
            Person person = personRegister.findPerson(identificationNumber);
            if (person != null) {
                // Person found, proceed with removal
                personRegister.removePerson(identificationNumber);
                observablePersonList.removeIf(p -> p.getIdentificationNumber().equals(identificationNumber));
                textAreaAllPersonsInfo.setText("Person successfully removed from system.");
                tableView.refresh(); // Refresh the TableView
            } else {

                textAreaAllPersonsInfo.setText("Person not found. Could not remove person.");
            }

            newStage.close();
        });

        // Add the text field and button to the layout.
        layout.getChildren().addAll(textFieldRemovePersonByID, submitButton); // Adding elements (Button and Textfield)
                                                                              // in the layout (VBox).

        Scene newScene = new Scene(layout, 300, 100);
        newStage.setScene(newScene);
        newStage.showAndWait(); // This makes the new window modal.
    }

    // Handle find account button by account ID
    @FXML
    private void handleButtonFindAccountAction(ActionEvent event) {

        Stage newStage = new Stage();
        newStage.getIcons().add(new Image("gigaLogoIcon.png"));
        newStage.setTitle("Find Account");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        // Create a text field for inputting the ID.
        TextField textFieldFindAccountByID = new TextField();
        textFieldFindAccountByID.setPromptText("Enter ID number here");

        // Create a label for instructions.
        Label label = new Label("Enter the account ID to find:");

        // Create a submit button.
        Button submitButton = new Button("Find");
        submitButton.setOnAction(e -> {
            String accountNumber = textFieldFindAccountByID.getText();
            BankAccount account = personRegister.findAccount(accountNumber);
            if (account != null) {
                textAreaAllPersonsInfo.setText(account.toString());
            } else {
                textAreaAllPersonsInfo.setText("No account found.");
            }
            newStage.close(); // Close the stage after searching
        });

        // Add the label, text field, and button to the layout.
        layout.getChildren().addAll(label, textFieldFindAccountByID, submitButton);

        // Set the scene and show the stage.
        Scene newScene = new Scene(layout, 300, 100);
        newStage.setScene(newScene);
        newStage.showAndWait(); // Use showAndWait to block interaction with other windows until this one is
                                // closed.

    }

    // Handle find person button by identification number
    @FXML
    private void handleButtonCustomerFindAction(ActionEvent event) {

        Stage newStage = new Stage();
        newStage.getIcons().add(new Image("gigaLogoIcon.png"));
        newStage.setTitle("Find Person");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        // Create a text field for inputting the ID.
        TextField textFieldFindPersonByID = new TextField();
        textFieldFindPersonByID.setPromptText("Enter ID number here");

        // Create a label for instructions.
        Label label = new Label("Enter the person's ID to find:");

        // Create a submit button.
        Button submitButton = new Button("Find");
        submitButton.setOnAction(e -> {
            String identificationNumber = textFieldFindPersonByID.getText();
            Person person = personRegister.findPerson(identificationNumber);
            if (person != null) {
                textAreaAllPersonsInfo.setText(person.toString());
            } else {
                textAreaAllPersonsInfo.setText("No person found.");
            }
            newStage.close();
        });

        // Add the label, text field, and button to the layout.
        layout.getChildren().addAll(label, textFieldFindPersonByID, submitButton);

        // Set the scene and show the stage.
        Scene newScene = new Scene(layout, 300, 100); // Adjust the size as needed
        newStage.setScene(newScene);
        newStage.showAndWait();

    }

    // Handle add account button

    @FXML
    private void handleButtonAccountAddAction(ActionEvent event) {
        String identificationNumber = textFieldAccountHolder.getText();
        String accountNumber = textFieldAccountId.getText();
        double initialBalance;

        try {
            initialBalance = Double.parseDouble(textFieldInitialBalance.getText());
        } catch (NumberFormatException e) {
            textAreaAllPersonsInfo.setText("Invalid balance format.");
            return;
        }

        Person person = personRegister.findPerson(identificationNumber);
        if (person != null && person.getAge() >= 18) {
            // Check if the person already has the maximum number of accounts
            if (person.getNumberOfAccounts() >= 3) {
                textAreaAllPersonsInfo.setText("Cannot have more than 3 accounts.");
            } else {
                BankAccount account = new BankAccount(accountNumber, initialBalance);
                person.addAccount(account);

                // Update the observable list to refresh the TableView
                observablePersonList.set(observablePersonList.indexOf(person), person);

                tableView.refresh();

                textAreaAllPersonsInfo.setText("Account successfully added.");
            }
        } else {
            textAreaAllPersonsInfo.setText("No person found or is not old enough!");
        }

        textFieldAccountHolder.clear();
        textFieldAccountId.clear();
        textFieldInitialBalance.clear();
    }

    // Handle withdrawal using textFieldAccountId
    @FXML
    private void handleButtonWithdrawAction(ActionEvent event) {
        String accountNumber = textFieldAccountToUse.getText();
        String amountText = textFieldAmount.getText();
        double amount;

        if (accountNumber.isEmpty()) {
            textAreaAllPersonsInfo.setText(" Account number cannot be empty.");
            return;
        }

        // Attempt to parse the amount, ensure it's a valid double
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            textAreaAllPersonsInfo.setText(" Invalid amount entered.");
            return;
        }

        // Check for positive amount
        if (amount <= 0) {
            textAreaAllPersonsInfo.setText(" The withdrawal amount must be positive.");
            return;
        }

        // If balance on account negative, throw error
        BankAccount account = personRegister.findAccount(accountNumber);
        if (account.getBalance() < 0) {
            textAreaAllPersonsInfo.setText("Account balance is negative.");
            return;
        }

        // Find the account using the account number
        BankAccount account2 = personRegister.findAccount(accountNumber);
        if (account2 == null) {
            textAreaAllPersonsInfo.setText("Account not found.");
            return;
        }

        // Check if the withdrawal amount is more than half the balance
        double halfBalance = account.getBalance() / 2;
        if (amount > halfBalance) {
            textAreaAllPersonsInfo.setText(" You cannot withdraw more than half of your balance.");
            return;
        }

        // Attempt withdrawal
        try {
            account.withdraw(amount);
            textFieldTotalBalance.setText(String.format("%.2f", account.getBalance()));
            textAreaAllPersonsInfo.setText("Withdrawal successful");
        } catch (IllegalArgumentException | IllegalStateException e) {
            textAreaAllPersonsInfo.setText("Withdrawal error, contact Giga Bank customer service ");
        }

        tableView.refresh();
    }

    // Deposit
    @FXML
    private void handleButtonDepositAction(ActionEvent event) {
        String accountNumber = textFieldAccountToUse.getText();
        String amountText = textFieldAmount.getText();
        double amount;

        if (accountNumber.isEmpty()) {
            textAreaAllPersonsInfo.setText("Account number cannot be empty.");
            return; // Exit the method early
        }

        // Attempt to parse the amount, ensure it's a valid double and positive
        try {
            amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                throw new NumberFormatException("Amount must be positive");
            }
        } catch (NumberFormatException e) {
            textAreaAllPersonsInfo.setText(" Invalid amount entered.");
            return; // Exit the method early if there's a parsing error
        }

        // Find the account using the account number
        BankAccount account = personRegister.findAccount(accountNumber);
        if (account == null) {
            textAreaAllPersonsInfo.setText("Account not found.");
            return; // Exit the method early if account is not found
        }

        // If all checks pass, proceed with the deposit
        try {
            account.deposit(amount);
            textFieldTotalBalance.setText(String.format("%.2f", account.getBalance()));
            textAreaAllPersonsInfo.setText("Deposit successful");
        } catch (IllegalArgumentException | IllegalStateException e) {
            textAreaAllPersonsInfo.setText("Deposit error, contact Giga Bank customer service ");
        }

        tableView.refresh();
    }

    // Show total balance of all accounts held by given person by customer ID
    @FXML
    private void handleButtonTotalBalanceAction(ActionEvent event) {
        String identificationNumber = textFieldPersonIdForAccountInfo.getText();
        double totalBalance = personRegister.calculateTotalBalance(identificationNumber);
        textFieldTotalBalance.setText(String.valueOf(totalBalance));

    }

    // Using the customerBankAccounts list, print each account ID and balance
    @FXML
    private void handleButtonDisplayAccountInfoAction(ActionEvent event) {

        Stage newStage = new Stage();
        newStage.getIcons().add(new Image("gigaLogoIcon.png"));

        TextField textFieldPersonIdForAccountInfo = new TextField();
        textFieldPersonIdForAccountInfo.setPromptText("Enter Person ID");

        // Create a Button to submit the input
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            // Use the text from the TextField and call the displayAccountInfo method
            displayAccountInfo(textFieldPersonIdForAccountInfo.getText());
            newStage.close(); // Close the pop-up window after submission
        });

        // Create a layout and add the TextField and Button to it
        VBox vBox = new VBox(10, textFieldPersonIdForAccountInfo, submitButton);
        vBox.setAlignment(Pos.CENTER);

        // Create a new Scene with the layout
        Scene newScene = new Scene(vBox, 300, 100);

        // Set the scene to the stage and show it
        newStage.setScene(newScene);
        newStage.setTitle("Enter Person ID");
        newStage.show();
    }

    // Print out all persons info
    @FXML
    private void handleButtonPrintAllPersonsInfoAction(ActionEvent event) {
        String allPersonsInfo = "";

        if (personRegister.getAllPersons().length == 0) {
            textAreaAllPersonsInfo.setText("No persons found.");
            return;
        }
        // Iterate through all persons in the register
        for (Person person : personRegister.getAllPersons()) {
            // Append the person's information
            allPersonsInfo += person.toString();

            // Calculate and append the total balance of the person
            double totalBalance = person.calculateTotalBalance();
            allPersonsInfo += "Total Balance: " + totalBalance + "\n";
            allPersonsInfo += "----------------------------\n";
        }

        // Set the text to the text area
        textAreaAllPersonsInfo.setText(allPersonsInfo);
    }

    // Handle show balance of all accounts
    @FXML
    private void handleButtonShowTotalBalanceAllAccountsAction(ActionEvent event) {
        double totalBalance = personRegister.calculateTotalBalance();
        if (totalBalance != 0) {
            textAreaAllPersonsInfo.setText(String.valueOf(totalBalance));
        } else {
            textAreaAllPersonsInfo.setText("No accounts found.");
        }

    }

    public void setPersonRegister(PersonRegister personRegister2) {
    }

    public void displayAccountInfo(String identificationNumber) {
        Person person = personRegister.findPerson(identificationNumber);
        if (person != null) {
            textAreaAllPersonsInfo.setText(person.getCustomerBankAccounts().toString());
        } else {
            textAreaAllPersonsInfo.setText("No person found.");
        }
    }

    // About us button
    @FXML
    private void handleButtonAboutUs(ActionEvent event) {

        Stage newStage = new Stage();
        newStage.getIcons().add(new Image("gigaLogoIcon.png"));
        newStage.setTitle("Information");

        // Create a label for displaying the information
        Label infoLabel = new Label();
        infoLabel.setFont(Font.font("System", FontPosture.ITALIC, 16)); // Adjust the font size as needed
        infoLabel.setText("Application made by:\nSimon Baas\nMirnes Dizdar\nSänna Janfada");
        infoLabel.setAlignment(Pos.CENTER); // Center the text in the label
        infoLabel.setTextAlignment(TextAlignment.CENTER); // Center the text alignment
        infoLabel.setStyle("-fx-background-color: transparent;"); // Remove any background styling

        // Create a layout and add the label to it
        VBox layout = new VBox(infoLabel);
        layout.setAlignment(Pos.CENTER); // Center the label in the VBox
        layout.setPadding(new Insets(10));

        // Set the scene and show the stage
        Scene scene = new Scene(layout, 300, 150); // Adjust the size as needed
        newStage.setScene(scene);
        newStage.show();
    }

    // Create a test method that populates the TableView with test data

    @FXML
    private void handleButtonTestAction(ActionEvent event) {
        // Person 1 with 1 bank account
        Person person1 = new Person("19980101-1000", "Alice Johnson", 25);
        BankAccount account1 = new BankAccount("1001", 5000);
        person1.addAccount(account1);
        personRegister.addPerson(person1);
        observablePersonList.add(person1);

        // Person 2 with 2 bank accounts
        Person person2 = new Person("19860202-2000", "Bob Smith", 37);
        BankAccount account2_1 = new BankAccount("1002", 3000);
        BankAccount account2_2 = new BankAccount("1003", 7500);
        person2.addAccount(account2_1);
        person2.addAccount(account2_2);
        personRegister.addPerson(person2);
        observablePersonList.add(person2);

        // Person 3 with 3 bank accounts
        Person person3 = new Person("19750303-3000", "Charlotte Emily", 48);
        BankAccount account3_1 = new BankAccount("1004", 15000);
        BankAccount account3_2 = new BankAccount("1005", 4600);
        BankAccount account3_3 = new BankAccount("1006", 300);
        person3.addAccount(account3_1);
        person3.addAccount(account3_2);
        person3.addAccount(account3_3);
        personRegister.addPerson(person3);
        observablePersonList.add(person3);

        // Person 4 with 1 bank account
        Person person4 = new Person("19900404-4000", "David Turner", 33);
        BankAccount account4 = new BankAccount("1007", 2000);
        person4.addAccount(account4);
        personRegister.addPerson(person4);
        observablePersonList.add(person4);

        // Person 5 with 2 bank accounts
        Person person5 = new Person("19950505-5000", "Eva Lawrence", 28);
        BankAccount account5_1 = new BankAccount("1008", 6700);
        BankAccount account5_2 = new BankAccount("1009", 1300);
        person5.addAccount(account5_1);
        person5.addAccount(account5_2);
        personRegister.addPerson(person5);
        observablePersonList.add(person5);

        // Person 6 with 1 bank account
        Person person6 = new Person("19960606-6000", "Franklin Moore", 27);
        BankAccount account6 = new BankAccount("1010", 4800);
        person6.addAccount(account6);
        personRegister.addPerson(person6);
        observablePersonList.add(person6);

        // Person 7 with 3 bank accounts
        Person person7 = new Person("19770707-7000", "Grace Hall", 46);
        BankAccount account7_1 = new BankAccount("1011", 9200);
        BankAccount account7_2 = new BankAccount("1012", 2300);
        BankAccount account7_3 = new BankAccount("1013", 5400);
        person7.addAccount(account7_1);
        person7.addAccount(account7_2);
        person7.addAccount(account7_3);
        personRegister.addPerson(person7);
        observablePersonList.add(person7);

        // Person 8 with 2 bank accounts
        Person person8 = new Person("19880808-8000", "Henry Adams", 35);
        BankAccount account8_1 = new BankAccount("1014", 12000);
        BankAccount account8_2 = new BankAccount("1015", 3750);
        person8.addAccount(account8_1);
        person8.addAccount(account8_2);
        personRegister.addPerson(person8);
        observablePersonList.add(person8);

        // Person 9 with 1 bank account
        Person person9 = new Person("19990909-9000", "Ivy Johnson", 24);
        BankAccount account9 = new BankAccount("1016", 1500);
        person9.addAccount(account9);
        personRegister.addPerson(person9);
        observablePersonList.add(person9);

        // Person 10 with 2 bank accounts
        Person person10 = new Person("19901010-1000", "Jackie Loomis", 33);
        BankAccount account10_1 = new BankAccount("1017", 8000);
        BankAccount account10_2 = new BankAccount("1018", 4500);
        person10.addAccount(account10_1);
        person10.addAccount(account10_2);
        personRegister.addPerson(person10);
        observablePersonList.add(person10);

        // Person 11 with 1 bank account
        Person person11 = new Person("19911111-1100", "Kevin Wright", 32);
        BankAccount account11 = new BankAccount("1019", 9000);
        person11.addAccount(account11);
        personRegister.addPerson(person11);
        observablePersonList.add(person11);

        // Person 12 with 3 bank accounts
        Person person12 = new Person("19821212-1200", "Laura Harper", 41);
        BankAccount account12_1 = new BankAccount("1020", 7500);
        BankAccount account12_2 = new BankAccount("1021", 6500);
        BankAccount account12_3 = new BankAccount("1022", 1000);
        person12.addAccount(account12_1);
        person12.addAccount(account12_2);
        person12.addAccount(account12_3);
        personRegister.addPerson(person12);
        observablePersonList.add(person12);

        // Person 13 with 2 bank accounts
        Person person13 = new Person("19831313-1300", "Megan Stone", 40);
        BankAccount account13_1 = new BankAccount("1023", 8600);
        BankAccount account13_2 = new BankAccount("1024", 3000);
        person13.addAccount(account13_1);
        person13.addAccount(account13_2);
        personRegister.addPerson(person13);
        observablePersonList.add(person13);

        // Person 14 with 1 bank account
        Person person14 = new Person("19941414-1400", "Nathan Yorke", 29);
        BankAccount account14 = new BankAccount("1025", 4900);
        person14.addAccount(account14);
        personRegister.addPerson(person14);
        observablePersonList.add(person14);

        // Person 15 with 2 bank accounts
        Person person15 = new Person("19851515-1500", "Olivia Wright", 38);
        BankAccount account15_1 = new BankAccount("1026", 12000);
        BankAccount account15_2 = new BankAccount("1027", 2000);
        person15.addAccount(account15_1);
        person15.addAccount(account15_2);
        personRegister.addPerson(person15);
        observablePersonList.add(person15);

        // Refresh the TableView
        tableView.refresh();
    }

}
