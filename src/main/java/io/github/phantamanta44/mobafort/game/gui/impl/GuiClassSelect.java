package io.github.phantamanta44.mobafort.game.gui.impl;

import io.github.phantamanta44.mobafort.game.GamePlugin;
import io.github.phantamanta44.mobafort.game.GameState;
import io.github.phantamanta44.mobafort.game.game.Announcer;
import io.github.phantamanta44.mobafort.game.game.GameEngine;
import io.github.phantamanta44.mobafort.game.gui.Gui;
import io.github.phantamanta44.mobafort.game.gui.slot.GuiSlot;
import io.github.phantamanta44.mobafort.game.hero.IHero;
import org.apache.commons.lang.mutable.MutableInt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.UUID;

public class GuiClassSelect extends Gui {

    private final GameEngine.PlayerInfo playerInfo;
    private boolean ready;

    public GuiClassSelect(UUID player, GameEngine.PlayerInfo info) {
        super(6, "Class Selection", Bukkit.getServer().getPlayer(player));
        this.playerInfo = info;
        this.ready = false;
        GamePlugin.getStater().getClassSelect().register(player, this);
    }

    @Override
    public void init() {
        slot(0, new SlotClassSelectReady(this));
        MutableInt i = new MutableInt(9);
        for (Map.Entry<UUID, GameEngine.PlayerInfo> teammate
                : GamePlugin.getEngine().getPlayers(playerInfo.getTeam())) {
            slot(i.intValue(), new SlotClassSelectPlayer(teammate.getValue()));
            i.add(9);
        }
        i.setValue(0);
        GamePlugin.getHeros().forEach((heroClass, heros) -> heros.stream()
                .sorted(Comparator.comparing(IHero::getName))
                .forEach(h -> {
                    int j = i.intValue() % 7;
                    slot(9 * (i.intValue() - j) / 7 + j, new SlotClassSelectClass(this, h));
                    i.increment();
                }));
    }

    @Override
    public void destroy() {
        if (GamePlugin.getStater().getState() == GameState.DRAFTING) {
            Announcer.player(
                    "Return to the class selection menu with " + ChatColor.GRAY + "/class" + ChatColor.RESET + ".", player());
        }
    }

    public GameEngine.PlayerInfo getGamePlayer() {
        return playerInfo;
    }

    public boolean isReady() {
        return ready;
    }

    private static class SlotClassSelectPlayer extends GuiSlot {

        private static final ItemStack DEFAULT_STACK = new ItemStack(Material.STONE);

        private final GameEngine.PlayerInfo player;
        private final GuiClassSelect playerGui;
        private IHero cachedHero;
        private ItemStack cachedIcon;

        public SlotClassSelectPlayer(GameEngine.PlayerInfo player) {
            this.player = player;
            this.playerGui = GamePlugin.getStater().getClassSelect().getForPlayer(player);
            this.cachedHero = player.getHero();
            this.cachedIcon = DEFAULT_STACK;
        }

        @Override
        public ItemStack stack() {
            if (player.getHero() != cachedHero)
                this.cachedIcon = formatStack(playerGui.player(), player.getHero(),
                        player.getHero() != null ? player.getHero().getIcon().construct(1) : DEFAULT_STACK);
            return cachedIcon;
        }

        private static ItemStack formatStack(Player player, IHero hero, ItemStack stack) {
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(ChatColor.BLUE + player.getName());
            meta.setLore(Collections.singletonList(ChatColor.GRAY + (hero != null ? hero.getName() : "Selecting...")));
            stack.setItemMeta(meta);
            return stack;
        }

    }

    private static class SlotClassSelectReady extends GuiSlot {

        private static final ItemStack DISABLED_STACK = new ItemStack(Material.IRON_FENCE);
        private static final ItemStack OFF_STACK = new ItemStack(Material.STAINED_GLASS, 1, (short)14);
        private static final ItemStack ON_STACK = new ItemStack(Material.STAINED_GLASS, 1, (short)5);

        static {
            ItemMeta meta = DISABLED_STACK.getItemMeta();
            meta.setDisplayName(ChatColor.WHITE + "Not Ready...");
            meta.setLore(Collections.singletonList(ChatColor.GRAY + "Select a class first!"));
            DISABLED_STACK.setItemMeta(meta);

            meta = OFF_STACK.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "Not Ready...");
            meta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to toggle."));
            OFF_STACK.setItemMeta(meta);

            meta = ON_STACK.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Ready!");
            meta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to toggle."));
            ON_STACK.setItemMeta(meta);
        }

        private final GuiClassSelect parent;

        public SlotClassSelectReady(GuiClassSelect parent) {
            this.parent = parent;
        }

        @Override
        public ItemStack stack() {
            return parent.playerInfo.getHero() == null ? DISABLED_STACK : (parent.ready ? ON_STACK : OFF_STACK);
        }

        @Override
        public boolean onInteract(Player player, InventoryClickEvent event) {
            parent.ready = parent.playerInfo.getHero() != null && !parent.ready;
            return false;
        }

    }

    private static class SlotClassSelectClass extends GuiSlot {

        private final GuiClassSelect parent;
        private final IHero hero;
        private final ItemStack icon;

        public SlotClassSelectClass(GuiClassSelect parent, IHero hero) {
            this.parent = parent;
            this.hero = hero;
            this.icon = hero.getIcon().construct(1);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + hero.getName());
            meta.setLore(Collections.singletonList(ChatColor.GRAY + hero.getHeroClass().name));
            icon.setItemMeta(meta);
        }

        @Override
        public ItemStack stack() {
            return icon;
        }

        @Override
        public boolean onInteract(Player player, InventoryClickEvent event) {
            parent.playerInfo.setHero(hero);
            return false;
        }

    }
}
