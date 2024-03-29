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

import java.util.NoSuchElementException;

/**
 * JUnit test class for userDatabaseTest.
 */
public class UserDatabaseTest {

    userDatabaseTest db;

    @BeforeAll
    public static void setUpClass() {
        System.out.println("Setting up class...");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("Tearing down class...");
    }

    @BeforeEach
    public void setUp() {
        db = new userDatabaseTest();
        System.out.println("Setting up...");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Tearing down...");
    }

    /**
     * Test of createUser method, of class userDatabaseTest.
     */
    @Test
    public void testCreateUser() {
        System.out.println("Testing createUser");
        db.createUser("TestUser");
        assertNotNull(db.findUser("TestUser"), "The user should exist after being created.");
    }

    /**
     * Test of findUser method, of class userDatabaseTest.
     */
    @Test
    public void testFindUser() {
        System.out.println("Testing findUser");
        String expectedUserName = "ExistingUser";
        db.createUser(expectedUserName);
        user result = db.findUser(expectedUserName);
        assertEquals(expectedUserName, result.getName(), "The found user name should match the expected name.");
    }

    /**
     * Test of exportDatabase method, of class userDatabaseTest.
     * This test needs to interact with the file system, consider mocking or verifying the file's existence and content.
     */
    @Test
    public void testExportDatabase() {
        System.out.println("Testing exportDatabase");
        // This test is left as an exercice because need more information.
    }
}
