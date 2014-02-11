package main.java.ua.com.secretSanta;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * User: Andrew
 * Date: 11.01.14
 */
public class App {
    public static void main(String[] args) throws Exception {
        try {
            SecretSantaCommand s = new SecretSantaJdbc(ConnectionDB.getConnection("root", "andrew85"));
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            while (s.isExit()) {
                System.out.println(s.getMenu());
                s.createCommand(input.readLine());
            }
        } catch (Exception e) {
            System.out.println("Problem occurred: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection();
        }
    }
}
