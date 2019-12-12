package ca.jamiesinn.trailgui.trails;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class RedstoneTrail extends Trail
{
    private Particle.DustOptions dustOptions;

    public RedstoneTrail(ConfigurationSection config)
    {
        super(config);
        Color color = config.getColor("dustColor", Color.RED);
        float size = (float) config.getDouble("dustSize", 1);
        dustOptions = new Particle.DustOptions(color, size);
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
            player.getWorld().spawnParticle(type, player.getLocation().add(0.0D, displayLocation, 0.0D), amount, 0,0,0, speed, dustOptions);
    }
}
