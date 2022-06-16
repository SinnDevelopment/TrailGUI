package ca.jamiesinn.trailgui;

import ca.jamiesinn.trailgui.trails.Trail;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Listeners implements Listener
{
    private TrailGUI trailGUI;

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
        if (event.getView().getTitle().contains(TrailGUI.getPlugin().getConfig().getString("inventoryName")))
        {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            if ((event.getCurrentItem() == null) || (event.getCurrentItem() == new ItemStack(Material.AIR)))
            {
                return;
            }
            int currentPage = Integer.parseInt(event.getView().getTitle().replaceFirst(".+? ([0-9]+) / [0-9]+", "$1"));
            List<Trail> trails = Util.getSubList(currentPage);

            for (Trail trail : trails)
            {
                if (trail.onInventoryClick(player, event.getCurrentItem()))
                {
                    if (event.getView().getTopInventory().equals(player.getOpenInventory().getTopInventory()))
                    {
                        Util.openGUI(player, currentPage);
                    }
                    return;
                }
            }

            if (event.getCurrentItem().equals(Util.itemNoPerms()) && TrailGUI.getPlugin().getConfig().getBoolean("closeInventoryOnDenyPermission"))
            {
                player.sendMessage(TrailGUI.getPlugin().getConfig().getString("GUI.denyPermissionMessage").replaceAll("&", "\u00A7"));
                player.closeInventory();
                return;
            }

            if (event.getCurrentItem().equals(Util.getItemPreviousPage()))
            {
                if (!player.hasPermission("trailgui.inventory.previouspage"))
                {
                    player.sendMessage(TrailGUI.getPlugin().getConfig().getString("GUI.denyPermissionMessage").replaceAll("&", "\u00A7"));
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
                    player.sendMessage(TrailGUI.getPlugin().getConfig().getString("GUI.denyPermissionMessage").replaceAll("&", "\u00A7"));
                    if (TrailGUI.getPlugin().getConfig().getBoolean("closeInventoryOnDenyPermission"))
                    {
                        player.closeInventory();
                    }
                    return;
                }
                Util.clearTrails(player);

                player.sendMessage(TrailGUI.getPlugin().getConfig().getString("RemoveTrails.message").replaceAll("&", "\u00A7"));
                if (TrailGUI.getPlugin().getConfig().getBoolean("GUI.closeInventoryAferSelect"))
                {
                    player.closeInventory();
                }
                else
                {
                    Util.openGUI(player, currentPage);
                }
            }
            else if (event.getCurrentItem().equals(Util.getItemNextPage()))
            {
                if (!player.hasPermission("trailgui.inventory.nextpage"))
                {
                    player.sendMessage(TrailGUI.getPlugin().getConfig().getString("GUI.denyPermissionMessage").replaceAll("&", "\u00A7"));
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

        if (trailGUI.isWorldDisabled(player.getWorld().getName()))
        {
            return;
        }
        List<Trail> trails = TrailGUI.enabledTrails.get(player.getUniqueId());
        try
        {
            for (Trail trail : trails)
            {
                trail.display(player);
            }
        }
        catch (NullPointerException e)
        {
            Util.clearTrails(player);
        }
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent e)
    {
        if(TrailGUI.getPlugin().getConfig().getBoolean("clearTrailsOnDisconnect"))
            Util.clearTrails(e.getPlayer());
        else
            Util.saveTrails(e.getPlayer().getUniqueId());
    }
}

