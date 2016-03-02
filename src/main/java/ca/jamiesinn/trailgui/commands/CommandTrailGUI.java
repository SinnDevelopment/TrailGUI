package ca.jamiesinn.trailgui.commands;

import ca.jamiesinn.trailgui.TrailGUI;
import ca.jamiesinn.trailgui.files.Userdata;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTrailGUI
        implements CommandExecutor
{
    TrailGUI trailGUI;

    public CommandTrailGUI(TrailGUI trailGUI)
    {
        this.trailGUI = trailGUI;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        for (String string : TrailGUI.disabledWorlds)
        {
            string = string.replace("[", "");
            string = string.replace("]", "");
            if (sender instanceof Player)
            {
                Player player = (Player) sender;
                if (string.equals(player.getWorld().getName()))
                {
                    player.sendMessage(TrailGUI.prefix + ChatColor.RED + "You cannot use this command in this world.");
                    return false;
                }
            }
            if (args.length == 0)
            {
                sender.sendMessage(TrailGUI.prefix + ChatColor.GREEN + "Available commands:");
                sender.sendMessage(ChatColor.GREEN + "/trailgui reload");
                sender.sendMessage(ChatColor.GREEN + "/trailgui version");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload"))
            {
                if (!sender.hasPermission("trailgui.commands.reloadconfigs"))
                {
                    sender.sendMessage(TrailGUI.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "\u00A7"));
                    return false;
                }
                Userdata.getInstance().reloadConfig();
                Userdata.getInstance().saveConfig();

                TrailGUI.getPlugin().reload();

                sender.sendMessage(TrailGUI.prefix + ChatColor.GREEN + "Successfully reloaded all config files.");
                return true;
            }
            if (args[0].equalsIgnoreCase("version"))
            {
                sender.sendMessage(TrailGUI.prefix + ChatColor.GREEN + "Version: "
                        + this.trailGUI.getDescription().getVersion());
                return true;
            }
        }

        return false;
    }
}