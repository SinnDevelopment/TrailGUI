package ca.jamiesinn.trailgui.trails.v1_8_x;

import ca.jamiesinn.trailgui.util.ITrailManager;
import com.darkblade12.particleeffect.ParticleEffect;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class TrailManager implements ITrailManager
{

    @Override
    public void spawnParticle(Player player, double location, int amount, int cooldown, float speed, int range, String type)
    {
        spawnParticle(player, location, amount, range, speed, type, null, null);
    }

    private void spawnParticle(Player player, double location, int amount, int range, float speed, String type, ItemStack iData, MaterialData mData)
    {
        ParticleEffect effect = ParticleEffect.valueOf(type.toUpperCase());
        if(iData != null)
        {
            ParticleEffect.ItemData data = new ParticleEffect.ItemData(iData.getType(), iData.getData().getData());
            effect.display(data, player.getLocation().getDirection(), speed, player.getLocation().add(0.0D, location, 0.0D), range);
        }
        else if(mData != null)
        {
            ParticleEffect.BlockData blockData = new ParticleEffect.BlockData(mData.getItemType(), mData.getData());
            effect.display(blockData, player.getLocation().getDirection(), speed, player.getLocation().add(0.0D, location, 0.0D), range);
        }
        else
        {
            effect.display(0.0F, 0.0F, 0.0F, speed, amount, player.getLocation().add(0.0D, location, 0.0D), range);
        }
    }

    @Override
    public void spawnItemParticle(Player player, double location, int amount, float speed, int range, String type, ItemStack iData)
    {
        spawnParticle(player, location, amount, range, speed, type, null, null);
    }

    @Override
    public void spawnBlockParticle(Player player, double location, int amount, float speed, int range, String type, MaterialData data)
    {
        player.getWorld().spawnParticle(Particle.valueOf(type), player.getLocation().add(0.0D, location, 0.0D), amount, 0, 0, 0, speed, data);
    }
}