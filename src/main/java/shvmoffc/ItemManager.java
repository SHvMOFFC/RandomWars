package shvmoffc;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemManager {
    private final YourPlugin plugin;
    public ItemManager(YourPlugin plugin) { this.plugin = plugin; }

    public void giveStartItems(List<Player> players) {
        for (Player p : players) {
            p.getInventory().clear();
            p.getInventory().addItem(new ItemStack(Material.STONE_SWORD, 1));
            p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
        }
    }
}
