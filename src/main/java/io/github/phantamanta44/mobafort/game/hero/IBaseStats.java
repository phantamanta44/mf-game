package io.github.phantamanta44.mobafort.game.hero;

import io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat;

import java.util.Collection;

@FunctionalInterface
public interface IBaseStats {

	Collection<ProvidedStat<?>> getStats(int level);

}
