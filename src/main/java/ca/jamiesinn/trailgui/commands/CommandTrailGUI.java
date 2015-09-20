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
        for (String string : Main.disabledWorlds)
        {

            string = string.replace("[", "");
            string = string.replace("]", "");
            if (sender instanceof Player)
            {
                Player player = (Player) sender;
                if (string.equals(player.getWorld().getName()))
                {
                    player.sendMessage(Main.prefix + ChatColor.RED + "You cannot use this command in this world.");
                    return false;
                }
            }
            if (args.length == 0)
            {
                sender.sendMessage(Main.prefix + ChatColor.GREEN + "Available commands:");
                sender.sendMessage(ChatColor.GREEN + "/trailgui reload");
                sender.sendMessage(ChatColor.GREEN + "/trailgui version");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload"))
            {
                if (!sender.hasPermission("trailgui.commands.reloadconfigs"))
                {
                    sender.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "\u00A7"));
                    return false;
                }
                Userdata.getInstance().reloadConfig();
                Userdata.getInstance().saveConfig();

                Main.getPlugin().reload();

                sender.sendMessage(Main.prefix + ChatColor.GREEN + "Successfully reloaded all config files.");
                return true;
            }
            if (args[0].equalsIgnoreCase("version"))
            {
                if (!sender.hasPermission("trailgui.commands.version"))
                {
                    sender.sendMessage(this.main.getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "\u00A7"));
                    return false;
                }
                sender.sendMessage(Main.prefix + ChatColor.GREEN + "Version"
                        + this.main.getDescription().getVersion()
                        + ChatColor.GREEN + " created by " + ChatColor.BOLD + "Coder_M, JamieSinn, and kukelekuuk00" + ChatColor.GREEN + ".");
                return true;
            }
        }

        return false;
    }
}