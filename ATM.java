import java.io.*;
import java.util.*;

public class ATM {

    static double balance = 5000;
    static int pin = 1234;
    static Scanner sc = new Scanner(System.in);
    static String fileName = "atm.txt";

    public static void main(String[] args) {

        loadData();

        System.out.println("===== ATM SIMULATION =====");

        System.out.print("Enter PIN: ");
        int enteredPin = sc.nextInt();

        if (enteredPin != pin) {
            System.out.println("Wrong PIN!");
            return;
        }

        System.out.println("Login Successful!");

        while (true) {
            System.out.println("\n===== ATM MENU =====");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transaction History");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("Balance: ₹" + balance);
                    break;

                case 2:
                    System.out.print("Enter amount to withdraw: ₹");
                    double withdraw = sc.nextDouble();

                    if (withdraw <= 0) {
                        System.out.println("Invalid amount!");
                    } else if (withdraw > balance) {
                        System.out.println("Insufficient balance!");
                    } else {
                        balance = balance - withdraw;
                        saveData("Withdraw: ₹" + withdraw);
                        System.out.println("Withdrawal successful!");
                        System.out.println("Remaining Balance: ₹" + balance);
                    }
                    break;

                case 3:
                    System.out.print("Enter amount to deposit: ₹");
                    double deposit = sc.nextDouble();

                    if (deposit <= 0) {
                        System.out.println("Invalid amount!");
                    } else {
                        balance = balance + deposit;
                        saveData("Deposit: ₹" + deposit);
                        System.out.println("Deposit successful!");
                        System.out.println("New Balance: ₹" + balance);
                    }
                    break;

                case 4:
                    showHistory();
                    break;

                case 5:
                    System.out.println("Thank you for using ATM!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Save balance and transaction
    static void saveData(String transaction) {
        try {
            FileWriter fw = new FileWriter(fileName, true);
            fw.write(transaction + "\n");
            fw.close();

            FileWriter balanceFile = new FileWriter("balance.txt");
            balanceFile.write(String.valueOf(balance));
            balanceFile.close();

        } catch (IOException e) {
            System.out.println("Error saving data!");
        }
    }

    // Load previous balance
    static void loadData() {
        try {
            File file = new File("balance.txt");

            if (file.exists()) {
                Scanner fileScanner = new Scanner(file);
                if (fileScanner.hasNextDouble()) {
                    balance = fileScanner.nextDouble();
                }
                fileScanner.close();
            }

        } catch (Exception e) {
            System.out.println("Error loading data!");
        }
    }

    // Display transaction history
    static void showHistory() {
        try {
            File file = new File(fileName);

            if (!file.exists()) {
                System.out.println("No transactions yet.");
                return;
            }

            Scanner fileScanner = new Scanner(file);

            System.out.println("\n===== TRANSACTION HISTORY =====");

            while (fileScanner.hasNextLine()) {
                System.out.println(fileScanner.nextLine());
            }

            fileScanner.close();

        } catch (Exception e) {
            System.out.println("Error reading history!");
        }
    }
}
