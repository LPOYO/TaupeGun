package fr.lpoyo.taupegun.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by ROMAIN on 23/12/2016.
 */
@AllArgsConstructor
public class Kit {

    @Getter
    private String name;
    @Getter @Setter
    private List<ItemStack> items;
}
