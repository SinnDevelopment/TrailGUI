package ca.jamiesinn.trailgui.files;

import ca.jamiesinn.trailgui.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class TrailData
{
    public static FileConfiguration config;
    public static File file = new File(Main.getPlugin().getDataFolder(), "TrailData");

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void createFile()
    {
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            } catch (Exception ex)
            {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "Failed to generate the file: TrailData");
            }
            config = YamlConfiguration.loadConfiguration(file);
            config.createSection("AngryVillager");
            config.createSection("Cloud");
            config.createSection("Criticals");
            config.createSection("DripLava");
            config.createSection("DripWater");
            config.createSection("Enchantment");
            config.createSection("Spark");
            config.createSection("Flame");
            config.createSection("HappyVillager");
            config.createSection("InstantSpell");
            config.createSection("LargeSmoke");
            config.createSection("Lava");
            config.createSection("MagicCrit");
            config.createSection("MobSpell");
            config.createSection("MobSpellAmbient");
            config.createSection("Note");
            config.createSection("Portal");
            config.createSection("RedDust");
            config.createSection("ColoredRedDust");
            config.createSection("Slime");
            config.createSection("SnowShovel");
            config.createSection("SnowballPoof");
            config.createSection("Spell");
            config.createSection("Splash");
            config.createSection("TownAura");
            config.createSection("Wake");
            config.createSection("WitchMagic");
            config.createSection("Hearts");
            config.createSection("EnderSignal");
            config.createSection("IconCrack");
            config.createSection("BlockBreak");
            saveConfig();
        }
    }

    public static FileConfiguration getConfig()
    {
        return config;
    }

    public static void reloadConfig()
    {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static void saveConfig()
    {
        try
        {
            config.save(file);
        } catch (IOException ex)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "Could not save the file: TrailData");
        }
    }
}