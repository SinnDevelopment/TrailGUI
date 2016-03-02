package ca.jamiesinn.trailgui.trails;

import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class BlockTrail extends Trail
{
    MaterialData blockData;
    byte itemData;

    public BlockTrail(ConfigurationSection config)
    {
        super(config);
        itemData = (byte)config.getInt("data", 0);
        blockData = new MaterialData(itemType, itemData);
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
            player.getWorld().spawnParticle(type, player.getLocation().add(0.0D, displayLocation, 0.0D), amount, 0,0,0, speed, blockData);

    }
}
