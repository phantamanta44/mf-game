package io.github.phantamanta44.mobafort.game.hero;

import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import org.bukkit.Material;

public enum HeroClass {

    MARKSMAN(new ItemSig(Material.STONE)),
    MAGE(new ItemSig(Material.STONE)),
    ASSASSIN(new ItemSig(Material.STONE)),
    TANK(new ItemSig(Material.STONE)),
    BRUISER(new ItemSig(Material.STONE)),
    SUPPORT(new ItemSig(Material.STONE));

    public final ItemSig icon;

    HeroClass(ItemSig icon) {
        this.icon = icon;
    }

}
