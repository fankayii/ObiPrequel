package ui.gamestates;

import model.EntityState;
import model.items.Bacta;
import ui.Level;
import model.items.Bullet;
import model.people.EnemyManager;
import model.people.Player;
import model.people.Stormtrooper;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.LoadImg;
import ui.buttons.GameCompletedOverlay;
import ui.buttons.GameOverOverlay;
import ui.buttons.LevelCompletedOverlay;
import ui.buttons.PauseOverlay;
import ui.main.SWGame;
import ui.sprites.PlayerSprite;
import ui.sprites.TKsprite;
import ui.LevelManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 Representing the playing state of the game
 */

public class Playing extends State implements StateMethods {
    private Player player;
    private static final String JSON_STORE = "./data/game.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    float enSpeedX;
    private List<Bullet> bullets = new ArrayList<>();

    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private GameCompletedOverlay gameCompletedOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private boolean gameOver;
    private boolean lvlCompleted;
    private boolean gameCompleted;
    private boolean playerDying;
    private boolean paused = false;
    private int lvlOffsetX;
    private int leftBorder = (int) (0.25 * SWGame.GAME_WIDTH);
    private int rightBorder = (int) (0.75 * SWGame.GAME_WIDTH);
    private BufferedImage[][] animations;
    private ArrayList<Stormtrooper> tks = new ArrayList<>();
    private ArrayList<Bacta> bactas = new ArrayList<>();
    private TKsprite sts;
    private int maxLvlOffsetX;
    private BufferedImage backgroundImg;
    private float playerSpeedX;
    private PlayerSprite ps;


    // Constructor, create a new playing state with the game
    public Playing(SWGame game) {
        super(game);
        initClasses();

        backgroundImg = LoadImg.getSpriteAtlas(LoadImg.PLAYING_BG_IMG);


        calcLvlOffset();
        jsonWriter = new JsonWriter(JSON_STORE);
        loadStartLevel();
        setImgArr();



    }

