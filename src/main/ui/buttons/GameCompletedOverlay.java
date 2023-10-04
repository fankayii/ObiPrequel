package ui.buttons;

import model.GameState;
import ui.LoadImg;
import ui.gamestates.Playing;
import ui.main.SWGame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 GUI screen for completing the game
 */

public class GameCompletedOverlay {
    private Playing playing;
    private BufferedImage img;
    private MenuButton quit;
    private int imgX;
    private int imgY;
    private int imgW;
    private int imgH;

    // Constructor, create a new overlay with the playing
    public GameCompletedOverlay(Playing playing) {
        this.playing = playing;
        createImg();
        createButtons();
    }

    // EFFECTS: create the button on the screen
    // MODIFIES: this
    private void createButtons() {
        quit = new MenuButton(SWGame.GAME_WIDTH / 2, (int) (270 * SWGame.SCALE), 2, GameState.MENU);
    }

    // EFFECTS: load the images and set value
    // MODIFIES: this
    private void createImg() {
        img = LoadImg.getSpriteAtlas(LoadImg.GAME_COMPLETED);
        imgW = (int) (img.getWidth() * SWGame.SCALE);
        imgH = (int) (img.getHeight() * SWGame.SCALE);
        imgX = SWGame.GAME_WIDTH / 2 - imgW / 2;
        imgY = (int) (100 * SWGame.SCALE);

    }

    // EFFECTS: draw the overlay and buttons
    // MODIFIES: g
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, SWGame.GAME_WIDTH, SWGame.GAME_HEIGHT);

        g.drawImage(img, imgX, imgY, imgW, imgH, null);
        quit.draw(g);
    }

    // EFFECTS: update the button
    // MODIFIES: this
    public void update() {
        quit.update();
    }

    // EFFECTS: check if the mouse hover over the buttons
    private boolean isIn(MenuButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    // EFFECTS: set quit if the mouse is in the bounds of the button
    // REQUIRES: isIn(quit,e)
    // MODIFIES: this
    public void mouseMoved(MouseEvent e) {
        quit.setMouseOver(false);
        if (isIn(quit, e)) {
            quit.setMouseOver(true);

        }

    }

    // EFFECTS: update the game state if mouse click
    // MODIFIES: this
    public void mouseReleased(MouseEvent e) {
        if (isIn(quit, e)) {
            if (quit.isMousePressed()) {
                playing.resetAll();
                playing.resetGameCompleted();
                playing.setGamestate(GameState.MENU);

            }
        }

        quit.resetBools();

    }

    // EFFECTS: set the button if the mouse is pressed
    // MODIFIES: this
    public void mousePressed(MouseEvent e) {
        if (isIn(quit, e)) {
            quit.setMousePressed(true);
        }


    }
}
