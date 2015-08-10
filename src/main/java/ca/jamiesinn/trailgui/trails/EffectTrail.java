package ca.jamiesinn.trailgui.trails;

import ca.jamiesinn.trailgui.Main;
import ca.jamiesinn.trailgui.api.TrailDisplayEvent;
import org.bukkit.Effect;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class EffectTrail extends Trail
{
    Effect effect;

    public EffectTrail(ConfigurationSection config)
    {
        super(config);
        loadType(config.getString("type"));
    }

    @Override
    protected void loadType(String sType)
    {
        this.effect = Effect.valueOf(sType);
    }

    @Override
    public void justDisplay(Player player)
    {
        TrailDisplayEvent event = new TrailDisplayEvent(this.getName(),
                this.getDisplayLocation(), this.getAmount(), this.cooldown, this.speed, this.range, this.type);
        Main.getPlugin().getServer().getPluginManager().callEvent(event);
        if(!event.isCancelled())
            player.getWorld().playEffect(player.getLocation(), this.effect, 1);
    }
}
