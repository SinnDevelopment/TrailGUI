package ca.jamiesinn.trailgui.trails;

import ca.jamiesinn.trailgui.Main;
import ca.jamiesinn.trailgui.api.TrailDisplayEvent;
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
        TrailDisplayEvent event = new TrailDisplayEvent(this.getName(),
                this.getDisplayLocation(), this.getAmount(), this.cooldown, this.speed, this.range, this.type);
        Main.getPlugin().getServer().getPluginManager().callEvent(event);
        if(!event.isCancelled())
            type.display(0.0F, 0.0F, 0.0F, speed, amount, player.getLocation().add(0.0D, displayLocation, 0.0D), range);
    }
}
