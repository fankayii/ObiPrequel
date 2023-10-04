package model.items;

import model.Direction;
import ui.main.SWGame;

import java.awt.geom.Rectangle2D;

/**
 Representing the bullets
 **/

public class Bullet extends Item {
    private int width = 30;
    private int height = 20;
    private int offsetX = 5;
    private int offsetY = 5;
    private float speed = (float) (0.75 * SWGame.SCALE);
    private Direction dir;

    //constructor, set a new bullet with x,y coordinates and direction
    public Bullet(int x, int y, Direction dir) {
        super(x,y);
        initHitbox(x,y);
        this.posx = x;
        this.posy = y;
        this.dir = dir;
    }

    // EFFECTS: create a new hitbox given x and y value
    @Override
    public void initHitbox(int x, int y) {
        hitbox = new Rectangle2D.Float(x + offsetX,y + offsetY,width,height);
    }

    // EFFECTS: move the bullet based on the direction
    public void updatePos() {
        hitbox.x = dir == Direction.LEFT ? hitbox.x - speed : hitbox.x + speed;
    }

    // getters
    public boolean isHit() {
        return !alive;
    }

}
