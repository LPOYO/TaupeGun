package fr.lpoyo.taupegun.utils.gui;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by Corentin on 15/08/2016.<br>
 * An option for an inventory
 */
public abstract class AbstractGuiOption {

    @Getter @Setter
    protected Material icon;
    @Getter @Setter
    protected String name;
    @Getter @Setter
    protected int amount, data;
    @Getter @Setter
    protected String[] lore;
    @Getter @Setter
    protected NBTTagCompound nbtTagCompound;
    @Getter
    protected UUID optionId;

    public AbstractGuiOption(Material icon, String name, int amount, int data, String... description) {
        this(icon, name, amount, data, null, description);
    }

    public AbstractGuiOption(Material icon, String name, int amount, int data, NBTTagCompound nbtTagCompound, String... description) {
        this.icon = icon;
        this.name = name;
        this.lore = description;
        this.amount = amount;
        this.data = data;
        this.nbtTagCompound = nbtTagCompound;
        this.optionId = UUID.randomUUID();
    }

    /**
     *
     * @return The itemstack representing this option
     */
    public ItemStack getOptionValue() {
        return setName(setLore(setAmount(setData(setTag(new ItemStack(this.icon), this.nbtTagCompound), this.data), this.amount), this.lore), this.name);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 211).append(optionId).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AbstractGuiOption && ((AbstractGuiOption) obj).optionId.equals(optionId);
    }

    /**
     * Called when the option is selected
     */
    public abstract void onSelection(Player selecter, InventoryAction selectionType);

    /**
     *
     * @return A separator option (icon: stick), with no name, no lore, and does nothing
     */
    public static AbstractGuiOption separator() {
        return new AbstractGuiOption(Material.STICK, " ", 1, 0) {
            @Override
            public void onSelection(Player selecter, InventoryAction selectionType) {}
        };
    }

    public void setTag(NBTTagCompound tag) {
        this.nbtTagCompound = tag;
    }

    public void setLore(String... lore) {
        this.lore = lore;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setData(int data) {
        this.data = data;
    }

    public static ItemStack setTag(ItemStack item, NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound == null)
            return item;
        net.minecraft.server.v1_10_R1.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
        itemStack.setTag(nbtTagCompound);
        item = CraftItemStack.asCraftMirror(itemStack);
        return item;
    }

    public static ItemStack setLore(ItemStack item, String... lore) {
        ItemMeta im = item.getItemMeta();
        im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack setName(ItemStack item, String name) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack setAmount(ItemStack item, int amount) {
        item.setAmount(amount);
        return item;
    }

    public static ItemStack setData(ItemStack item, int data) {
        item.setDurability((short)data);
        return item;
    }
}
