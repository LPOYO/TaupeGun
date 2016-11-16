package fr.lpoyo.taupegun.utils.gui;

import org.bukkit.entity.Player;

/**
 * Created by Corentin on 16/08/2016.<br>
 * A utility class to handle multiple GUIs
 */
public class MultipleGUI {

    protected InventoryGUI[] guis;
    protected int current;

    public MultipleGUI(InventoryGUI... guis) {
        this.guis = guis;
    }

    /**
     *
     * @return The current index in the GUIs
     */
    public int currentIndex() {
        return this.current;
    }

    /**
     *
     * @return The current GUI
     */
    public InventoryGUI current() {
        return guis[current];
    }

    /**
     *
     * @return Whether there is a next GUI
     */
    public boolean hasNext() {
        return (current + 1) < guis.length;
    }

    /**
     *
     * @return Whether there is a previous GUI
     */
    public boolean hasPrevious() {
        return current > 0;
    }

    /**
     * Increments the current index by 1
     * @return The next inventory if exists, null otherwise
     * @see #hasNext()
     */
    public InventoryGUI next() {
        return hasNext() ? guis[current++] : null;
    }

    /**
     * Decrements the current index by 1
     * @return The previous GUI if exists, null otherwise
     * @see #hasPrevious()
     */
    public InventoryGUI previous() {
        return hasPrevious() ? guis[current--] : null;
    }

    /**
     * Shows this <code>player</code> the next inventory.<br>
     * If there is no next inventory, nothing happens
     * @return The next inventory if exists, null otherwise
     */
    public InventoryGUI showNext(Player player) {
        if(!hasNext())
            return null;
        current().closeGUI(player);
        return next().openGUI(player);
    }

    /**
     * Shows this <code>player</code> the previous inventory.<br>
     * If there is no previous inventory, nothing happens
     * @return The previous inventory if exists, null otherwise
     */
    public InventoryGUI showPrevious(Player player) {
        if(!hasPrevious())
            return null;
        return previous().openGUI(player);
    }
}
