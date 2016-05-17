package ca.jamiesinn.trailgui.trails;

import ca.jamiesinn.trailgui.ParticleManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemTrail extends Trail
{
    ItemStack data;
    byte itemData;

    public ItemTrail(ConfigurationSection config)
    {
        super(config);
        itemData = (byte)config.getInt("data", 0);
        data = new ItemStack(itemType, 1, itemData);
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
        ParticleManager.spawnItemParticle(player, displayLocation, amount, cooldown, speed, range, type, getItem());
    }
}
