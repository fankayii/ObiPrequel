package ui.inputs;

import model.GameState;
import ui.main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 Handle the mouse inputs for the game
 */
public class MouseInputs implements MouseListener, MouseMotionListener {
    private GamePanel gamePanel;

    // Constructor, create a new MouseInputs with given game panel
    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    // EFFECT: update the game state according to mouse event
    // REQUIRES: GameState.getState() == GameState.PLAYING || GameState.getState() == GameState.MENU
    // MODIFIES: this
    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.getState()) {
            case MENU:
                gamePanel.getGame().getMenu().mouseMoved(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseMoved(e);
                break;
            default:
                break;

        }

    }

    // EFFECT: update the game state according to mouse event
    // REQUIRES: GameState.getState() == GameState.PLAYING
    // MODIFIES: this
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.getState()) {
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseClicked(e);
                break;
            default:
                break;

        }

    }

    // EFFECT: update the game state according to mouse event
    // GameState.getState() == GameState.PLAYING || GameState.getState() == GameState.MENU
    // MODIFIES: this
    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.getState()) {
            case MENU:
                gamePanel.getGame().getMenu().mousePressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mousePressed(e);
                break;
            default:
                break;

        }

    }

    // EFFECT: update the game state according to mouse event
    // REQUIRES: GameState.getState() == GameState.PLAYING || GameState.getState() == GameState.MENU
    // MODIFIES: this
    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.getState()) {
            case MENU:
                gamePanel.getGame().getMenu().mouseReleased(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseReleased(e);
                break;
            default:
                break;

        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}
