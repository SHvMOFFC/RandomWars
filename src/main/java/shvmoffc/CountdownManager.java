package shvmoffc;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

public class CountdownManager {

    private final YourPlugin plugin;
    private final CopyOnWriteArraySet<UUID> frozenPlayers = new CopyOnWriteArraySet<>();

    public CountdownManager(YourPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new MovementBlockListener(this), plugin);
    }

    public void startPreGameCountdown(List<Player> players, int seconds, Runnable onComplete) {
        frozenPlayers.clear();
        for (Player p : players) frozenPlayers.add(p.getUniqueId());

        new BukkitRunnable() {
            int t = seconds;
            @Override
            public void run() {
                if (t <= 0) {
                    frozenPlayers.clear();
                    for (Player p : players) {
                        p.sendTitle("§aGO!", "", 5, 20, 5);
                    }
                    onComplete.run();
                    cancel();
                    return;
                }
                for (Player p : players) {
                    p.sendTitle("§eСтарт через", "§6" + t + " с", 5, 20, 5);
                    p.sendActionBar("§eСтарт через §6" + t + " с");
                }
                t--;
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public boolean isPlayerFrozen(Player p) {
        return frozenPlayers.contains(p.getUniqueId());
    }
}
