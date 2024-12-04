package test.java;

import main.java.app.DBController;
import main.java.object.MenuItem;
import main.java.service.CommandService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

public class SearchItemByTagTest {
    private DBController dbController;
    private CommandService command;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        command = new CommandService();
        // Add test data
        List<String> tags1 = new ArrayList<>();
        tags1.add("Tag1");
        tags1.add("Tag2");
        MenuItem item1 = new MenuItem("TestItem1", "Description1", 10.0f, tags1);

        List<String> tags2 = new ArrayList<>();
        tags2.add("Tag2");
        tags2.add("Tag3");
        MenuItem item2 = new MenuItem("TestItem2", "Description2", 15.0f, tags2);

        dbController.addNewMenuItem(item1);
        dbController.addNewMenuItem(item2);
    }

    @Test
    public void testSearchItemByTagSuccess() {
        String input = "\nTag1,Tag2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        command.searchItemByTags(scanner);

        String output = outputStream.toString();
        System.out.println(output);
        assertTrue(output.contains("TestItem1"));
    }

    @Test
    public void testSearchItemByTagInvalid() {
        String input = "\nNonExistentTag\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        command.searchItemByTags(scanner);

        String output = outputStream.toString();
        System.out.println(output);

        assertTrue(output.contains("No items found with tags"));
    }

    @Test
    public void testSearchItemByTagMultiple() {
        String input = "\nTag2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        command.searchItemByTags(scanner);

        String output = outputStream.toString();
        System.out.println(output);

        assertTrue(output.contains("TestItem1"));
        assertTrue(output.contains("TestItem2"));
    }

    @After
    public void tearDown() {
        dbController.removeNewMenuItem("TestItem1");
        dbController.removeNewMenuItem("TestItem2");
    }
}