package io.github.phantamanta44.mobafort.game.map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.github.phantamanta44.mobafort.lib.collection.CollectionUtils;
import io.github.phantamanta44.mobafort.lib.format.SerUtils;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class MapLoader {

	private static final List<MobaMap> loaded = new ArrayList<>();

	public static void load(File file) {
		JsonParser parser = new JsonParser();
		try (FileReader strIn = new FileReader(file)) {
			JsonArray data = parser.parse(strIn).getAsJsonArray();
			StreamSupport.stream(data.spliterator(), false)
					.map(JsonElement::getAsJsonObject)
					.forEach(d -> {
						MobaMap map = new MobaMap(Bukkit.getServer().getWorld(d.get("world").getAsString()));
						map.setRedSpawn(SerUtils.deserVector(d.get("redSpawn").getAsJsonObject()));
						map.setBlueSpawn(SerUtils.deserVector(d.get("blueSpawn").getAsJsonObject()));
						loaded.add(map); // TODO Load structures
					});
		} catch (IOException | IllegalStateException e) {
			e.printStackTrace();
		}
	}

	public static MobaMap random() {
		return CollectionUtils.random(loaded);
	}

	public static List<MobaMap> getMaps() {
		return loaded;
	}

}
