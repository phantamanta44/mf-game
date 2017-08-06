package io.github.phantamanta44.mobafort.game.map;

import com.google.gson.JsonObject;

public interface IMapProviderProvider {

    String getKey();

    String getName();

    IMapProvider resolve(JsonObject dto);

}
