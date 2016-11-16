package fr.lpoyo.taupegun.guis;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.game.Game;
import fr.lpoyo.taupegun.utils.gui.AbstractGuiOption;
import fr.lpoyo.taupegun.utils.gui.FormattedInventoryGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

import java.util.Random;

/**
 * Created by ROMAIN on 29/10/2016.
 */
public class ParamsGui {

    public ParamsGui(Player p) {
        FormattedInventoryGUI gui = new FormattedInventoryGUI(5, "§6Equipes");
        set(gui);
        gui.openGUI(p);
    }

    public void set(FormattedInventoryGUI gui) {
        TaupeGun pl = TaupeGun.getInstance();
        Random random = new Random();

        gui.setOption(2, 2, new AbstractGuiOption(Material.PAPER, Game.Mode.CLASSIC.getName(), 1, 0, Game.Mode.CLASSIC.getDesc().toArray(new String[0])) {
            @Override
            public void onSelection(Player selecter, InventoryAction selectionType) {
                pl.getGame().setMode(Game.Mode.CLASSIC);
                Bukkit.broadcastMessage(pl.getGame().getPrefix() + Game.Mode.CLASSIC.getName() + " sélectionné !");
                gui.closeGUI(selecter);
                new MainGui(selecter);
            }
        });


        gui.setOption(6, 2, new AbstractGuiOption(Material.PAPER, Game.Mode.ADVENCED.getName(), 1, 0, Game.Mode.ADVENCED.getDesc().toArray(new String[0])) {
            @Override
            public void onSelection(Player selecter, InventoryAction selectionType) {
                pl.getGame().setMode(Game.Mode.ADVENCED);
                Bukkit.broadcastMessage(pl.getGame().getPrefix() + Game.Mode.ADVENCED.getName() + " sélectionné !");
                gui.closeGUI(selecter);
                new MainGui(selecter);
            }
        });

        for (int i = 0; i < 9; i++) {
            gui.setOption(i, 0, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, "§7Ca va ?") {
                @Override
                public void onSelection(Player selecter, InventoryAction selectionType) {
                }
            });

            if (i <= 4)
                gui.setOption(0, i, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, "§7Ca va ?") {
                    @Override
                    public void onSelection(Player selecter, InventoryAction selectionType) {
                    }
                });

            gui.setOption(i, 4, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, "§7Ca va ?") {
                @Override
                public void onSelection(Player selecter, InventoryAction selectionType) {
                }
            });

            if (i <= 4)
                gui.setOption(8, i, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, "§7Ca va ?") {
                    @Override
                    public void onSelection(Player selecter, InventoryAction selectionType) {
                    }
                });
        }

    }
}
