package model.people;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


/**
 Represent a collection of Enemy
 */
public class EnemyManager {
    private ArrayList<Stormtrooper> tks = new ArrayList<>();

    // constructor
    public EnemyManager() {

    }

    // EFFECTS: check if any of the enemy is hit by the given attackbox
    // MODIFIES: this
    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Stormtrooper c : tks) {
            if (c.isActive()) {
                if (attackBox.intersects(c.getHitbox())) {
                    c.takeDamage(10);
                    return;
                }

            }

        }



    }




    // EFFECTS: load the enemies
    // MODIFIES: this
    public void loadEnemies(ArrayList<Stormtrooper> tks) {
        this.tks = tks;
    }



    // EFFECTS: reset all the enemies to inital state
    // MODIFIES: this
    public void resetAllEnemies() {
        for (Stormtrooper c : tks) {
            c.reset();

        }

    }

    //getters
    public ArrayList<Stormtrooper> getTKS() {
        return tks;
    }
}
