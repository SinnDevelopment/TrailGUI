package ca.jamiesinn.trailgui.commands;

import ca.jamiesinn.trailgui.Main;
import ca.jamiesinn.trailgui.Methods;
import ca.jamiesinn.trailgui.trails.Trail;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandTrail implements CommandExecutor, TabCompleter
{
    Main main;

    public CommandTrail(Main main)
    {
        this.main = main;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        List<String> trailList = new ArrayList<String>();
        trailList.addAll(Main.trailTypes.keySet());
        trailList.add("ClearAll");
        if (args.length == 1)
        {
            Collections.sort(trailList);
            return trailList;
        }
        return null;
    }

    private void showList(Player player)
    {
        player.sendMessage(Main.prefix + ChatColor.GREEN + "Available trails:");
        String list = ChatColor.RED + "values: " + ChatColor.GREEN;
        for (Trail type : Main.trailTypes.values())
        {
            if (type.canUse(player))
            {
                list += type.getName() + ", ";
            }
        }
        list = list.substring(0, list.length() - 2);
        player.sendMessage(list);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        List<Trail> trails = new ArrayList<Trail>(Main.trailTypes.values());
        if (!(sender instanceof Player))
        {
            for (Trail trail : trails)
            {
                if (trail.onCommand(sender, args))
                {
                    return true;
                }
            }
        }
        Player player = (Player) sender;
        for (String string : Main.disabledWorlds)
        {
            string = string.replace("[", "");
            string = string.replace("]", "");
            if (string.equals(player.getWorld().getName()))
            {
                player.sendMessage(Main.prefix + ChatColor.GREEN + "You cannot use this command in this world.");
                return false;
            }
            if (args.length == 0)
            {
                player.sendMessage(Main.prefix + ChatColor.GREEN + "Available commands:");
                player.sendMessage(ChatColor.GREEN + "/trail <TrailName>");
                player.sendMessage(ChatColor.GREEN + "/trail <TrailName> <PlayerName>");
                showList(player);
                return true;
            }


            for (Trail trail : trails)
            {
                if (trail.onCommand(player, args))
                {
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("clearall"))
            {
                if (!player.hasPermission("trailgui.commands.clearall"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "\u00A7"));
                    return false;
                }
                if (args.length == 1)
                {
                    Methods.clearTrails(player);
                    player.sendMessage(this.main.getConfig().getString("ClearAll-message").replaceAll("&", "\u00A7").replaceAll("%TrailName%", "ClearAll"));
                    return true;
                }
                if (!player.hasPermission("trailgui.commands.clearall.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "\u00A7"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "\u00A7").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", "ClearAll"));
                    return false;
                }
                Methods.clearTrails(target);
                target.sendMessage(this.main.getConfig().getString("ClearAll-targetMessage").replaceAll("&", "\u00A7").replaceAll("%Sender%", player.getName()));
                player.sendMessage(this.main.getConfig().getString("ClearAll-senderMessage").replaceAll("&", "\u00A7").replaceAll("%Target%", args[1]));
                return false;
            }
            else
            {
                player.sendMessage(Main.prefix + ChatColor.DARK_RED + "That's not a valid trail.");
                showList(player);
            }
            //endregion
        }

        return false;
    }
}