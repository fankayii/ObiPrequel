package model.people;

import model.Direction;
import model.EntityState;
import model.items.Bullet;
import ui.main.SWGame;

import java.util.List;

/**
Represent the Storm Trooper of the game
 **/

public class Stormtrooper extends Entity {
    protected float attackDistance = 300; //Game.TILES_SIZE
    private int tileY;
    private int attackBoxOffsetX = 10;
    protected boolean active;
    protected boolean attackChecked;
    protected int tick;
    protected int width = 72;
    protected int height = 40;



    // Constructor, create a new storm trooper with x, y coordinate, width and height
    public Stormtrooper(float x, float y, int width, int height) {
        super(x, y, 144,80);
        initHitbox(25,20);
        initAttackBox(30,28);
        maxHealth = 20;
        health = maxHealth;
        active = true;
        walkSpeed = SWGame.SCALE * 0.35f;
        widthScaled = 72;
        heightScaled = 40;
    }

    // EFFECTS: update the attack box
    // MODIFIES: this
    public void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }



    // EFFECTS: update the attack box when flip
    // MODIFIES: this
    public void updateAttackBoxFlip() {
        if (walkDir == Direction.RIGHT) {
            attackBox.x = hitbox.x + hitbox.width - 10;
        } else {
            attackBox.x = hitbox.x - attackBoxOffsetX;
        }
        attackBox.y = hitbox.y;
    }

    // EFFECTS: update the count for shooting
    // MODIFIES: this
    public void update() {
        tick++;
    }

    // EFFECTS: update the storm trooper position in air
    // MODIFIES: this
    public void updateInAir(boolean canMove) {
        if (canMove) {
            hitbox.y += airSpeed;
            airSpeed += 0.08;
        } else {
            inAir = false;
            hitbox.y = getPosyForJump(airSpeed);
            tileY = (int) (hitbox.y / SWGame.TILES_SIZE);
        }
    }

    // EFFECTS: move the storm trooper and return the speed of x-coordinate
    // MODIFIES: this
    public float moveTK() {
        updateAttackBox();
        updateAttackBoxFlip();
        float speedX;

        if (walkDir == Direction.LEFT) {
            speedX = -walkSpeed;
            flipW = 1;

        } else {
            speedX = walkSpeed;
            flipW = -1;

        }
        return speedX;


    }

    // EFFECTS: change the walking direction
    // MODIFIES: this
    public void changeWalkDir() {
        walkDir = walkDir == Direction.RIGHT ? Direction.LEFT : Direction.RIGHT;

    }

    // EFFECTS: check if the player is in range
    public boolean inRange(Player p) {
        int absValue = (int) Math.abs(p.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 5;

    }

    // EFFECTS: turn the storm trooper toward the player
    // MODIFIES: this
    public void turnTowardsPlayer(Player player) {
        walkDir = player.hitbox.x > hitbox.x ? Direction.RIGHT : Direction.LEFT;

    }

    // EFFECTS: check if the player is close enough to attack
    public boolean isPlayerCloseForAttack(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance;
    }

    // EFFECTS: change the entity state of storm trooper
    // MODIFIES: this
    public void checkPlayer2Attack(Player p) {
        this.state = isPlayerCloseForAttack(p) ? EntityState.ATTACK : EntityState.RUNNING;
    }

    // EFFECTS: add a new bullet to the list and return
    public List<Bullet> shootBullet(List<Bullet> bulllets) {
        bulllets.add(new Bullet((int) hitbox.x, (int) hitbox.y,walkDir));
        return bulllets;


    }

    // getters
    public int getTick() {
        return tick;
    }

    public boolean getInAir() {
        return inAir;
    }

    public int getTileY() {
        return tileY;
    }

    public boolean isActive() {
        return active;
    }


    // setters
    public void setInActive() {
        active = false;

    }
}
