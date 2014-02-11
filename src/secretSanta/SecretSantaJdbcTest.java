package secretSanta;

import main.java.ua.com.secretSanta.ConnectionDB;
import main.java.ua.com.secretSanta.SecretSantaJdbc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * User: Andrew
 * Date: 03.02.14
 */
public class SecretSantaJdbcTest {
    private SecretSantaJdbc jdbc;

    @Before
    public void setUp() throws Exception {
        jdbc = new SecretSantaJdbc(ConnectionDB.getConnection("root", "andrew85"));
    }

    @After
    public void tearDown() throws Exception {
        ConnectionDB.closeConnection();
    }

    @Test
    public void testCreateCommand() throws Exception {

    }

    @Test
    public void testAddGroup() throws Exception {
    }

    @Test
    public void testAddUser() throws Exception {

    }

    @Test
    public void testAddPresent() throws Exception {

    }

    @Test
    public void testShowGroup() throws Exception {

    }

    @Test
    public void testGenerationPairs() throws Exception {

    }

    @Test
    public void testIsExit() throws Exception {

    }
}
