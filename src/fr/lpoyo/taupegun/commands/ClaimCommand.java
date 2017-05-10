package fr.lpoyo.taupegun.commands;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.game.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by ROMAIN on 16/11/2016.
 */
public class ClaimCommand implements CommandExecutor {

    private TaupeGun pl;

    public ClaimCommand(TaupeGun pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (pl.getGame().isTaupe(p) && pl.getGame().getTaupeKit(p) != null && pl.getGame().isTaupesRevealed()) {
                Kit kit = pl.getGame().getTaupeKit(p);
                kit.getItems().forEach(itemStack -> p.getWorld().dropItem(p.getLocation(), itemStack));
                pl.getGame().getTaupesKit().remove(p);
                p.sendMessage("§cKit §a"+kit.getName() + " §creçu !");
            }
        }

        return false;
    }
}
