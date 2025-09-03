package shvmoffc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

import java.util.List;

public class ArenaManager {

    private final YourPlugin plugin;
    private final int pillarRadius = 20;
    private final int pillarHeight = 6;
    private final int barrierOffset = 10;
    private final int pillarBlockYStart = 64;
    private final Material pillarMaterial = Material.OAK_LOG;

    public ArenaManager(YourPlugin plugin) {
        this.plugin = plugin;
    }

    public void startMatch(List<Player> players, World arenaWorld, Location arenaCenter) {
        int n = Math.max(1, players.size());
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            double x = arenaCenter.getX() + pillarRadius * Math.cos(angle);
            double z = arenaCenter.getZ() + pillarRadius * Math.sin(angle);
            Location pillarBase = new Location(arenaWorld, x, pillarBlockYStart, z);
            spawnPillar(pillarBase);
        }

        WorldBorder border = arenaWorld.getWorldBorder();
        border.setCenter(arenaCenter);
        border.setSize((pillarRadius + barrierOffset) * 2);

        for (int i = 0; i < n; i++) {
            Player p = players.get(i);
            double angle = 2 * Math.PI * i / n;
            double x = arenaCenter.getX() + pillarRadius * Math.cos(angle);
            double z = arenaCenter.getZ() + pillarRadius * Math.sin(angle);
            Location spawnLoc = new Location(arenaWorld, x + 1.0, pillarBlockYStart + 1, z);
            p.teleport(spawnLoc);
        }

        CountdownManager countdownManager = plugin.getCountdownManager();
        countdownManager.startPreGameCountdown(players, 5, () -> {
            plugin.getItemManager().giveStartItems(players);
        });
    }

    private void spawnPillar(Location base) {
        World w = base.getWorld();
        for (int y = 0; y < pillarHeight; y++) {
            w.getBlockAt(base.getBlockX(), base.getBlockY() + y, base.getBlockZ()).setType(pillarMaterial);
        }
    }
}
