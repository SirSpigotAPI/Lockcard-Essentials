package de.lockcard.essentials.commands;

import de.lockcard.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GlowCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        switch (args.length) {
                // lockglow
            case 0:
                if(!sender.hasPermission("glow.self")) {
                    sender.sendMessage(Main.getMessage("NoPermission"));
                    return true;
                }

                if(!(sender instanceof Player player))
                    return false;

                player.setGlowing(!player.isGlowing());

                //Message Output
                player.sendMessage(getGlowMessage(player));
                break;

                // lockglow [Player]
            case 1:
                if (!sender.hasPermission("glow.other")) {
                    sender.sendMessage(Main.getMessage("NoPermission"));
                    return true;
                }

                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Main.getMessage("PlayerNotFound").replace("%name%", args[0]));
                    return true;
                }

                target.setGlowing(!target.isGlowing());

                //Message Output
                target.sendMessage(getGlowMessage(target));
                sender.sendMessage(getGlowMessageOther(target));
                break;

            default:
                return false;
        }

        return true;
    }

    private String getGlowMessage(Player target) {
        return Main.getMessage(target.isGlowing() ? "GlowSelf.Enable" : "GlowSelf.Disable");
    }

    private String getGlowMessageOther(Player target) {
        return Main.getMessage(target.isGlowing() ? "GlowOther.Enable" : "GlowOther.Disable").replace("%name%", target.getName());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        List<String> completions = new ArrayList<>();

        if(args.length == 1 && sender.hasPermission("glow.other")) {
            Bukkit.getOnlinePlayers().forEach(player -> commands.add(player.getName()));
        }

        StringUtil.copyPartialMatches(args[args.length - 1], commands, completions);
        Collections.sort(completions);
        return completions;
    }
}
