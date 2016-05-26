package ca.jamiesinn.trailgui.trails;

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
        if(!displayEvent(getName(), getDisplayLocation(), getAmount(), cooldown, getSpeed(), getRange(), type).isCancelled())
            player.getWorld().playEffect(player.getLocation(), this.effect, 1);
    }
}
