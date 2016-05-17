package ca.jamiesinn.trailgui.trails;

import ca.jamiesinn.trailgui.ParticleManager;
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
        this.type = sType;
    }

    @Override
    public void justDisplay(Player player)
    {
        if (type == null)
        {
            return;
        }
        ParticleManager.spawnParticle(player, displayLocation, amount, cooldown, speed, range, type);
    }
}
