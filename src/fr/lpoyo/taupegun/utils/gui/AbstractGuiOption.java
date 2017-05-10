package fr.lpoyo.taupegun.utils.gui;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Corentin on 15/08/2016.<br>
 * An option for an inventory
 */
public abstract class AbstractGuiOption {

    @Getter @Setter
    protected Material icon;
    @Getter @Setter
    protected PotionType type;
    @Getter @Setter
    protected String name;
    @Getter @Setter
    protected int amount, data;
    @Getter @Setter
    protected Map<Enchantment, Integer> enchants;
    @Getter @Setter
    protected String[] lore;
    @Getter @Setter
    protected boolean upgraded, extend;
    @Getter @Setter
    protected NBTTagCompound nbtTagCompound;
    @Getter
    protected UUID optionId;

    public AbstractGuiOption(Material icon, String name, int amount, int data, PotionType type, boolean upgraded, boolean extend, String... description) {
        this(icon, name, amount, data, null, null, type, upgraded, extend, description);
    }

    public AbstractGuiOption(ItemStack is, String name, String... lore) {
        this.icon = is.getType();
        this.name = name;
        this.lore = lore;
        this.amount = is.getAmount();
        this.data = is.getDurability();
        this.nbtTagCompound = CraftItemStack.asNMSCopy(is).getTag();
        this.enchants = is.getEnchantments();
        this.optionId = UUID.randomUUID();

        if (is.getType().toString().contains("POTION")) {
            PotionMeta m = (PotionMeta) is.getItemMeta();
            this.icon = is.getType();
            this.name = is.getItemMeta().getDisplayName();
            this.amount = is.getAmount();
            this.data = is.getDurability();
            this.type = m.getBasePotionData().getType();
            this.upgraded = m.getBasePotionData().isUpgraded();
            this.extend = m.getBasePotionData().isExtended();
            this.lore = lore;
        }
    }

    public AbstractGuiOption(Material icon, String name, int amount, int data, NBTTagCompound nbtTagCompound, Map<Enchantment, Integer> enchants, PotionType type, boolean upgraded, boolean extend, String... description) {
        this.icon = icon;
        this.name = name;
        this.enchants = enchants;
        this.lore = description;
        this.amount = amount;
        this.data = data;
        this.nbtTagCompound = nbtTagCompound;
        this.type = type;
        this.upgraded = upgraded;
        this.extend = extend;
        this.optionId = UUID.randomUUID();
    }

    /**
     *
     * @return The itemstack representing this option
     */
    public ItemStack getOptionValue() {
        if (!icon.toString().contains("POTION"))
            return setEnchants(setName(setLore(setAmount(setData(setTag(new ItemStack(this.icon), this.nbtTagCompound), this.data), this.amount), this.lore), this.name), this.enchants);
        else
            return setPotion(setName(setLore(setAmount(setData(new ItemStack(this.icon), this.data), this.amount), this.lore), this.name), this.type, this.extend, this.upgraded);
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
        return new AbstractGuiOption(Material.STICK, " ", 1, 0, null, false, false) {
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

    public static ItemStack setPotion(ItemStack item, PotionType type, boolean extend, boolean upgraded) {
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setBasePotionData(new PotionData(type, extend, upgraded));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setTag(ItemStack item, NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound == null)
            return item;
        net.minecraft.server.v1_11_R1.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
        itemStack.setTag(nbtTagCompound);
        item = CraftItemStack.asCraftMirror(itemStack);
        return item;
    }

    public static ItemStack setEnchants(ItemStack item, Map<Enchantment, Integer> enchantments) {
        if (enchantments != null)
            enchantments.forEach(item::addUnsafeEnchantment);
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
