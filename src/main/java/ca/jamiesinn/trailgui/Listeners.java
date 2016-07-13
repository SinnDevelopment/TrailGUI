package ca.jamiesinn.trailgui;

import ca.jamiesinn.trailgui.trails.Trail;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Listeners implements Listener
{
    TrailGUI trailGUI;

    public Listeners(TrailGUI trailGUI)
    {
        this.trailGUI = trailGUI;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if (TrailGUI.removeTrailOnPlayerHit)
        {
            if (((event.getDamager() instanceof Player)) &&
                    ((event.getEntity() instanceof Player)))
            {
                Player hit = (Player) event.getEntity();

                Util.clearTrails(hit);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        if (event.getInventory().getTitle().contains(TrailGUI.getPlugin().getConfig().getString("inventoryName")))
        {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            if ((event.getCurrentItem() == null) || (event.getCurrentItem() == new ItemStack(Material.AIR)))
            {
                return;
            }
            int currentPage = Integer.parseInt(event.getInventory().getTitle().replaceFirst(".+? ([0-9]+) / [0-9]+", "$1"));
            List<Trail> trails = Util.getSubList(currentPage);

            for (Trail trail : trails)
            {
                if (trail.onInventoryClick(player, event.getCurrentItem()))
                {
                    return;
                }
            }

            if (event.getCurrentItem().equals(Util.itemNoPerms()) && TrailGUI.getPlugin().getConfig().getBoolean("closeInventoryOnDenyPermission"))
            {
                player.sendMessage(TrailGUI.getPlugin().getConfig().getString("GUI-denyPermissionMessage").replaceAll("&", "\u00A7"));
                player.closeInventory();
                return;
            }

            if (event.getCurrentItem().equals(Util.getItemPreviousPage()))
            {
                if (!player.hasPermission("trailgui.inventory.previouspage"))
                {
                    player.sendMessage(TrailGUI.getPlugin().getConfig().getString("GUI-denyPermissionMessage").replaceAll("&", "\u00A7"));
                    if (TrailGUI.getPlugin().getConfig().getBoolean("closeInventoryOnDenyPermission"))
                    {
                        player.closeInventory();
                    }
                    return;
                }
                Util.openGUI(player, currentPage - 1);
            }
            else if (event.getCurrentItem().equals(Util.getItemRemoveTrails()))
            {
                if (!player.hasPermission("trailgui.inventory.clearall"))
                {
                    player.sendMessage(TrailGUI.getPlugin().getConfig().getString("GUI-denyPermissionMessage").replaceAll("&", "\u00A7"));
                    if (TrailGUI.getPlugin().getConfig().getBoolean("closeInventoryOnDenyPermission"))
                    {
                        player.closeInventory();
                    }
                    return;
                }
                Util.clearTrails(player);

                player.sendMessage(TrailGUI.getPlugin().getConfig().getString("RemoveTrails-message").replaceAll("&", "\u00A7"));
                if (TrailGUI.getPlugin().getConfig().getBoolean("closeInventoryAferSelect"))
                {
                    player.closeInventory();
                }
            }
            else if (event.getCurrentItem().equals(Util.getItemNextPage()))
            {
                if (!player.hasPermission("trailgui.inventory.nextpage"))
                {
                    player.sendMessage(TrailGUI.getPlugin().getConfig().getString("GUI-denyPermissionMessage").replaceAll("&", "\u00A7"));
                    if (TrailGUI.getPlugin().getConfig().getBoolean("closeInventoryOnDenyPermission"))
                    {
                        player.closeInventory();
                    }
                    return;
                }
                Util.openGUI(player, currentPage + 1);
            }
        }
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        final Player player = event.getPlayer();
        if (!TrailGUI.enabledTrails.containsKey(player.getUniqueId()))
        {
            return;
        }
        if ((TrailGUI.getPlugin().getConfig().getBoolean("disabledWhenSpinning")) &&
                (event.getFrom().getX() == event.getTo().getX()) && (event.getFrom().getY() == event.getTo().getY()) && (event.getFrom().getZ() == event.getTo().getZ()))
        {
            return;
        }

        for (String string : TrailGUI.disabledWorlds)
        {
            string = string.replace("[", "");
            string = string.replace("]", "");
            if (string.equals(player.getWorld().getName()))
            {
                return;
            }
        }
        List<Trail> trails = TrailGUI.enabledTrails.get(player.getUniqueId());
        for (Trail trail : trails)
        {
            trail.display(player);
        }
    }
}

