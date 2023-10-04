package ui.sprites;

import model.EntityState;
import model.people.Stormtrooper;
import ui.LoadImg;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represent sprite for enemy
 */
public class EnemySprite extends Sprite {
    private Stormtrooper tk;
    private float drawOffsetX = 40;
    private float drawOffsetY = 25; //11


    // Constructor, create a enemy sprite with given tk
    public EnemySprite(Stormtrooper tk) {
        super(tk);
        this.tk = tk;
        setSpriteNum();
        setStateNum();
    }



    // EFFECTS: set the hashmap to link state and the sprites amount
    // MODIFIES: this
    private void setSpriteNum() {
        spriteNum.put(EntityState.IDLE,8);
        spriteNum.put(EntityState.RUNNING,6);
        spriteNum.put(EntityState.ATTACK,6);
        spriteNum.put(EntityState.HIT,4);
        spriteNum.put(EntityState.DEAD,5);
    }

    // EFFECTS: set the hashmap to link state and the state index
    // MODIFIES: this
    private void setStateNum() {
        stateNum.put(EntityState.IDLE,0);
        stateNum.put(EntityState.RUNNING,1);
        stateNum.put(EntityState.ATTACK,2);
        stateNum.put(EntityState.HIT,3);
        stateNum.put(EntityState.DEAD,4);
    }

    // EFFECTS: update the animation
    // MODIFIES: this
    public void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= spriteNum.get(tk.getState())) {
                aniIndex = 0;
                EntityState s = tk.getState();
                if (s == EntityState.ATTACK || s == EntityState.HIT) {
                    tk.changeState(EntityState.IDLE);
                }
                if (s == EntityState.DEAD) {
                    tk.setAlive();
                }
            }
        }

    }

    // EFFECTS: load the animations
    // MODIFIES: this
    @Override
    public void loadAnimations() {
        BufferedImage img = LoadImg.getSpriteAtlas(LoadImg.TK_SPRITE);
        animations = new BufferedImage[5][9];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * entity.getWidthScaled(),
                        j * entity.getHeightScaled(), entity.getWidthScaled(), entity.getHeightScaled());

            }

        }



    }

    // EFFECTS: draw the image with the given g, animations
    // MODIFIES: g
    public void draw(Graphics g, int lvlOffset, BufferedImage[][] animations) {

        g.drawImage(animations[stateNum.get(entity.getState())][aniIndex],
                (int) (hitbox.x - drawOffsetX) - lvlOffset + entity.flipX(),
                (int) (hitbox.y - drawOffsetY), entity.getWidth() * entity.getFlipW(), entity.getHeight(), null);

    }

    // EFFECTS: draw the enemy's health with g
    // MODIFIES g
    public void drawHealth(Graphics g,int index) {

        g.setColor(Color.GREEN);
        g.fillRect(0,2,entity.getHealth(),index);
    }
}
