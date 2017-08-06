package io.github.phantamanta44.mobafort.game.map.impl;

import com.google.gson.JsonObject;
import io.github.phantamanta44.mobafort.game.GamePlugin;
import io.github.phantamanta44.mobafort.game.game.Team;
import io.github.phantamanta44.mobafort.game.map.IGameMap;
import io.github.phantamanta44.mobafort.game.map.IMapProvider;
import io.github.phantamanta44.mobafort.game.map.IMapProviderProvider;
import io.github.phantamanta44.mobafort.game.map.TeamSpawnGameMap;
import io.github.phantamanta44.mobafort.mfrp.event.MobaEventKilled;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class MapProviderProvider1v1 implements IMapProviderProvider {

    @Override
    public String getKey() {
        return "1v1";
    }

    @Override
    public String getName() {
        return "1v1";
    }

    @Override
    public IMapProvider resolve(JsonObject dto) {
        return new MapProvider(dto);
    }

    private class MapProvider implements IMapProvider {

        private final JsonObject dto;
        private final String name;

        public MapProvider(JsonObject dto) {
            this.dto = dto;
            this.name = dto.get("name").getAsString();
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public IMapProviderProvider getProvider() {
            return MapProviderProvider1v1.this;
        }

        @Override
        public IGameMap getMap() {
            return new GameMap(this, dto);
        }

        @Override
        public int getTeamSize() {
            return 1;
        }

    }

    private static class GameMap extends TeamSpawnGameMap<MapProvider> implements Listener {

        private Team winner;

        public GameMap(MapProvider provider, JsonObject dto) {
            super(provider, dto);
            this.winner = null;
            Bukkit.getServer().getPluginManager().registerEvents(this, GamePlugin.INSTANCE);
        }

        @Override
        public void cleanUp() {
            HandlerList.unregisterAll(this);
        }

        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onKill(MobaEventKilled event) {
            winner = GamePlugin.getEngine().getPlayer(event.getTarget()).getTeam().getOpposite();
        }

        @Override
        public void tick(long tick) {
            // TODO healing fountain with death laser
            // TODO passive gold/xp
        }

        @Override
        public Team getWinner() {
            return winner;
        }

        @Override
        public void reset() {
            // NO-OP
        }

    }

}
