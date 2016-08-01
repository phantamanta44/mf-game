package io.github.phantamanta44.mobafort.game.struct;

import com.google.gson.JsonObject;
import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class BlockOffset {

	public final ItemSig type;
	public final int x, y, z;

	public BlockOffset(ItemSig type, int x, int y, int z) {
		if (!type.material.isBlock())
			throw new IllegalArgumentException();
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void place(Location origin) {
		place(origin.getWorld(), origin.getBlockX(), origin.getBlockY(), origin.getBlockZ());
	}

	public void place(World world, Vector origin) {
		place(world, origin.getBlockX(), origin.getBlockY(), origin.getBlockZ());
	}

	@SuppressWarnings("deprecation")
	public void place(World world, int originX, int originY, int originZ) {
		Block block = world.getBlockAt(originX + x, originY + y, originZ + z);
		block.setType(type.material);
		block.setData((byte)(type.meta != -1 ? type.meta : 0));
	}

	public JsonObject serialize() {
		JsonObject ser = new JsonObject();
		ser.add("type", type.serialize());
		ser.addProperty("x", x);
		ser.addProperty("y", y);
		ser.addProperty("z", z);
		return ser;
	}

	public static BlockOffset deserialize(JsonObject ser) {
		return new BlockOffset(ItemSig.deserialize(ser.get("type").getAsJsonObject()),
				ser.get("x").getAsInt(), ser.get("y").getAsInt(), ser.get("z").getAsInt());
	}

}
