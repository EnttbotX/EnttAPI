package x.Entt.EnttAPI.API.Messages;

import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class Color {
    public static String set(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static List<String> colorList(List<String> messages) {
        return messages.stream()
                .map(Color::set)
                .collect(Collectors.toList());
    }
}