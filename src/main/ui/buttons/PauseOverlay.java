package ui.buttons;

import model.EventLog;
import ui.LoadImg;
import ui.gamestates.Playing;
import ui.main.SWGame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 Representing the pause screen
 */

public class PauseOverlay {
    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX;
    private int bgY;
    private int bgW;
    private int bgH;
    private UrmButton menuB;
    private UrmButton replayB;
    private UrmButton unpauseB;
    private int sizeURM = 112;

    // Constructor, create a new overlay with the playing
    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        createUrmButtons();


    }


    // EFFECTS: create the button for pasue screen
    // MODIFIES: this
    private void createUrmButtons() {
        int menuX = (int) (313 * SWGame.SCALE);
        int replayX = (int) (387 * SWGame.SCALE);
        int unpauseX = (int) (462 * SWGame.SCALE);
        int by = (int) (325 * SWGame.SCALE);

        menuB = new UrmButton(menuX, by, sizeURM, sizeURM, 2);
        replayB = new UrmButton(replayX, by, sizeURM, sizeURM, 1);
        unpauseB = new UrmButton(unpauseX, by, sizeURM, sizeURM, 0);

    }

    // EFFECTS: load the image for pause screen and set the value
    // MODIFIES: this
    private void loadBackground() {
        backgroundImg = LoadImg.getSpriteAtlas(LoadImg.PAUSE_BACKGROUND);
        bgW = (int) (backgroundImg.getWidth() * SWGame.SCALE);
        bgH = (int) (backgroundImg.getHeight() * SWGame.SCALE);
        bgX = SWGame.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (25 * SWGame.SCALE);

    }

    // EFFECTS: update the buttons' behaviours
    // MODIFIES: this
    public void update() {
        menuB.update();
        replayB.update();
        unpauseB.update();


    }

    // EFFECTS: draw the overlay and buttons
    // MODIFIES: g
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);


    }

    // EFFECTS: set the button if the mouse is pressed
    // MODIFIES: this
    // REQUIRES: isIn(e,buttons)
    public void mousePressed(MouseEvent e) {

        if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        } else if (isIn(e, replayB)) {
            replayB.setMousePressed(true);
        } else if (isIn(e, unpauseB)) {
            unpauseB.setMousePressed(true);
        }

    }

    // EFFECTS: update the game state if mouse click
    // MODIFIES: this
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuB)) {
            if (menuB.isMousePressed()) {
                System.out.println(EventLog.getInstance().toConsole());
                System.exit(0);

            }
        } else if (isIn(e, replayB)) {
            if (replayB.isMousePressed()) {
                playing.save();
                playing.unpauseGame();
            }
        } else if (isIn(e, unpauseB)) {
            if (unpauseB.isMousePressed()) {
                playing.unpauseGame();
            }
        }
        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();

    }

    // EFFECTS: update buttons' behaviours  if the mouse is in the bounds of the button
    // REQUIRES: isIn(quit,e)
    // MODIFIES: this
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        if (isIn(e, menuB)) {
            menuB.setMouseOver(true);
        } else if (isIn(e, replayB)) {
            replayB.setMouseOver(true);
        } else if (isIn(e, unpauseB)) {
            unpauseB.setMouseOver(true);
        }


    }

    // EFFECTS: check if the mouse hover over the buttons
    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
