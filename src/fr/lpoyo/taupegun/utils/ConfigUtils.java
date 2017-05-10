package fr.lpoyo.taupegun.utils;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.game.Kit;
import fr.lpoyo.taupegun.utils.item.CustomItem;
import org.apache.commons.io.FileUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by ROMAIN on 17/07/2016.
 */
public class ConfigUtils {

    private TaupeGun pl;

    private File kitsFile;
    private FileConfiguration kits;

    public ConfigUtils(TaupeGun pl) {
        this.pl = pl;

        kitsFile = new File(pl.getDataFolder(), "kits.yml");

        if (!kitsFile.exists()) {
            try {
                kitsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        kits = YamlConfiguration.loadConfiguration(kitsFile);

        addKit(new Kit("Aerien", Arrays.asList(new ItemStack(Material.ENDER_PEARL, 16),
                new CustomItem(Material.ENCHANTED_BOOK, null, null).addEnchantment(Enchantment.PROTECTION_FALL, 4).create())));

        addKit(new Kit("Potions", Arrays.asList(new Potion(PotionType.INSTANT_DAMAGE, 1, true).toItemStack(1),
                new Potion(PotionType.POISON, 1, true).toItemStack(1),
                new Potion(PotionType.WEAKNESS, 1, true).toItemStack(1),
                new Potion(PotionType.SLOWNESS, 1, true).toItemStack(1))));

        addKit(new Kit("Creeper", Arrays.asList(new CustomItem(Material.MONSTER_EGG, null, null).setAmount(5).createEgg("Creeper"),
                new CustomItem(Material.MONSTER_EGG, null, null).setAmount(5).createEgg("Blaze"),
                new CustomItem(Material.MONSTER_EGG, null, null).setAmount(5).createEgg("Ghast"))));

        addKit(new Kit("Temp", Arrays.asList(new ItemStack(Material.DIAMOND_SWORD))));
    }

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

    public void addKit(Kit kit) {
        if (kits.getConfigurationSection("kits.name") != null)
            return;

        ConfigurationSection c = kits.createSection("kits." + kit.getName());

        for (ItemStack item : kit.getItems()) {
            c.set(UUID.randomUUID().toString(), item);
        }
        /*for (ItemStack is : items) {
            if (is.getType().toString().contains("POTION")) {
                ConfigurationSection p = kit.createSection("POTION#"+UUID.randomUUID());
                PotionMeta pm = (PotionMeta) is.getItemMeta();

                p.set("type", pm.getBasePotionData().getType().toString());
                p.set("splash", is.getType().toString().contains("SPLASH"));
                p.set("lingering", is.getType().toString().contains("LINGERING"));
                p.set("extend", pm.getBasePotionData().isExtended());
                p.set("upgraded", pm.getBasePotionData().isUpgraded());

            }

            else {
                ConfigurationSection i = kit.createSection(is.getType().toString() + "#" + UUID.randomUUID());

                i.set("amount", is.getAmount());
                if (is.getDurability() != 0)
                    i.set("data", is.getDurability());

                is.getEnchantments().forEach((enchantment, integer) -> {
                    ConfigurationSection e = i.createSection("enchants");
                    e.set(enchantment.getName(), integer);
                });
            }
        }*/

        try {
            kits.save(kitsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Kit getKit(String name) {
        if (!kits.contains("kits." + name))
            return null;

        List<ItemStack> items = new ArrayList<>();

        ConfigurationSection kit = kits.getConfigurationSection("kits." + name);

        /*for (String is : kit.getKeys(false)) {
            if (is.contains("POTION")) {
                ConfigurationSection p = kit.getConfigurationSection(is);

                PotionType type = PotionType.valueOf(p.getString("type"));
                boolean splash = p.getBoolean("splash");
                boolean lingering = p.getBoolean("lingering");
                boolean extend = p.getBoolean("extend");
                boolean upgraded = p.getBoolean("upgraded");

                ItemStack potion = new ItemStack(Material.POTION);
                if (splash)
                    potion.setType(Material.SPLASH_POTION);
                if (lingering)
                    potion.setType(Material.LINGERING_POTION);

                PotionMeta meta = (PotionMeta) potion.getItemMeta();
                meta.setBasePotionData(new PotionData(type, extend, upgraded));
                potion.setItemMeta(meta);

                items.add(potion);
            }

            else {
                Material m = Material.valueOf(is.split("#")[0]);
                ConfigurationSection i = kit.getConfigurationSection(is);
                CustomItem itemStack = new CustomItem(m, null, null);
                itemStack.setAmount(i.getInt("amount"));
                if (i.get("data") != null)
                    itemStack.setData((byte) i.getInt("data"));

                if (i.getConfigurationSection("enchants") != null)
                    i.getConfigurationSection("enchants").getValues(false).forEach((s, o) -> itemStack.addEnchantment(Enchantment.getByName(s), (int)o));

                items.add(itemStack.create());

            }
        }*/

        kit.getValues(false).forEach((s, o) -> items.add((ItemStack) o));

        return new Kit(name, items);
    }

    public List<Kit> getAllKits() {
        List<Kit> l = new ArrayList<>();
        kits.getConfigurationSection("kits").getKeys(false).forEach(s -> l.add(getKit(s)));
        return l;
    }

    public void deleteKit(Kit kit) {
        kits.set("kits." + kit.getName(), null);
    }

    public void saveKit(Kit kit) {
        deleteKit(kit);
        addKit(kit);
    }
}