package main.java.ua.com.secretSanta;

import java.io.IOException;
import java.sql.SQLException;

/**
 * User: Andrew
 * Date: 11.01.14
 */
public interface SecretSantaCommand {

    String getMenu();

    void createCommand(String input) throws SQLException, IOException;

    void addGroup(String nameGroup) throws SQLException;

    void addUser(String nameGroup, String nameUser) throws SQLException;

    void addPresent(String nameUser, String titlePresent) throws SQLException;

    void showGroup(String nameGroup) throws SQLException;

    void generationPairs(String nameGroup) throws SQLException;

    Boolean isExit();

}
