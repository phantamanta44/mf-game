package io.github.phantamanta44.mobafort.game.util;

import org.bukkit.Material;

import java.util.EnumSet;

public class FreeItems {

    private static final EnumSet<Material> blacklist = EnumSet.of(
            Material.FLINT_AND_STEEL, Material.BOW, Material.STRING, Material.PAINTING,
            Material.SIGN, Material.WOOD_DOOR, Material.BUCKET, Material.WATER_BUCKET,
            Material.LAVA_BUCKET, Material.MINECART, Material.IRON_DOOR, Material.REDSTONE,
            Material.SNOW_BALL, Material.BOAT, Material.SUGAR_CANE, Material.STORAGE_MINECART,
            Material.POWERED_MINECART, Material.EGG, Material.COMPASS, Material.FISHING_ROD,
            Material.WATCH, Material.CAKE, Material.BED, Material.DIODE, Material.MAP,
            Material.ENDER_PEARL, Material.BREWING_STAND, Material.CAULDRON, Material.EYE_OF_ENDER,
            Material.MONSTER_EGG, Material.EXP_BOTTLE, Material.FIREBALL, Material.BOOK_AND_QUILL,
            Material.WRITTEN_BOOK, Material.ITEM_FRAME, Material.FLOWER_POT_ITEM, Material.EMPTY_MAP,
            Material.SKULL_ITEM, Material.FIREWORK, Material.FIREWORK_CHARGE, Material.ENCHANTED_BOOK,
            Material.REDSTONE_COMPARATOR, Material.EXPLOSIVE_MINECART, Material.HOPPER_MINECART, Material.ARMOR_STAND,
            Material.LEASH, Material.NAME_TAG, Material.COMMAND_MINECART, Material.BANNER,
            Material.SPRUCE_DOOR_ITEM, Material.BIRCH_DOOR_ITEM, Material.JUNGLE_DOOR_ITEM, Material.ACACIA_DOOR_ITEM,
            Material.DARK_OAK_DOOR_ITEM, Material.SPLASH_POTION, Material.TIPPED_ARROW, Material.LINGERING_POTION,
            Material.SHIELD, Material.ELYTRA, Material.BOAT_SPRUCE, Material.BOAT_BIRCH,
            Material.BOAT_JUNGLE, Material.BOAT_ACACIA, Material.BOAT_DARK_OAK, Material.RECORD_12
    );
    private static final int maxId = Material.values().length;
    private static int i = Material.STRUCTURE_BLOCK.ordinal();

    public static synchronized Material next() {
        Material mat;
        do {
            if (++i > maxId)
                throw new IllegalStateException("No remaining free items!");
        } while (isIllegal(mat = Material.values()[i]));
        return mat;
    }

    public static boolean isIllegal(Material m) {
        String n = m.toString();
        return m.isBlock()
                || m.isEdible()
                || n.contains("HELMET")
                || n.contains("CHESTPLATE")
                || n.contains("LEGGINGS")
                || n.contains("BOOTS")
                || n.contains("HOE")
                || n.contains("SWORD")
                || blacklist.contains(m);
    }

}
