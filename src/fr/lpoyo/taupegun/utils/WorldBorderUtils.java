package fr.lpoyo.taupegun.utils;

import fr.lpoyo.taupegun.core.TaupeGun;
import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;

public class WorldBorderUtils {

    private TaupeGun pl;

    public WorldBorderUtils(TaupeGun pl) {
        this.pl = pl;
        Bukkit.getWorlds().forEach(world -> world.getWorldBorder().reset());
    }

    public void set(int size, long time) {
        WorldBorder wb;
        wb = Bukkit.getWorlds().get(0).getWorldBorder();
        wb.setCenter(0, 0);
        wb.setSize(size, time);
        wb.setDamageAmount(2);
        wb.setDamageBuffer(5);
        wb.setWarningDistance(20);

        wb = Bukkit.getWorlds().get(1).getWorldBorder();
        wb.setCenter(0, 0);
        wb.setSize(size, time);
        wb.setDamageAmount(2);
        wb.setDamageBuffer(5);
        wb.setWarningDistance(20);

        wb = Bukkit.getWorlds().get(2).getWorldBorder();
        wb.setCenter(0, 0);
        wb.setSize(size, time);
        wb.setDamageAmount(2);
        wb.setDamageBuffer(5);
        wb.setWarningDistance(20);
    }
}