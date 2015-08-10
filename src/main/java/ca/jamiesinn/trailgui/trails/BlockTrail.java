package ca.jamiesinn.trailgui.trails;

import ca.jamiesinn.trailgui.Main;
import ca.jamiesinn.trailgui.api.TrailDisplayEvent;
import com.darkblade12.particleeffect.ParticleEffect;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class BlockTrail extends Trail
{
    ParticleEffect.BlockData blockData;
    int itemData;

    public BlockTrail(ConfigurationSection config)
    {
        super(config);
        itemData = config.getInt("data", 0);
        blockData = new ParticleEffect.BlockData(itemType, (byte) itemData);
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
        TrailDisplayEvent event = new TrailDisplayEvent(this.getName(),
                this.getDisplayLocation(), this.getAmount(), this.cooldown, this.speed, this.range, this.type);
        Main.getPlugin().getServer().getPluginManager().callEvent(event);
        if(!event.isCancelled())
            type.display(blockData, player.getLocation().getDirection(), speed, player.getLocation().add(0.0D, displayLocation, 0.0D), range);
    }
}
