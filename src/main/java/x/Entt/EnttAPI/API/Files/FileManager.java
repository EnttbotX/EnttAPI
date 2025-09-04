package x.Entt.EnttAPI.API.Files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static x.Entt.EnttAPI.EAPI.prefix;

public class FileManager {

    private final JavaPlugin plugin;
    private final Map<String, FileConfiguration> configs = new HashMap<>();
    private final Map<String, File> files = new HashMap<>();

    public FileManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration get(String name) {
        return configs.computeIfAbsent(name, k -> {
            File file = getFile(name);
            files.put(name, file);
            if (!file.exists()) plugin.saveResource(name, false);
            return YamlConfiguration.loadConfiguration(file);
        });
    }

    public void save(String name) {
        FileConfiguration config = configs.get(name);
        File file = files.get(name);
        if (config != null && file != null) {
            try {
                config.save(file);
            } catch (IOException e) {
                plugin.getLogger().severe(prefix + "Can´t save the file " + name + ": " + e.getMessage());
            }
        }
    }

    public void reload(String name) {
        File file = getFile(name);
        configs.put(name, YamlConfiguration.loadConfiguration(file));
        files.put(name, file);
    }

    public File getFile(String name) {
        return name.contains("/") ?
                new File(plugin.getDataFolder(), name) :
                new File(plugin.getDataFolder(), name);
    }

    public void saveAll() {
        configs.keySet().forEach(this::save);
    }

    public void reloadAll() {
        configs.keySet().forEach(this::reload);
    }
}