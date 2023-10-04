package ui.sprites;

import model.EntityState;
import model.people.Player;
import ui.LoadImg;
import ui.main.SWGame;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represent sprite for player
 */
public class PlayerSprite extends Sprite {
    private Player player;
    private float drawOffsetX  = 25 * SWGame.SCALE;
    private float drawOffsetY = 9 * SWGame.SCALE;
    private int numx = 7;
    private int numy = 8;
    private int width = 64;
    private int height = 40;


    // Constructor, create a player sprite with given p
    public PlayerSprite(Player p) {

        super(p);
        this.player = p;
        setSpriteNum();
        setStateNum();
    }

    // EFFECTS: set the hashmap to link state and the sprites amount
    // MODIFIES: this
    private void setSpriteNum() {
        spriteNum.put(EntityState.IDLE,5);
        spriteNum.put(EntityState.RUNNING,6);
        spriteNum.put(EntityState.DEAD,8);
        spriteNum.put(EntityState.HIT,4);
        spriteNum.put(EntityState.ATTACK,3);
        spriteNum.put(EntityState.JUMP,1);
        spriteNum.put(EntityState.FALLING,1);


    }

    // EFFECTS: set the hashmap to link state and the state index
    // MODIFIES: this
    private void setStateNum() {
        stateNum.put(EntityState.IDLE,0);
        stateNum.put(EntityState.RUNNING,1);
        stateNum.put(EntityState.JUMP,2);
        stateNum.put(EntityState.FALLING,3);
        stateNum.put(EntityState.ATTACK,4);
        stateNum.put(EntityState.HIT,5);
        stateNum.put(EntityState.DEAD,6);
    }

    // EFFECTS: update the player state and update animation
    // MODIFIES: this
    @Override
    public void setAnimations() {
        EntityState s = player.getState();
        player.changeState(player.getMoving() ? EntityState.RUNNING : EntityState.IDLE);
        if (player.getInAir()) {
            if (player.getAirSpeed() < 0) {
                player.changeState(EntityState.JUMP);
            } else {
                player.changeState(EntityState.FALLING);
            }

        }

        if (player.isAttacking()) {
            player.changeState(EntityState.ATTACK);
            if (s != EntityState.ATTACK) {
                aniIndex = 1;
                aniTick = 0;
                return;
            }

        }

        if (s != player.getState()) {
            resetAniTick();
        }


    }


    // EFFECTS: load the animations
    // MODIFIES: this
    @Override
    public void loadAnimations() {
        BufferedImage img = LoadImg.getSpriteAtlas(LoadImg.PLAYER_ATLAS);
        animations = new BufferedImage[numx][numy];

        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * width, j * height, width, height);

            }


        }

    }

    // EFFECTS: draw the image with the given g
    // MODIFIES: g
    @Override
    public void render(Graphics g, int lvlOffset) {

        g.drawImage(animations[stateNum.get(entity.getState())][aniIndex],
                (int) (hitbox.x - drawOffsetX) - lvlOffset + entity.getFlipX(),
                (int) (hitbox.y - drawOffsetY), entity.getWidth() * entity.getFlipW(), entity.getHeight(), null);










    }

    // EFFECTS: draw the rectangle bar
    // MODIFIES g
    public void drawHealth(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(0,0,100,10);

        drawH(g);

    }

    // EFFECTS: draw the player's health with g
    // MODIFIES g
    private void drawH(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(1,0,entity.getHealth(),10);

    }
}
