package fr.lpoyo.taupegun.utils.gui;

import fr.lpoyo.taupegun.utils.gui.arrays.Array2d;
import fr.lpoyo.taupegun.utils.gui.arrays.FixedArray2d;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Corentin on 15/08/2016.<br>
 * An inventory GUI, formatted, with fixed, absolute positions.<br>
 * Uses a 2d array to store option information.
 */
public class FormattedInventoryGUI extends InventoryGUI {

    @Getter
    private Array2d<AbstractGuiOption> options;

    /**
     * Since an inventory gui must have rows of 9 elements...
     *
     * @param lines The number of lines for this gui
     */
    public FormattedInventoryGUI(int lines, String title) {
        super(lines, title);
        this.options = new FixedArray2d<>(9, lines);
    }

    /**
     * <b>/!\ Use {@link #setOptions(Integer[], Integer[], AbstractGuiOption[])} to set multiple options at once.</b>
     */
    @Override
    public void addOptions(AbstractGuiOption... options) {
        throw new UnsupportedOperationException("Use setOptions(int[], int[], AbstractGuiOption[]) instead.");
    }

    @Override
    public void removeOptions(AbstractGuiOption... options) {
        for(AbstractGuiOption option : options) {
            inventory.remove(option.getOptionValue());
            this.options.remove(option);
        }
    }

    @Override
    public boolean containOption(AbstractGuiOption option) {
        return options.contains(option) != null;
    }

    @Override
    public AbstractGuiOption getOptionAtSlot(int slot) {
        return options.getOrdinal(slot);
    }

    public AbstractGuiOption getOptionAtCoord(int x, int y) {
        return options.get(x, y);
    }

    /**
     *
     * @param x The X position in the gui of item
     * @param y Y position
     * @param option The option to add
     * @return The gui itself (to chain setOption)
     */
    public FormattedInventoryGUI setOption(int x, int y, AbstractGuiOption option) {
        options.set(x, y, option);
        ItemStack stack = option.getOptionValue();
        inventory.setItem(options.fromPosition(x, y), stack);

        return this;
    }

    public FormattedInventoryGUI setOption(int slot, AbstractGuiOption option) {
        options.set(options.fromOrdinal(slot).x, options.fromOrdinal(slot).y, option);
        inventory.setItem(options.fromPosition(options.fromOrdinal(slot).x, options.fromOrdinal(slot).y), option.getOptionValue());

        return this;
    }

    /**
     * Set several options. Each index in each array represents a xy pos and an option.<br>
     * You may still use [primitive] integer arrays, but I must use object arrays for convenience.
     */
    public void setOptions(Integer[] x, Integer[] y, AbstractGuiOption[] options) {
        //int is a primitive and I didn't want to test it by hand...too lazy I guess
        Array2d.checkEquivalentLength(x, y, options);
        for(int i = 0;i < options.length;i++)
            setOption(x[i], y[i], options[i]);
    }


    @Override
    public void onClose(Player player) {}
}
