package fr.lpoyo.taupegun.guis;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.game.teams.TaupeTeam;
import fr.lpoyo.taupegun.utils.gui.AbstractGuiOption;
import fr.lpoyo.taupegun.utils.gui.RunnableInventoryGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by ROMAIN on 29/10/2016.
 */
public class TeamsGui {
    

    public TeamsGui(Player p) {
        Random random = new Random();

        RunnableInventoryGUI gui = new RunnableInventoryGUI(5, "§6Equipes") {
            @Override
            public void update() {
                options.clear();
                inventory.clear();

                for (int i = 0; i < 9; i++) {
                    setOption(i, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, "§7Ca va ?") {
                        @Override
                        public void onSelection(Player selecter, InventoryAction selectionType) {
                        }
                    });

                    setOption(36 + i, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, "§7Ca va ?") {
                        @Override
                        public void onSelection(Player selecter, InventoryAction selectionType) {
                        }
                    });

                    /*if (i <= 4)
                        setOption(0, i, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, "§7Ca va ?") {
                            @Override
                            public void onSelection(Player selecter, InventoryAction selectionType) {
                            }
                        });

                    setOption(i, 4, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, "§7Ca va ?") {
                        @Override
                        public void onSelection(Player selecter, InventoryAction selectionType) {
                        }
                    });

                    if (i <= 4)
                        setOption(8, i, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, "§7Ca va ?") {
                            @Override
                            public void onSelection(Player selecter, InventoryAction selectionType) {
                            }
                        });*/
                }

                for (int i = 0; i < TaupeGun.getInstance().getGame().getMode().getTeams(); i++) {
                    TaupeTeam team = TaupeGun.getInstance().getTeamsManager().getTeams().get(i + 2);
                    ArrayList<String> lores = new ArrayList<>();

                    if (team.getTeam().getEntries().size() >= 5)
                        lores.add("§6Equipe pleine");

                    lores.addAll(team.getTeam().getEntries().stream().map(s -> team.getPrefix() + "- " + s).collect(Collectors.toList()));

                    ItemStack is = team.getItemStack();

                    setOption(19 + i, new AbstractGuiOption(is.getType(), is.getItemMeta().getDisplayName(), is.getAmount(), is.getDurability(), lores.toArray(new String[lores.size()])) {
                        @Override
                        public void onSelection(Player player, InventoryAction inventoryAction) {
                            if (team.getTeam().getEntries().size() >= 5) {
                                if (team.getTeam().hasEntry(player.getName()))
                                    team.remove(player);
                                else {
                                    player.sendMessage("§cL'équipe est pleine !");
                                    return;
                                }
                            }

                            else {
                                if (team.getTeam().hasEntry(player.getName()))
                                    team.remove(player);
                                else {
                                    team.add(player);
                                }
                            }
                        }
                    });
                }

                p.updateInventory();
            }
        };

        gui.openGUI(p);
    }
}
