package x.Entt.EnttAPI;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import x.Entt.EnttAPI.API.Messages.Color;
import x.Entt.EnttAPI.CMDs.CMD;
import x.Entt.EnttAPI.Utils.CacheManager;

import java.util.Objects;

public class EAPI extends JavaPlugin {
    private static CacheManager cache;
    public String version = getDescription().getVersion();
    public static String prefix = "&8[&6&lEntt&f&lAPI&8]&r &7» &r";

    @Override
    public void onEnable() {
        cache = new CacheManager(getDataFolder());

        cache.load();
        cache.clearVolatile();
        cache.save();

        registerCommands();

        Bukkit.getConsoleSender().sendMessage(Color.set
                (prefix + "&av" + version + " &2Enabled! &5&l[API]"));
    }

    @Override
    public void onDisable() {
        cache.clearVolatile();
        cache.save();

        Bukkit.getConsoleSender().sendMessage(Color.set
                (prefix + "&av" + version + " &cDisabled &5&l[API]"));
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("eapi")).setExecutor(new CMD(this));
    }

    public static CacheManager getCache() {
        return cache;
    }
}