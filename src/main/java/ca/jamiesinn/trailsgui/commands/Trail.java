package ca.jamiesinn.trailsgui.commands;

import ca.jamiesinn.trailsgui.Main;
import ca.jamiesinn.trailsgui.Methodes;
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

public class Trail
        implements CommandExecutor, TabCompleter
{
    List<String> trailList1 = new ArrayList();
    List<String> trailList2 = new ArrayList();
    Main main;

    public Trail(Main main)
    {
        this.main = main;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if (args.length == 1)
        {
            this.trailList1.clear();
            this.trailList1.add("AngryVillager");
            this.trailList1.add("Cloud");
            this.trailList1.add("Criticals");
            this.trailList1.add("DripLava");
            this.trailList1.add("DripWater");
            this.trailList1.add("Enchantment");
            this.trailList1.add("Spark");
            this.trailList1.add("Flame");
            this.trailList1.add("HappyVillager");
            this.trailList1.add("InstantSpell");
            this.trailList1.add("LargeSmoke");
            this.trailList1.add("Lava");
            this.trailList1.add("MagicCrit");
            this.trailList1.add("MobSpell");
            this.trailList1.add("MobSpellAmbient");
            this.trailList1.add("Note");
            this.trailList1.add("Portal");
            this.trailList1.add("RedDust");
            this.trailList1.add("ColoredRedDust");
            this.trailList1.add("Slime");
            this.trailList1.add("SnowShovel");
            this.trailList1.add("SnowballPoof");
            this.trailList1.add("Spell");
            this.trailList1.add("Splash");
            this.trailList1.add("TownAura");
            this.trailList1.add("Wake");
            this.trailList1.add("WitchMagic");
            this.trailList1.add("Hearts");
            this.trailList1.add("EnderSignal");
            this.trailList1.add("IconCrack");
            this.trailList1.add("ClearAll");

            Collections.sort(this.trailList1);
            return this.trailList1;
        }
        if (args[0].equals(""))
        {
            this.trailList2.clear();
            this.trailList2.add("AngryVillager");
            this.trailList2.add("Cloud");
            this.trailList2.add("Criticals");
            this.trailList2.add("DripLava");
            this.trailList2.add("DripWater");
            this.trailList2.add("Enchantment");
            this.trailList2.add("Spark");
            this.trailList2.add("Flame");
            this.trailList2.add("HappyVillager");
            this.trailList2.add("InstantSpell");
            this.trailList2.add("LargeSmoke");
            this.trailList2.add("Lava");
            this.trailList2.add("MagicCrit");
            this.trailList2.add("MobSpell");
            this.trailList2.add("MobSpellAmbient");
            this.trailList2.add("Note");
            this.trailList2.add("Portal");
            this.trailList2.add("RedDust");
            this.trailList2.add("ColoredRedDust");
            this.trailList2.add("Slime");
            this.trailList2.add("SnowShovel");
            this.trailList2.add("SnowballPoof");
            this.trailList2.add("Spell");
            this.trailList2.add("Splash");
            this.trailList2.add("TownAura");
            this.trailList2.add("Wake");
            this.trailList2.add("WitchMagic");
            this.trailList2.add("Hearts");
            this.trailList2.add("IconCrack");
            this.trailList2.add("EnderSignal");
            this.trailList2.add("ClearAll");
            for (String trails : this.trailList2)
            {
                if (!trails.toLowerCase().startsWith(args[0].toLowerCase()))
                {
                    this.trailList2.remove(trails);
                }
            }
            Collections.sort(this.trailList2);
            return this.trailList2;
        }

        return null;
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
            if (args.length == 0)
            {
                player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "TrailGUI" + ChatColor.DARK_GRAY + "]: " + ChatColor.GREEN + "Available commands:");
                player.sendMessage(ChatColor.GREEN + "/trail <TrailName>");
                player.sendMessage(ChatColor.GREEN + "/trail <TrailName> <PlayerName>");
                return true;
            }
            //region ARG 0 PARSING
            if (args[0].equalsIgnoreCase("AngryVillager") || args[0].equalsIgnoreCase("angry"))
            {
                if (!player.hasPermission("trailgui.trails.angryvillager"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailAngryVillager.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailAngryVillager.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "AngryVillager"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailAngryVillager.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "AngryVillager"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.angryvillager.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "AngryVillager"));
                    return false;
                }
                if (Main.trailAngryVillager.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailAngryVillager.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "AngryVillager"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailAngryVillager.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "AngryVillager").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "AngryVillager"));
                return true;
            }
            if (args[0].equalsIgnoreCase("Cloud"))
            {
                if (!player.hasPermission("trailgui.trails.cloud"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailCloud.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailCloud.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Cloud"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailCloud.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Cloud"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.cloud.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Cloud"));
                    return false;
                }
                if (Main.trailCloud.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailCloud.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]).replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Cloud"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailCloud.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Cloud"));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Cloud"));
                return false;
            }
            if (args[0].equalsIgnoreCase("Criticals"))
            {
                if (!player.hasPermission("trailgui.trails.criticals"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailCriticals.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailCriticals.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Criticals"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailCriticals.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Criticals"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.criticals.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Criticals"));
                    return false;
                }
                if (Main.trailCriticals.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailCriticals.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Criticals"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailCriticals.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Criticals").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Criticals"));
                return false;
            }
            if (args[0].equalsIgnoreCase("DripLava"))
            {
                if (!player.hasPermission("trailgui.trails.driplava"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailDripLava.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailDripLava.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "DripLava"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailDripLava.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "DripLava"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.driplava.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "DripLava"));
                    return false;
                }
                if (Main.trailDripLava.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailDripLava.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "DripLava"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailDripLava.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "DripLava").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "DripLava"));
                return false;
            }
            if (args[0].equalsIgnoreCase("DripWater"))
            {
                if (!player.hasPermission("trailgui.trails.dripwater"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailDripWater.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailDripWater.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "DripWater"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailDripWater.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "DripWater"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.dripwater.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "DripWater"));
                    return false;
                }
                if (Main.trailDripWater.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailDripWater.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "DripWater"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailDripWater.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "DripWater").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "DripWater"));
                return false;
            }
            if (args[0].equalsIgnoreCase("Enchantment"))
            {
                if (!player.hasPermission("trailgui.trails.enchantment"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailEnchantment.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailEnchantment.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Enchantment"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailEnchantment.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Enchantment"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.enchantment.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Enchantment"));
                    return false;
                }
                if (Main.trailEnchantment.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailEnchantment.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Enchantment"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailEnchantment.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Enchantment").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Enchantment"));
                return false;
            }
            if (args[0].equalsIgnoreCase("Spark"))
            {
                if (!player.hasPermission("trailgui.trails.spark"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailSpark.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailSpark.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Spark"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailSpark.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Spark"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.spark.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Spark"));
                    return false;
                }
                if (Main.trailSpark.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailSpark.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Spark"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailSpark.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Spark").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Spark"));
                return false;
            }
            if (args[0].equalsIgnoreCase("Flame"))
            {
                if (!player.hasPermission("trailgui.trails.flame"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailFlame.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailFlame.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Flame"));
                        return false;
                    }
                    Main.trailFlame.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Flame"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.flame.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Flame"));
                    return false;
                }
                if (Main.trailFlame.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailFlame.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Flame"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailFlame.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Flame").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Flame"));
                return false;
            }
            if (args[0].equalsIgnoreCase("HappyVillager") || args[0].equalsIgnoreCase("happy"))
            {
                if (!player.hasPermission("trailgui.trails.happyvillager"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailHappyVillager.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailHappyVillager.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "HappyVillager"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailHappyVillager.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "HappyVillager"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.happyvillager.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "HappyVillager"));
                    return false;
                }
                if (Main.trailHappyVillager.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailHappyVillager.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "HappyVillager"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailHappyVillager.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "HappyVillager").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "HappyVillager"));
                return false;
            }
            if (args[0].equalsIgnoreCase("InstantSpell"))
            {
                if (!player.hasPermission("trailgui.trails.instantspell"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailInstantSpell.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailInstantSpell.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "InstantSpell"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailInstantSpell.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "InstantSpell"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.instantspell.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "InstantSpell"));
                    return false;
                }
                if (Main.trailInstantSpell.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailInstantSpell.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "InstantSpell"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailInstantSpell.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "InstantSpell").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "InstantSpell"));
                return false;
            }
            if (args[0].equalsIgnoreCase("LargeSmoke"))
            {
                if (!player.hasPermission("trailgui.trails.largesmoke"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailLargeSmoke.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailLargeSmoke.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "LargeSmoke"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailLargeSmoke.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "LargeSmoke"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.largesmoke.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "LargeSmoke"));
                    return false;
                }
                if (Main.trailLargeSmoke.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailLargeSmoke.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "LargeSmoke"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailLargeSmoke.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "LargeSmoke").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "LargeSmoke"));
                return false;
            }
            if (args[0].equalsIgnoreCase("Lava") || args[0].equalsIgnoreCase("erupt"))
            {
                if (!player.hasPermission("trailgui.trails.lava"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailLava.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailLava.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Lava"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailLava.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Lava"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.lava.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Lava"));
                    return false;
                }
                if (Main.trailLava.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailLava.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Lava"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailLava.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Lava").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Lava"));
                return false;
            }
            if (args[0].equalsIgnoreCase("MagicCrit"))
            {
                if (!player.hasPermission("trailgui.trails.magiccrit"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailMagicCrit.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailMagicCrit.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "MagicCrit"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailMagicCrit.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "MagicCrit"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.magiccrit.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "MagicCrit"));
                    return false;
                }
                if (Main.trailMagicCrit.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailMagicCrit.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "MagicCrit"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailMagicCrit.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "MagicCrit").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "MagicCrit"));
                return false;
            }
            if (args[0].equalsIgnoreCase("MobSpell") || args[0].equalsIgnoreCase("rainbowpotion"))
            {
                if (!player.hasPermission("trailgui.trails.mobspell"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailMobSpell.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);

                        Main.trailMobSpell.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "MobSpell"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailMobSpell.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "MobSpell"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.mobspell.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "MobSpell"));
                    return false;
                }
                if (Main.trailMobSpell.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailMobSpell.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "MobSpell"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailMobSpell.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "MobSpell").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "MobSpell"));
                return false;
            }
            if (args[0].equalsIgnoreCase("MobSpellAmbient") || args[0].equalsIgnoreCase("fadedrainbowpotion"))
            {
                if (!player.hasPermission("trailgui.trails.mobspellambient"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailMobSpellAmbient.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailMobSpellAmbient.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "MobSpellAmbient"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailMobSpellAmbient.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "MobSpellAmbient"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.mobspellambient.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "MobSpellAmbient"));
                    return false;
                }
                if (Main.trailMobSpellAmbient.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailMobSpellAmbient.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "MobSpellAmbient"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailMobSpellAmbient.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "MobSpellAmbient").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "MobSpellAmbient"));
                return false;
            }
            if (args[0].equalsIgnoreCase("Note"))
            {
                if (!player.hasPermission("trailgui.trails.note"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailNote.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailNote.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Note"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailNote.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Note"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.note.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Note"));
                    return false;
                }
                if (Main.trailNote.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailNote.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Note"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailNote.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Note").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Note"));
                return false;
            }
            if (args[0].equalsIgnoreCase("Portal"))
            {
                if (!player.hasPermission("trailgui.trails.portal"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailPortal.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailPortal.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Portal"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailPortal.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Portal"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.portal.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Portal"));
                    return false;
                }
                if (Main.trailPortal.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailPortal.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Portal"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailPortal.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Portal").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Portal"));
                return false;
            }
            if (args[0].equalsIgnoreCase("RedDust"))
            {
                if (!player.hasPermission("trailgui.trails.reddust"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailRedDust.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailRedDust.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "RedDust"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailRedDust.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "RedDust"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.reddust.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "RedDust"));
                    return false;
                }
                if (Main.trailRedDust.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailRedDust.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "RedDust"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailRedDust.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "RedDust").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "RedDust"));
                return false;
            }
            if (args[0].equalsIgnoreCase("ColoredRedDust") || args[0].equalsIgnoreCase("rainbowdust"))
            {
                if (!player.hasPermission("trailgui.trails.coloredreddust"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailColoredRedDust.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailColoredRedDust.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "ColoredRedDust"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailColoredRedDust.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "ColoredRedDust"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.coloredreddust.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "ColoredRedDust"));
                    return false;
                }
                if (Main.trailColoredRedDust.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailColoredRedDust.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "ColoredRedDust"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailColoredRedDust.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "ColoredRedDust").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "ColoredRedDust"));
                return false;
            }
            if (args[0].equalsIgnoreCase("Slime"))
            {
                if (!player.hasPermission("trailgui.trails.slime"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailSlime.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailSlime.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Slime"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailSlime.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Slime"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.slime.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Slime"));
                    return false;
                }
                if (Main.trailSlime.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailSlime.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Slime"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailSlime.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Slime").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Slime"));
                return false;
            }
            if (args[0].equalsIgnoreCase("SnowShovel"))
            {
                if (!player.hasPermission("trailgui.trails.snowshovel"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailSnowShovel.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailSnowShovel.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "SnowShovel"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailSnowShovel.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "SnowShovel"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.snowshovel.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "SnowShovel"));
                    return false;
                }
                if (Main.trailSnowShovel.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailSnowShovel.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "SnowShovel"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailSnowShovel.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "SnowShovel").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "SnowShovel"));
                return false;
            }
            if (args[0].equalsIgnoreCase("SnowballPoof"))
            {
                if (!player.hasPermission("trailgui.trails.snowballpoof"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailSnowballPoof.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailSnowballPoof.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "SnowballPoof"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailSnowballPoof.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "SnowballPoof"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.snowballpoof.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "SnowballPoof"));
                    return false;
                }
                if (Main.trailSnowballPoof.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailSnowballPoof.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "SnowballPoof"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailSnowballPoof.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "SnowballPoof").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "SnowballPoof"));
                return false;
            }
            if (args[0].equalsIgnoreCase("Spell"))
            {
                if (!player.hasPermission("trailgui.trails.spell"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailSpell.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailSpell.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Spell"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailSpell.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Spell"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.spell.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Spell"));
                    return false;
                }
                if (Main.trailSpell.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailSpell.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Spell"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailSpell.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Spell").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Spell"));
                return false;
            }
            if (args[0].equalsIgnoreCase("Splash"))
            {
                if (!player.hasPermission("trailgui.trails.splash"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailSplash.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailSplash.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Splash"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailSplash.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Splash"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.splash.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Splash"));
                    return false;
                }
                if (Main.trailSplash.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailSplash.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Splash"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailSplash.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Splash").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Splash"));
                return false;
            }
            if (args[0].equalsIgnoreCase("townaura"))
            {
                if (!player.hasPermission("trailgui.trails.townaura"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailTownAura.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailTownAura.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "TownAura"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailTownAura.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "TownAura"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.townaura.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "TownAura"));
                    return false;
                }
                if (Main.trailTownAura.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailTownAura.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "TownAura"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailTownAura.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "TownAura").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "TownAura"));
                return false;
            }
            if (args[0].equalsIgnoreCase("Wake"))
            {
                if (!player.hasPermission("trailgui.trails.wake"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailWake.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailWake.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Wake"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailWake.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Wake"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.wake.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Wake"));
                    return false;
                }
                if (Main.trailWake.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailWake.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Wake"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailWake.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Wake").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Wake"));
                return false;
            }
            if (args[0].equalsIgnoreCase("witchmagic") || args[0].equalsIgnoreCase("purplesparkle"))
            {
                if (!player.hasPermission("trailgui.trails.witchmagic"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailWitchMagic.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailWitchMagic.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "WitchMagic"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailWitchMagic.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "WitchMagic"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.witchmagic.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "WitchMagic"));
                    return false;
                }
                if (Main.trailWitchMagic.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailWitchMagic.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "WitchMagic"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailWitchMagic.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "WitchMagic").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "WitchMagic"));
                return false;
            }
            if (args[0].equalsIgnoreCase("hearts") || args[0].equalsIgnoreCase("heart"))
            {
                if (!player.hasPermission("trailgui.trails.hearts"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailHearts.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailHearts.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Hearts"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailHearts.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Hearts"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.hearts.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Hearts"));
                    return false;
                }
                if (Main.trailHearts.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailHearts.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Hearts"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailHearts.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Hearts").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "Hearts"));
                return false;
            }
            if (args[0].equalsIgnoreCase("endersignal"))
            {
                if (!player.hasPermission("trailgui.trails.endersignal"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailEnderSignal.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailEnderSignal.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "EnderSignal"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailEnderSignal.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "EnderSignal"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.endersignal.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayer(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "EnderSignal"));
                    return false;
                }
                if (Main.trailEnderSignal.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailEnderSignal.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]).replaceAll("%TrailName%", "EnderSignal"));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "EnderSignal"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailEnderSignal.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "EnderSignal").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "EnderSignal"));
                return false;
            }
            if (args[0].equalsIgnoreCase("iconcrack"))
            {
                if (!player.hasPermission("trailgui.trails.iconcrack"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    if (Main.trailIconCrack.contains(player.getUniqueId().toString()))
                    {
                        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                            Methodes.clearTrails(player);
                        Main.trailIconCrack.remove(player.getUniqueId().toString());
                        player.sendMessage(this.main.getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "IconCrack"));
                        return false;
                    }
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(player);
                    Main.trailIconCrack.add(player.getUniqueId().toString());
                    player.sendMessage(this.main.getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "§").replaceAll("%TrailName%", "IconCrack"));
                    return true;
                }
                if (!player.hasPermission("trailgui.trails.iconcrack.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayer(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "IconCrack"));
                    return false;
                }
                if (Main.trailIconCrack.contains(target.getUniqueId().toString()))
                {
                    if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                        Methodes.clearTrails(target);
                    Main.trailIconCrack.remove(target.getUniqueId().toString());

                    player.sendMessage(this.main.getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]).replaceAll("%TrailName%", "IconCrack"));
                    target.sendMessage(this.main.getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "IconCrack"));
                    return false;
                }
                if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
                    Methodes.clearTrails(target);
                Main.trailIconCrack.add(target.getUniqueId().toString());
                player.sendMessage(this.main.getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "§").replaceAll("%TrailName%", "IconCrack").replaceAll("%Target%", args[1]));
                target.sendMessage(this.main.getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "§").replaceAll("%TrailName%", "IconCrack"));
                return false;
            }
            if (args[0].equalsIgnoreCase("clearall"))
            {
                if (!player.hasPermission("trailgui.commands.clearall"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                if (args.length == 1)
                {
                    Methodes.clearTrails(player);
                    player.sendMessage(this.main.getConfig().getString("ClearAll-message").replaceAll("&", "§").replaceAll("%TrailName%", "ClearAll"));
                    return true;
                }
                if (!player.hasPermission("trailgui.commands.clearall.other"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "§"));
                    return false;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(this.main.getConfig().getString("noTargetMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                    return false;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(this.main.getConfig().getString("targetSelfMessage").replaceAll("&", "§").replaceAll("%TrailName%", "ClearAll"));
                    return false;
                }
                Methodes.clearTrails(target);
                target.sendMessage(this.main.getConfig().getString("ClearAll-targetMessage").replaceAll("&", "§").replaceAll("%Sender%", player.getName()));
                player.sendMessage(this.main.getConfig().getString("ClearAll-senderMessage").replaceAll("&", "§").replaceAll("%Target%", args[1]));
                return false;
            }
            //endregion
        }

        return false;
    }

    private void checkTrailLimits(Player target)
    {
        if (Main.getPlugin().getConfig().getBoolean("oneTrailAtATime"))
            Methodes.clearTrails(target);
    }
}