package io.github.phantamanta44.mobafort.game.struct;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.Collection;

public class BlockBuild {

	public static final BlockBuild BLANK = new BlockBuild();

	private ImmutableList<BlockOffset> blocks;

	public BlockBuild(Collection<BlockOffset> blocks) {
		this.blocks = ImmutableList.copyOf(blocks);
	}

	private BlockBuild() {
		blocks = ImmutableList.of();
	}

	public void build(World world, Vector origin) {
		blocks.forEach(b -> b.place(world, origin));
	}

	public ImmutableList<BlockOffset> getBlocks() {
		return blocks;
	}

	public JsonArray serialize() {
		JsonArray ser = new JsonArray();
		blocks.forEach(b -> ser.add(b.serialize()));
		return ser;
	}

	public static BlockBuild deserialize(JsonArray ser) {
		BlockBuild build = new BlockBuild();
		ser.forEach(e -> build.blocks.add(BlockOffset.deserialize(e.getAsJsonObject())));
		return build;
	}

}
