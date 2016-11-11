package fr.lpoyo.taupegun.tasks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ROMAIN on 11/11/2016.
 */
@AllArgsConstructor
public enum Task {

    LOBBYTASK(false),
    GAMETASK(false),
    WINTASK(false);

    @Getter
    @Setter
    private boolean running;
}
