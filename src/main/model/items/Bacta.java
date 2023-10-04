package model.items;

import model.people.Player;
import org.json.JSONObject;

import java.awt.geom.Rectangle2D;

/**
Representing the item that can heal player of the game
 **/
public class Bacta extends Item {
    private static final int amount = 20;
    private int offsetX = 2;
    private int offsetY = 2;

    //Constructor for Bacta
    public Bacta(int x, int y) {
        super(x, y);
        initHitbox(x,y);
    }


    // EFFECT: set up the hitbox of bacta
    @Override
    public void initHitbox(int x, int y) {
        hitbox = new Rectangle2D.Float(x + offsetX,y + offsetY,16,16);


    }

    // MODIFIES: p
    // EFFECTS: increase the player's health by amount
    public void use(Player p) {
        p.heal(amount);

    }



    // EFFECTS: write the x and y value of hitbox to json object and return it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("xpos",(int) hitbox.x);
        json.put("ypos",(int) hitbox.y);
        return json;
    }

    // getters
    public int getAmount() {
        return amount;
    }

    public boolean isHit() {
        return !alive;
    }


}
