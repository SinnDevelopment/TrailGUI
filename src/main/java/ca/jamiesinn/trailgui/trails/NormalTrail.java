package ca.jamiesinn.trailgui.trails;

import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class NormalTrail extends Trail
{
    public NormalTrail(ConfigurationSection config)
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
        if (type == null)
        {
            return;
        }
        if(!displayEvent(getName(), getDisplayLocation(), getAmount(), cooldown, getSpeed(), getRange(), type).isCancelled())
            player.getWorld().spawnParticle(type, player.getLocation().add(0.0D, displayLocation, 0.0D), amount, 0, 0, 0, speed);
    }
}
