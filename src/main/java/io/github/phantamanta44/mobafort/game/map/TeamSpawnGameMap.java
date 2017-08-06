package io.github.phantamanta44.mobafort.game.map;

import com.google.gson.JsonObject;
import io.github.phantamanta44.mobafort.game.GamePlugin;
import io.github.phantamanta44.mobafort.lib.format.SerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public abstract class TeamSpawnGameMap<T extends IMapProvider> implements IGameMap {

    protected final T provider;
    protected final JsonObject dto;
    protected final World world;
    protected final Vector spawnRed, spawnBlue;

    public TeamSpawnGameMap(T provider, JsonObject dto) {
        this.provider = provider;
        this.dto = dto;
        this.world = Bukkit.getServer().getWorld(dto.get("world").getAsString());
        JsonObject spawns = dto.getAsJsonObject("spawns");
        this.spawnRed = SerUtils.deserVector(spawns.getAsJsonObject("red"));
        this.spawnBlue = SerUtils.deserVector(spawns.getAsJsonObject("blue"));
    }

    @Override
    public T getProvider() {
        return provider;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public Location getSpawn(Player player) {
        switch (GamePlugin.getEngine().getPlayer(player).getTeam()) {
            case BLUE:
                return spawnBlue.toLocation(world);
            case RED:
                return spawnRed.toLocation(world);
        }
        throw new IllegalStateException("Unpossible");
    }

}
