package ui.inputs;

import model.GameState;
import ui.main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 Handle keyboard inputs
 */
public class KeyboardInputs implements KeyListener {
    private GamePanel gamePanel;

    // Constructor, create a new KeyboardInputs with given game panel
    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    // EFFECTS: update the state of game according to KeyEvent
    // REQUIRES: GameState.getState() == GameState.PLAYING || GameState.getState() == GameState.MENU
    // MODIFIES: this
    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.getState()) {
            case MENU:
                gamePanel.getGame().getMenu().keyReleased(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyReleased(e);
                break;
            default:
                break;

        }
    }

    // EFFECTS: update the state of game according to KeyEvent
    // REQUIRES: GameState.getState() == GameState.PLAYING || GameState.getState() == GameState.MENU
    // MODIFIES: this
    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.getState()) {
            case MENU:
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            default:
                break;
        }
    }
}
