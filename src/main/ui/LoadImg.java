package ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 Load Images for the game
 */
public class LoadImg {
    public static final String bullet = "bullet.png";
    public static final String PLAYER_ATLAS = "obw.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menub.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";

    public static final String URM_BUTTONS = "urm_buttons.png";

    public static final String MENU_BACKGROUND_IMG = "bg.png";
    public static final String PLAYING_BG_IMG = "bg_img.png";

    public static final String TK_SPRITE = "sts.png";

    public static final String COMPLETED_IMG = "completed_sprite.png";

    public static final String BACTA = "bacta.png";
    public static final String DEATH_SCREEN = "death_screen.png";

    public static final String GAME_COMPLETED = "game_completed.png";

    public static final String complete = "complete.png";

    private static Point playerSpawn;
    private static File file = null;
    private static URL url = LoadImg.class.getResource("/lvls");

    // EFFECTS: load image from given file name
    // MODIFIES: this
    public static BufferedImage getSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadImg.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    // EFFECTS: load all levels
    // MODIFIES: this
    public static BufferedImage[] getAllLevels() {


        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        for (int i = 0; i < filesSorted.length; i++) {
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals((i + 1) + ".png")) {
                    filesSorted[i] = files[j];
                }
            }
        }
        BufferedImage[] imgs = new BufferedImage[filesSorted.length];

        for (int i = 0; i < imgs.length; i++) {
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                System.exit(0);
            }
        }
        return imgs;
    }
}
