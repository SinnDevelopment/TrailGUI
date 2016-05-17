package ca.jamiesinn.trailgui;

import ca.jamiesinn.trailgui.util.ITrailManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class ParticleManager
{
    private static final String VERSION = org.bukkit.Bukkit.getServer().getClass().getCanonicalName().split("\\.")[3];
    private static ITrailManager MANAGER;

    static
    {
        try
        {
            Class<?> managerClass = Class.forName("ca.jamiesinn.trails." + (VERSION.contains("1_9") ? "1_9_x" : "1_8_x") + ".TrailManager");
            MANAGER = (ITrailManager) managerClass.newInstance();
        }
        catch (InstantiationException | IllegalAccessException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void spawnParticle(Player player, double location, int amount, int cooldown, float speed, int range, String type)
    {
        MANAGER.spawnParticle(player, location, amount, 0, speed, 0, type);
    }
    public static void spawnItemParticle(Player player, double location, int amount, int cooldown, float speed, int range, String type, ItemStack item)
    {
        MANAGER.spawnItemParticle(player, location, amount, speed, range, type, item);
    }
    public static void spawnBlockParticle(Player player, double location, int amount, int cooldown, float speed, int range, String type, MaterialData data)
    {
        MANAGER.spawnBlockParticle(player, location, amount, speed, range, type, data);
    }
}
