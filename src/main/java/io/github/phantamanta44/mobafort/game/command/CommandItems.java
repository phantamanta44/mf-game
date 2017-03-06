package io.github.phantamanta44.mobafort.game.command;

import io.github.phantamanta44.mobafort.game.GamePlugin;
import io.github.phantamanta44.mobafort.game.game.Announcer;
import io.github.phantamanta44.mobafort.mfrp.item.IItem;
import io.github.phantamanta44.mobafort.mfrp.item.ItemTracker;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandItems implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player pl = (Player)sender;
            if (GamePlugin.getEngine().isInGame(pl)) {
                Player target = pl;
                if (args.length != 0)
                    target = Bukkit.getPlayer(args[0]);
                if (target == null)
                    Announcer.player("No player by this name was found.", pl);
                else {
                    Collection<Map.Entry<ItemStack, IItem>> items = ItemTracker.get(pl);
                    String[] msg = new String[items.size()];
                    msg[0] = String.format("\u00a73%s's Items:", target.getName());
                    AtomicInteger index = new AtomicInteger(1);
                    items.forEach(e -> {
                        ItemStack fake = e.getValue().getType().construct(1);
                        e.getValue().initialize(pl, fake);
                        msg[index.getAndIncrement()] = "\u00a78- " + fake.getItemMeta().getDisplayName();
                    });
                    pl.sendMessage(msg);
                }
            }
            else
                Announcer.player("You can only use this command in a game.", pl);
            return true;
        }
        return false;
    }

}
