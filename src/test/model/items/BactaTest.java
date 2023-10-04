package model.items;

import model.people.Player;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BactaTest {
    private Bacta bacta;
    private Player player;


    @BeforeEach
    public void setup() {
        player = new Player(0,0,0,0);
        bacta = new Bacta(0,0);
    }

    @Test
    public void testUse() {
        player.takeDamage(10);
        assertEquals(player.getMaxHealth()-10,player.getHealth());
        bacta.use(player);
        assertEquals(Math.min(player.getHealth()+bacta.getAmount(),player.getMaxHealth()),player.getHealth());
    }

    @Test
    public void testHit() {
        assertFalse(bacta.isHit());
        bacta.setAlive();
        assertTrue(bacta.isHit());

    }

    @Test
    public void testJson() {
        JSONObject json = bacta.toJson();
        assertEquals(2,json.get("xpos"));
        assertEquals(2,json.get("ypos"));

    }
}
