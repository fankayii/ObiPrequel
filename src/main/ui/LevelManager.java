package ui;

import ui.main.SWGame;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 Manager for all levels
 */
public class LevelManager {
    private SWGame game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private BufferedImage[] waterSprite;
    private int lvlIndex = 0;
    private int aniTick;
    private int aniIndex;

    // Constructor, create a new LevelManager with given game
    public LevelManager(SWGame game) {

        this.game = game;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    // EFFECTS: set the new level
    // MODIFIES: this
    public void loadNextLevel() {
        Level newLevel = levels.get(lvlIndex);
        game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());

    }

    // EFFECTS: read all levels from img
    // MODIFIES: this
    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadImg.getAllLevels();
        for (BufferedImage img : allLevels) {
            levels.add(new Level(img));

        }

    }

    // EFFECTS: set the grid images to levelSprite
    // MODIFIES: this
    private void importOutsideSprites() {
        BufferedImage img = LoadImg.getSpriteAtlas(LoadImg.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }

        }

    }

    // EFFECTS: draw the level with g
    // MODIFIES: g
    public void draw(Graphics g, int lvlOffset) {
        for (int j = 0; j < SWGame.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                int x = SWGame.TILES_SIZE * i - lvlOffset;
                int y = SWGame.TILES_SIZE * j;
                if (index == -1) {
                    g.drawImage(LoadImg.getSpriteAtlas(LoadImg.complete),x,y,null);
                } else {
                    if (index == 48 || index == 49) {
                        continue;
                    } else {
                        g.drawImage(levelSprite[index], x, y, SWGame.TILES_SIZE, SWGame.TILES_SIZE, null);
                    }


                }
            }
        }

    }


    // getters
    public Level getCurrentLevel() {
        return levels.get(lvlIndex);
    }

    public int getAmountOfLevels() {
        return levels.size();
    }

    public int getLevelIndex() {
        return lvlIndex;
    }

    // setters
    public void setLevelIndex(int lvlIndex) {
        this.lvlIndex = lvlIndex;
    }
}
