package ui.main;

import model.EventLog;
import model.GameState;
import model.items.Bacta;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonWriter;
import ui.gamestates.Menu;
import ui.gamestates.Playing;
import model.Event;

import java.awt.*;
import java.util.Iterator;

/**
 The representation of the game
 */
public class SWGame implements Runnable {
    private JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/game.json";
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int fpsSET = 120;
    private final int upsSET = 200;


    private Playing playing;
    private Menu menu;
    private double deltaU = 0;
    private double deltaF = 0;

    public static final int TILES_DEFAULT_SIZE = 32;
    public static final float SCALE = 2f;
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    private int frames = 0;
    private int updates = 0;

    private double timePerFrame = 1000000000.0 / fpsSET;
    private double timePerUpdate = 1000000000.0 / upsSET;


    // Constructor
    public SWGame() {
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

        startGameLoop();


    }

    // EFFECTS: initialise the state of the game and jsonWriter
    // MODIFIES: this
    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    // EFFECTS: start the game loop
    // MODIFIES: this
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // EFFECTS: update the game behaviour according to the state
    // MODIFIES: this
    public void update() {
        switch (GameState.getState()) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case QUIT:
            default:
                EventLog.getInstance().toConsole();
                System.exit(0);
                break;

        }
    }

    // EFFECTS: draw the game with g
    // MODIFIES: this. g
    // REQUIRES: GameState().getState() == GameState.MENU || GameState().getState() == GameState.PLAYING
    public void render(Graphics g) {
        switch (GameState.getState()) {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            default:
                break;
        }
    }


    // EFFECTS: run the game
    // MODIFIES: this
    @Override
    public void run() {



        long previousTime = System.nanoTime();
        long lastCheck = System.currentTimeMillis();

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                frames = 0;
                updates = 0;

            }



        }

    }

    // EFFECTS: write the game status to json object and return
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Level",playing.getLevelManager().getLevelIndex());
        json.put("Position",playing.getPlayer().getPos());
        json.put("Health",playing.getPlayer().getHealth());
        json.put("Bactas",getBactas());
        return json;
    }

    // EFFECTS: write the bactas list to json array object and return
    public JSONArray getBactas() {
        JSONArray jsonArray = new JSONArray();
        for (Bacta b: playing.getPlayer().getBag()) {
            jsonArray.put(b.toJson());
        }
        return jsonArray;

    }

    // EFFECTS: reset the player direction
    // REQUIRES:GameState.getState() == GameState.PLAYING
    // MODIFIES: this
    public void windowFocusLost() {
        if (GameState.getState() == GameState.PLAYING) {
            playing.getPlayer().resetDirBooleans();
        }

    }

    // getters
    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public String print() {
        StringBuilder res = new StringBuilder();
        for (Iterator<Event> e = EventLog.getInstance().iterator(); e.hasNext();) {
            res.append(e.next());
        }

        return res.toString();
    }
}
