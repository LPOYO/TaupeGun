package fr.lpoyo.taupegun.commands;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.tasks.GameTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;

/**
 * Created by ROMAIN on 16/11/2016.
 */
public class TCommand implements CommandExecutor {

    private TaupeGun pl;

    public TCommand(TaupeGun pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (pl.getGame().isTaupe(p) && pl.getGame().isTaupesRevealed()) {
                if (args.length == 0) {
                    p.sendMessage("§c==> Usage : §6/t <message>");
                    return false;
                }

                StringBuilder sb = new StringBuilder();
                for (String arg : args)
                    sb.append(arg + " ");

                pl.getGame().getTaupes().forEach((player, taupeTeam) -> {
                    if (taupeTeam.equals(pl.getGame().getTaupes().get(p)))
                        player.sendMessage("§c§o[" + p.getName() + "] §r" + sb);
                });
            }
        }
        return false;
    }
}
