package x.Entt.EnttAPI.CMDs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import x.Entt.EnttAPI.API.Messages.Color;
import x.Entt.EnttAPI.EAPI;
import x.Entt.EnttAPI.Utils.CacheManager;

import java.util.ArrayList;
import java.util.List;

import static x.Entt.EnttAPI.EAPI.prefix;

public class CMD implements CommandExecutor, TabCompleter {

    private final EAPI plugin;

    public CMD(EAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        if (args.length == 0) {
            sender.sendMessage(Color.set(prefix + "&c&lUSE: &f/" + s + " <reload|cache>"));
            return true;
        }

        switch (args[0].toLowerCase()) {

            case "reload" -> {
                plugin.reloadConfig();
                sender.sendMessage(Color.set(prefix + "&2Config reloaded!"));
                return true;
            }

            case "cache" -> {
                if (args.length > 1 && args[1].equalsIgnoreCase("clear")) {
                    CacheManager cm = EAPI.getCache();
                    cm.clearAll();
                    sender.sendMessage(Color.set(prefix + "&2Cache cleared!"));
                    return true;
                } else {
                    sender.sendMessage(Color.set(prefix + "&c&lUSE: &f/" + s + " cache clear"));
                    return true;
                }
            }

            default -> sender.sendMessage(Color.set(prefix + "&cUnknown command."));
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, @NotNull String[] args) {

        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("reload");
            completions.add("cache");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("cache")) {
            completions.add("clear");
        }

        return completions;
    }
}