package de.lockcard.essentials;

import de.lockcard.essentials.commands.GlowCommand;
import de.lockcard.essentials.listener.PlayerJoinListener;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Plugin plugin;

    @Override
    public void onEnable() {
        Main.plugin = this;
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        GlowCommand glowCommand = new GlowCommand();
        getCommand("lockglow").setExecutor(glowCommand);
        getCommand("lockglow").setTabCompleter(glowCommand);
    }

    public static String getMessage(String path) {
        String message = "";
        message += plugin.getConfig().getString("Messages.Prefix", "");
        message += plugin.getConfig().getString("Messages." +path, "");
        message = ChatColor.translateAlternateColorCodes('&', message);

        return message;
    }
}
