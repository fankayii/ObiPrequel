package ui.sprites;

import model.people.EnemyManager;
import model.people.Stormtrooper;
import ui.LoadImg;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent sprite for storm trooper
 */
public class TKsprite {
    private List<EnemySprite> tks = new ArrayList<EnemySprite>();
    private ArrayList<Stormtrooper> tk;
    private BufferedImage[][] animations;
    private EnemyManager em;

    // Constructor, create a tk sprite with given em
    public TKsprite(EnemyManager em) {
        setImgArr(LoadImg.getSpriteAtlas(LoadImg.TK_SPRITE),9,5,72, 45);
        this.em = em;

    }

    // EFFECTS: load the images to animation
    // MODIFIES: this
    private void setImgArr(BufferedImage atlas, int sizeX, int sizeY, int spriteW, int spriteH) {
        animations = new BufferedImage[sizeY][sizeX];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);

            }

        }




    }





    // EFFECTS: load the given list t
    // MODIFIES: this
    public void init(ArrayList<Stormtrooper> t) {
        tks.clear();
        for (Stormtrooper tk: t) {
            EnemySprite e = new EnemySprite(tk);
            this.tks.add(e);
        }
    }

    // EFFECTS: update every enemysprite in the list
    // MODIFIES: this
    public void update() {
        for (EnemySprite e: tks) {
            e.updateAnimationTick();
            e.setAnimations();

        }
    }

    // EFFECTS: draw the image with the given g, animations
    // MODIFIES: g
    public void render(Graphics g, int lvlOffset, BufferedImage[][] animations) {
        int index = 20;


        for (EnemySprite e: tks) {

            if (e.getEntity().isAlive()) {
                index += 10;
                e.draw(g,lvlOffset,animations);
                e.drawHitbox(g,lvlOffset);

                e.drawHealth(g,index);

            }

        }





    }


}
