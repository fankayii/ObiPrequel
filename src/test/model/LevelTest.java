package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Level;
import ui.LoadImg;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LevelTest {
    private Level lvl;
    private BufferedImage img;

    @BeforeEach
    public void setup() {
        InputStream is = LoadImg.class.getResourceAsStream("/lvls/1.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            fail("");
        }
        lvl = new Level(img);
    }

    @Test
    public void testConstructor() {
        assertEquals(14,lvl.getLevelData().length);
        assertEquals(75,lvl.getLevelData()[0].length);
        assertEquals(64,lvl.getPlayerSpawn().x);
        assertEquals(576,lvl.getPlayerSpawn().y);
        assertEquals(11,lvl.getSpriteIndex(0,0));
        assertEquals(lvl,lvl.getLeve());
        assertEquals(3136,lvl.getLvlOffset());

    }

    @Test
    public void testLoad() {
        lvl.testLoad();
        assertEquals(lvl.getLevelData()[0][0],11);
        assertEquals(lvl.getTks().size(),10);
        assertEquals(lvl.getBactas().size(),2);
    }



}
