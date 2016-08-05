package io.github.phantamanta44.mobafort.game.map;

import io.github.phantamanta44.mobafort.game.map.struct.StructTower;
import io.github.phantamanta44.mobafort.lib.collection.OneToManyMap;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.List;

public class MobaMap {

	private World world;
	private Vector rSpawn, bSpawn;
	private OneToManyMap<Lane, StructTower, List<StructTower>> rTowers, bTowers;

	public MobaMap(World world) {
		this.world = world;
	}

	public void setRedSpawn(Vector pos) {
		this.rSpawn = pos;
	}

	public void setBlueSpawn(Vector pos) {
		this.bSpawn = pos;
	}

	public void addRedTower(StructTower tower, Lane lane) {
		rTowers.put(lane, tower);
	}

	public void addBlueTower(StructTower tower, Lane lane) {
		bTowers.put(lane, tower);
	}

	public void reset() {
		rTowers.forEach((l, t) -> t.forEach(StructTower::reset));
		bTowers.forEach((l, t) -> t.forEach(StructTower::reset));
	}

}
