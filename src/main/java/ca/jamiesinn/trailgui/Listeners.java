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
    Main main;

    public Listeners(Main main)
    {
        this.main = main;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if(Main.removeTrailOnPlayerHit)
        {
            if(((event.getDamager() instanceof Player)) &&
                    ((event.getEntity() instanceof Player)))
            {
                Player hit = (Player) event.getEntity();

                Methods.clearTrails(hit);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        if(event.getInventory().getTitle().contains(Main.getPlugin().getConfig().getString("inventoryName")))
        {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            if((event.getCurrentItem() == null) || (event.getCurrentItem() == new ItemStack(Material.AIR)))
            {
                return;
            }
            int currentPage = Integer.parseInt(event.getInventory().getTitle().replaceFirst(".+? ([0-9]+) / [0-9]+", "$1"));
            List<Trail> trails = Methods.getSubList(currentPage);

            for(Trail trail : trails)
            {
                if(trail.onInventoryClick(player, event.getCurrentItem()))
                {
                    return;
                }
            }

            if(event.getCurrentItem().equals(Methods.itemNoPerms()) && Main.getPlugin().getConfig().getBoolean("closeInventoryOnDenyPermission"))
            {
                player.sendMessage(Main.getPlugin().getConfig().getString("GUI-denyPermissionMessage").replaceAll("&", "\u00A7"));
                player.closeInventory();
                return;
            }

            if(event.getCurrentItem().equals(Methods.getItemPreviousPage()))
            {
                if(!player.hasPermission("trailgui.inventory.previouspage"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("GUI-denyPermissionMessage").replaceAll("&", "\u00A7"));
                    if(Main.getPlugin().getConfig().getBoolean("closeInventoryOnDenyPermission"))
                    {
                        player.closeInventory();
                    }
                    return;
                }
                Methods.openGUI(player, currentPage - 1);
            }
            else if(event.getCurrentItem().equals(Methods.getItemRemoveTrails()))
            {
                if(!player.hasPermission("trailgui.inventory.clearall"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("GUI-denyPermissionMessage").replaceAll("&", "\u00A7"));
                    if(Main.getPlugin().getConfig().getBoolean("closeInventoryOnDenyPermission"))
                    {
                        player.closeInventory();
                    }
                    return;
                }
                Methods.clearTrails(player);

                player.sendMessage(Main.getPlugin().getConfig().getString("RemoveTrails-message").replaceAll("&", "\u00A7"));
                if(Main.getPlugin().getConfig().getBoolean("closeInventoryAferSelect"))
                {
                    player.closeInventory();
                }
            }
            else if(event.getCurrentItem().equals(Methods.getItemNextPage()))
            {
                if(!player.hasPermission("trailgui.inventory.nextpage"))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("GUI-denyPermissionMessage").replaceAll("&", "\u00A7"));
                    if(Main.getPlugin().getConfig().getBoolean("closeInventoryOnDenyPermission"))
                    {
                        player.closeInventory();
                    }
                    return;
                }
                Methods.openGUI(player, currentPage + 1);
            }
        }
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        final Player player = event.getPlayer();
        if(!Main.enabledTrails.containsKey(player.getUniqueId()))
        {
            return;
        }
        if((Main.getPlugin().getConfig().getBoolean("disabledWhenSpinning")) &&
                (event.getFrom().getX() == event.getTo().getX()) && (event.getFrom().getY() == event.getTo().getY()) && (event.getFrom().getZ() == event.getTo().getZ()))
        {
            return;
        }

        for(String string : Main.disabledWorlds)
        {
            string = string.replace("[", "");
            string = string.replace("]", "");
            if(string.equals(player.getWorld().getName()))
            {
                return;
            }
        }
        List<Trail> trails = Main.enabledTrails.get(player.getUniqueId());
        for(Trail trail : trails)
        {
            trail.display(player);
        }
    }
}

