package view;

import controller.Atm;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Atm m = new Atm();
        int choice = 0;
        System.out.println("Dang nhap thong tin");
        m.login();
        do {
            System.out.println("ATM Menu");
            System.out.println("1. Rut tien.");
            System.out.println("2. Kiem tra so du.");
            System.out.println("3. Chuyen khoang.");
            System.out.println("4. Nap tien.");
            System.out.print("Your choice: ");
            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    m.rutTien();
                    break;
                case 2:
                    m.checkBalance();
                    break;
                case 3:
                    m.transferMoney();
                    break;
                case 4:
                    m.napTien();
                    break;
                default:
                    System.out.println("See You Again!");
            }
        }while(choice >0 && choice <5);
        
    }
}
