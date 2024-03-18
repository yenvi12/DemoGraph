
package model;

import java.util.Scanner;

public class User {
    
    private String account;
    private String password;
    private double balance;

    public User() {
    }
    

    public User(String account, String password, double balance) {
        this.account = account;
        this.password = password;
        this.balance = balance;
    }
    

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        
    }

    public double getBalance() {
        return balance;
    }
    

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    
    
}
