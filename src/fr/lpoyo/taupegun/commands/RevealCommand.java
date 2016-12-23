package fr.lpoyo.taupegun.commands;

import fr.lpoyo.taupegun.core.TaupeGun;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by ROMAIN on 16/11/2016.
 */
public class RevealCommand implements CommandExecutor {

    private TaupeGun pl;

    public RevealCommand(TaupeGun pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (pl.getGame().isTaupe(p)) {
                pl.getGame().getTaupes().get(p).add(p);
                Bukkit.broadcastMessage("§c" + p.getName() + " se révele être une taupe !");
                Bukkit.getOnlinePlayers().forEach(o -> o.playSound(o.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1F, 1F));
                p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
            }
        }

        return false;
    }
}
