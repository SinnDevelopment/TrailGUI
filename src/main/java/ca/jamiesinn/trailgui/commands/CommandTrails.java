package ca.jamiesinn.trailgui.commands;

import ca.jamiesinn.trailgui.TrailGUI;
import ca.jamiesinn.trailgui.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTrails
        implements CommandExecutor
{
    private TrailGUI trailGUI;

    public CommandTrails(TrailGUI trailGUI)
    {
        this.trailGUI = trailGUI;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(TrailGUI.prefix + ChatColor.RED + "Only players can perform this command.");
            return true;
        }
        Player player = (Player) sender;
        for (String string : TrailGUI.disabledWorlds)
        {
            string = string.replace("[", "");
            string = string.replace("]", "");
            if (string.equals(player.getWorld().getName()))
            {
                player.sendMessage(TrailGUI.prefix + ChatColor.GREEN + "You cannot use this command in this world.");
                return true;
            }
            if (!player.hasPermission("trailgui.commands.trails") && !player.hasPermission("trailgui.*"))
            {
                player.sendMessage(TrailGUI.getPlugin().getConfig().getString("Commands.denyPermissionMessage").replaceAll("&", "\u00A7"));
                if (TrailGUI.getPlugin().getConfig().getBoolean("closeInventoryOnDenyPermission"))
                {
                    player.closeInventory();
                }
                return true;
            }
            Util.openGUI(player);
        }
        return false;
    }
}