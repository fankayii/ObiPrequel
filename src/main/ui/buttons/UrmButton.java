package ui.buttons;

import ui.LoadImg;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 Represent the button for the pause screen
 */

public class UrmButton extends PauseButton {
    private BufferedImage[] imgs;
    private int rowIndex;
    private int index;
    private boolean mouseOver;
    private boolean mousePressed;
    private int sizeUD = 56;
    private int sizeURM = 112;

    // Constructor, create a pause screen button with x,y coordinates, width, height and row index
    public UrmButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImgs();
    }

    // EFFECTS: load the images for the buttons
    // MODIFIES: this
    private void loadImgs() {
        BufferedImage temp = LoadImg.getSpriteAtlas(LoadImg.URM_BUTTONS);
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i * sizeUD, rowIndex * sizeUD, sizeUD, sizeUD);
        }


    }

    // EFFECTS: update the index for button image based on the mouse event
    // MODIFIES: this
    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
        if (mousePressed) {
            index = 2;
        }


    }

    // EFFECTS: draw the overlay and buttons
    // MODIFIES: g
    public void draw(Graphics g) {
        g.drawImage(imgs[index], posx, posy, sizeURM, sizeURM, null);
    }

    // EFFECTS: reset the fields to false
    // MODIFIES: this
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    // getters
    public boolean isMouseOver() {
        return mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    // setters
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
