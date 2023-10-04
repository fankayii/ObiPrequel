package model.people;

import model.EntityState;
import model.items.Bacta;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PlayerTest {
    private Player player;
    private Bacta bacta;

    @BeforeEach
    public void setup() {
        player = new Player(0,0,128,80);
        bacta = new Bacta(0,0);

    }

    @Test
    public void testBacta() {
        player.addBacta(bacta);
        assertEquals(player.getBag().size(),1);
        player.takeDamage(20);
        player.useItem();
        assertEquals(player.getMaxHealth(),player.getHealth());
    }

    @Test
    public void testConstructor() {
        // test getters
        assertEquals(0,player.getFlipX());
        assertEquals(1,player.getFlipW());
        assertEquals(128,player.getWidth());
        assertEquals(80,player.getHeight());
        assertFalse(player.getAttackCheck());
        assertFalse(player.getMoving());
        assertFalse(player.isAttacking());
        assertEquals(0,player.getX());
        assertEquals(0,player.getY());
        assertTrue(player.isAlive());
        //assertEquals(100,player.getMaxHealth());

        // test setters
        player.setAlive();
        assertFalse(player.isAlive());

    }

    @Test
    public void testTakeDamage() {
        player.takeDamage(player.getMaxHealth());
        assertEquals(player.getState(), EntityState.DEAD);
    }

    @Test
    public void testReset() {
        player.changeState(EntityState.ATTACK);
        player.setAlive();
        player.reset();
        assertTrue(player.isAlive());
        assertEquals(player.getState(),EntityState.IDLE);
    }



    @Test
    public void testMove() {
        // don't move
        float speed = player.movePlayer();
        assertEquals(-10,speed);
        assertEquals(0,player.getHitbox().x);
        assertEquals(0,player.getHitbox().y);
        // move left
        player.setLeft(true);
        speed = player.movePlayer();
        assertEquals(speed,-2);
        // move right
        player.setLeft(false);
        player.setRight(true);
        speed = player.movePlayer();
        assertEquals(speed,2);
        // jump
        player.setRight(false);
        player.setJump(true);
        player.movePlayer();
        assertEquals(player.getAirSpeed(),-4.5);

    }

    @Test
    public void testMoveInAir() {
        player.jump();


        // test can move
        player.moveInAir(true);
        assertEquals(-4.5,player.getHitbox().y);
        // test cannot move
        player.moveInAir(false);
        assertEquals(0.0,player.getHitbox().y);
        player.setAirSpeed();
        player.moveInAir(false);
        assertFalse(player.getInAir());
        assertEquals(player.getAirSpeed(),0);


    }

    @Test
    public void testUpdateXPos() {
        // test cannot move
        player.updateXPos(1,false);
        assertEquals(23,player.getHitbox().x);
        player.reset();
        player.updateXPos(-1,false);
        assertEquals(0,player.getHitbox().x);
        // can move
        player.reset();
        player.updateXPos(1,true);
        assertEquals(1,player.getHitbox().x);
    }

    @Test
    public void testUpdae() {
        // test right
        player.setRight(true);
        player.update();
        assertEquals(player.getAttackBox().x,player.getHitbox().x+player.getHitbox().width+20);
        // test left
        player.setRight(false);
        player.setLeft(true);
        player.update();
        assertEquals(player.getAttackBox().x,player.getHitbox().x-player.getHitbox().width-20);
        assertEquals(player.getAttackBox().y,player.getHitbox().y+20);
        // test dead
        player.setLeft(false);
        player.takeDamage(100);
        player.update();
        assertEquals(player.getState(),EntityState.DEAD);

    }

    @Test
    public void testResetAttack() {
        player.setFlipW(0);
        player.testResetAttackBox();
        assertEquals(player.getAttackBox().x,player.getHitbox().x-player.getHitbox().width-20);

    }

    @Test
    public void testResetAll() {
        player.setLeft(true);
        player.setRight(true);
        player.setDown(true);
        player.setUp(true);
        player.setMoving();
        player.setState(EntityState.HIT);
        player.takeDamage(10);
        player.resetAll();
        assertFalse(player.getMoving());
        assertFalse(player.isRight());
        assertFalse(player.isLeft());
        assertFalse(player.isUp());
        assertFalse(player.isDown());
        assertEquals(player.getState(),EntityState.IDLE);
        assertEquals(player.getHealth(),player.getMaxHealth());

    }

    @Test
    public void testSetSpawn() {
        Point test = new Point(1,1);
        player.setSpawn(test);
        assertEquals(player.getHitbox().x,1);
        assertEquals(player.getHitbox().y,1);
    }

    @Test
    public void testJson() {
        JSONObject json = player.getPos();
        assertEquals(json.get("xpos"),0);
        assertEquals(json.get("ypos"),0);

    }

    @Test
    public void testSetHealth() {
        player.setHealth(20);
        assertEquals(20,player.getHealth());
    }

    @Test
    public void testSetAttacking() {
        player.setAttacking(true);
        assertTrue(player.isAttacking());
        player.setAttacking(false);
        assertFalse(player.isAttacking());
    }

    @Test
    public void testSetInAir() {
        player.setInAir(true);
        assertTrue(player.getInAir());
        player.setInAir(false);
        assertFalse(player.getInAir());
    }

    @Test
    public void testUseItem() {
        player.takeDamage(10);
        assertEquals(player.getMaxHealth()-10,player.getHealth());
        player.useItem();
        assertEquals(player.getMaxHealth()-10,player.getHealth());
        player.heal(1);
        assertEquals(player.getMaxHealth()-9,player.getHealth());
        player.heal(9);
        assertEquals(player.getMaxHealth(),player.getHealth());
        player.heal(10);
        assertEquals(player.getMaxHealth(),player.getHealth());

    }

    @Test
    public void testHealth() {
        // full blood
        player.heal(10);
        assertEquals(player.getMaxHealth(),player.getHealth());
        //
        player.takeDamage(20);
        player.heal(20);
        assertEquals(player.getMaxHealth(),player.getHealth());
        //
        player.takeDamage(20);
        player.heal(30);
        assertEquals(player.getMaxHealth(),player.getHealth());
        //
        player.takeDamage(20);
        player.heal(10);
        assertEquals(player.getMaxHealth()-10,player.getHealth());

    }

    @Test
    public void testJump() {
        player.setInAir(false);
        player.jump();
        assertTrue(player.getInAir());
    }

    @Test
    public void testMovePlayer() {
        player.setInAir(false);
        player.setLeft(true);
        player.setRight(false);
        float speed = player.movePlayer();
        assertEquals(-4,(int) player.moveLeft(speed));
        player.setInAir(true);
        player.setRight(false);
        player.setLeft(false);
        assertEquals(0,player.movePlayer());
    }

    @Test
    public void testUpdateAttackBox() {
        float y = player.getAttackBox().y;
        player.updateAttackBox();
        assertEquals(player.getAttackBox().y,y+20);
    }

    @Test
    public void testNoJump() {
        player.setInAir(true);
        player.jump();
        assertEquals(0,player.getHitbox().y);

    }

    @Test
    public void testMovePlayerForMiss() {
        player.setInAir(true);
        assertEquals(0,player.movePlayer());
        player.setLeft(true);
        assertEquals(player.movePlayer(),player.moveLeft(0));
        player.setRight(true);
        player.setLeft(false);
        assertEquals(player.movePlayer(),player.moveRight(0));
        player.setLeft(true);
        assertEquals(0,player.movePlayer());

    }
}
