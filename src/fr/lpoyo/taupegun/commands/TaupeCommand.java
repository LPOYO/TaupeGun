package fr.lpoyo.taupegun.commands;

import fr.lpoyo.taupegun.core.TaupeGun;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by ROMAIN on 16/11/2016.
 */
public class TaupeCommand implements CommandExecutor {

    private TaupeGun pl;

    public TaupeCommand(TaupeGun pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.isOp() || sender.hasPermission("taupegun.command.taupe")) {
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("teams")) {
                    if (args[1].equalsIgnoreCase("set")) {
                        if (Bukkit.getPlayer(args[2]) != null) {
                            pl.getTeamsManager().getTeams().get(Integer.valueOf(args[3])).add(Bukkit.getPlayer(args[2]));
                        }
                    }
                }

                else if (args[0].equalsIgnoreCase("test") && sender instanceof Player) {
                    Player p = (Player) sender;
                    pl.getConfigUtils().getKit(args[1]).getItems().stream().filter(itemStack -> itemStack != null).forEach(itemStack -> p.getInventory().addItem(itemStack));
                }
            }
        }

        else
            sender.sendMessage("§ctoz");

        return false;
    }

    private void sendUsage(CommandSender sender) {
        sender.sendMessage("§c/taupe time <nombre>");
    }
}
