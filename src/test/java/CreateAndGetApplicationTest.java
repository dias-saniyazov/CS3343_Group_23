package test.java;

import main.java.app.Application;
import main.java.app.ApplicationFactory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class CreateAndGetApplicationTest {
    private ApplicationFactory applicationFactory;

    @Before
    public void setUp() {
        applicationFactory = ApplicationFactory.getInstance();
    }

    @Test
    public void testApplication1() {
        Application application = applicationFactory.createAndGetApplication("Client");
        assertTrue(application != null);
    }

    @Test
    public void testApplication2() {
        Application application = applicationFactory.createAndGetApplication("Admin");
        assertTrue(application != null);
    }

    @Test
    public void testApplication3() {
        Application application = applicationFactory.createAndGetApplication("Invalid");
        assertTrue(application == null);
    }
}