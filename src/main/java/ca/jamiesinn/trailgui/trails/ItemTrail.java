package ca.jamiesinn.trailgui.trails;

import com.darkblade12.particleeffect.ParticleEffect;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ItemTrail extends Trail
{
    ParticleEffect.ItemData data;
    int itemData;

    public ItemTrail(ConfigurationSection config)
    {
        super(config);
        itemData = config.getInt("data", 0);
        data = new ParticleEffect.ItemData(itemType, (byte) itemData);
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
            type.display(data, player.getLocation().getDirection(), speed, player.getLocation().add(0.0D, displayLocation, 0.0D), range);
    }
}
