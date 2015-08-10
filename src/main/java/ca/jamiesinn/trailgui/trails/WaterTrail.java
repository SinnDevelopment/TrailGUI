package ca.jamiesinn.trailgui.trails;

import ca.jamiesinn.trailgui.Main;
import ca.jamiesinn.trailgui.api.TrailDisplayEvent;
import com.darkblade12.particleeffect.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class WaterTrail extends Trail
{
    ParticleEffect.ParticleProperty property;

    public WaterTrail(ConfigurationSection config)
    {
        super(config);
    }

    private static boolean isWater(Location location)
    {
        Material material = location.getBlock().getType();
        return material == Material.WATER || material == Material.STATIONARY_WATER;
    }

    @Override protected void loadType(String sType)
    {
        this.type = ParticleEffect.valueOf(sType);
    }

    @Override public void justDisplay(Player player)
    {
        if (type == null || !isWater(player.getLocation()))
        {
            return;
        }
        TrailDisplayEvent event = new TrailDisplayEvent(getName(),
                getDisplayLocation(), getAmount(), cooldown, speed, range, type);
        Main.getPlugin().getServer().getPluginManager().callEvent(event);
        if(!event.isCancelled())
            type.display(player.getVelocity(), speed, player.getLocation().add(0.0D, displayLocation, 0.0D), range);
    }
}
