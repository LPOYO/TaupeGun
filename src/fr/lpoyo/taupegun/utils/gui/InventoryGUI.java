package fr.lpoyo.taupegun.utils.gui;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

/**
 * Created by Corentin on 15/08/2016.<br>
 * An inventory representing a base GUI
 */
public abstract class InventoryGUI implements Listener {

    protected Inventory inventory;

    /**
     * Since an inventory gui must have rows of 9 elements...
     * @param lines The number of lines for this gui
     */
    public InventoryGUI(int lines, String title) {
        this.inventory = Bukkit.createInventory(new InventoryId(this), lines * 9, title);
    }

    /**
     *
     * @param options Options to add
     */
    public void addOptions(AbstractGuiOption... options) {
        for(AbstractGuiOption option : options)
            inventory.addItem(option.getOptionValue());
    }

    /**
     *
     * @param options Options to remove
     */
    public void removeOptions(AbstractGuiOption... options) {
        for(AbstractGuiOption option : options)
            inventory.remove(option.getOptionValue());
    }

    /**
     * Opens this GUI to the given <code>player</code>
     * @return The inventory itself
     */
    public InventoryGUI openGUI(Player player) {
        player.closeInventory();
        player.openInventory(this.inventory);

        return this;
    }

    /**
     * Handy method
     * @return The inventory itself
     */
    public InventoryGUI closeGUI(Player player) {
        player.closeInventory();
        return this;
    }

    /**
     *
     * @return The inventory representing this GUI
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     *
     * @return The inventory id of this gui
     */
    public InventoryId getId() {
        return (InventoryId) inventory.getHolder();
    }

    /**
     * You may override this one to change the "option selected behaviour"
     * @param selecter The player who clicked
     * @param option The option which was selected
     * @param action The action which was performed
     */
    public void optionSelected(Player selecter, AbstractGuiOption option, InventoryAction action) {
        if (option == null)
            return;
        option.onSelection(selecter, action);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof InventoryGUI)
            return ((InventoryGUI) obj).getId().equals(getId());
        else if(obj instanceof Inventory)
            return ((Inventory) obj).getHolder() instanceof InventoryId && ((Inventory) obj).getHolder().equals(getId());
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(45, 9).append(getId()).toHashCode();
    }

    /**
     *
     * @return Whether this gui contains the given <code>option</code>
     */
    public abstract boolean containOption(AbstractGuiOption option);

    /**
     *
     * @param slot The slot number
     * @return The option at the given slot (raw value, 0= top right)
     */
    public abstract AbstractGuiOption getOptionAtSlot(int slot);

    public abstract void onClose(Player player);

    public Inventory inventoryCopy() {
        Inventory i = Bukkit.createInventory(new InventoryId(this), inventory.getSize(), inventory.getTitle());
        i.setContents(i.getContents());
        return i;
    }

    /**
     * A dummy inventory holder to recognize unique inventories
     */
    public static final class InventoryId implements InventoryHolder {
        private InventoryGUI linked;
        private UUID uuid;

        /**
         * Will use a random UUID
         * @param linked Inventory GUI linked to this holder
         */
        public InventoryId(InventoryGUI linked) {
            this(linked, UUID.randomUUID());
        }

        public InventoryId(InventoryGUI linked, UUID uuid) {
            this.linked = linked;
            this.uuid = uuid;
        }

        public InventoryGUI getGUI() {
            return linked;
        }

        public UUID getUUID() {
            return uuid;
        }

        @Override
        public Inventory getInventory() {
            return linked.inventory;
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(81, 54).append(uuid).toHashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof InventoryId && ((InventoryId) obj).uuid.equals(uuid);
        }
    }

    public static class InventoryListener implements Listener {
        @EventHandler
        public final void guiClicked(InventoryClickEvent event) {
            if (event.getClickedInventory() == null)
                return;
            InventoryHolder holder = event.getClickedInventory().getHolder();
            if(!(holder instanceof InventoryId))
                return;
            InventoryGUI gui = ((InventoryId) holder).getGUI();
            gui.optionSelected((Player) event.getWhoClicked(), gui.getOptionAtSlot(event.getSlot()), event.getAction());
            event.setCancelled(true);
        }

        @EventHandler
        public final void inventoryClosed(InventoryCloseEvent event) {
            if (event.getInventory() == null)
                return;
            InventoryHolder holder = event.getInventory().getHolder();
            if(!(holder instanceof InventoryId))
                return;
            InventoryGUI gui = ((InventoryId) holder).getGUI();
            gui.onClose((Player)event.getPlayer());
        }
    }

}
