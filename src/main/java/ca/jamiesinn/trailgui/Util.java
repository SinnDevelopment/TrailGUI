package ca.jamiesinn.trailgui;

import ca.jamiesinn.trailgui.files.Userdata;
import ca.jamiesinn.trailgui.sql.SQLManager;
import ca.jamiesinn.trailgui.trails.Trail;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.*;

public class Util
{
    private static ItemStack itemNextPage;
    private static ItemStack itemPreviousPage;
    private static ItemStack itemRemoveTrails;

    public static void clearTrails(OfflinePlayer player)
    {
        List<Trail> currentTrails;
        if (TrailGUI.enabledTrails.containsKey(player.getUniqueId()))
        {
            currentTrails = TrailGUI.enabledTrails.get(player.getUniqueId());
            Trail.disableEvent(player.getPlayer(), currentTrails);
            currentTrails.clear();
            TrailGUI.enabledTrails.put(player.getUniqueId(), currentTrails);
        }
    }

    static void saveTrails(UUID user)
    {
        if (TrailGUI.getPlugin().getConfig().getBoolean("mysql"))
        {
            SQLManager sql = TrailGUI.getSqlManager();

            List<String> trailNames = new ArrayList<>();
            for (Trail t : TrailGUI.enabledTrails.get(user))
                trailNames.add(t.getName());
            try
            {
                sql.insertUser(user, trailNames);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

        }
        else
        {
            FileConfiguration config = Userdata.getInstance().getConfig();
            for (String key : config.getKeys(false))
            {
                config.set(key, null);
            }


            List<String> trailNames = new ArrayList<>();
            for (Trail trail : TrailGUI.enabledTrails.get(user))
            {
                trailNames.add(trail.getName());
            }
            config.set(user.toString(), trailNames);

            Userdata.getInstance().saveConfig();
        }
    }

    static void saveTrails()
    {
        for (UUID player : TrailGUI.enabledTrails.keySet())
        {
            saveTrails(player);
        }
    }

    static void restoreTrails()
    {
        if(TrailGUI.getPlugin().getConfig().getBoolean("mysql"))
        {
            try
            {
                HashMap<UUID, List<Trail>> trails = TrailGUI.getSqlManager().getTrails();
                if(trails == null ) return;
                for(UUID uuid : trails.keySet())
                {
                    if(trails.get(uuid) == null) return;
                    List<Trail> trailTypes = new ArrayList<>();
                    for (Trail t: trails.get(uuid))
                    {
                        trailTypes.add(t);
                    }
                    Trail.enableEvent(Bukkit.getPlayer(uuid), trailTypes);
                    TrailGUI.enabledTrails.put(uuid, trailTypes);
                }

            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            FileConfiguration config = Userdata.getInstance().getConfig();
            for (String key : config.getKeys(false))
            {
                List<String> trails = config.getStringList(key);
                if (!trails.isEmpty())
                {
                    List<Trail> trailTypes = new ArrayList<Trail>();
                    for (String trail : trails)
                    {
                        trailTypes.add(TrailGUI.trailTypes.get(trail));
                    }
                    Trail.enableEvent(Bukkit.getPlayer(UUID.fromString(key)), trailTypes);
                    TrailGUI.enabledTrails.put(UUID.fromString(key), trailTypes);
                }
            }
        }
    }

    public static void openGUI(Player player)
    {
        openGUI(player, 1);
    }

    static void openGUI(Player player, int currentPage)
    {

        int pageSize = 27;
        List<Trail> sortedList = new ArrayList<>(TrailGUI.trailTypes.values());
        Collections.sort(sortedList, new orderComparator());
        int maxPages = (int) Math.ceil((double) sortedList.size() / pageSize);
        currentPage = currentPage > maxPages ? 1 : currentPage;
        int begin = (pageSize * currentPage) - pageSize;
        int end = begin + pageSize;
        if (end > sortedList.size())
        {
            end = sortedList.size();
        }
        List<Trail> subList = sortedList.subList(begin, end);
        String page = " " + currentPage + " / " + maxPages;
        Inventory inv = Bukkit.createInventory(null, 45,
                TrailGUI.getPlugin().getConfig().getString("inventoryName").replaceAll("&", "\u00A7")
        + (TrailGUI.getPlugin().getConfig().getBoolean("showPages") ? page : ""));
        int i = 0;
        for (Trail trail : subList)
        {
            if (trail.canUseInventory(player))
            {
                inv.setItem(i, trail.getItem());
            }
            else
            {
                inv.setItem(i, itemNoPerms());
            }
            i++;
        }

        if (currentPage > 1)
        {
            inv.setItem(TrailGUI.getPlugin().getConfig().getInt("PreviousPage.inventorySlot"), getItemPreviousPage());
        }
        inv.setItem(TrailGUI.getPlugin().getConfig().getInt("RemoveTrails.inventorySlot"), getItemRemoveTrails());
        if (currentPage < maxPages)
        {
            inv.setItem(TrailGUI.getPlugin().getConfig().getInt("NextPage.inventorySlot"), getItemNextPage());
        }
        player.openInventory(inv);

    }

    //region Items for Inventory Slots
    static ItemStack itemNoPerms()
    {
        ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(TrailGUI.getPlugin().getConfig().getString("GUI.noPermMessage").replaceAll("&", "\u00A7"));
        i.setItemMeta(meta);
        return i;

    }

    static ItemStack getItemNextPage()
    {
        if (itemNextPage == null)
        {
            itemNextPage = createItemNextPage();
        }
        return itemNextPage;
    }

    private static ItemStack createItemNextPage()
    {
        ItemStack itemNextPage = new ItemStack(Material.valueOf(TrailGUI.getPlugin().getConfig().getString("NextPage.itemType").toUpperCase()), 1);
        ItemMeta metaNextPage = itemNextPage.getItemMeta();

        String name1 = TrailGUI.getPlugin().getConfig().getString("NextPage.itemName");
        String name2 = name1.replaceAll("&", "\u00A7");
        if (TrailGUI.getPlugin().getConfig().getBoolean("NextPage.loreEnabled"))
        {
            List<String> loreNextPage = new ArrayList<>();

            for(String s : TrailGUI.getPlugin().getConfig().getStringList("NextPage.lore"))
            {
                loreNextPage.add(ChatColor.translateAlternateColorCodes('&', s));
            }
            metaNextPage.setLore(loreNextPage);
        }
        metaNextPage.setDisplayName(name2);
        itemNextPage.setItemMeta(metaNextPage);
        return itemNextPage;
    }

    static ItemStack getItemPreviousPage()
    {
        if (itemPreviousPage == null)
        {
            itemPreviousPage = createItemPreviousPage();
        }
        return itemPreviousPage;
    }

    private static ItemStack createItemPreviousPage()
    {
        ItemStack itemPreviousPage = new ItemStack(Material.valueOf(TrailGUI.getPlugin().getConfig().getString("PreviousPage.itemType").toUpperCase()), 1);
        ItemMeta metaPreviousPage = itemPreviousPage.getItemMeta();

        String name1 = TrailGUI.getPlugin().getConfig().getString("PreviousPage.itemName");
        String name2 = name1.replaceAll("&", "\u00A7");
        if (TrailGUI.getPlugin().getConfig().getBoolean("PreviousPage.loreEnabled"))
        {
            List<String> lorePreviousPage = new ArrayList<>();

            for(String s : TrailGUI.getPlugin().getConfig().getStringList("PreviousPage.lore"))
            {
                lorePreviousPage.add(ChatColor.translateAlternateColorCodes('&', s));
            }

            metaPreviousPage.setLore(lorePreviousPage);
        }
        metaPreviousPage.setDisplayName(name2);
        itemPreviousPage.setItemMeta(metaPreviousPage);
        return itemPreviousPage;
    }

    static ItemStack getItemRemoveTrails()
    {
        if (itemRemoveTrails == null)
        {
            itemRemoveTrails = createItemRemoveTrails();
        }
        return itemRemoveTrails;
    }

    private static ItemStack createItemRemoveTrails()
    {
        ItemStack itemRemoveTrails = new ItemStack(Material.valueOf(TrailGUI.getPlugin().getConfig().getString("RemoveTrails.itemType").toUpperCase()), 1);
        ItemMeta metaRemoveTrails = itemRemoveTrails.getItemMeta();

        String name1 = TrailGUI.getPlugin().getConfig().getString("RemoveTrails.itemName");
        String name2 = name1.replaceAll("&", "\u00A7");
        if (TrailGUI.getPlugin().getConfig().getBoolean("RemoveTrails.loreEnabled"))
        {
            List<String> loreRemoveTrail = new ArrayList<>();

            for(String s : TrailGUI.getPlugin().getConfig().getStringList("RemoveTrails.lore"))
            {
                loreRemoveTrail.add(ChatColor.translateAlternateColorCodes('&', s));
            }

            metaRemoveTrails.setLore(loreRemoveTrail);
        }
        metaRemoveTrails.setDisplayName(name2);
        itemRemoveTrails.setItemMeta(metaRemoveTrails);
        return itemRemoveTrails;
    }

    static List<Trail> getSubList(int currentPage)
    {
        int pageSize = 27;
        List<Trail> sortedList = new ArrayList<>(TrailGUI.trailTypes.values());
        Collections.sort(sortedList, new orderComparator());
        int maxPages = (int) Math.ceil((double) sortedList.size() / pageSize);
        currentPage = currentPage > maxPages ? 1 : currentPage;
        int begin = (pageSize * currentPage) - pageSize;
        int end = begin + pageSize;
        if (end > sortedList.size())
        {
            end = sortedList.size();
        }
        return sortedList.subList(begin, end);
    }

    //endregion

    private static class orderComparator implements Comparator<Trail>
    {
        public int compare(Trail o1, Trail o2)
        {
            return o1.getOrder() - o2.getOrder();
        }
    }

}
