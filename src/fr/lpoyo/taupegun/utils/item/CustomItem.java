package fr.lpoyo.taupegun.utils.item;

import net.minecraft.server.v1_11_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ROMAIN on 08/02/2016.
 */
public class CustomItem {
    private Material m;
    private String name;
    private List<String> lores;
    private Map<Enchantment, Integer> enchants;
    private int amount;
    private byte data;

    public CustomItem(Material m, String name, List<String> lores,
                      int amount, byte data) {
        if (amount == 0) {
            ++amount;
        }

        if (data == 0) {
            ++data;
        }

        this.m = m;
        this.name = name;
        this.lores = lores;
        this.amount = amount;
        this.data = data;
        this.enchants = new HashMap<>();
    }

    public CustomItem(Material m, String name, List<String> lores) {
        this.m = m;
        this.name = name;
        this.lores = lores;
        this.amount = 1;
        this.data = 0;
    }

    public CustomItem setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public CustomItem setName(String name) {
        this.name = name;
        return this;
    }

    public CustomItem setMaterial(Material m) {
        this.m = m;
        return this;
    }

    public CustomItem setLores(List<String> lores) {
        this.lores = lores;
        return this;
    }

    public CustomItem setData(byte data) {
        this.data = data;
        return this;
    }

    public CustomItem addEnchantment(Enchantment enchant, int level) {
        if (this.enchants != null) this.enchants.put(enchant, level);
        else {
            this.enchants = new HashMap<>();
            this.enchants.put(enchant, level);
        }

        return this;
    }

    public ItemStack create() {
        ItemStack is = new ItemStack(this.m, this.amount, (short) this.data);
        ItemMeta meta = is.getItemMeta();
        if (this.name != null) {
            meta.setDisplayName(this.name);
        }

        if (this.lores != null) {
            meta.setLore(this.lores);
        }

        if (this.enchants != null) {
            for (Map.Entry<Enchantment, Integer> entry : this.enchants.entrySet()) meta.addEnchant(entry.getKey(), entry.getValue(), false);
        }

        is.setItemMeta(meta);
        return is;
    }

    public ItemStack createHead(String owner) {

        ItemStack is = new ItemStack(Material.SKULL_ITEM, this.amount,
                (short) 3);
        SkullMeta meta = (SkullMeta) is.getItemMeta();
        meta.setOwner(owner);
        meta.setDisplayName(this.name);
        if (this.lores != null) {
            meta.setLore(this.lores);
        }

        is.setItemMeta(meta);
        return is;
    }

    public ItemStack createEgg(String mobType) {

        ItemStack is = new ItemStack(Material.MONSTER_EGG);
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(this.name);
        if (this.lores != null) {
            meta.setLore(this.lores);
        }
        is.setItemMeta(meta);
        is.setAmount(amount);

        NBTTagCompound idTag = new NBTTagCompound();
        NBTTagCompound tag = new NBTTagCompound();
        idTag.setString("id", mobType);
        tag.set("EntityTag", idTag);

        net.minecraft.server.v1_11_R1.ItemStack itemStack = CraftItemStack.asNMSCopy(is);
        itemStack.setTag(tag);

        return CraftItemStack.asCraftMirror(itemStack);
    }
}
