package fr.lpoyo.taupegun.utils;

import fr.lpoyo.taupegun.core.TaupeGun;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Created by ROMAIN on 17/07/2016.
 */
@AllArgsConstructor
public class ConfigUtils {

    private TaupeGun pl;

    public int getInt(String path) {
        return pl.getConfig().getInt(path);
    }

    public String getString(String path) {
        return pl.getConfig().getString(path);
    }

    public List<String> getStringList(String path) {
        return pl.getConfig().getStringList(path);
    }

    public void set(Object o, String path) {
        pl.getConfig().set(path, o);
        pl.saveConfig();
    }
}