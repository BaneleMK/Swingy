package com.swingy;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test.
     */
    @Test
    public void testApp() {
        assertTrue(true);
    }

    @Test
    public void testweapon(){
        Weapon xtra = new Weapon("qwerty", 201);
        assertEquals(201, xtra.get_attack());
    }
}
