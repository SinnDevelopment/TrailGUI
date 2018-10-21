package ca.jamiesinn.trailgui.trails;

import ca.jamiesinn.trailgui.TrailGUI;
import ca.jamiesinn.trailgui.Util;
import ca.jamiesinn.trailgui.api.TrailDisableEvent;
import ca.jamiesinn.trailgui.api.TrailDisplayEvent;
import ca.jamiesinn.trailgui.api.TrailEnableEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public abstract class Trail
{
    private String name;
    double displayLocation;
    int amount;
    int cooldown;
    float speed;
    private int range;
    private int order;
    Material itemType;
    private String itemName;
    private boolean loreEnabled;
    private boolean glowEnabled;
    private List<String> lore;
    Particle type;
    private Map<UUID, Long> cooldownMap = new HashMap<>();

    public Trail(ConfigurationSection config)
    {
        this.name = config.getName();
        this.displayLocation = config.getDouble("displayLocation");
        this.amount = config.getInt("amount");
        this.speed = (float) config.getDouble("speed");
        this.range = config.getInt("range");
        this.cooldown = config.getInt("cooldown", 0);
        this.order = config.getInt("order");
        this.itemType = Material.valueOf(config.getString("itemType").toUpperCase());
        this.itemName = config.getString("itemName", "").replace("&", "\u00a7");
        this.loreEnabled = config.getBoolean("loreEnabled", false);
        this.glowEnabled = config.getBoolean("glowEnabled", true);
        this.lore = new ArrayList<>();

        if (isLoreEnabled())
            for (String s : config.getStringList("lore"))
            {
                lore.add(ChatColor.translateAlternateColorCodes('&', s));
            }
    }

    protected abstract void loadType(String sType);

    double getDisplayLocation()
    {
        return displayLocation;
    }

    int getAmount()
    {
        return amount;
    }

    float getSpeed()
    {
        return speed;
    }

    int getRange()
    {
        return range;
    }

    public int getOrder()
    {
        return order;
    }

    public Material getItemType()
    {
        return itemType;
    }

    public String getItemName()
    {
        return itemName;
    }

    public boolean isLoreEnabled()
    {
        return loreEnabled;
    }

    public List<String> getLore()
    {
        return lore;
    }

    public String getName()
    {
        return name;
    }

    public boolean canUse(Player player)
    {
        return player.hasPermission("trailgui.trails." + getName()) || player.hasPermission("trailgui.trail." + getName())
                || player.hasPermission("trailgui.trail.*") || player.hasPermission("trailgui.*");
    }

    public boolean canUseCommand(Player player)
    {
        return player.hasPermission("trailgui.trails." + getName()) || player.hasPermission("trailgui.commands." + getName())
                || player.hasPermission("trailgui.*");
    }

    public boolean canUseInventory(Player player)
    {
        return player.hasPermission("trailgui.inventory." + getName()) || player.hasPermission("trailgui.trail.*") || player.hasPermission("trailgui.*");
    }

    public boolean canUseOnOthers(Player player)
    {
        return player.hasPermission("trailgui.trails." + getName() + ".other") || player.hasPermission("trailgui.commands.other." + getName())
                || player.hasPermission("trailgui.*");
    }

    // Returns true if you are at your limit via permissions
    public boolean getPermLimit(Player player)
    {
        List<Trail> currentTrails = new ArrayList<Trail>();
        int max = 0;
        for (int i = 0; i <= TrailGUI.trailTypes.size(); i++)
            if (player.hasPermission("trailgui.trailmax." + i))
                max = i;
        if (max == 0)
            return false;

        if (TrailGUI.enabledTrails.containsKey(player.getUniqueId()))
            currentTrails = TrailGUI.enabledTrails.get(player.getUniqueId());
        return currentTrails.size() >= max;
    }

    public ItemStack getItem()
    {
        ItemStack item = new ItemStack(itemType, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(itemName);
        if (loreEnabled)
        {
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }

    public void display(Player player)
    {
        if (player.hasPotionEffect(PotionEffectType.INVISIBILITY))
            return;

        if (cooldown <= 0)
        {
            justDisplay(player);
        }
        else
        {
            if (!cooldownMap.containsKey(player.getUniqueId()))
            {
                justDisplay(player);
                cooldownMap.put(player.getUniqueId(), System.currentTimeMillis());
            }
            else if (System.currentTimeMillis() - cooldownMap.get(player.getUniqueId()) > cooldown)
            {
                justDisplay(player);
                cooldownMap.put(player.getUniqueId(), System.currentTimeMillis());
            }
        }
    }

    public abstract void justDisplay(Player player);

    public boolean onInventoryClick(Player player, ItemStack currentItem)
    {
        if (currentItem.equals(this.getItem()))
        {
            List<Trail> currentTrails = new ArrayList<Trail>();

            if (TrailGUI.enabledTrails.containsKey(player.getUniqueId()))
            {
                currentTrails = TrailGUI.enabledTrails.get(player.getUniqueId());
            }

            if (!canUseInventory(player))
            {
                player.sendMessage(TrailGUI.getPlugin().getConfig().getString("GUI.denyPermissionMessage").replaceAll("&", "\u00A7"));
                if (TrailGUI.getPlugin().getConfig().getBoolean("closeInventoryOnDenyPermission"))
                {
                    player.closeInventory();
                }
                return true;
            }
            if (currentTrails.contains(this))
            {
                if (TrailGUI.oneTrailAtATime)
                {
                    Util.clearTrails(player);
                }

                currentTrails.remove(this);
                disableEvent(player, this);
                TrailGUI.enabledTrails.put(player.getUniqueId(), currentTrails);
                player.sendMessage(TrailGUI.getPlugin().getConfig().getString("GUI.removeTrailMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.getName()));
                if (TrailGUI.getPlugin().getConfig().getBoolean("GUI.closeInventoryAferSelect"))
                {
                    player.closeInventory();
                }
                return true;
            }
            else
            {
                if (TrailGUI.oneTrailAtATime)
                {
                    Util.clearTrails(player);
                }
                if ((TrailGUI.maxTrails < currentTrails.size() && TrailGUI.maxTrails != 0) || getPermLimit(player))
                {
                    player.sendMessage(TrailGUI.getPlugin().getConfig().getString("Commands.tooManyTrailsMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                    return true;
                }
                currentTrails.add(this);
                enableEvent(player, this);
                TrailGUI.enabledTrails.put(player.getUniqueId(), currentTrails);
                player.sendMessage(TrailGUI.getPlugin().getConfig().getString("GUI.selectTrailMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.getName()));
                if (TrailGUI.getPlugin().getConfig().getBoolean("GUI.closeInventoryAferSelect"))
                {
                    player.closeInventory();
                }
            }

        }
        return false;
    }

    public boolean onCommand(Player player, String[] args)
    {
        if (args[0].equalsIgnoreCase(getName()))
        {
            if (!canUseCommand(player))
            {
                player.sendMessage(TrailGUI.getPlugin().getConfig().getString("Commands.denyPermissionMessage").replaceAll("&", "\u00A7"));
                return false;
            }

            if (args.length == 1)
            {
                List<Trail> currentTrails = new ArrayList<Trail>();
                if (TrailGUI.enabledTrails.containsKey(player.getUniqueId()))
                {
                    currentTrails = TrailGUI.enabledTrails.get(player.getUniqueId());
                }
                if (currentTrails.contains(this))
                {
                    if (TrailGUI.oneTrailAtATime)
                    {
                        Util.clearTrails(player);
                    }
                    currentTrails.remove(this);
                    TrailGUI.enabledTrails.put(player.getUniqueId(), currentTrails);
                    player.sendMessage(TrailGUI.getPlugin().getConfig().getString("Commands.removeTrailMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                    return true;
                }
                else
                {
                    if (TrailGUI.oneTrailAtATime)
                    {
                        Util.clearTrails(player);
                    }
                    if ((TrailGUI.maxTrails < currentTrails.size() && TrailGUI.maxTrails != 0) || getPermLimit(player))
                    {
                        player.sendMessage(TrailGUI.getPlugin().getConfig().getString("Commands.tooManyTrailsMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                        return true;
                    }
                    currentTrails.add(this);
                    enableEvent(player, this);
                    TrailGUI.enabledTrails.put(player.getUniqueId(), currentTrails);
                    player.sendMessage(TrailGUI.getPlugin().getConfig().getString("Commands.selectTrailMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                    return true;
                }
            }


            if (canUseOnOthers(player))
            {
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(TrailGUI.getPlugin().getConfig().getString("Commands.noTargetMessage").replaceAll("&", "\u00A7").replaceAll("%Target%", args[1]));
                    return true;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(TrailGUI.getPlugin().getConfig().getString("Commands.targetSelfMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                    return true;
                }

                List<Trail> currentTrails = new ArrayList<Trail>();
                if (TrailGUI.enabledTrails.containsKey(target.getUniqueId()))
                {
                    currentTrails = TrailGUI.enabledTrails.get(target.getUniqueId());
                }
                if (currentTrails.contains(this))
                {
                    if (TrailGUI.oneTrailAtATime)
                    {
                        Util.clearTrails(target);
                    }
                    currentTrails.remove(this);
                    TrailGUI.enabledTrails.put(target.getUniqueId(), currentTrails);

                    player.sendMessage(TrailGUI.getPlugin().getConfig().getString("Commands.removeTrailSenderMessage").replaceAll("&", "\u00A7").replaceAll("%Target%", args[1]));
                    target.sendMessage(TrailGUI.getPlugin().getConfig().getString("Commands.removeTrailTargetMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                    return true;
                }
                else
                {
                    if (TrailGUI.oneTrailAtATime)
                    {
                        Util.clearTrails(target);
                    }
                    if ((TrailGUI.maxTrails < currentTrails.size() && TrailGUI.maxTrails != 0) || getPermLimit(target))
                    {
                        player.sendMessage(TrailGUI.getPlugin().getConfig().getString("Commands.tooManyTrailsMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                        return true;
                    }
                    currentTrails.add(this);
                    TrailGUI.enabledTrails.put(target.getUniqueId(), currentTrails);
                    player.sendMessage(TrailGUI.getPlugin().getConfig().getString("Commands.selectTrailSenderMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name).replaceAll("%Target%", args[1]));
                    target.sendMessage(TrailGUI.getPlugin().getConfig().getString("Commands.selectTrailTargetMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                    return true;
                }
            }
            else
            {
                player.sendMessage(TrailGUI.getPlugin().getConfig().getString("Commands.denyPermissionMessage").replaceAll("&", "\u00A7"));
                return true;
            }

        }
        return false;
    }

    TrailDisplayEvent displayEvent(String name, double location, int amount, int cooldown, float speed, int range, Particle type)
    {
        TrailDisplayEvent event = new TrailDisplayEvent(name,
                location, amount, cooldown, speed, range, type);
        TrailGUI.getPlugin().getServer().getPluginManager().callEvent(event);
        return event;
    }

    private static void enableEvent(Player player, Trail trail)
    {
        TrailEnableEvent event = new TrailEnableEvent(player, trail);
        TrailGUI.getPlugin().getServer().getPluginManager().callEvent(event);
    }

    public static void enableEvent(Player player, List<Trail> trails)
    {
        TrailEnableEvent event = new TrailEnableEvent(player, trails);
        TrailGUI.getPlugin().getServer().getPluginManager().callEvent(event);
    }

    public static void disableEvent(Player player, Trail trail)
    {
        TrailDisableEvent event = new TrailDisableEvent(player, trail);
        TrailGUI.getPlugin().getServer().getPluginManager().callEvent(event);
    }

    public static void disableEvent(Player player, List<Trail> trails)
    {
        TrailDisableEvent event = new TrailDisableEvent(player, trails);
        TrailGUI.getPlugin().getServer().getPluginManager().callEvent(event);
    }

    public boolean isGlowEnabled()
    {
        return glowEnabled;
    }

    public Particle getType()
    {
        return type;
    }
}
