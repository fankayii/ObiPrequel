package ui.buttons;

import java.awt.*;
/**
 Representing the button for the pause screen
 */

public class PauseButton {
    protected int posx;
    protected int posy;
    protected int width;
    protected int height;
    protected Rectangle bounds;


    // Constructor, create a new pause button with x,y coordinate, width and height
    public PauseButton(int posx, int y, int width, int height) {
        this.posx = posx;
        this.posy = y;
        this.width = width;
        this.height = height;
        createBounds();
    }

    // EFFECTS: set the bounds for the button
    // MODIFIES: this
    private void createBounds() {
        bounds = new Rectangle(posx, posy, width, height);
    }

    // getters
    public int getX() {
        return posx;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getY() {
        return posy;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    // setters
    public void setX(int x) {
        this.posx = x;
    }

    public void setY(int y) {
        this.posy = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }








}
