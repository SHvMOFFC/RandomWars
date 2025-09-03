package shvmoffc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovementBlockListener implements Listener {

    private final CountdownManager countdownManager;

    public MovementBlockListener(CountdownManager countdownManager) {
        this.countdownManager = countdownManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent e) {
        if (!countdownManager.isPlayerFrozen(e.getPlayer())) return;

        if (e.getFrom().getX() != e.getTo().getX() ||
            e.getFrom().getY() != e.getTo().getY() ||
            e.getFrom().getZ() != e.getTo().getZ()) {
            e.setTo(e.getFrom());
        }
    }
}
