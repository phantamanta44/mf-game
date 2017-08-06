package io.github.phantamanta44.mobafort.game.map;

public interface IMapProvider {

    String getName();

    IMapProviderProvider getProvider();

    IGameMap getMap();

    int getTeamSize();

}
