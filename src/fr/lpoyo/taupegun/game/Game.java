package fr.lpoyo.taupegun.game;

import lombok.Data;

/**
 * Created by ROMAIN on 11/11/2016.
 */
@Data
public abstract class Game {

    private int maxPlayers;
    private int minPlayers;
    private String prefix;

    public abstract void start();
}
