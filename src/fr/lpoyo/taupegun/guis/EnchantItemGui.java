package fr.lpoyo.taupegun.guis;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.game.Kit;
import fr.lpoyo.taupegun.utils.gui.AbstractGuiOption;
import fr.lpoyo.taupegun.utils.gui.FormattedInventoryGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by ROMAIN on 29/10/2016.
 */
public class EnchantItemGui {

    private int x, y, i;

    public EnchantItemGui(Player p, Kit kit, ItemStack itemStack) {
        FormattedInventoryGUI gui = new FormattedInventoryGUI(7, "§6Kits réservés aux taupes");

        for (Enchantment enchantment : Enchantment.values()) {
            if (enchantment.canEnchantItem(itemStack)) {
                i = 1;
                while (i <= enchantment.getMaxLevel()) {
                    gui.setOption(x, y, new AbstractGuiOption(Material.ENCHANTED_BOOK, "§5" + enchantment.getName(), 1, 0, null, false, false, "§7Clique pour enchanter", ("Niveau : " + String.valueOf(i))) {
                        @Override
                        public void onSelection(Player selecter, InventoryAction selectionType) {
                            for (int i = 0; i < kit.getItems().size(); i++) {
                                if (kit.getItems().get(i).isSimilar(itemStack))
                                    kit.getItems().remove(i);
                            }

                            itemStack.addUnsafeEnchantment(enchantment, i);
                            kit.getItems().add(itemStack);
                            TaupeGun.getInstance().getConfigUtils().saveKit(kit);
                            gui.closeGUI(selecter);
                            new KitEditGui(selecter, kit);
                        }
                    });

                    i++;

                    x++;
                    if (x >= 9) {
                        x = 0;
                        y++;
                    }
                }

                gui.setOption(x, y, new AbstractGuiOption(Material.ENCHANTED_BOOK, "§5" + enchantment.getName(), 1, 0, null, false, false, "§7Clique pour enchanter", ("Niveau : " + String.valueOf(250))) {
                    @Override
                    public void onSelection(Player selecter, InventoryAction selectionType) {
                        for (int i = 0; i < kit.getItems().size(); i++) {
                            if (kit.getItems().get(i).isSimilar(itemStack))
                                kit.getItems().remove(i);
                        }

                        itemStack.addUnsafeEnchantment(enchantment, 250);
                        kit.getItems().add(itemStack);
                        TaupeGun.getInstance().getConfigUtils().saveKit(kit);
                        gui.closeGUI(selecter);
                        new KitEditGui(selecter, kit);
                    }
                });


                x++;
                if (x >= 9) {
                    x = 0;
                    y++;
                }
            }
        }

        gui.setOption(8, 6, new AbstractGuiOption(Material.BARRIER, "§cRetour", 1, 0, null, false, false) {
            @Override
            public void onSelection(Player selecter, InventoryAction selectionType) {
                new KitEditGui(selecter, kit);
            }
        });

        gui.openGUI(p);
    }
}
