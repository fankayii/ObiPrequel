package ui.main;

import ui.inputs.KeyboardInputs;
import ui.inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

/**
 Game Panel for the game
 */
public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private SWGame game;

    // Constructor, create a new GamePanel with given game
    public GamePanel(SWGame game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    // EFFECTS: set the panel size
    // MODIFIES: this
    private void setPanelSize() {
        Dimension size = new Dimension(SWGame.GAME_WIDTH, SWGame.GAME_HEIGHT);
        setPreferredSize(size);
    }

    // EFFECTS: draw the game with g
    // MODIFIES: this, g
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    // getters
    public SWGame getGame() {
        return game;
    }
}
