package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import model.User;

public class Test {

    public User currentUser;
    Scanner sc = new Scanner(System.in);

    public void addAccount() {
        String newAccount = null;
        String newPassword = null;
        double balance = 0;
        do {
            System.out.println("Enter your new accountNumber (10 digit): ");
            newAccount = sc.nextLine();
        } while (newAccount.length() != 10);
        do {
            System.out.println("Enter your new password (>= 6 digit): ");
            newPassword = sc.nextLine();
        } while (newPassword.length() < 6);

        try ( Connection conn = Connect.getConnection();  PreparedStatement stmt = conn.prepareStatement("INSERT INTO user (account_number,account_name, account_password, account_balance) VALUES (?,?,?,?)")) {
            stmt.setString(1, newAccount);
            stmt.setString(2, newPassword);
            stmt.setDouble(3, balance);
            stmt.executeUpdate();
            System.out.println("Account added successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to add account to the database.");
        }
    }

    public void checkBalance() {
        if (currentUser == null) {
            System.out.println("Please login!");
            return;
        }
        System.out.println("Current balance: " + currentUser.getBalance());
    }

    public void withdraw() {
        if (currentUser == null) {
            System.out.println("Please login! ");
            return;
        }
        System.out.println("Enter the amount to withdraw");
        double amount = getValidAmount();
        if (amount < 50000 || amount > currentUser.getBalance()) {
            System.out.println("Invalid balance");
            return;
        }
        currentUser.setBalance(currentUser.getBalance() - amount);
        System.out.println("Withdraw successful! Balance after withdraw is: " + currentUser.getBalance());
    }

    public void transfer() {
        if (currentUser == null) {
            System.out.println("Please login!");
            return;
        }
        System.out.println("Enter account number to transfer: ");
        String accountNumber = sc.nextLine();
        User target = findAccount(accountNumber);
        if (target == null) {
            System.out.println("Invalid account number! ");
            return;
        }
        System.out.println("Enter the amount to transfer:  ");
        double amount = getValidAmount();
        if (amount < 0 || amount > currentUser.getBalance()) {
            System.out.println("Amount is invalid!");
            return;
        }
        currentUser.setBalance(currentUser.getBalance() - amount);
        target.setBalance(target.getBalance() + amount);
        System.out.println("Balance after transfer is: " + currentUser.getBalance());
    }

    public void deposit() {
        if (currentUser == null) {
            System.out.println("Please login!");
            return;
        }
        System.out.print("Enter the amount to deposit: ");
        double amount = getValidAmount();
        if (amount < 0) {
            System.out.println("Invalid amount.");
            return;
        }
        currentUser.setBalance(currentUser.getBalance() + amount);
        System.out.println("Deposit successful. New balance: " + currentUser.getBalance());

        try ( Connection conn = Connect.getConnection();  PreparedStatement stmt = conn.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_number = ?")) {
            stmt.setDouble(1, amount);
            stmt.setString(2, currentUser.getPassword());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                currentUser.setBalance(currentUser.getBalance() + amount);
                System.out.println("Deposit successful. New balance: " + currentUser.getBalance());
            } else {
                System.out.println("Failed to update balance in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to deposit. Please try again later.");
        }
    }

    public void login() {
        System.out.print("Enter account name: ");
        String accountName = sc.nextLine();
        System.out.print("Enter password : ");
        String password = sc.nextLine();
        try ( Connection conn = Connect.getConnection();  PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE account_name = ? AND account_password = ?")) {
            stmt.setString(1, accountName);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                currentUser = new User(rs.getString("account_name"), rs.getString("account_password"), rs.getDouble("account_balance"));
                System.out.println("Login successful. Welcome, " + accountName + "!");
            } else {
                System.out.println("Invalid account number or password.");
            }
        } catch (SQLException e) {
            System.out.println("Login failed due to an error.");
        }
    }

    public void displayAccounts() {
        try ( Connection conn = Connect.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement("SELECT account_number FROM users"); 
                ResultSet rs = stmt.executeQuery()) {
            System.out.println("\nAccount List:");
            while (rs.next()) {
                System.out.println("Account Number: " + rs.getString("account_number"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve account list.");
        }
    }

    private double getValidAmount() {
        double amount;
        while (true) {
            try {
                amount = Double.parseDouble(sc.nextLine());
                if (amount < 0) {
                    System.out.print("Please enter a non-negative amount: ");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid amount: ");
            }
        }
        return amount;
    }

    private User findAccount(String accountNumber) {
        try ( Connection conn = Connect.getConnection();  PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE account_number = ?")) {
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User (rs.getString("account_number"), rs.getString("account_password"), rs.getDouble("account_balance"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve account information.");
        }
        return null;
    }

    public void showAll() {
        try ( Connection conn = Connect.getConnection();  PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");  ResultSet rs = stmt.executeQuery()) {
            System.out.println("\nAll Accounts:");
            while (rs.next()) {
                String accountNumber = rs.getString("account_number");
                String password = rs.getString("account_password");
                double balance = rs.getDouble("account_balance");
                System.out.println("Account Number: " + accountNumber + ", Account Password: " + password + ", Account Balance: " + balance);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve account information.");
        }
    }
}
