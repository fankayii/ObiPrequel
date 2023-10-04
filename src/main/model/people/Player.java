package model.people;

import model.EntityState;
import model.Event;
import model.EventLog;
import model.items.Bacta;
import org.json.JSONObject;
import ui.main.SWGame;

import java.awt.*;
import java.util.ArrayList;

/**
 Represent the player
 */

public class Player extends Entity {
    private boolean left;
    private boolean up;
    private boolean right;
    private boolean down;
    private boolean jump;
    private float jumpSpeed = -2.25f * SWGame.SCALE;
    private EntityState playerAction = EntityState.IDLE;
    private float fallSpeedAfterCollision = 0.5f * SWGame.SCALE;
    private ArrayList<Bacta> bag = new ArrayList<>();
    private int maxHealth = 100;




    // Constructor, set a new player based on the given x, y coordinates, width and height
    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        initHitbox(20,27);
        initAttackBox(35,20);
        resetAttackBox();
        health = maxHealth;
        this.state = EntityState.IDLE;
        this.walkSpeed = SWGame.SCALE;
        alive = true;

    }

    // EFFECTS: reset the attack box value based on the flipW value
    // MODIFIES: this
    private void resetAttackBox() {
        if (flipW == 1) {
            setAttackBoxOnRightSide();

        } else {
            setAttackBoxOnLeftSide();
        }
    }

    // EFFECTS: set the attack box x value when player towards left
    // MODIFIES: this
    private void setAttackBoxOnLeftSide() {
        attackBox.x = hitbox.x - hitbox.width - (int) (SWGame.SCALE * 10);
    }

    // EFFECTS: set the attack box x value when player towards right
    // MODIFIES: this
    private void setAttackBoxOnRightSide() {
        attackBox.x = hitbox.x + hitbox.width - (int) (SWGame.SCALE * 5);
    }


    // EFFECTS: update the attack box x,y values based on the direction player is currently facing
    // MODIFIES: this
    public void updateAttackBox() {
        if (right) {
            attackBox.x = hitbox.x + hitbox.width + (int) (SWGame.SCALE * 10);
        } else if (left) {
            attackBox.x = hitbox.x - hitbox.width - (int) (SWGame.SCALE * 10);
        }
        attackBox.y = hitbox.y + (SWGame.SCALE * 10);
    }

    // EFFECTS: update the player position and return the moving speed in x-coordinate
    // MODIFIES: this
    public float movePlayer() {
        moving = false;
        if (jump) {
            jump();
        }
        if (!inAir) {
            if ((!left && !right) || (right && left)) {
                return -10;
            }

        }

        float speedX = 0;
        if (left && !right) {
            speedX = moveLeft(speedX);
        }

        if (right && !left) {
            speedX = moveRight(speedX);
        }
        return speedX;

    }

    // EFFECTS: add the given bacta to bag
    // MODIFIES: this
    public void addBacta(Bacta b) {
        bag.add(b);
        EventLog.getInstance().logEvent(new Event("bacta "+b.getHitbox().x+","+b.getHitbox().y+"added to bag"));

    }




    // EFFECTS: set corresponding field value when player jump
    // MODIFIES: this
    public void jump() {
        if (!inAir) {
            inAir = true;
            airSpeed = jumpSpeed;
        }
    }


    // EFFECTS: change the given speed as player moves left
    // MODIFIES: speed
    public float moveLeft(float speed) {
        speed -= walkSpeed;
        flipX = width;
        flipW = -1;
        return speed;

    }

    // EFFECTS: change the given speed as player moves right
    // MODIFIES: speed
    public float moveRight(float speed) {
        speed += walkSpeed;
        flipX = 0;
        flipW = 1;
        return speed;

    }

    // EFFECTS: update the player position in air
    // MODIFIES: this
    public void moveInAir(boolean canMove) {
        if (canMove) {
            hitbox.y += airSpeed;
            airSpeed += 0.08;

        } else {
            hitbox.y = getPosyForJump(airSpeed);
            if (airSpeed > 0) {
                resetInAir();
            } else {
                airSpeed = 0.5f * SWGame.SCALE;
                inAir = false;
            }


        }

    }

    // EFFECTS: reset the player to not in air
    // MODIFIES: this
    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    // EFFECTS: update the player position when moving on ground
    // MODIFIES: this
    public void updateXPos(float speedX,boolean canMove) {
        moving = true;
        if (canMove) {
            hitbox.x += speedX;
        } else {
            hitbox.x = getPosxForMove(speedX);
        }
    }

    // EFFECTS: calculate the x coordinate based on the given speed
    // MODIFIES: this
    private float getPosxForMove(float speedX) {
        int currentTile = (int) (hitbox.x / SWGame.TILES_SIZE);
        if (speedX > 0) {
            int tileXPos = currentTile * SWGame.TILES_SIZE;
            int offsetX = (int) (SWGame.TILES_SIZE - hitbox.width);
            return tileXPos + offsetX - 1;
        } else {
            return currentTile * SWGame.TILES_SIZE;
        }

    }

    // EFFECTS: increase the player's health by amount
    // MODIFIES: this
    public void heal(int amount) {
        health = health + amount >= maxHealth ? maxHealth : health + amount;

    }

    // REQUIRES: !bag.isEmpty()
    // EFFECTS: use the item in the bag
    // MODIFIES: this
    public void useItem() {
        if (!bag.isEmpty()) {
            Bacta i = bag.get(0);
            EventLog.getInstance().logEvent(new Event("Player use one bacta"));
            i.use(this);
            this.bag.remove(i);

        }
    }


    // EFFECTS: reset all the important fields to initial value
    // MODIFIES: this
    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        this.state = EntityState.IDLE;
        health = maxHealth;
        hitbox.x = posx;
        hitbox.y = posy;
    }

    // EFFECTS: update player's state
    // MODIFIES: this
    public void update() {
        checkAlive();
        if (!alive) {
            return;
        }
        updateAttackBox();



    }

    // EFFECTS: check if player's alvie, if so, set the player's entity state to dead
    // MODIFIES: this
    // REQUIRES: health<=0
    public void checkAlive() {
        if (health <= 0) {
            alive = false;
            this.state = EntityState.DEAD;

        }
    }

    // EFFECTS: reset all direction booleans to false
    // MODIFIES: this
    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    // EFFECTS: reset the attack box to initial value
    // MODIFIES: this
    public void testResetAttackBox() {
        resetAttackBox();
    }

    // EFFECTS: set the player position to the given point
    // MODIFIES: this
    public void setSpawn(Point spawn) {
        this.posx = spawn.x;
        this.posy = spawn.y;
        hitbox.x = posx;
        hitbox.y = posy;
    }

    // EFFECTS: write x, y coordinates of hitbox to a json object and return
    public JSONObject getPos() {
        JSONObject json = new JSONObject();
        json.put("xpos",(int) hitbox.x);
        json.put("ypos",(int) hitbox.y);
        return json;
    }

    // getters
    public ArrayList<Bacta> getBag() {
        return bag;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean getInAir() {
        return inAir;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }



    // setters
    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;

    }

    public void setAirSpeed() {
        airSpeed = 1;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void setMoving() {
        moving = true;
    }


    public void setHealth(int h) {
        health = h;
    }

    // for test
    public void setFlipW(int w) {
        flipW = w;
    }
}
