package ca.jamiesinn.trailgui.trails;

import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class SculkChargeTrail extends Trail
{
    public SculkChargeTrail(ConfigurationSection config)
    {
        super(config);
        loadType(config.getString("type"));
    }

    @Override
    protected void loadType(String sType)
    {
        this.type = Particle.valueOf(sType);
    }

    @Override
    public void justDisplay(Player player)
    {
        if(!displayEvent(getName(), getDisplayLocation(), getAmount(), cooldown, getSpeed(), getRange(), type).isCancelled())
            player.getWorld().spawnParticle(type, player.getLocation().add(0.0D, displayLocation, 0.0D), amount, 0,0,0, speed, (float) 0);
    }
}
