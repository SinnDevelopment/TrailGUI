package ca.jamiesinn.trailgui;

import ca.jamiesinn.trailgui.commands.CommandTrail;
import ca.jamiesinn.trailgui.commands.CommandTrailGUI;
import ca.jamiesinn.trailgui.commands.CommandTrails;
import ca.jamiesinn.trailgui.files.Userdata;
import ca.jamiesinn.trailgui.trails.*;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Main
        extends JavaPlugin
{
    public static Main plugin;
    public static String prefix;
    public static boolean removeTrailOnPlayerHit;
    public static boolean oneTrailAtATime;
    public static int maxTrails;
    public static List<String> disabledWorlds;
    public static Map<UUID, List<Trail>> enabledTrails = new HashMap<UUID, List<Trail>>();
    public static Map<String, Trail> trailTypes = new HashMap<String, Trail>();

    public static Main getPlugin()
    {
        return plugin;
    }

    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(new Listeners(this), this);
        getCommand("trail").setExecutor(new CommandTrail(this));
        getCommand("trails").setExecutor(new CommandTrails(this));
        getCommand("trailgui").setExecutor(new CommandTrailGUI(this));
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        plugin = this;
        load();
    }

    private void load()
    {
        reloadConfig();
        saveConfig();
        maxTrails = getConfig().getInt("maxActiveTrails");
        oneTrailAtATime = getConfig().getBoolean("oneTrailAtATime", false);
        prefix = getConfig().getString("prefix").replaceAll("&", "\u00A7");
        if(prefix == null)
        {
            getLogger().info(ChatColor.RED + "Warning - You have either no value for the prefix - or you have an outdated config. Please update it.");
            prefix = ChatColor.DARK_GRAY + "[" + ChatColor.RED + "TrailGUI" + ChatColor.DARK_GRAY + "] ";
        }
        removeTrailOnPlayerHit = getConfig().getBoolean("removeTrailOnPlayerHit", false);
        disabledWorlds = getConfig().getStringList("disabledWorlds");
        new Userdata().loadConfig();
        loadTrails();
        Methods.restoreTrails();
    }

    public void reload()
    {
        trailTypes.clear();
        enabledTrails.clear();
        load();
    }

    private void loadTrails()
    {
        if(getConfig().isConfigurationSection("trails"))
        {
            ConfigurationSection section = getConfig().getConfigurationSection("trails");
            for(String key : section.getKeys(false))
            {
                if(section.isConfigurationSection(key))
                {
                    ConfigurationSection trailTypeSection = section.getConfigurationSection(key);
                    try
                    {
                        if(trailTypeSection.getString("type").equalsIgnoreCase("ITEM_CRACK"))
                        {
                            trailTypes.put(trailTypeSection.getName(), new ItemTrail(trailTypeSection));
                        }
                        else if(trailTypeSection.getString("type").equalsIgnoreCase("BLOCK_CRACK"))
                        {
                            trailTypes.put(trailTypeSection.getName(), new BlockTrail(trailTypeSection));
                        }
                        else if(trailTypeSection.getBoolean("is_effect", false))
                        {
                            trailTypes.put(trailTypeSection.getName(), new EffectTrail(trailTypeSection));
                        }
                        else
                        {
                            trailTypes.put(trailTypeSection.getName(), new NormalTrail(trailTypeSection));
                        }
                    }
                    catch(Exception ex)
                    {
                        getLogger().warning("Failed to load '" + trailTypeSection.getName() + "'. Error: " + ex.getMessage());
                    }

                }
            }
        }
    }

    @Override
    public void onDisable()
    {
        Methods.saveTrails();
    }
}