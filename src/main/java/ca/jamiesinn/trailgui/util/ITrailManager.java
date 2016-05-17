package ca.jamiesinn.trailgui.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public interface ITrailManager
{

    void spawnParticle(Player player,  double location, int amount, int cooldown, float speed, int range, String type);
    void spawnItemParticle(Player player, double location, int amount, float speed, int range, String type, ItemStack data);
    void spawnBlockParticle(Player player,  double location, int amount, float speed, int range, String type, MaterialData data);
}
