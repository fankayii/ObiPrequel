package persistence;

import org.junit.jupiter.api.Test;
import ui.gamestates.Playing;
import ui.main.SWGame;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    private JsonWriter jsonWriter;

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriter() {
        try {
            SWGame game = new SWGame();
            JsonWriter writer = new JsonWriter("./data/testWriter.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriter.json");
            int health = reader.readHealth();
            assertEquals(100,health);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
