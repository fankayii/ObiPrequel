package model;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
public class GameStateTest {

    @Test
    public void test() {
        assertEquals(GameState.MENU,GameState.getState());
        GameState.setState(GameState.PLAYING);
        assertEquals(GameState.PLAYING,GameState.getState());
    }
}
