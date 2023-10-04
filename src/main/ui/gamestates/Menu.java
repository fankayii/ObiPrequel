package ui.gamestates;

import model.GameState;
import model.items.Bacta;
import persistence.JsonReader;
import ui.LoadImg;
import ui.buttons.MenuButton;
import ui.LevelManager;
import ui.main.SWGame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 Represent the menu state of the game
 */
public class Menu extends State implements StateMethods {
    private MenuButton[] buttons = new MenuButton[3];
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/game.json";
    private BufferedImage backgroundImg;
    private BufferedImage backgroundImgPink;
    private int menuX;
    private int menuY;
    private int menuWidth;
    private int menuHeight;

    // Create a new menu with the given game
    public Menu(SWGame game) {
        super(game);
        loadButtons();
        loadBackground();
        backgroundImgPink = LoadImg.getSpriteAtlas(LoadImg.MENU_BACKGROUND_IMG);
        jsonReader = new JsonReader(JSON_STORE);

    }

    // EFFECTS: load the images for menu and set the values
    // MODIFIES: this
    private void loadBackground() {
        backgroundImg = LoadImg.getSpriteAtlas(LoadImg.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImg.getWidth() * SWGame.SCALE);
        menuHeight = (int) (backgroundImg.getHeight() * SWGame.SCALE);
        menuX = SWGame.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (45 * SWGame.SCALE);

    }

    // EFFECTS: create new buttons for menu
    // MODIFIES: this
    private void loadButtons() {
        buttons[0] = new MenuButton(SWGame.GAME_WIDTH / 2, (int) (150 * SWGame.SCALE), 0, GameState.PLAYING);
        buttons[1] = new MenuButton(SWGame.GAME_WIDTH / 2, (int) (220 * SWGame.SCALE), 1, GameState.LOAD);
        buttons[2] = new MenuButton(SWGame.GAME_WIDTH / 2, (int) (290 * SWGame.SCALE), 2, GameState.QUIT);
    }

    // EFFECTS: update the buttons' behaviours
    @Override
    public void update() {
        for (MenuButton mb : buttons) {
            mb.update();
        }
    }

    // EFFECTS: draw the menuand buttons
    // MODIFIES: g
    @Override
    public void draw(Graphics g) {

        g.drawImage(backgroundImgPink, 0, 0, SWGame.GAME_WIDTH, SWGame.GAME_HEIGHT, null);
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

        for (MenuButton mb : buttons) {
            mb.draw(g);
        }
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    // EFFECTS: change the button state if mouse is pressed
    // REQUIRES: isIn(e,mb)
    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
            }
        }
    }

    // EFFECTS: change the game state according to the button pressed;
    // MODIFIES: this
    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                if (mb.isMousePressed()) {
                    mb.applyGamestate();
                    if (mb.getState() == GameState.LOAD) {
                        setGamestate(GameState.PLAYING);
                        read();

                    }
                }
                break;
            }
        }
        resetButtons();
    }


    // EFFECTS: reset the button
    private void resetButtons() {
        for (MenuButton mb : buttons) {
            mb.resetBools();
        }
    }

    // EFFECTS: check the mouse move and change the button sttate
    // MODIFIES: this
    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons) {
            mb.setMouseOver(false);
        }
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }
        }
    }

    // EFFECTS: read the data from json file and set the game
    // MODIFIES: this
    public void read() {
        try {
            int health = jsonReader.readHealth();
            int levelIndex = jsonReader.readLevel();
            ArrayList<Bacta> bactas = jsonReader.readBactas();
            Point spawn = jsonReader.readPos();
            game.getPlaying().getPlayer().setSpawn(spawn);
            game.getPlaying().getPlayer().setHealth(health);
            game.getPlaying().getLevelManager().setLevelIndex(levelIndex);
            game.getPlaying().updateLvl(game.getPlaying().getLevelManager().getCurrentLevel());
            game.getPlaying().updateBactas(bactas);




        } catch (Exception e) {
            setGamestate(GameState.MENU);

        }
    }

    // EFFECTS: check the key event and set the game state
    // REQUIRES: e.getKeyCode() == KeyEvent.VK_ENTER
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            setGamestate(GameState.PLAYING);
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}
