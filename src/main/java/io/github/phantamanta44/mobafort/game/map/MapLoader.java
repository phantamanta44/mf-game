package io.github.phantamanta44.mobafort.game.map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.phantamanta44.mobafort.game.game.Team;
import io.github.phantamanta44.mobafort.game.map.struct.BlockBuild;
import io.github.phantamanta44.mobafort.game.map.struct.StructTower;
import io.github.phantamanta44.mobafort.lib.collection.CollectionUtils;
import io.github.phantamanta44.mobafort.lib.format.SerUtils;
import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class MapLoader {

    private static final List<MapProvider> loaded = new ArrayList<>();

    public static void load(File file) {
        JsonParser parser = new JsonParser();
        try (FileReader strIn = new FileReader(file)) {
            JsonArray data = parser.parse(strIn).getAsJsonArray();
            StreamSupport.stream(data.spliterator(), false)
                    .map(JsonElement::getAsJsonObject)
                    .forEach(d -> {
                        MapProvider map = new MapProvider(
                                Bukkit.getServer().getWorld(d.get("world").getAsString()),
                                SerUtils.deserVector(d.get("redSpawn").getAsJsonObject()),
                                SerUtils.deserVector(d.get("blueSpawn").getAsJsonObject())
                        );
                        BlockBuild towerFull = BlockBuild.deserialize(d.get("buildTowerFull").getAsJsonArray());
						BlockBuild towerHalf = BlockBuild.deserialize(d.get("buildTowerHalf").getAsJsonArray());
						BlockBuild towerNone = BlockBuild.deserialize(d.get("buildTowerNone").getAsJsonArray());
                        d.get("redTowers").getAsJsonObject().entrySet().forEach(e -> {
                            Lane lane = Lane.valueOf(e.getKey());
                            e.getValue().getAsJsonArray().forEach(tElem -> {
                                JsonObject tDto = tElem.getAsJsonObject();
                                Vector base = SerUtils.deserVector(tDto.get("pos").getAsJsonObject());
                                StructTower.TowerType type = StructTower.TowerType.valueOf(tDto.get("type").getAsString());
                                map.addRedTower(lane, () -> new StructTower(
                                        base, type, Team.RED, towerFull, towerHalf, towerNone, lane != Lane.BOT
                                ));
                            });
                        });
                        d.get("blueTowers").getAsJsonObject().entrySet().forEach(e -> {
                            Lane lane = Lane.valueOf(e.getKey());
                            e.getValue().getAsJsonArray().forEach(tElem -> {
                                JsonObject tDto = tElem.getAsJsonObject();
                                Vector base = SerUtils.deserVector(tDto.get("pos").getAsJsonObject());
                                StructTower.TowerType type = StructTower.TowerType.valueOf(tDto.get("type").getAsString());
                                map.addBlueTower(lane, () -> new StructTower(
                                        base, type, Team.BLUE, towerFull, towerHalf, towerNone, lane != Lane.BOT
                                ));
                            });
                        });
                        loaded.add(map);
                    });
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public static MapProvider random() {
        return CollectionUtils.random(loaded);
    }

    public static List<MapProvider> getMaps() {
        return loaded;
    }

}
