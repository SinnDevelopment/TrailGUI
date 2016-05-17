package ca.jamiesinn.trailgui.trails.v1_9_x;

import ca.jamiesinn.trailgui.util.ITrailManager;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class TrailManager implements ITrailManager
{

    @Override
    public void spawnParticle(Player player, double location, int amount, int cooldown, float speed, int range, String type)
    {
        player.getWorld().spawnParticle(Particle.valueOf(type.toUpperCase()), player.getLocation().add(0.0D, location, 0.0D), amount, 0, 0, 0, speed);
    }

    @Override
    public void spawnItemParticle(Player player, double location, int amount, float speed, int range, String type, ItemStack data)
    {
        player.getWorld().spawnParticle(Particle.valueOf(type), player.getLocation().add(0.0D, location, 0.0D), amount, 0,0,0, speed, data);
    }

    @Override
    public void spawnBlockParticle(Player player, double location, int amount, float speed, int range, String type, MaterialData data)
    {
        player.getWorld().spawnParticle(Particle.valueOf(type), player.getLocation().add(0.0D, location, 0.0D), amount, 0,0,0, speed, data);
    }

}
