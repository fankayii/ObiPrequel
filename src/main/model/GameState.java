package model;

/**
 * Represent the state of the game
 */
public enum GameState {
    PLAYING, MENU, QUIT, LOAD;

    private static GameState state = MENU;

    //setter
    public static void setState(GameState st) {
        state = st;
    }

    // getter
    public static GameState getState() {
        return state;
    }
}
