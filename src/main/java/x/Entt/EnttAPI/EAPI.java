package x.Entt.EnttAPI;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import x.Entt.EnttAPI.API.Messages.Color;

public class EAPI extends JavaPlugin {
    public String version = getDescription().getVersion();
    public static String prefix = "&6&lEntt&7&lAPI &f&l»&r ";

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(Color.set
                (prefix + "&av" + version + " &2Enabled! &5&l[API]"));
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(Color.set
                (prefix + "&av" + version + " &cDisabled &5&l[API]"));
    }
}