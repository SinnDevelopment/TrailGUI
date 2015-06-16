package ca.jamiesinn.trailsgui.commands;

import ca.jamiesinn.trailsgui.Main;
import ca.jamiesinn.trailsgui.Methodes;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Trails
        implements CommandExecutor
{
    Main main;

    public Trails(Main main)
    {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.DARK_RED + "[TrailGUI] Only players can perform this command.");
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
            if (!player.hasPermission("trailgui.commands.trails"))
            {
                player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                if (Main.getPlugin().getConfig().getBoolean("closeInventoryOnDenyPermission"))
                {
                    player.closeInventory();
                }
                return false;
            }
            Methodes.openGUI1(player);
        }

        return false;
    }
}