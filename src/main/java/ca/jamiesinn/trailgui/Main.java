package ca.jamiesinn.trailgui;

import ca.jamiesinn.trailgui.commands.Trail;
import ca.jamiesinn.trailgui.commands.TrailGUI;
import ca.jamiesinn.trailgui.commands.Trails;
import ca.jamiesinn.trailgui.files.TrailData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main
        extends JavaPlugin
{
    public static Main plugin;
    public static List<String> trailAngryVillager = new ArrayList();
    public static List<String> trailCloud = new ArrayList();
    public static List<String> trailCriticals = new ArrayList();
    public static List<String> trailDripLava = new ArrayList();
    public static List<String> trailDripWater = new ArrayList();
    public static List<String> trailEnchantment = new ArrayList();
    public static List<String> trailSpark = new ArrayList();
    public static List<String> trailFlame = new ArrayList();
    public static List<String> trailHappyVillager = new ArrayList();
    public static List<String> trailInstantSpell = new ArrayList();
    public static List<String> trailLargeSmoke = new ArrayList();
    public static List<String> trailLava = new ArrayList();
    public static List<String> trailMagicCrit = new ArrayList();
    public static List<String> trailMobSpell = new ArrayList();
    public static List<String> trailMobSpellAmbient = new ArrayList();
    public static List<String> trailNote = new ArrayList();
    public static List<String> trailPortal = new ArrayList();
    public static List<String> trailRedDust = new ArrayList();
    public static List<String> trailColoredRedDust = new ArrayList();
    public static List<String> trailSlime = new ArrayList();
    public static List<String> trailSnowShovel = new ArrayList();
    public static List<String> trailSnowballPoof = new ArrayList();
    public static List<String> trailSpell = new ArrayList();
    public static List<String> trailSplash = new ArrayList();
    public static List<String> trailTownAura = new ArrayList();
    public static List<String> trailWake = new ArrayList();
    public static List<String> trailWitchMagic = new ArrayList();
    public static List<String> trailHearts = new ArrayList();
    public static List<String> trailEnderSignal = new ArrayList();
    public static List<String> trailIconCrack = new ArrayList<String>();
    public static Main getPlugin()
    {
        return plugin;
    }

    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(new Listeners(this), this);

        getCommand("Trail").setExecutor(new Trail(this));
        getCommand("Trails").setExecutor(new Trails(this));
        getCommand("TrailGUI").setExecutor(new TrailGUI(this));

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        plugin = this;
        TrailData.createFile();
        TrailData.config = YamlConfiguration.loadConfiguration(TrailData.file);
        Methodes.restoreTrails();
    }

    @Override
    public void onDisable()
    {
        Methodes.saveTrails();
    }
}