
package test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Menu {

    public static void main(String[] args) throws SQLException {
        Menu m = new Menu();
        m.showMenu();
    }

    public void showMenu() {
        try {
            Test ma = new Test();
            Connection conn = Connect.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM menu WHERE menu_index = 1");
            checkPosition(rs);
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.print("Enter your choice: ");
                int choose = sc.nextInt();
                sc.nextLine();
                switch (choose) {
                    case 1:
                        ma.login();
                        ResultSet rs2 = stmt.executeQuery("SELECT * FROM menu WHERE menu_index = 2");
                        checkPosition(rs2);
                        break;
                    case 2:
                        ma.addAccount();
                        break;
                    case 0:
                        System.out.println("Exiting ATM Application. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option. Please choose again.");
                        break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve menu items from the database.");
        }
    }

    public void checkPosition(ResultSet rs2) throws SQLException {
//        Scanner sc = new Scanner(System.in);
        while (rs2.next()) {
            int id = rs2.getInt(1);
            System.out.print(id);
            String name = rs2.getString("name");
            System.out.print(name);
        }
    }
}
