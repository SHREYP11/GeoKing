/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.mycompany.project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for user.java
 */
public class userTest {

    public userTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        System.out.println("setUpClass()");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("tearDownClass()");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("setUp()");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("tearDown()");
    }

    /**
     * Test of getName method, of class user.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        user instance = new user("Alice", 1, 2, false);
        String expResult = "Alice";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getClassicLevel method, of class user.
     */
    @Test
    public void testGetClassicLevel() {
        System.out.println("getClassicLevel");
        user instance = new user("Bob", 3, 4, true);
        int expResult = 3;
        int result = instance.getClassicLevel();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFrenzyLevel method, of class user.
     */
    @Test
    public void testGetFrenzyLevel() {
        System.out.println("getFrenzyLevel");
        user instance = new user("Charlie", 5, 6, true);
        int expResult = 6;
        int result = instance.getFrenzyLevel();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAdmin method, of class user.
     */
    @Test
    public void testGetAdmin() {
        System.out.println("getAdmin");
        user instance = new user("Dana", 7, 8, false);
        boolean expResult = false;
        boolean result = instance.getAdmin();
        assertEquals(expResult, result);
    }

    /**
     * Test of incrementClassicLevel method, of class user.
     */
    @Test
    public void testIncrementClassicLevel() {
        System.out.println("incrementClassicLevel");
        user instance = new user("Eve", 9, 10, true);
        instance.incrementClassicLevel();
        assertEquals(10, instance.getClassicLevel());
    }

    /**
     * Test of incrementFrenzyLevel method, of class user.
     */
    @Test
    public void testIncrementFrenzyLevel() {
        System.out.println("incrementFrenzyLevel");
        user instance = new user("Frank", 11, 12, false);
        instance.incrementFrenzyLevel();
        assertEquals(13, instance.getFrenzyLevel());
    }
}
