package fr.lpoyo.taupegun.utils.world;

import fr.lpoyo.taupegun.core.TaupeGun;
import net.minecraft.server.v1_11_R1.BiomeBase;

import java.io.File;

/**
 * Created by ROMAIN on 11/11/2016.
 */
public class WorldUtils {

    private TaupeGun pl;
    public WorldUtils(TaupeGun pl, boolean worldRegen) {
        this.pl = pl;
        if (worldRegen) {
            deleteWorlds("world");
        }
    }

    private void deleteWorlds(String... worlds) {
        for (String world : worlds)
            deleteWorld(new File(world));
    }

    private boolean deleteWorld(File path) {
        if (path.exists()) {
            File files[] = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    /*private void replaceBiomes() {
        System.out.println("==================================================================");
        for (int i = 0; i < BiomeBase.i.size(); i++) {
            if (BiomeBase.i.toArray()[i].getClass().getName().equalsIgnoreCase("net.minecraft.server.v1_11_R1.BiomeOcean")) {
                BiomeBase.i.remove(i);
                TaupeGun.log("§eAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            }
        }

        for (BiomeBase biomeBase : BiomeBase.i) {
            TaupeGun.log("§c" + biomeBase.getClass().getName());
        }
    }*/
}