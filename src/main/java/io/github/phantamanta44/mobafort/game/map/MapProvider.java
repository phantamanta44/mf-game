package io.github.phantamanta44.mobafort.game.map;

import io.github.phantamanta44.mobafort.game.map.struct.StructTower;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class MapProvider {

    public final World world;
    public final Vector rSpawn, bSpawn;
    public final Map<Lane, List<Supplier<StructTower>>> rTowers, bTowers;

    public MapProvider(World world, Vector rSpawn, Vector bSpawn) {
        this.world = world;
        this.rSpawn = rSpawn;
        this.bSpawn = bSpawn;
        this.rTowers = new HashMap<>();
        this.bTowers = new HashMap<>();
        for (Lane lane : Lane.values()) {
            rTowers.put(lane, new LinkedList<>());
            bTowers.put(lane, new LinkedList<>());
        }
    }

    public void addRedTower(Lane lane, Supplier<StructTower> tower) {
        rTowers.get(lane).add(tower);
    }

    public void addBlueTower(Lane lane, Supplier<StructTower> tower) {
        bTowers.get(lane).add(tower);
    }

    public MobaMap getMap() {
        return new MobaMap(this);
    }

}
