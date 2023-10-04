package model.items;



import java.awt.geom.Rectangle2D;

/**
 Represent an item
 */

public class Item {
    protected Rectangle2D.Float hitbox;
    protected boolean alive = true;
    protected int posx;
    protected int posy;

    // constructor
    public Item(int x, int y) {
        initHitbox(x,y);
    }

    // set a hitbox
    public void initHitbox(int x, int y) {
    }

    //getters
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    // EFFECT: set the item to inactive
    public void setAlive() {
        alive = false;
    }



}
