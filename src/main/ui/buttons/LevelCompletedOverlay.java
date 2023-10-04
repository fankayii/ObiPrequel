package ui.buttons;

import model.GameState;
import ui.LoadImg;
import ui.gamestates.Playing;
import ui.main.SWGame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


/**
 Represent the screen for level complete
 */
public class LevelCompletedOverlay {
    private int sizeURM = 112;
    private Playing playing;
    private UrmButton menu;
    private UrmButton next;
    private BufferedImage img;
    private int bgX;
    private int bgY;
    private int bgW;
    private int bgH;

    // Constructor, create a new overlay with the playing
    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        initImg();
        initButtons();
    }

    // EFFECTS: create the button on the screen
    // MODIFIES: this
    private void initButtons() {
        int menuX = (int) (330 * SWGame.SCALE);
        int nextX = (int) (445 * SWGame.SCALE);
        int y = (int) (195 * SWGame.SCALE);
        next = new UrmButton(nextX, y, sizeURM, sizeURM, 0);
        menu = new UrmButton(menuX, y, sizeURM, sizeURM, 2);
    }

    // EFFECTS: load the images and set value
    // MODIFIES: this
    private void initImg() {
        img = LoadImg.getSpriteAtlas(LoadImg.COMPLETED_IMG);
        bgW = (int) (img.getWidth() * SWGame.SCALE);
        bgH = (int) (img.getHeight() * SWGame.SCALE);
        bgX = SWGame.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (75 * SWGame.SCALE);
    }

    // EFFECTS: draw the overlay and buttons
    // MODIFIES: g
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, SWGame.GAME_WIDTH, SWGame.GAME_HEIGHT);

        g.drawImage(img, bgX, bgY, bgW, bgH, null);
        next.draw(g);
        menu.draw(g);
    }


    // EFFECTS: update the button behaviour
    // MODIFIES: this
    public void update() {
        next.update();
        menu.update();
    }

    // EFFECTS: check if the mouse hover over the buttons
    private boolean isIn(UrmButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    // EFFECTS: update the button behaviour if the mouse is in the bounds of the button
    // REQUIRES: isIn(quit,e)
    // MODIFIES: this
    public void mouseMoved(MouseEvent e) {
        next.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(menu, e)) {
            menu.setMouseOver(true);
        } else if (isIn(next, e)) {
            next.setMouseOver(true);
        }

    }

    // EFFECTS: update the game state if mouse click
    // MODIFIES: this
    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) {
            if (menu.isMousePressed()) {
                playing.resetAll();
                playing.setGamestate(GameState.MENU);
            }
        } else if (isIn(next, e)) {
            if (next.isMousePressed()) {
                playing.loadNextLevel();
                //playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex());
            }
        }


        menu.resetBools();
        next.resetBools();
    }

    // EFFECTS: set the button if the mouse is pressed
    // MODIFIES: this
    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e)) {
            menu.setMousePressed(true);
        } else if (isIn(next, e)) {
            next.setMousePressed(true);
        }

    }
}
