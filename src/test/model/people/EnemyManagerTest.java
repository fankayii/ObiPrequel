package model.people;

import model.Direction;
import model.items.Bullet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnemyManagerTest {
    private EnemyManager em;
    private Stormtrooper st;
    private List<Bullet> bullets = new ArrayList<>();
    private Player p;
    private ArrayList<Stormtrooper> tks= new ArrayList<>();

    @BeforeEach
    public void setup() {
        em = new EnemyManager();
        p = new Player(1,1,1,1);
        st = new Stormtrooper(1,1,2,2);
        bullets.add(new Bullet(1,1, Direction.RIGHT));
        tks.add(st);
        em.loadEnemies(tks);


    }

    @Test
    public void testConstructor() {
        assertEquals(1,em.getTKS().size());
    }

    @Test
    public void testReset() {
        st.takeDamage(10);
        assertEquals(st.getMaxHealth()-10,st.getHealth());
        em.resetAllEnemies();
        assertEquals(st.getMaxHealth(),st.getHealth());

    }

    @Test
    public void testCheckHit() {
        // test intersect
        em.checkEnemyHit(new Rectangle2D.Float(1,1,1,1));
        assertEquals(st.getHealth(),st.getMaxHealth()-10);
        // not intersect
        em.checkEnemyHit(new Rectangle2D.Float(-1,-1,0,0));
        assertEquals(st.getHealth(),st.getMaxHealth()-10);
        st.setInActive();
        em.checkEnemyHit(new Rectangle2D.Float(-1,-1,0,0));
        assertEquals(st.getHealth(),st.getMaxHealth()-10);


    }

}
