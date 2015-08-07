package ca.jamiesinn.trailgui.files;

import ca.jamiesinn.trailgui.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Userdata
{
    private static Userdata instance;
    private FileConfiguration config;
    private String FILENAME = "Userdata.yml";
    private File file;


    public Userdata()
    {
        instance = this;
        file = new File(Main.getPlugin().getDataFolder(), FILENAME);
    }

    public static Userdata getInstance()
    {
        return instance;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void createFile()
    {
        try
        {
            file.createNewFile();
        } catch (Exception ex)
        {
            Main.getPlugin().getLogger().warning(ChatColor.DARK_RED + "Failed to generate the file: " + FILENAME);
        }
    }

    public void loadConfig()
    {
        if (!file.exists())
        {
            createFile();
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig()
    {
        return config;
    }

    public void reloadConfig()
    {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveConfig()
    {
        try
        {
            config.save(file);
        } catch (IOException ex)
        {
            Main.getPlugin().getLogger().warning(ChatColor.DARK_RED + "Could not save the file: " + FILENAME);
        }
    }
}