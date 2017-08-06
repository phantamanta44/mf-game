package io.github.phantamanta44.mobafort.game.event;

import io.github.phantamanta44.mobafort.game.GamePlugin;
import io.github.phantamanta44.mobafort.game.GameState;
import io.github.phantamanta44.mobafort.mfrp.event.MobaEventDamage;
import io.github.phantamanta44.mobafort.mfrp.event.MobaEventDamageByPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class MechanicSuppressor implements Listener {

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(MobaEventDamage event) {
        if (event.getTarget() instanceof Player) {
            boolean inGame = GamePlugin.getEngine().isInGame((Player)event.getTarget());
            if (!inGame || GamePlugin.getStater().getState() != GameState.PLAYING)
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageByPlayer(MobaEventDamageByPlayer event) {
        onDamage(event);
    }

}
