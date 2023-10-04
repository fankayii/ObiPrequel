package ui.buttons;

import model.GameState;
import ui.LoadImg;
import ui.gamestates.Playing;
import ui.main.SWGame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 Represent the screen for game over
 */
public class GameOverOverlay {
    private Playing playing;
    private BufferedImage img;
    private int imgX;
    private int imgY;
    private int imgW;
    private int imgH;
    private UrmButton menu;
    private UrmButton play;
    private int sizeURM = 112;

    // Constructor, create a new overlay with playign
    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        createImg();
        createButtons();
    }

    // EFFECTS: create buttons for the screen
    // MODIFIES: this
    private void createButtons() {
        int menuX = (int) (335 * SWGame.SCALE);
        int playX = (int) (440 * SWGame.SCALE);
        int y = (int) (250 * SWGame.SCALE);
        play = new UrmButton(playX, y, sizeURM, sizeURM, 0);
        menu = new UrmButton(menuX, y, sizeURM, sizeURM, 2);

    }

    // EFFECTS: load the image and set the value
    // MODIFIES: this
    private void createImg() {
        img = LoadImg.getSpriteAtlas(LoadImg.DEATH_SCREEN);
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

        menu.draw(g);
        play.draw(g);
    }

    // EFFECTS: update the button behaviours
    // MODIFIES: this
    public void update() {
        menu.update();
        play.update();
    }

    // EFFECTS: check if the mouse hover over the buttons
    private boolean isIn(UrmButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    // EFFECTS: set quit if the mouse is in the bounds of the buttons
    // REQUIRES: isIn(quit,e)
    // MODIFIES: this
    public void mouseMoved(MouseEvent e) {
        play.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(menu, e)) {
            menu.setMouseOver(true);

        } else if (isIn(play, e)) {
            play.setMouseOver(true);
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
        } else if (isIn(play, e)) {
            if (play.isMousePressed()) {
                playing.resetAll();
            }

        }


        menu.resetBools();
        play.resetBools();
    }

    // EFFECTS: set the button if the mouse is pressed
    // MODIFIES: this
    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e)) {
            menu.setMousePressed(true);

        } else if (isIn(play, e)) {
            play.setMousePressed(true);
        }

    }
}
