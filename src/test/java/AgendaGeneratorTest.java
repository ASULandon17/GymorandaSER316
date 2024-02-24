package test.java;

import main.java.memoranda.BeltValue;
import main.java.memoranda.Course;
import main.java.memoranda.PersistentClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static main.java.memoranda.util.AgendaGenerator.beltColor;

import static org.junit.Assert.*;

/**
 * This class tests the methods of AgendaGenerator.
 */
public class AgendaGeneratorTest {
    /**
     * Test to check if beltColor gives the proper hex codes.
     */
    @Test
    public void beltColorTest() {
        assertEquals("NO_BELT should be red.", "#ca0000",
                beltColor(BeltValue.NO_BELT));
        assertEquals("WHITE should be white.", "#FFFFFF",
                beltColor(BeltValue.WHITE));
        assertEquals("YELLOW should be yellow.", "#f8fe5a",
                beltColor(BeltValue.YELLOW));
        assertEquals("ORANGE should be orange.", "#FFA500",
                beltColor(BeltValue.ORANGE));
        assertEquals("PURPLE should be purple.", "#800080",
                beltColor(BeltValue.PURPLE));
        assertEquals("BLUE should be blue.", "#0000FF",
                beltColor(BeltValue.BLUE));
        assertEquals("BLUE_STRIPE should be blue.", "#0000FF",
                beltColor(BeltValue.BLUE_STRIPE));
        assertEquals("GREEN should be green.", "#007e00",
                beltColor(BeltValue.GREEN));
        assertEquals("GREEN_STRIPE should be green.", "#007e00",
                beltColor(BeltValue.GREEN_STRIPE));
        assertEquals("BROWN1 should be brown.", "#964B00",
                beltColor(BeltValue.BROWN1));
        assertEquals("BROWN2 should be brown.", "#964B00",
                beltColor(BeltValue.BROWN2));
        assertEquals("BROWN3 should be brown.", "#964B00",
                beltColor(BeltValue.BROWN3));
        assertEquals("BLACK1 should be black.", "#000000",
                beltColor(BeltValue.BLACK1));
        assertEquals("BLACK2 should be black.", "#000000",
                beltColor(BeltValue.BLACK2));
        assertEquals("BLACK3 should be black.", "#000000",
                beltColor(BeltValue.BLACK3));

    }

}
