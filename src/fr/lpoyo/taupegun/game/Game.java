package fr.lpoyo.taupegun.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ROMAIN on 11/11/2016.
 */
public abstract class Game {

    @Getter
    public Mode mode;
    @Getter @Setter
    private int maxPlayers;
    @Getter @Setter
    private String prefix;
    @Getter @Setter
    private boolean damage;

    public abstract void setMode(Mode mode);
    public abstract void start();

    @AllArgsConstructor
    @Getter
    public enum Mode {

        CLASSIC(0, 4, 1, 20, "§6Mode Classique", Arrays.asList("§7Taupe Gun saison 1", "§74 équipes normales de 5 joueurs", "§71 équipes de taupes de 4 joueurs", "§720 joueurs", "§7Révélation 20 minutes")),
        ADVENCED(0, 7, 2, 35, "§6Mode Avancé", Arrays.asList("§7Taupe Gun saison 5", "§77 équipes normales de 5 joueurs", "§72 équipes de taupes de 7 joueurs", "§735 joueurs", "§7Révélation 30 minutes"));

        private int id, teams, taupeTeams, players;
        private String name;
        private List<String> desc;

        public static Mode byId(int id) {
            for (Mode mode : values()) {
                if (mode.getId() == id)
                    return mode;
            }
            return CLASSIC;
        }
    }
}
