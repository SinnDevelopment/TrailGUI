package ca.jamiesinn.trailgui.commands;

import ca.jamiesinn.trailgui.Main;
import ca.jamiesinn.trailgui.files.Userdata;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTrailGUI
        implements CommandExecutor
{
    Main main;

    public CommandTrailGUI(Main main)
    {
        this.main = main;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("TrailGUI"))
        {
            if (!(sender instanceof Player))
            {
                sender.sendMessage(ChatColor.DARK_RED + "[TrailGUI] Only players can perform this command");
                return true;
            }
            Player player = (Player) sender;
            for(String string : Main.disabledWorlds)
            {
                string = string.replace("[", "");
                string = string.replace("]", "");
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
                        player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "\u00A7"));
                        return false;
                    }
                    Userdata.getInstance().reloadConfig();
                    Userdata.getInstance().saveConfig();

                    Main.getPlugin().reload();

                    player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "TrailGUI" + ChatColor.DARK_GRAY + "]: " + ChatColor.GREEN + "Successfully reloaded all config files.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("Version"))
                {
                    if (!player.hasPermission("trailgui.commands.version"))
                    {
                        player.sendMessage(this.main.getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "\u00A7"));
                        return false;
                    }
                    player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "TrailGUI" + ChatColor.DARK_GRAY + "]: " + ChatColor.GREEN + "Version"
                            + this.main.getDescription().getVersion()
                            + ChatColor.GREEN + " created by " + ChatColor.BOLD + "Coder_M, JamieSinn, and kukelekuuk00" + ChatColor.GREEN + ".");
                    return true;
                }
            }
        }
        return false;
    }
}