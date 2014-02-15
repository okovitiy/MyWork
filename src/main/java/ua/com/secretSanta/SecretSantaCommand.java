package main.java.ua.com.secretSanta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 11.02.14.
 */
public interface SecretSantaCommand {
    Boolean isExit();
    List<String> getInputDescriptions();
    void addInput(String s);
    void executeCommand() throws SQLException;
}

abstract class AbstractSecretSantaCommand implements SecretSantaCommand {
    protected List<String> inputList = new ArrayList<String>();

    @Override
    public Boolean isExit() {
        return false;
    }

    @Override
    public void addInput(String s) {
        inputList.add(s);
    }
}

class AddGroupCommand extends AbstractSecretSantaCommand {
    SecretSantaDao dao;

    AddGroupCommand(SecretSantaDao dao) {
        this.dao = dao;
    }

    @Override
    public List<String> getInputDescriptions() {
        List<String> inputDesc = new ArrayList<String>();
        inputDesc.add("Please enter group name");
        return inputDesc;
    }

    @Override
    public void executeCommand() throws SQLException {
        dao.addGroup(inputList.get(0));
    }
}

class AddUserCommand extends AbstractSecretSantaCommand {
    SecretSantaDao dao;

    AddUserCommand(SecretSantaDao dao) {
        this.dao = dao;
    }

    @Override
    public List<String> getInputDescriptions() {
        List<String> inputDesc = new ArrayList<String>();
        inputDesc.add("Please enter user name");
        inputDesc.add("Please enter group name");
        return inputDesc;
    }

    @Override
    public void executeCommand() throws SQLException {
        dao.addUser(inputList.get(0), inputList.get(1));
    }
}

class AddPresentCommand extends AbstractSecretSantaCommand {
    SecretSantaDao dao;

    AddPresentCommand(SecretSantaDao dao) {
        this.dao = dao;
    }

    @Override
    public List<String> getInputDescriptions() {
        List<String> inputDesc = new ArrayList<String>();
        inputDesc.add("Please enter your name");
        inputDesc.add("Please enter a desired present to you");
        return inputDesc;
    }

    @Override
    public void executeCommand() throws SQLException {
        dao.addPresent(inputList.get(0), inputList.get(1));
    }
}

class ShowGroupCommand extends AbstractSecretSantaCommand {
    SecretSantaDao dao;

    ShowGroupCommand(SecretSantaDao dao) {
        this.dao = dao;
    }

    @Override
    public List<String> getInputDescriptions() {
        List<String> inputDesc = new ArrayList<String>();
        inputDesc.add("Please enter the name of group to view all members");
        return inputDesc;
    }

    @Override
    public void executeCommand() throws SQLException {
        dao.showGroup(inputList.get(0));
    }
}

class GenerationPairsCommand extends AbstractSecretSantaCommand {
    SecretSantaDao dao;

    GenerationPairsCommand(SecretSantaDao dao) {
        this.dao = dao;
    }

    @Override
    public List<String> getInputDescriptions() {
        List<String> inputDesc = new ArrayList<String>();
        inputDesc.add("Please enter the name of group for the generation pars");
        return inputDesc;
    }

    @Override
    public void executeCommand() throws SQLException {
        dao.generationPairs(inputList.get(0));
    }
}

class ExitCommand extends AbstractSecretSantaCommand {
    @Override
    public Boolean isExit() {
        return true;
    }

    @Override
    public List<String> getInputDescriptions() {
        return null;
    }

    @Override
    public void executeCommand() {
    }
}
