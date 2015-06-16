package ca.jamiesinn.trailsgui.commands;

import ca.jamiesinn.trailsgui.Main;
import ca.jamiesinn.trailsgui.files.TrailData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrailGUI
        implements CommandExecutor
{
    Main main;

    public TrailGUI(Main main)
    {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.DARK_RED + "[TrailGUI] Only players can perform this command");
            return true;
        }
        Player player = (Player) sender;
        for (String string : this.main.getConfig().getStringList("disabledWorlds"))
        {
            string.replace("[", "");
            string.replace("]", "");
            if (string.equals(player.getWorld().getName()))
            {
                player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "TrailGUI" + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + "You cannot use this command in this world.");
                return false;
            }
            if (args.length == 0)
            {
                player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "TrailGUI" + ChatColor.DARK_GRAY + "]: " + ChatColor.GREEN + "Available commands:");
                player.sendMessage(ChatColor.GREEN + "/TrailGUI ReloadConfigs");
                player.sendMessage(ChatColor.GREEN + "/TrailGUI Version");
                return true;
            }
            if (args[0].equalsIgnoreCase("ReloadConfigs"))
            {
                if (!player.hasPermission("trailgui.commands.reloadconfigs"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                TrailData.reloadConfig();
                TrailData.saveConfig();

                Main.getPlugin().reloadConfig();
                Main.getPlugin().saveConfig();

                player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "TrailGUI" + ChatColor.DARK_GRAY + "]: " + ChatColor.GREEN + "Successfully reloaded all config files.");
                return true;
            }
            if (args[0].equalsIgnoreCase("Version"))
            {
                if (!player.hasPermission("trailgui.commands.version"))
                {
                    player.sendMessage(this.main.getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "TrailGUI" + ChatColor.DARK_GRAY + "]: " + ChatColor.GREEN + "Version 3.1" + ChatColor.GREEN + " created by " + ChatColor.BOLD + "Coder_M" + ChatColor.GREEN + ".");
                return true;
            }
        }

        return false;
    }
}