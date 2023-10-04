package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityStateTest {
    private EntityState es_idle;
    private EntityState es_run;
    private EntityState es_jump;
    private EntityState es_fall;
    private EntityState es_attack;
    private EntityState es_hit;
    private EntityState es_dead;


    @BeforeEach
    public void setup() {
        es_idle = EntityState.IDLE;
        es_run = EntityState.RUNNING;
        es_jump = EntityState.JUMP;
        es_fall = EntityState.FALLING;
        es_attack = EntityState.ATTACK;
        es_hit = EntityState.HIT;
        es_dead = EntityState.DEAD;
    }

    @Test
    public void testGetState() {
        assertEquals(0,es_idle.getState());
        assertEquals(1,es_run.getState());
        assertEquals(2,es_jump.getState());
        assertEquals(3,es_fall.getState());
        assertEquals(4,es_attack.getState());
        assertEquals(5,es_hit.getState());
        assertEquals(6,es_dead.getState());


    }

    @Test
    public void testGetSprite() {
        assertEquals(5,es_idle.getSprite());
        assertEquals(6,es_run.getSprite());
        assertEquals(3,es_jump.getSprite());
        assertEquals(1,es_fall.getSprite());
        assertEquals(3,es_attack.getSprite());
        assertEquals(4,es_hit.getSprite());
        assertEquals(8,es_dead.getSprite());

    }
}
