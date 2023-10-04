package ui.sprites;

import model.EntityState;
import model.people.Entity;
import ui.Level;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 Represent sprite for an entity
 */
public class Sprite {
    protected Entity entity;
    protected BufferedImage[][] animations;
    protected float drawOffsetX;
    protected float drawOffsetY;
    protected int aniTick = 0;
    protected int aniSpeed = 25;
    protected int aniIndex = 0;
    protected int width;
    protected int height;
    protected Rectangle2D.Float hitbox;
    protected Rectangle2D.Float attackbox;
    protected Map<EntityState,Integer> spriteNum;
    protected Map<EntityState,Integer> stateNum;

    // Constructor, create a sprite with given e
    public Sprite(Entity e) {

        entity = e;
        hitbox = e.getHitbox();
        attackbox = e.getAttackBox();
        spriteNum = new HashMap<>();
        stateNum = new HashMap<>();

    }

    // EFFECTS: load the animations
    public void loadAnimations() {

    }

    // EFFECTS: draw the image with the given g, animations
    // MODIFIES: g
    public void render(Graphics g, int lvlOffset) {

        g.drawImage(animations[entity.getState().getState()][aniIndex],
                (int) (hitbox.x - drawOffsetX) - lvlOffset + entity.getFlipX(),
                (int) (hitbox.y - drawOffsetY), entity.getWidth() * entity.getFlipW(), entity.getHeight(), null);








    }



    // EFFECTS: draw the hitbox with g
    // MODIFIES: g
    public void drawHitbox(Graphics g, int lvlOffsetX) {

        g.setColor(Color.BLUE);
        g.drawRect((int) hitbox.x - lvlOffsetX, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    // EFFECTS: draw the attackbox with g
    // MODIFIES: g
    public void drawAttackbox(Graphics g, int lvlOffsetX) {
        g.setColor(Color.red);
        g.drawRect((int) (attackbox.x - lvlOffsetX), (int) attackbox.y, (int) attackbox.width, (int) attackbox.height);

    }

    // EFFECTS: update the entity state and animation
    // MODIFIES: this
    public void updateAnimationTick(Level lvl) {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex += 1;

            if (aniIndex >= spriteNum.get(entity.getState())) {
                aniIndex = 0;
                entity.setAttack(false);
                if (entity.getState() == EntityState.HIT) {
                    entity.setState(EntityState.IDLE);
                    entity.setHit();
                    if (!lvl.checkOnGround(entity.getHitbox())) {
                        entity.setInAir();
                    }
                }
            }


        }


    }

    // EFFECTS: check entity state and update animations
    public void setAnimations() {}

    // getters
    public Entity getEntity() {
        return entity;
    }

    // EFFECTS: reset the animation
    // MODIFIES: this
    protected void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }
}
