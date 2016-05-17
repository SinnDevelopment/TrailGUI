package ca.jamiesinn.trailgui.trails;

import ca.jamiesinn.trailgui.ParticleManager;
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
        itemData = (byte) config.getInt("data", 0);
        blockData = new MaterialData(itemType, itemData);
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
        ParticleManager.spawnBlockParticle(player, displayLocation, amount, cooldown,speed,range, type, blockData);

    }
}
