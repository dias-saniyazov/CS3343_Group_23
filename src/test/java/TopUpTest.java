package test.java;

import main.java.user.Member;
import main.java.app.DBController;
import main.java.app.MemberApplication;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue; 
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class TopUpTest {
    private DBController dbController;
    private Member member;
    private MemberApplication memberApp;
    private Scanner scanner;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        dbController.createMember("testUser", "password");
        member = dbController.validateMemberCredentials("testUser", "password");
        memberApp = new MemberApplication(member);
    } 

    @Test
    public void testTopUpSuccess() {
        String input = "50.0";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        input = "1";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);


        float initialBalance = member.getBalance();
        float topUpAmount = 50.0f;
        memberApp.topUp(scanner, dbController);
        assertEquals(initialBalance + topUpAmount, member.getBalance(), 0.0);
    }

    @Test
    public void testTopUpNegativeAmount() {
        String input = "-50.0";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        input = "1";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        float initialBalance = member.getBalance();
        memberApp.topUp(scanner, dbController);
        assertEquals(initialBalance, member.getBalance(), 0.0);
    }

    @Test
    public void testTopUpInvalidInput() {
        String input = "invalid";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        input = "1";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        float initialBalance = member.getBalance();
        memberApp.topUp(scanner, dbController);
        assertEquals(initialBalance, member.getBalance(), 0.0);
    }
}