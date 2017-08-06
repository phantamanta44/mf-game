package io.github.phantamanta44.mobafort.game.event;

import io.github.phantamanta44.mobafort.game.GamePlugin;
import io.github.phantamanta44.mobafort.game.game.GameEngine;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class SpawnHandler implements Listener {

    @EventHandler
    public void onSpawn(PlayerSpawnLocationEvent event) {
        GameEngine.PlayerInfo player = GamePlugin.getEngine().getPlayer(event.getPlayer());
        if (player != null) {
            switch (GamePlugin.getStater().getState()) {
                case DRAFTING:
                    event.setSpawnLocation(GamePlugin.getLobbies().getTeamLobby(player.getTeam()));
                    break;
                case PLAYING:
                    event.setSpawnLocation(GamePlugin.getEngine().getMap().getSpawn(event.getPlayer()));
                    break;
                default:
                    event.setSpawnLocation(GamePlugin.getLobbies().getMainLobby());
                    break;
            }
        } else {
            event.setSpawnLocation(GamePlugin.getLobbies().getMainLobby());
        }
    }

}
