package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OopComplianceTests {

    @Test
    @DisplayName("TC-22: OOP structure and responsibility boundaries")
    void tc22_oopComplianceReview() throws NoSuchFieldException {
        assertTrue(Modifier.isAbstract(Player.class.getModifiers()));
        assertEquals("org.example", Board.class.getPackageName());
        assertEquals("org.example", Game.class.getPackageName());

        assertTrue(Player.class.isAssignableFrom(HumanPlayer.class));
        assertTrue(Player.class.isAssignableFrom(ComputerPlayer.class));

        Field cells = Board.class.getDeclaredField("cells");
        assertTrue(Modifier.isPrivate(cells.getModifiers()));
        assertEquals(int[].class, cells.getType());
    }
}
