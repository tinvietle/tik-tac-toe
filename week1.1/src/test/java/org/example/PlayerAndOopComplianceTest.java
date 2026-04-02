package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

class PlayerAndOopComplianceTest {

    @Test
    void tc22_player_is_abstract_and_has_encapsulated_state() throws Exception {
        assertTrue(Modifier.isAbstract(Player.class.getModifiers()));
        assertTrue(Modifier.isPrivate(Player.class.getDeclaredField("playerNumber").getModifiers()));
    }

    @Test
    void tc22_concrete_players_extend_player() {
        assertTrue(Player.class.isAssignableFrom(HumanPlayer.class));
        assertTrue(Player.class.isAssignableFrom(ComputerPlayer.class));
    }

    @Test
    void player_number_is_exposed_via_getter() {
        Player p1 = new ComputerPlayer(2);

        assertEquals(2, p1.getPlayerNumber());
    }
}
