package io.github.phantamanta44.mobafort.game.map;

import io.github.phantamanta44.mobafort.game.game.Team;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public interface IGameMap {

    void cleanUp();

    IMapProvider getProvider();

    World getWorld();

    Location getSpawn(Player player);

    void tick(long tick);

    Team getWinner();

    void reset();

}
