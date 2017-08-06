package io.github.phantamanta44.mobafort.game.hero;

import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import org.bukkit.Material;

public enum HeroClass {

    MARKSMAN("Marksman", new ItemSig(Material.STONE)),
    MAGE("Mage", new ItemSig(Material.STONE)),
    ASSASSIN("Assassin",new ItemSig(Material.STONE)),
    TANK("Tank", new ItemSig(Material.STONE)),
    BRUISER("Bruiser", new ItemSig(Material.STONE)),
    SUPPORT("Support", new ItemSig(Material.STONE));

    public final String name;
    public final ItemSig icon;

    HeroClass(String name, ItemSig icon) {
        this.name = name;
        this.icon = icon;
    }

}
