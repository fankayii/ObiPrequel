package persistence;


import model.items.Bacta;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class JsonReaderTest {
    @Test
    void testReaderNonExistenFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            int health = reader.readHealth();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGame.json");
        try {
            int level = reader.readLevel();
            List<Bacta> bactas = reader.readBactas();
            int health = reader.readHealth();
            assertEquals(level,0);
            assertTrue(bactas.isEmpty());
            assertEquals(100,health);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderParseBactas() {
        JsonReader reader = new JsonReader("./data/testReaderParseBactas.json");
        try {

            List<Bacta> bactas = reader.readBactas();
            assertEquals((int) bactas.get(0).getHitbox().x,4);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderParsePos() {
        JsonReader reader = new JsonReader("./data/testReaderParsePos.json");
        try {

            Point pos = reader.readPos();
            assertEquals(pos.x,2);
            assertEquals(pos.y,17);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
