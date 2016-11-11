package fr.lpoyo.taupegun.game;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ROMAIN on 11/11/2016.
 */
public enum  GameState {

    LOBBY(),
    GAME(),
    END();

    @Getter
    @Setter
    private static GameState currentState = LOBBY;

    public static boolean isState(GameState state) {
        return currentState == state;
    }
}
