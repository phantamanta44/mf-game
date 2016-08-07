package io.github.phantamanta44.mobafort.game.hero;

import io.github.phantamanta44.mobafort.lib.item.ItemSig;

public interface IHero {

	String getName();

	ItemSig getIcon();

	HeroClass getHeroClass();

	IBaseStats getStats();

	HeroKit getKit();

}
