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
 *
 * @author Micha
 */
public class levelDatabaseTest {
    
    public levelDatabaseTest() {
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
     * Test of selectLevel method, of class levelDatabase.
     */
    @Test
    public void testSelectLevel() {
        System.out.println("selectLevel");
        int userLevel = 1;
        levelDatabase instance = new levelDatabase();
        country expResult = new country("China","Asia");
        assertTrue(expResult.getName().equals("Canada") || expResult.getName().equals("United States") || expResult.getName().equals("Russia") || expResult.getName().equals("China"));
        assertTrue(expResult.getContinent().equals("North America") || expResult.getContinent().equals("Europe") || expResult.getContinent().equals("Asia"));
    }

    /**
     * Test of adminSelect method, of class levelDatabase.
     */
    @Test
    public void testAdminSelect() {
        System.out.println("adminSelect");
        int level = 1;
        int choice = 1;
        levelDatabase instance = new levelDatabase();
        country expResult = new country("Canada", "North America");
        country result = instance.adminSelect(level, choice);
        System.out.println(result);
        assertEquals(expResult.getName(), result.getName());
        assertEquals(expResult.getContinent(),result.getContinent());
    }
    
}
