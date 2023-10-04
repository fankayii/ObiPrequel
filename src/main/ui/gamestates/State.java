package ui.gamestates;

import model.GameState;
import ui.buttons.MenuButton;
import ui.main.SWGame;

import java.awt.event.MouseEvent;

public class State {
    protected SWGame game;

    public State(SWGame game) {
        this.game = game;
    }

    public boolean isIn(MouseEvent e, MenuButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    public void setGamestate(GameState state) {
        /*
        switch (state) {
            case MENU -> game.getAudioPlayer().playSong(AudioPlayer.MENU_1);
            case PLAYING -> game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex());
        }

         */
        GameState.setState(state);

    }


    public SWGame getGame() {
        return game;
    }
}
