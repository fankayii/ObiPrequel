package model.people;

import model.Direction;
import model.EntityState;
import ui.main.SWGame;

import java.awt.geom.Rectangle2D;


/**
 Represent an entity
 */
public class Entity {
    protected float posx;
    protected float posy;
    protected int widthScaled;
    protected int heightScaled;
    protected int width;
    protected int height;
    protected int flipX = 0;
    protected int flipW = 1;
    protected Rectangle2D.Float hitbox;
    protected EntityState state = EntityState.IDLE; //player state
    protected float airSpeed;
    protected boolean inAir = false;
    protected int maxHealth;
    protected int health;
    protected Rectangle2D.Float attackBox;
    protected float walkSpeed;
    protected Direction walkDir;
    protected boolean alive = true;
    protected boolean attackChecked = false;
    protected boolean attacking = false;
    protected boolean moving = false;


    // Constructor, set a new entity based on the given x, y coordinates, width and height
    public Entity(float x, float y, int width, int height) {
        this.posx = x;
        this.posy = y;
        this.width = width;
        this.height = height;
    }


    // EFFECTS: create a new hitbox
    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(posx, posy,(int) (width * SWGame.SCALE), (int) (height * SWGame.SCALE));
    }


    // EFFECTS: reduce entity's health by the amount of damage
    // MODIFIES: this
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            setState(EntityState.DEAD);
        } else {
            setState(EntityState.HIT);
        }
    }

    // EFFECTS: create a new attack box based on the given width and height
    protected void initAttackBox(int width, int height) {
        attackBox = new Rectangle2D.Float(posx, posy, (int) (width * SWGame.SCALE), (int) (height * SWGame.SCALE));
    }


    // EFFECTS: calculate the y-coordinate based on the given airspeed and return it
    // MODIFIES: this
    protected float getPosyForJump(float airSpeed) {
        int currentTile = (int) (hitbox.y / SWGame.TILES_SIZE);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * SWGame.TILES_SIZE;
            int offsetY = (int) (SWGame.TILES_SIZE - hitbox.height);
            return tileYPos + offsetY - 1;
        } else {
            // Jumping
            return currentTile * SWGame.TILES_SIZE;

        }

    }

    // EFFECTS: reset the entity
    // MODIFIES: this
    public void reset() {
        hitbox.x = posx;
        hitbox.y = posy;

        health = maxHealth;
        setState(EntityState.IDLE);
        alive = true;


    }

    // EFFEECTS: return the flip value for x coordinate based on the walk direction
    public int flipX() {
        if (walkDir == Direction.RIGHT) {
            return width;
        }
        return 0;
    }

    // getters
    public EntityState getState() {
        return state;
    }

    public int getFlipX() {
        return flipX;
    }

    public int getFlipW() {
        return flipW;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getAirSpeed() {
        return airSpeed;
    }

    public boolean getAttackCheck() {
        return attackChecked;
    }

    public boolean getMoving() {
        return moving;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public float getX() {
        return posx;
    }

    public float getY() {
        return posy;
    }

    public Rectangle2D.Float getAttackBox() {
        return attackBox;
    }

    public boolean isAlive() {
        return alive;
    }

    public Direction getWalkDir() {
        return walkDir;
    }


    public int getWidthScaled() {
        return widthScaled;
    }

    public int getHeightScaled() {
        return heightScaled;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    // setter
    public void setState(EntityState state) {
        this.state = state;

    }

    public void setHit() {
        airSpeed = 0f;
    }

    public void setInAir() {
        inAir = true;
    }

    public void setAttack(boolean attack) {
        this.attacking = attack;
        this.attackChecked = attack;
    }

    public void changeState(EntityState s) {
        this.state = s;
    }

    public void setAlive() {
        alive = false;
    }

    public void setWalkDir(Direction direction) {
        walkDir = direction;
    }
}
