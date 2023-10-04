package model.items;

import model.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BulletTest {
    private Bullet bullet;
    private Bullet bullet2;

    @BeforeEach
    public void setup() {
        bullet = new Bullet(2,0, Direction.LEFT);
        bullet2 = new Bullet(10,2,Direction.RIGHT);
    }

    @Test
    public void testUpdate() {
        bullet.updatePos();
        assertEquals(5,(int) bullet.getHitbox().x);

    }

    @Test
    public void testHit() {
        assertFalse(bullet.isHit());
        bullet.setAlive();
        assertTrue(bullet.isHit());
    }

    @Test
    public void testMove() {
        bullet2.updatePos();
        assertEquals(16,(int) bullet2.getHitbox().x);

    }


}
