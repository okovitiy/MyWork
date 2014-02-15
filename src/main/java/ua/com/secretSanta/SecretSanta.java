package main.java.ua.com.secretSanta;

/**
 * Created by Andrew on 11.02.14.
 */
public class SecretSanta {
    SecretSantaDao dao;

    public SecretSanta() throws Exception {
        this.dao = new SecretSantaDao(ConnectionDB.getConnection("root", "qwerty"));
    }

    public SecretSantaCommand createCommand(String input) {
        final int menuItem;
        try {
            menuItem = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a number of menu item");
        }

        switch (menuItem) {
            case 0:
                return new ExitCommand();
            case 1:
                return new AddGroupCommand(dao);
            case 2:
                return new AddUserCommand(dao);
            case 3:
                return new AddPresentCommand(dao);
            case 4:
                return new ShowGroupCommand(dao);
            case 5:
                return new GenerationPairsCommand(dao);
            default:
                throw new IllegalArgumentException("Illegal menu item was entered: " + menuItem);
        }
    }

    public String getMenu() {
        return  "\n" + "Secret Santa." +
                "\n" + "0. Exit." +
                "\n" + "1. Add a new group." +
                "\n" + "2. Add a new user in the group." +
                "\n" + "3. Add a desired present to the user." +
                "\n" + "4. Show the group." +
                "\n" + "5. Generation the pairs." +
                "\n" + "Choose 0-5 to continue.";
    }
}