    // EFFECTS: load the enemy animation
    // MODIFIES: this
    public void setImgArr() {
        animations = new BufferedImage[5][9];
        BufferedImage atlas = LoadImg.getSpriteAtlas(LoadImg.TK_SPRITE);
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = atlas.getSubimage(i * 72, j * 45, 72, 45);

            }

        }


    }

    // EFFECTS: load the next level
    // MODIFIES: this
    public void loadNextLevel() {
        levelManager.setLevelIndex(levelManager.getLevelIndex() + 1);
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        resetAll();
        loadBactas();
    }


    // EFFECTS: load the start level
    // MODIFIES: this
    private void loadStartLevel() {
        loadBactas();
    }

    // EFFECTS: calculate the level offset value
    // MODIFIES: this
    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    // EFFECTS: initialise the elements of game
    // MODIFIES: this
    private void initClasses() {
        jsonReader = new JsonReader(JSON_STORE);
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager();
        sts = new TKsprite(enemyManager);

        player = new Player(0,0, (int) (64 * SWGame.SCALE), (int) (40 * SWGame.SCALE));
        checkFirst();
        ps = new PlayerSprite(player);

        ps.loadAnimations();
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
        gameCompletedOverlay = new GameCompletedOverlay(this);
        loadEnemies();
        sts.init(levelManager.getCurrentLevel().getTks());






    }


    // EFFECTS: update the game
    // MODIFIES: this
    @Override
    public void update() {
        if (paused) {
            pauseOverlay.update();
        } else if (lvlCompleted) {
            levelCompletedOverlay.update();
        } else if (gameCompleted) {
            gameCompletedOverlay.update();
        } else if (gameOver) {
            gameOverOverlay.update();
        } else if (playerDying) {
            player.update();
        } else {
            playerUpdate();
            checkPlayerAlive();
            checkPlayerAttack();
            checkAdd();
            ps.updateAnimationTick(levelManager.getCurrentLevel());
            ps.setAnimations();
            updateTKs();
            sts.update();
            handleBullets();
            checkCloseToBorder();
        }

    }

    // EFFECTS: check if player hit any enemy
    // MODIFIES: this
    public void checkPlayerAttack() {
        if (player.getState() == EntityState.ATTACK || player.isAttacking()) {
            player.changeState(EntityState.ATTACK);
            if (!player.getAttackCheck()) {
                player.setAttack(true);
                enemyManager.checkEnemyHit(player.getAttackBox());
            }
        }


    }

    // EFFECTS: update the bactas list with given list
    // MODIFIES: this
    public void updateBactas(ArrayList<Bacta> arr) {
        for (Bacta b: bactas) {
            if (arr.contains(b)) {
                b.setAlive();
            }
        }
    }

    // EFFECTS: set the game to over if player is dead
    // MODIFIES: this
    // REQUIRES: !player.isAlive()
    public void checkPlayerAlive() {
        if (!player.isAlive()) {
            setPlayerDying();
            setGameOver(true);
        }
    }

    // EFFECTS: load the bactas list for the current level
    // MODIFIES: this
    public void loadBactas() {
        bactas = levelManager.getCurrentLevel().getBactas();
    }

    // EFFECTS: load the enemy list for the current level
    // MODIFIES: this
    public void loadEnemies() {
        tks = levelManager.getCurrentLevel().getLeve().getTks();
        enemyManager.loadEnemies(tks);

    }

    // EFFECTS: set player in air if the player is not on ground
    // MODIFIES: this
    // REQUIRES: !player.getInAir() && !levelManager.getCurrentLevel().checkOnGround(player.getHitbox())
    public void checkPlayerInAir() {
        if (!player.getInAir() && !levelManager.getCurrentLevel().checkOnGround(player.getHitbox())) {

            player.setInAir(true);

        }

    }

    // EFFECTS: check if the player on ground
    // MODIFIES: this
    public void checkFirst() {
        if (!levelManager.getCurrentLevel().checkOnGround(player.getHitbox())) {
            player.setInAir();
        }
    }

    // EFFECTS: update the player behaviour
    // MODIFIES: this
    public void playerUpdate() {

        Rectangle2D.Float playerHitBox;
        Level lvl = levelManager.getCurrentLevel();
        player.update();
        if (!player.isAlive()) {
            return;
        }
        player.updateAttackBox();
        playerSpeedX = player.movePlayer();
        if (playerSpeedX == -10) {
            return;
        }
        checkPlayerInAir();
        if (player.getInAir()) {
            playerHitBox = player.getHitbox();
            player.moveInAir(lvl.canMove(playerHitBox.x,
                    playerHitBox.y + player.getAirSpeed(), playerHitBox.width,playerHitBox.height));
            checkFirst();
        }
        playerHitBox = player.getHitbox();
        player.updateXPos(playerSpeedX,lvl.canMove(playerHitBox.x + playerSpeedX,
                playerHitBox.y,playerHitBox.width,playerHitBox.height));
        player.setMoving();

    }


    // EFFECTS: update the state of bullets
    // MODIFIES: this
    public void handleBullets() {
        for (Bullet b: bullets) {
            if (!b.isHit()) {
                b.updatePos();
            }
            if (b.getHitbox().intersects(player.getHitbox())) {
                player.takeDamage(1);
                b.setAlive();
            } else if (levelManager.getCurrentLevel().checkSolid(b.getHitbox().x + b.getHitbox().width / 2,
                    b.getHitbox().y + b.getHitbox().height / 2)) {
                b.setAlive();
            }
        }
        bullets.removeIf(Bullet::isHit);

    }


    // EFFECTS: update the state of storm troopers and set the level completed if all is dead
    // MODIFIES: this
    public void updateTKs() {
        boolean isAnyActive = false;
        for (Stormtrooper c : levelManager.getCurrentLevel().getTks()) {
            if (c.isAlive()) {
                tkUpdate(c);
                isAnyActive = true;
                if (c.getState() == EntityState.ATTACK && c.getTick() % 100 == 0) {
                    bullets = c.shootBullet(bullets);
                }

            }


        }
        if (!isAnyActive) {
            setLevelCompleted(true);
        }

    }

    // EFFECTS: update the behaviour of the given storm trooper
    // MODIFIES: tk
    public void tkUpdate(Stormtrooper tk) {
        tk.update();
        Level lvl = levelManager.getCurrentLevel();
        Rectangle2D.Float hitbox = tk.getHitbox();
        if (tk.getInAir()) {
            tk.updateInAir(lvl.canMove(hitbox.x,hitbox.y + tk.getAirSpeed(),hitbox.width,hitbox.height));
        } else {
            if (tk.getState() == EntityState.IDLE) {
                if (lvl.checkOnGround(hitbox)) {
                    tk.changeState(EntityState.RUNNING);
                } else {
                    tk.setInAir();
                }
            } else if (tk.getState() == EntityState.RUNNING) {
                checkTkAttack(tk);
                enSpeedX = tk.moveTK();
                if (lvl.canMove(hitbox.x + enSpeedX,hitbox.y,hitbox.width,hitbox.height)
                        && lvl.isFloor(hitbox,enSpeedX)) {
                    hitbox.x += enSpeedX;
                    return;
                }
                tk.changeWalkDir();
            }
        }
    }

    // EFFECTS: check if the given storm trooper attacks the player
    // MODIFIES: tk
    public void checkTkAttack(Stormtrooper tk) {
        Level lvl = levelManager.getCurrentLevel();
        int playerTileY = (int) (player.getHitbox().y / SWGame.TILES_SIZE);
        if (playerTileY == tk.getTileY() && tk.inRange(player)
                && lvl.checkSight(tk.getHitbox(),player.getHitbox(),tk.getTileY())) {
            tk.turnTowardsPlayer(player);
            tk.checkPlayer2Attack(player);
        }

    }

    // EFFECTS: draw the bactas list
    // MODIFIES: g
    // REQUIRES: !b.isHit()
    public void drawBactas(Graphics g, int lvlOffsetX) {
        try {
            for (Bacta b : bactas) {
                if (!b.isHit()) {

                    g.drawImage(LoadImg.getSpriteAtlas(LoadImg.BACTA),
                            (int) (b.getHitbox().x - lvlOffsetX), (int) (b.getHitbox().y), 30, 30, null);
                    drawHitbox(g, lvlOffsetX, b.getHitbox());

                }
            }
        } catch (ConcurrentModificationException e) {
            //
        }

    }

    // EFFECTS: draw the list of bactas from player's bag on the screen
    // MODIFIES: g
    public void showBactas(Graphics g) {
        int index = 0;
        for (Bacta b: player.getBag()) {

            g.drawImage(LoadImg.getSpriteAtlas(LoadImg.BACTA),index,100,30,30,null);
            index += 30;

        }
    }


    // EFFECTS: update the level
    // MODIFIES: this
    public void updateLvl(Level lvl) {
        checkFirst();
        loadEnemies();
        sts.init(levelManager.getCurrentLevel().getTks());
    }


    // EFFECTS: calculate the lvl offset if player moves close to the border
    // MODIFIES: this
    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - lvlOffsetX;

        if (diff > rightBorder) {
            lvlOffsetX += diff - rightBorder;
        } else if (diff < leftBorder) {
            lvlOffsetX += diff - leftBorder;
        }
        lvlOffsetX = Math.max(Math.min(lvlOffsetX, maxLvlOffsetX), 0);
    }


    // EFFECTS: draw the game
    // MODIFIES: g

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, SWGame.GAME_WIDTH, SWGame.GAME_HEIGHT, null);
        levelManager.draw(g, lvlOffsetX);
        ps.render(g,lvlOffsetX);

        ps.drawHitbox(g,lvlOffsetX);
        ps.drawAttackbox(g,lvlOffsetX);
        sts.render(g,lvlOffsetX,animations);
        drawBactas(g,lvlOffsetX);
        ps.drawHealth(g);
        drawBullet(g,lvlOffsetX);
        showBactas(g);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, SWGame.GAME_WIDTH, SWGame.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameOver) {
            gameOverOverlay.draw(g);
        } else if (lvlCompleted) {
            levelCompletedOverlay.draw(g);
        } else if (gameCompleted) {
            gameCompletedOverlay.draw(g);
        }

    }




    // EFFECTS: reset the elements of the game
    // MODIFIES: this
    public void resetAll() {
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        playerDying = false;
        player.resetAll();

        enemyManager.resetAllEnemies();
        loadEnemies();
        sts.init(enemyManager.getTKS());
    }

    // EFFECTS: add the bacta to player's bag if intersects
    // MODIFIES: this
    // REQUIRES: b.getHitbox().intersects(player.getHitbox())
    public void checkAdd() {
        for (Bacta b: bactas) {
            if (b.getHitbox().intersects(player.getHitbox())) {
                player.addBacta(b);
                b.setAlive();
            }
        }
        bactas.removeIf(Bacta::isHit);
    }

    // EFFECTS: write the game to json
    // MODIFIES: this
    public void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(getGame());
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.exit(0);
        }
    }


    // EFFECTS: draw the bullets
    // MODIFIES: g
    // REQUIRES: !b.isHit()
    public void drawBullet(Graphics g, int lvlOffsetX) {
        try {
            for (Bullet b: bullets) {
                if (!b.isHit()) {
                    g.drawImage(LoadImg.getSpriteAtlas(LoadImg.bullet),
                            (int) (b.getHitbox().x - lvlOffsetX), (int) (b.getHitbox().y), 30, 30, null);
                    drawHitbox(g,lvlOffsetX,b.getHitbox());

                }
            }

        } catch (ConcurrentModificationException e) {
            //aSystem.out.println(e.getMessage());
        }
    }

    // EFFECTS: draw the given hitbox
    // MODIFIES: g
    public void drawHitbox(Graphics g, int lvlOffsetX, Rectangle2D.Float hitbox) {
        g.setColor(Color.BLUE);
        g.drawRect((int) hitbox.x - lvlOffsetX, (int) hitbox.y + 5, (int) hitbox.width, (int) hitbox.height);

    }

    // EFFECTS: check if all level is completed
    // MODIFIES: this
    public void setLevelCompleted(boolean levelCompleted) {
        if (levelManager.getLevelIndex() + 1 >= levelManager.getAmountOfLevels()) {
            // No more levels
            gameCompleted = true;
            levelManager.setLevelIndex(0);
            levelManager.loadNextLevel();
            resetAll();

            return;
        }
        this.lvlCompleted = levelCompleted;
    }

    // setter
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    // EFFECTS: set player to attack
    // MODIFIES: this
    // REQUIRES: !gameOver && e.getButton() == MouseEvent.BUTTON1
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                player.setAttacking(true);
            }
        }
    }

    // EFFECTS: update player's behaviours according to the KeyEvent
    // MODIFIES: this
    // REQUIRES: !gameOver && !gameCompleted && !lvlCompleted
    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver && !gameCompleted && !lvlCompleted) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_B:
                    player.useItem();
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
            }
        }

    }

    // EFFECTS: set the player state accroding to the KeyEvent
    // MODIFIES: this
    // REQUIRES: !gameOver && !gameCompleted && !lvlCompleted
    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver && !gameCompleted && !lvlCompleted) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
        }
    }

    // EFFECTS: update the overlay's behaviour according to the MouseEvent
    // MODIFIES: this
    @Override
    public void mousePressed(MouseEvent e) {
        if (gameOver) {
            gameOverOverlay.mousePressed(e);
        } else if (paused) {
            pauseOverlay.mousePressed(e);
        } else if (lvlCompleted) {
            levelCompletedOverlay.mousePressed(e);
        } else if (gameCompleted) {
            gameCompletedOverlay.mousePressed(e);
        }
    }

    // EFFECTS: update the overlay's behaviour according to the MouseEvent
    // MODIFIES: this
    @Override
    public void mouseReleased(MouseEvent e) {
        if (gameOver) {
            gameOverOverlay.mouseReleased(e);
        } else if (paused) {
            pauseOverlay.mouseReleased(e);
        } else if (lvlCompleted) {
            levelCompletedOverlay.mouseReleased(e);
        } else if (gameCompleted) {
            gameCompletedOverlay.mouseReleased(e);
        }
    }

    // EFFECTS: update the overlay's behaviour according to the MouseEvent
    // MODIFIES: this
    @Override
    public void mouseMoved(MouseEvent e) {
        if (gameOver) {
            gameOverOverlay.mouseMoved(e);
        } else if (paused) {
            pauseOverlay.mouseMoved(e);
        } else if (lvlCompleted) {
            levelCompletedOverlay.mouseMoved(e);
        } else if (gameCompleted) {
            gameCompletedOverlay.mouseMoved(e);
        }

    }

    // setters
    public void setPlayerDying() {
        playerDying = true;
    }

    public void unpauseGame() {
        paused = false;
    }

    public void resetGameCompleted() {
        gameCompleted = false;
    }

    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffsetX = lvlOffset;
    }

    // getters
    public Player getPlayer() {
        return player;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }


}
