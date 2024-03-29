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
 * @author Michael Zhao
 */
public class countryTest {
    
    public countryTest() {
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
     * Test of getName method, of class country.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        country instance = new country("Japan","Asia");
        String expResult = "Japan";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getContinent method, of class country.
     */
    @Test
    public void testGetContinent() {
        System.out.println("getContinent");
        country instance = new country("Japan","Asia");
        String expResult = "Asia";
        String result = instance.getContinent();
        assertEquals(expResult, result);
    }
    
}
