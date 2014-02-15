package main.java.ua.com.secretSanta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Andrew on 11.02.14.
 */
public class SecretSantaDao {
    private final Connection connection;

    public SecretSantaDao(Connection connection) {
        this.connection = connection;
    }

    public void addGroup(String nameGroup) throws SQLException {
        if (isGroupInDB(nameGroup)) {
            System.out.println("The group with this name was already exist.");
        } else {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO groups(name) values (?)");
            statement.setString(1, nameGroup);
            statement.executeUpdate();
            System.out.println("The group " + nameGroup + " added.");
        }
    }

    public void addUser(String nameUser, String nameGroup) throws SQLException {
        if (isGroupInDB(nameGroup)) {
            if (isUserInGroup(nameGroup, nameUser)) {
                System.out.println("The user with this name was already exist in this group.");
            } else {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO users(name, id_group) values (?, ?);");
                statement.setString(1, nameUser);
                statement.setInt(2, getIdGroup(nameGroup));
                statement.executeUpdate();
                System.out.println("The user " + nameUser + " added.");
            }
        } else {
            System.out.println("The Group with this name doesn't exist");
        }
    }

    public void addPresent(String nameUser, String titlePresent) throws SQLException {
        if (hasTheUserPresent(nameUser, titlePresent)) {
            System.out.println("The present was already exist.");
        } else {
            PreparedStatement statement1 = connection.prepareStatement(
                    "INSERT INTO presents(title) values (?);");
            statement1.setString(1, titlePresent);
            statement1.executeUpdate();
            PreparedStatement statement2 = connection.prepareStatement(
                    "INSERT INTO user_present(id_user, id_present) values (?, ?);");
            statement2.setInt(1, getIdUser(nameUser));
            statement2.setInt(2, getIdPresent(titlePresent));
            statement2.executeUpdate();
            System.out.println("The present " + titlePresent + " added.");
        }
    }

    public void showGroup(String nameGroup) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                        "SELECT users.name FROM users " +
                        "JOIN groups ON users.id_group = groups.id_group " +
                        "WHERE groups.name = ?;");
        statement.setString(1, nameGroup);
        ResultSet result = statement.executeQuery();
        if (result.wasNull()) {
            System.out.println("This group does not exist");
        } else {
            System.out.println("\n" + "Members of the group " + nameGroup + ":");
            while (result.next()) {
                System.out.println(result.getString("name"));
            }
        }
    }

    public void generationPairs(String nameGroup) throws SQLException {
        ArrayList<String> giver = getUserCollection(nameGroup);
        ArrayList<String> generatedPairs = new ArrayList<String>();
        Collections.shuffle(giver);
        while (giver.size() != generatedPairs.size()) {
            ArrayList<String> receiver = (ArrayList<String>) giver.clone();
            generatedPairs.clear();
            for (int i = 0; i < giver.size(); i++) {
                Collections.shuffle(receiver);
                int target = 0;
                if (!receiver.get(target).equals(giver.get(i))) {
                    generatedPairs.add(giver.get(i) + " gives " + getUserPresent(receiver.get(target)) +
                            " to " + receiver.get(target));
                    receiver.remove(receiver.get(target));
                }
            }
        }
        if (generatedPairs.size() >= 2){
            for (int i = 0; i < generatedPairs.size(); i++) {
                System.out.println(generatedPairs.get(i));
            }
        } else {
            System.out.println("The group " + nameGroup + " has less than two members.");
        }

    }

    private ArrayList<String> getUserCollection(String nameGroup) throws SQLException {
        ArrayList<String> users = new ArrayList<String>();
        PreparedStatement statement = connection.prepareStatement(
                        "SELECT users.name FROM users " +
                        "JOIN groups ON users.id_group = groups.id_group " +
                        "WHERE groups.name = ?;");
        statement.setString(1, nameGroup);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            users.add(result.getString("name"));
        }
        return users;
    }

    private String getUserPresent(String nameUser) throws SQLException {
        String presents = "";
        PreparedStatement statement = connection.prepareStatement(
                        "SELECT title FROM presents " +
                        "JOIN user_present ON presents.id_present = user_present.id_present " +
                        "JOIN users ON users.id_user = user_present.id_user " +
                        "WHERE users.name = ?;");
        statement.setString(1, nameUser);
        ResultSet result = statement.executeQuery();
        if (!result.next()) {
            return "a present";
        } else {
            result.previous();
            while (result.next()) {
                presents += result.getString("title");
                if (result.next()) {
                    presents += " or ";
                    result.previous();
                }
            }
            return presents;
        }
    }

    private boolean isGroupInDB(String nameGroup) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                        "SELECT name FROM groups WHERE name = ?");
        statement.setString(1, nameGroup);
        ResultSet result = statement.executeQuery();
        return result.next();
    }

    private int getIdGroup(String nameGroup) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                        "SELECT id_group FROM groups " +
                        "WHERE groups.name = ?;");
        statement.setString(1, nameGroup);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            return result.getInt("id_group");
        } else {
            return 0;
        }
    }

    private boolean isUserInGroup(String nameGroup, String nameUser) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                        "SELECT users.name FROM users " +
                        "JOIN groups ON groups.id_group = users.id_group " +
                        "WHERE groups.name = ? and users.name = ?;");
        statement.setString(1, nameGroup);
        statement.setString(2, nameUser);
        ResultSet result = statement.executeQuery();
        return result.next();
    }

    private int getIdUser(String nameUser) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                        "SELECT id_user FROM users " +
                        "WHERE users.name = ?;");
        statement.setString(1, nameUser);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            return result.getInt("id_user");
        } else {
            return 0;
        }
    }

    private boolean hasTheUserPresent(String nameUser, String titlePresent) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                        "SELECT title FROM presents " +
                        "JOIN user_present ON presents.id_present = user_present.id_present " +
                        "JOIN users ON users.id_user = user_present.id_user " +
                        "WHERE users.name = ? and presents.title = ?;");
        statement.setString(1, nameUser);
        statement.setString(2, titlePresent);
        ResultSet result = statement.executeQuery();
        return result.next();
    }

    private int getIdPresent(String titlePresent) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                        "SELECT id_present FROM presents " +
                        "WHERE presents.title = ?;");
        statement.setString(1, titlePresent);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            return result.getInt("id_present");
        } else {
            return 0;
        }
    }
}
