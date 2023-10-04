package ui.buttons;

import model.GameState;
import ui.LoadImg;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 Representing the button for the menu
 */
public class MenuButton {
    private int xpos;
    private int ypos;
    private int rowIndex;
    private int index;
    private int widthB = 280;
    private int heightB = 112;
    private int offsetCenterX = 280 / 2;
    private int widthBD = 140;
    private int heightBD = 56;
    private GameState stateB;
    private BufferedImage[] imgs;
    private boolean mouseOver;
    private boolean mousePressed;
    private Rectangle bounds;

    // Constructor, create a new menu button with x, y coordinates, rowIndex and game state
    public MenuButton(int xpos, int ypos, int rowIndex, GameState state) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.rowIndex = rowIndex;
        this.stateB = state;
        loadImgs();
        initBounds();
    }

    // EFFECTS: set the bounds for the button
    // MODIFIES: this
    private void initBounds() {
        bounds = new Rectangle(xpos - offsetCenterX, ypos, widthB, heightB);

    }

    // EFFECTS: load the images for the buttons
    // MODIFIES: this
    private void loadImgs() {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadImg.getSpriteAtlas(LoadImg.MENU_BUTTONS);
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i * widthBD, rowIndex * heightBD, widthBD, heightBD);
        }

    }

    // EFFECTS: draw the overlay and buttons
    // MODIFIES: g
    public void draw(Graphics g) {
        g.drawImage(imgs[index], xpos - offsetCenterX, ypos, widthB, heightB, null);
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

    // EFFECTS: set the state of the game according to the button
    public void applyGamestate() {
        GameState.setState(stateB);
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

    public Rectangle getBounds() {
        return bounds;
    }

    public GameState getState() {
        return stateB;

    }

    // setters
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }





}
