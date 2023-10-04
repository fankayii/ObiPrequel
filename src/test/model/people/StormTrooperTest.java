package model.people;

import model.Direction;
import model.EntityState;
import model.items.Bullet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StormTrooperTest {
    private Stormtrooper tk421;
    private Player obw;
    private List<Bullet> bullets = new ArrayList<>();

    @BeforeEach
    public void setup() {
        tk421 = new Stormtrooper(1,1,1,1);
        obw = new Player(1502,1,1,1);

    }

    @Test
    public void testUpdateTick() {
        tk421.update();
        assertEquals(1,tk421.getTick());
    }

    @Test
    public void testInRange() {
        assertFalse(tk421.inRange(obw));
        obw.setSpawn(new Point(1501,1));
        assertTrue(tk421.inRange(obw));
        obw.setSpawn(new Point(1500,1));
        assertTrue(tk421.inRange(obw));
    }

    @Test
    public void testChangeWalkDir() {
        assertNull(tk421.getWalkDir());
        float speedX = tk421.moveTK();
        assertEquals(tk421.getFlipW(),-1);
        assertEquals( speedX,tk421.walkSpeed);
        tk421.setWalkDir(Direction.RIGHT);
        tk421.changeWalkDir();
        assertEquals(Direction.LEFT,tk421.getWalkDir());
        speedX = tk421.moveTK();
        assertEquals(speedX,-tk421.walkSpeed);
        tk421.setWalkDir(Direction.RIGHT);
        speedX = tk421.moveTK();
        assertEquals( speedX,tk421.walkSpeed);

    }

    @Test
    public void testTurnTowardsPlayer() {
        tk421.turnTowardsPlayer(obw);
        assertEquals(Direction.RIGHT,tk421.getWalkDir());
        obw.setSpawn(new Point(0,0));
        tk421.turnTowardsPlayer(obw);
        assertEquals(Direction.LEFT,tk421.getWalkDir());

    }

    @Test
    public void testIsPlayerClose() {
        boolean flag = tk421.isPlayerCloseForAttack(obw);
        assertFalse(flag);
        tk421.checkPlayer2Attack(obw);
        assertEquals(tk421.getState(), EntityState.RUNNING);
        obw.setSpawn(new Point(0,0));
        flag = tk421.isPlayerCloseForAttack(obw);
        assertTrue(flag);
        tk421.checkPlayer2Attack(obw);
        assertEquals(tk421.getState(),EntityState.ATTACK);
        obw.setSpawn(new Point(301,0));
        assertTrue(tk421.isPlayerCloseForAttack(obw));
    }

    @Test
    public void testShoot() {
        bullets  = tk421.shootBullet(bullets);
        assertEquals(bullets.size(),1);


    }

    @Test
    public void testUpdateInAir() {
        // test can move
        tk421.updateInAir(true);
        assertEquals((int)tk421.getHitbox().y,1);
        // test cannot move
        tk421.updateInAir(false);
        assertEquals(23,(int)tk421.getHitbox().y);
        assertFalse(tk421.getInAir());
        assertEquals(0,tk421.getTileY());
    }

    @Test
    public void testSetters() {
        tk421.setInAir();
        assertTrue(tk421.getInAir());
    }

    @Test
    public void testNoChangeWalkDir() {
        tk421.changeWalkDir();
        assertEquals(Direction.RIGHT,tk421.getWalkDir());
        tk421.changeWalkDir();
        assertEquals(Direction.LEFT,tk421.getWalkDir());
        tk421.changeWalkDir();
        assertEquals(Direction.RIGHT,tk421.getWalkDir());
    }
}
