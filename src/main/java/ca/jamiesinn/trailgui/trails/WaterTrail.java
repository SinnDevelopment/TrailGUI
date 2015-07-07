package ca.jamiesinn.trailgui.trails;

import com.darkblade12.particleeffect.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * Created by Jamie on 7/7/2015.
 */
public class WaterTrail extends Trail
{
    ParticleEffect.ParticleProperty property;
    public WaterTrail(ConfigurationSection config)
    {
        super(config);
    }
    @Override protected void loadType(String sType)
    {
        this.type = ParticleEffect.valueOf(sType);
    }

    @Override public void justDisplay(Player player)
    {
        if(type == null || !isWater(player.getLocation()))
        {
            return;
        }
        type.display(player.getVelocity(), speed, player.getLocation().add(0.0D, displayLocation, 0.0D), range);
    }
    private static boolean isWater(Location location) {
        Material material = location.getBlock().getType();
        return material == Material.WATER || material == Material.STATIONARY_WATER;
    }
}
