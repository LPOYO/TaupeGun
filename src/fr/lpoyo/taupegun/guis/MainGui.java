package fr.lpoyo.taupegun.guis;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.utils.gui.AbstractGuiOption;
import fr.lpoyo.taupegun.utils.gui.FormattedInventoryGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

import java.util.Random;

/**
 * Created by ROMAIN on 29/10/2016.
 */
public class MainGui {

    public MainGui(Player p) {
        TaupeGun pl = TaupeGun.getInstance();
        FormattedInventoryGUI gui = new FormattedInventoryGUI(5, "§6Menu");
        Random random = new Random();

        gui.setOption(5, 2, new AbstractGuiOption(Material.DIAMOND_SWORD, "§6Kits", 1, 0, "§7Kits de taupes") {
            @Override
            public void onSelection(Player selecter, InventoryAction selectionType) {
                gui.closeGUI(selecter);
                new KitsGui(selecter);
            }
        });

        gui.setOption(4, 2, new AbstractGuiOption(Material.BEACON, "§6Commencer", 1, 0, "§7Commencer la partie") {
            @Override
            public void onSelection(Player selecter, InventoryAction selectionType) {
                gui.closeGUI(selecter);
                pl.getGame().start();
            }
        });

        gui.setOption(3, 2, new AbstractGuiOption(Material.STICK, "§6Mode de jeu", 1, 0, "§7Choisis le mode de jeu", "§aActuel : " + pl.getGame().getMode().getName()) {
            @Override
            public void onSelection(Player selecter, InventoryAction selectionType) {
                gui.closeGUI(selecter);
                new ParamsGui(selecter);
            }
        });

        for (int i = 0; i < 9; i++) {
            gui.setOption(i, 0, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, "§7Ca va ?") {
                @Override public void onSelection(Player selecter, InventoryAction selectionType) {}});

            if (i <= 4)
                gui.setOption(0, i, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, "§7Ca va ?") {
                    @Override public void onSelection(Player selecter, InventoryAction selectionType) {}});

            gui.setOption(i, 4, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, "§7Ca va ?") {
                @Override public void onSelection(Player selecter, InventoryAction selectionType) {}});

            if (i <= 4)
                gui.setOption(8, i, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, "§7Ca va ?") {
                    @Override public void onSelection(Player selecter, InventoryAction selectionType) {}});
        }

        gui.openGUI(p);
    }
}
