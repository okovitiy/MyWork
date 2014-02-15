package main.java.ua.com.secretSanta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Andrew on 11.02.14.
 */
public class App {

    public static void main(String[] args) throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        SecretSanta secretSanta = new SecretSanta();
        try {
            while (true) {
                System.out.println(secretSanta.getMenu());
                SecretSantaCommand command = secretSanta.createCommand(input.readLine());
                if (command.isExit()) {
                    break;
                }
                collectInput(input, command);
                command.executeCommand();
            }
        } catch (Exception e) {
            System.out.println("Problem occurred: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection();
        }
    }

    private static void collectInput(BufferedReader input, SecretSantaCommand command) throws IOException {
        for (String desc : command.getInputDescriptions()) {
            System.out.println(desc);
            command.addInput(input.readLine());
        }
    }
}