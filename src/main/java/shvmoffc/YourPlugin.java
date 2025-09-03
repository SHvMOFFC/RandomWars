package shvmoffc;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.World;
import org.bukkit.Location;

import java.util.List;
import java.util.ArrayList;

public class YourPlugin extends JavaPlugin {

    private static YourPlugin instance;
    private CountdownManager countdownManager;
    private ItemManager itemManager;
    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
        instance = this;
        countdownManager = new CountdownManager(this);
        itemManager = new ItemManager(this);
        arenaManager = new ArenaManager(this);

        getLogger().info("RandomWars enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("RandomWars disabled!");
    }

    public static YourPlugin getInstance() {
        return instance;
    }

    public CountdownManager getCountdownManager() {
        return countdownManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("startgame")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can start the game.");
                return true;
            }
            Player player = (Player) sender;
            World world = player.getWorld();
            Location center = player.getLocation();

            List<Player> players = new ArrayList<>(world.getPlayers());

            arenaManager.startMatch(players, world, center);
            return true;
        }
        return false;
    }
}
