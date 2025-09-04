package x.Entt.EnttAPI.API.Player;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.Location;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Radius {

    public static List<Entity> getNearbyEntities(Player player, double radius) {
        Location loc = player.getLocation();
        return player.getWorld().getNearbyEntities(loc, radius, radius, radius)
                .stream()
                .filter(e -> !e.equals(player))
                .collect(Collectors.toList());
    }

    public static List<Block> getNearbyBlocks(Player player, int radius) {
        List<Block> blocks = new ArrayList<>();
        Location loc = player.getLocation();
        int px = loc.getBlockX();
        int py = loc.getBlockY();
        int pz = loc.getBlockZ();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    blocks.add(Objects.requireNonNull(loc.getWorld()).getBlockAt(px + x, py + y, pz + z));
                }
            }
        }
        return blocks;
    }
}