package io.github.phantamanta44.mobafort.game.event;

import io.github.phantamanta44.mobafort.game.GamePlugin;
import io.github.phantamanta44.mobafort.mfrp.event.MobaEventKilled;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class KillHandler implements Listener {

    @EventHandler
    public void onKill(MobaEventKilled event) {
        GamePlugin.getEngine().getDeathManager().onDeath(event);
    }

}
