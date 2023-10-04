package ui.gamestates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 Interface for state
 */
public interface StateMethods {

    // EFFECTS: update behaviour
    public void update();

    // EFFECTS: draw graphics with g
    public void draw(Graphics g);

    // EFFECTS: update according to mouse event
    public void mouseClicked(MouseEvent e);

    // EFFECTS: update according to mouse event
    public void mousePressed(MouseEvent e);

    // EFFECTS: update according to mouse event
    public void mouseReleased(MouseEvent e);

    // EFFECTS: update according to mouse event
    public void mouseMoved(MouseEvent e);

    // EFFECTS: update according to key event
    public void keyPressed(KeyEvent e);

    // EFFECTS: update according to key event
    public void keyReleased(KeyEvent e);
}
