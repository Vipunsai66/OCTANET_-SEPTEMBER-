import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Transaction class to store details of each transaction
class Transaction {
    private String type;
    private double amount;

    // Constructor to initialize a transaction
    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    // Method to get the transaction details
    @Override
    public String toString() {
        return "Transaction: " + type + ", Amount: $" + amount;
    }
}

// Account class representing a user's bank account
class Account {
    private String accountNumber;
    private String pin;
    private double balance;
    private List<Transaction> transactionHistory;

    // Constructor to initialize account with account number, PIN, and initial balance
    public Account(String accountNumber, String pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    // Method to validate the PIN
    public boolean validatePin(String enteredPin) {
        return this.pin.equals(enteredPin);
    }

    // Method to get the current balance
    public double getBalance() {
        return balance;
    }

    // Method to deposit money into the account
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount));
            System.out.println("Successfully deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Method to withdraw money from the account
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
            System.out.println("Successfully withdrew: $" + amount);
            return true;
        } else {
            System.out.println("Insufficient funds or invalid amount.");
            return false;
        }
    }

    // Method to change the account PIN
    public boolean changePin(String oldPin, String newPin) {
        if (validatePin(oldPin)) {
            this.pin = newPin;
            System.out.println("PIN successfully changed.");
            return true;
        } else {
            System.out.println("Invalid current PIN.");
            return false;
        }
    }

    // Method to display the transaction history
    public void printTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    // Method to get the account number
    public String getAccountNumber() {
        return accountNumber;
    }
}

// ATM class to manage ATM operations
class ATM {
    private Account account;

    // Constructor to associate an account with the ATM
    public ATM(Account account) {
        this.account = account;
    }

    // Method to display the ATM menu and handle user input
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nATM Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Change PIN");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Current Balance: $" + account.getBalance());
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;
                case 4:
                    scanner.nextLine();  // Consume newline left-over
                    System.out.print("Enter current PIN: ");
                    String currentPin = scanner.nextLine();
                    System.out.print("Enter new PIN: ");
                    String newPin = scanner.nextLine();
                    account.changePin(currentPin, newPin);
                    break;
                case 5:
                    account.printTransactionHistory();
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);

        scanner.close();
    }
}

// Main class to run the ATM simulation
public class ATMApp {
    public static void main(String[] args) {
        // Create an account with account number, PIN, and initial balance
        Account userAccount = new Account("12345678", "1234", 1000.00);

        // Prompt user for PIN verification
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your PIN: ");
        String enteredPin = scanner.nextLine();

        // Validate PIN before proceeding with ATM operations
        if (userAccount.validatePin(enteredPin)) {
            ATM atm = new ATM(userAccount);
            atm.showMenu();
        } else {
            System.out.println("Invalid PIN. Access denied.");
        }

        scanner.close();
    }
}
