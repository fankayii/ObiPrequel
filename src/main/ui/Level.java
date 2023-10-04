package ui;

import model.items.Bacta;
import model.people.Stormtrooper;
import ui.main.SWGame;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 Represent the level of the game
 */
public class Level {
    private int[][] lvlData;
    private ArrayList<Bacta> bactas = new ArrayList<>();
    private BufferedImage img;
    private Point playerSpawn;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;

    private ArrayList<Stormtrooper> tks = new ArrayList<>();

    // Constructor, create a level with given img
    public Level(BufferedImage img) {
        this.img = img;
        lvlData = new int[img.getHeight()][img.getWidth()];
        loadLevel();
        calcLvlOffsets();
    }

    // EFFECTS: load the level from the img
    // MODIFIES: this
    private void loadLevel() {
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color c = new Color(img.getRGB(x, y));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();

                loadLevelData(red, x, y);
                loadEntities(green, x, y);
                loadObjects(blue, x, y);
            }

        }

    }

    // EFFECTS: set the index for level
    // MODIFIES: this
    private void loadLevelData(int redValue, int x, int y) {
        if (redValue >= 50) {
            lvlData[y][x] = 0;
        } else {
            lvlData[y][x] = redValue;
        }
        if (redValue == 233) {
            lvlData[y][x] = -1;
        }
    }

    // EFFECTS: add enemy to list and set player spawn point
    // MODIFIES: this
    // REQUIRES: greenValue == 0 || greenValue == 100
    private void loadEntities(int greenValue, int x, int y) {
        if (greenValue == 0) {
            tks.add(new Stormtrooper(x * SWGame.TILES_SIZE, y * SWGame.TILES_SIZE,144,64));
        }
        if (greenValue == 100) {
            playerSpawn = new Point(x * SWGame.TILES_SIZE, y * SWGame.TILES_SIZE);
        }
    }

    // EFFECTS: add bactas to list
    // MODIFIES: this
    // REQUIRES: blueValue == 0
    private void loadObjects(int blueValue, int x, int y) {
        if (blueValue == 0) {
            lvlData[y][x] = 48;
            bactas.add(new Bacta(x * SWGame.TILES_SIZE,y * SWGame.TILES_SIZE));

        }
    }


    // EFFECTS: calculate the level offset based on the img
    // MODIFIES: this
    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - SWGame.TILES_IN_WIDTH;
        maxLvlOffsetX = SWGame.TILES_SIZE * maxTilesOffset;
    }

    // getters
    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLevelData() {
        return lvlData;
    }

    public Level getLeve() {
        return this;
    }

    // EFFECTS: check if the given hitbox is on ground
    public boolean checkOnGround(Rectangle2D.Float hitbox) {
        return checkSolid(hitbox.x, hitbox.y + hitbox.height + 1)
                &&
                checkSolid(hitbox.x + hitbox.width / 2, hitbox.y + hitbox.height + 1);

    }


    // EFFECTS; check if the given the point x,y is on ground
    public boolean checkSolid(float x, float y) {
        int maxWidth = lvlData[0].length * SWGame.TILES_SIZE;
        if (x < 0 || x >= maxWidth) {
            return true;
        }
        if (y < 0 || y >= SWGame.GAME_HEIGHT) {
            return true;
        }
        float indexX = x / SWGame.TILES_SIZE;
        float indexY = y / SWGame.TILES_SIZE;

        return checkTileSolid((int) indexX, (int) indexY);


    }

    // EFFECTS: check if the given tile x,y is solid
    public boolean checkTileSolid(int tileX, int tileY) {
        int value = lvlData[tileY][tileX];
        if (value == 11 || value == 48 || value == -1) {
            return false;
        }
        return true;

    }

    // EFFECTS: check if an entity can move
    public boolean canMove(float x, float y, float width, float height) {
        return !checkSolid(x,y) && !checkSolid(x + width,y + height)
                && !checkSolid(x + width,y) && !checkSolid(x,y + height);
    }

    // EFFECTS: check if the given hitbox is on floor
    public boolean isFloor(Rectangle2D.Float hitbox, float speedX) {
        return checkSolid(hitbox.x + speedX, hitbox.y + hitbox.height + 1);
    }

    // EFFECTS: check if h1 and h2 can see each other
    public boolean checkSight(Rectangle2D.Float h1, Rectangle2D.Float h2, int tileY) {
        int firstXTile = (int) (h1.x / SWGame.TILES_SIZE);
        int secondXTile = (int) (h2.x / SWGame.TILES_SIZE);

        if (firstXTile > secondXTile) {
            return isAllTilesWalkable(secondXTile, firstXTile, tileY);
        } else {
            return isAllTilesWalkable(firstXTile, secondXTile, tileY);

        }


    }

    // EFFECTS: check if the tile from s to e is all solid
    public boolean isAllTilesWalkable(int s, int e, int y) {
        for (int i = 0; i < e - s; i++) {
            if (checkTileSolid(s + i, y)) {
                return false;

            }
            if (!checkTileSolid(s + i, y + 1)) {
                return false;
            }

        }
        return true;
    }

    // getter
    public int getLvlOffset() {
        return maxLvlOffsetX;
    }

    public ArrayList<Stormtrooper> getTks() {
        return tks;
    }

    public ArrayList<Bacta> getBactas() {
        return bactas;
    }

    // for test
    public void testLoad() {
        loadLevel();
    }


}
