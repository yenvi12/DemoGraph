package controller;

import java.util.*;
import model.User;

public class Atm {
     List<User> u = new ArrayList();
     
    private double initialBalance = 1000000;
    private double recipientMoney = 0;
    private User login;
    
   Scanner sc = new Scanner(System.in);
   public void login(){
       System.out.println("Enter name account:  ");
       String nameAccount = sc.nextLine();
       System.out.println("Enter password: ");
       String password = sc.nextLine();
       for(User urr : u){
       if(urr.getAccount().equals(nameAccount) && nameAccount.equals(login.getAccount()) && password.equals(login.getPassword())){
           System.out.println("Login successful!");
       }else{
           System.out.println("Invalid account number or password. Please try again.");
       }
       } 
}
   
   public void rutTien(){
       System.out.println("Enter withdraw money amount: ");
       double amount = sc.nextDouble();
       if(amount<50000 || amount >5000000){
           System.out.println("withdraw money amount between 50000 and 5000000");
       }else if(amount>initialBalance){
           System.out.println("Balance not enough!");
       }else{
           initialBalance = initialBalance-amount;
           System.out.println("Withdrawal successful. Remaining balance: " + initialBalance);
       }      
       }
   
   public void checkBalance(){
       System.out.println("Your balance: " + initialBalance);
   }
   
   
   public void transferMoney(){
       int accountNumber = 0;
       display();
       System.out.println("Enter Account number you want to transfer:");
       accountNumber = sc.nextInt();
       switch(accountNumber){
           case 1: 
               System.out.println("123456");
               break;
           case 2: 
               System.out.println("234567");
               break;
           case 3:
               System.out.println("345678");
               break;
           case 4: 
               System.out.println("456789");
               break;
           case 5: 
               System.out.println("567890");
               break;
       }
       
       System.out.println("Enter the amount you want to transfer: ");
       double transferMoney = sc.nextDouble();
       if(transferMoney > initialBalance){
           System.out.println("Balance not enough");
       }else {
           initialBalance -= transferMoney;
           recipientMoney += transferMoney;
           System.out.println("Transfer Money successfull.");
           System.out.println("Remaining balance: " +initialBalance);
       }
   }
   
   public void napTien(){
       System.out.println("Enter the amount you want to deposit: ");
       double deposit = sc.nextDouble();
       initialBalance += deposit;
       System.out.println("Deposit Money Successfull!");
       System.out.println("Remaining balance: "+initialBalance);
   }
   
   public void display(){
       System.out.println("List account number ");
       System.out.println("Account 1 : 123456");
       System.out.println("Account 2: 234567");
       System.out.println("Account 3: 345678");
       System.out.println("Account 4: 456789");
       System.out.println("Account 5: 567890");
   }
   
        public void accountsSystem() {
        u.add(new User("huynhyen", "password1", 60000));
        u.add(new User("1234", "password2", 70000));
        u.add(new User("viyen", "password3", 800000));
        u.add(new User("2345", "password4", 900000));
        u.add(new User("3456", "password5", 1000000));
    }

    public void addAccount(){
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
        User acc = new User(newAccount, newPassword, balance);
        u.add(acc);
    }
}
