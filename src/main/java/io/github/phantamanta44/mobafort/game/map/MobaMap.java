package io.github.phantamanta44.mobafort.game.map;

import io.github.phantamanta44.mobafort.game.map.struct.StructTower;
import io.github.phantamanta44.mobafort.lib.collection.OneToManyMap;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.List;

public class MobaMap {

	private World world;
	private Location rSpawn, bSpawn;
	private OneToManyMap<Lane, StructTower, List<StructTower>> rTowers, bTowers;

	public MobaMap(MapProvider prov) {
		this.world = prov.world;
		this.rSpawn = prov.rSpawn.toLocation(world, (float)(prov.rSpawn.angle(prov.bSpawn) * 180D / Math.PI), 0F);
		this.bSpawn = prov.bSpawn.toLocation(world, (float)(prov.bSpawn.angle(prov.rSpawn) * 180D / Math.PI), 0F);
	}

	public World getWorld() {
		return world;
	}

	public Location getRedSpawn() {
		return rSpawn;
	}

	public Location getBlueSpawn() {
		return bSpawn;
	}

	public void addRedTower(StructTower tower, Lane lane) {
		rTowers.put(lane, tower);
	}

	public void addBlueTower(StructTower tower, Lane lane) {
		bTowers.put(lane, tower);
	}

	public OneToManyMap<Lane, StructTower, List<StructTower>> getRedTowers() {
		return rTowers;
	}

	public OneToManyMap<Lane, StructTower, List<StructTower>> getBlueTowers() {
		return bTowers;
	}

	public void reset() {
		rTowers.forEach((l, t) -> t.forEach(StructTower::reset));
		bTowers.forEach((l, t) -> t.forEach(StructTower::reset));
	}

}
