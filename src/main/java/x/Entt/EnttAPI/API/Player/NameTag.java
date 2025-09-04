package x.Entt.EnttAPI.API.Player;

import org.bukkit.entity.Entity;

public class NameTag {

    public static void setNameTag(Entity entity, String name, boolean visible) {
        entity.setCustomName(name);
        entity.setCustomNameVisible(visible);
    }

    public static void removeNameTag(Entity entity) {
        entity.setCustomName(null);
        entity.setCustomNameVisible(false);
    }
}