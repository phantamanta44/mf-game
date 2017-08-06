package io.github.phantamanta44.mobafort.game.map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.phantamanta44.mobafort.lib.collection.CollectionUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

public class MapLoader {

    private static final Map<String, IMapProviderProvider> mapProviderProviders = new HashMap<>();
    private static final List<IMapProvider> loaded = new ArrayList<>();

    static {
        new FastClasspathScanner("io.github.phantamanta44.mobafort.game.map.impl")
                .matchAllClasses(c -> {
                    if (IMapProvider.class.isAssignableFrom(c)) {
                        try {
                            IMapProviderProvider provider
                                    = (IMapProviderProvider)c.newInstance();
                            mapProviderProviders.put(provider.getKey(), provider);
                        } catch (Exception ignored) { }
                    }
                }).scan();
    }

    public static void load(File file) {
        JsonParser parser = new JsonParser();
        try (FileReader strIn = new FileReader(file)) {
            JsonArray data = parser.parse(strIn).getAsJsonArray();
            StreamSupport.stream(data.spliterator(), false)
                    .map(JsonElement::getAsJsonObject)
                    .forEach(d -> {
                        loaded.add(mapProviderProviders.get(d.get("mapType").getAsString())
                                .resolve(d));
                    });
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public static IMapProvider random() {
        return CollectionUtils.random(loaded);
    }

    public static List<IMapProvider> getMaps() {
        return loaded;
    }

}
