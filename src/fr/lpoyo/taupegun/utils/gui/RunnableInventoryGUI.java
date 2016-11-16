package fr.lpoyo.taupegun.utils.gui;

import fr.lpoyo.taupegun.core.TaupeGun;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Corentin on 15/08/2016.<br>
 * An inventory GUI, formatted, with fixed, absolute positions.<br>
 * Uses a 2d array to store option information.
 */
public class RunnableInventoryGUI extends InventoryGUI {

    public Map<Integer, AbstractGuiOption> options;
    public int current;
    public BukkitTask task;

    public RunnableInventoryGUI(int lines, String title) {
        super(lines, title);
        this.options = new HashMap<>();
        current = -1;
    }

    @Override
    public void removeOptions(AbstractGuiOption... options) {
        for (AbstractGuiOption option : options) {
            inventory.remove(option.getOptionValue());
            this.options.remove(option);
        }
    }

    @Override
    public boolean containOption(AbstractGuiOption option) {
        return options.containsValue(option);
    }

    @Override
    public AbstractGuiOption getOptionAtSlot(int slot) {
        return options.get(slot);
    }

    public RunnableInventoryGUI setOption(int pos, AbstractGuiOption option) {
        options.put(pos, option);
        inventory.setItem(pos, option.getOptionValue());
        return this;
    }

    public void setOptions(Integer[] pos, AbstractGuiOption[] options) {
        for(int i = 0; i < options.length; i++)
            setOption(pos[i], options[i]);
    }

    @Override
    public InventoryGUI openGUI(Player player) {
        player.openInventory(inventory);
        task = new BukkitRunnable() {
            @Override
            public void run() {
                update();
            }
        }.runTaskTimer(TaupeGun.getInstance(), 0, 1);
        return this;
    }

    public void update() {
    }

    @Override
    public void onClose(Player player) {
        if (task != null)
            task.cancel();
    }
}
