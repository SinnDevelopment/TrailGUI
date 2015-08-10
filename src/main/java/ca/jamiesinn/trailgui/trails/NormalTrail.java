package ca.jamiesinn.trailgui.trails;

import com.darkblade12.particleeffect.ParticleEffect;
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
        this.type = ParticleEffect.valueOf(sType);
    }

    @Override
    public void justDisplay(Player player)
    {
        if (type == null)
        {
            return;
        }
        if(!displayEvent(getName(), getDisplayLocation(), getAmount(), cooldown, getSpeed(), getRange(), type).isCancelled())
            type.display(0.0F, 0.0F, 0.0F, speed, amount, player.getLocation().add(0.0D, displayLocation, 0.0D), range);
    }
}
