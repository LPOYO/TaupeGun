package fr.lpoyo.taupegun.utils;

import com.google.common.collect.Sets;
import fr.lpoyo.taupegun.core.TaupeGun;
import net.minecraft.server.v1_10_R1.BiomeBase;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by ROMAIN on 11/11/2016.
 */
public class WorldUtils {

    private TaupeGun pl;
    public WorldUtils(TaupeGun pl, boolean worldRegen) {
        this.pl = pl;
        if (worldRegen) {
            deleteWorlds("world");
            replaceBiomes();
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

    private void replaceBiomes() {
        Field biomesField = null;
        try{
            biomesField = BiomeBase.class.getDeclaredField("i");
            biomesField.setAccessible(true);
            if(biomesField.get(null) instanceof Set){
                Set<BiomeBase> biomes = (Set<BiomeBase>)biomesField.get(null);
                Set<BiomeBase> copy = Sets.newHashSet(biomes);
                biomes.clear();
                for (BiomeBase biomeBase : copy) {
                    if (biomeBase.equals(BiomeBase.getBiome(24))) {
                        biomes.add(BiomeBase.getBiome(1));
                        continue;
                    }

                    if (biomeBase.equals(BiomeBase.getBiome(0))) {
                        biomes.add(BiomeBase.getBiome(4));
                        continue;
                    }

                    biomes.add(biomeBase);
                }
            }
        }catch(Exception e){e.printStackTrace();}
    }
}