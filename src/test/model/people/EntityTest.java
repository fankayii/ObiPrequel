package model.people;

import model.Direction;
import model.EntityState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EntityTest {
    private Entity en;

    @BeforeEach
    public void setup() {
        en = new Entity(1,1,1,1);
    }

    @Test
    public void testSetters() {
        en.setHit();
        assertEquals(0,(int) en.getAirSpeed());
        en.setAttack(true);
        assertTrue(en.isAttacking());
        assertTrue(en.getAttackCheck());
        assertEquals(0,en.getWidthScaled());
        assertEquals(0,en.getHeightScaled());
    }

    @Test
    public void testTakeDamage() {
        en.takeDamage(-1);
        assertEquals(en.getState(), EntityState.HIT);

    }

    @Test
    public void testFlipX() {
        en.setWalkDir(Direction.RIGHT);
        assertEquals(1,en.flipX());
        en.setWalkDir(Direction.LEFT);
        assertEquals(0,en.flipX());

    }
}
