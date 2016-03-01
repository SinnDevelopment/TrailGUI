package ca.jamiesinn.trailgui.trails;

import ca.jamiesinn.trailgui.Main;
import ca.jamiesinn.trailgui.Methods;
import ca.jamiesinn.trailgui.api.TrailDisplayEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public abstract class Trail
{
    protected String name;
    protected double displayLocation;
    protected int amount;
    protected int cooldown;
    protected float speed;
    protected int range;
    protected int order;
    protected Material itemType;
    protected String itemName;
    protected boolean loreEnabled;
    protected List<String> lore;
    protected Particle type;
    Map<UUID, Long> cooldownMap = new HashMap<UUID, Long>();

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
        this.lore = new ArrayList<String>();
        lore.add(config.getString("loreLineOne"));
        lore.add(config.getString("loreLineTwo"));
        lore.add(config.getString("loreLineThree"));
    }

    protected abstract void loadType(String sType);

    public double getDisplayLocation()
    {
        return displayLocation;
    }

    public int getAmount()
    {
        return amount;
    }

    public float getSpeed()
    {
        return speed;
    }

    public int getRange()
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
        return player.hasPermission("trailgui.trails." + getName()) || player.hasPermission("trailgui.trail." + getName());
    }

    public boolean canUseCommand(Player player)
    {
        return player.hasPermission("trailgui.trails." + getName()) || player.hasPermission("trailgui.commands." + getName());
    }

    public boolean canUseInventory(Player player)
    {
        return player.hasPermission("trailgui.inventory." + getName());
    }

    public boolean canUseOnOthers(Player player)
    {
        return player.hasPermission("trailgui.trails." + getName() + ".other") || player.hasPermission("trailgui.commands.other." + getName());
    }

    // Returns true if you are at your limit via permissions
    public boolean getPermLimit(Player player)
    {
        List<Trail> currentTrails = new ArrayList<Trail>();
        int max = 0;
        for (int i = 0; i <= Main.trailTypes.size(); i++)
        {
            if (player.hasPermission("trailgui.trailmax." + i))
            {
                max = i;
            }
        }
        if (max == 0)
        {
            return false;
        }

        if (Main.enabledTrails.containsKey(player.getUniqueId()))
        {
            currentTrails = Main.enabledTrails.get(player.getUniqueId());
        }
        return currentTrails.size() > max;
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

        if ((Main.ess != null && Main.ess.getUser(player).isVanished())
                || player.hasPotionEffect(PotionEffectType.INVISIBILITY))
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

            if (Main.enabledTrails.containsKey(player.getUniqueId()))
            {
                currentTrails = Main.enabledTrails.get(player.getUniqueId());
            }

            if (!canUseInventory(player))
            {
                player.sendMessage(Main.getPlugin().getConfig().getString("GUI-denyPermissionMessage").replaceAll("&", "\u00A7"));
                if (Main.getPlugin().getConfig().getBoolean("closeInventoryOnDenyPermission"))
                {
                    player.closeInventory();
                }
                return true;
            }
            if (currentTrails.contains(this))
            {
                if (Main.oneTrailAtATime)
                {
                    Methods.clearTrails(player);
                }

                currentTrails.remove(this);
                Main.enabledTrails.put(player.getUniqueId(), currentTrails);
                player.sendMessage(Main.getPlugin().getConfig().getString("GUI-removeTrailMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.getName()));
                if (Main.getPlugin().getConfig().getBoolean("closeInventoryAferSelect"))
                {
                    player.closeInventory();
                }
                return true;
            }
            else
            {
                if (Main.oneTrailAtATime)
                {
                    Methods.clearTrails(player);
                }
                if ((Main.maxTrails < currentTrails.size() && Main.maxTrails != 0) || getPermLimit(player))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-tooManyTrailsMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                    return true;
                }
                currentTrails.add(this);
                Main.enabledTrails.put(player.getUniqueId(), currentTrails);
                player.sendMessage(Main.getPlugin().getConfig().getString("GUI-selectTrailMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.getName()));
                if (Main.getPlugin().getConfig().getBoolean("closeInventoryAferSelect"))
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
                player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "\u00A7"));
                return false;
            }

            if (args.length == 1)
            {
                List<Trail> currentTrails = new ArrayList<Trail>();
                if (Main.enabledTrails.containsKey(player.getUniqueId()))
                {
                    currentTrails = Main.enabledTrails.get(player.getUniqueId());
                }
                if (currentTrails.contains(this))
                {
                    if (Main.oneTrailAtATime)
                    {
                        Methods.clearTrails(player);
                    }
                    currentTrails.remove(this);
                    Main.enabledTrails.put(player.getUniqueId(), currentTrails);
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-removeTrailMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                    return true;
                }
                else
                {
                    if (Main.oneTrailAtATime)
                    {
                        Methods.clearTrails(player);
                    }
                    if ((Main.maxTrails < currentTrails.size() && Main.maxTrails != 0) || getPermLimit(player))
                    {
                        player.sendMessage(Main.getPlugin().getConfig().getString("Commands-tooManyTrailsMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                        return true;
                    }
                    currentTrails.add(this);
                    Main.enabledTrails.put(player.getUniqueId(), currentTrails);
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-selectTrailMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                    return true;
                }
            }


            if (canUseOnOthers(player))
            {
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("noTargetMessage").replaceAll("&", "\u00A7").replaceAll("%Target%", args[1]));
                    return true;
                }
                if (player.getName().equals(args[1]))
                {
                    player.sendMessage(Main.getPlugin().getConfig().getString("targetSelfMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                    return true;
                }

                List<Trail> currentTrails = new ArrayList<Trail>();
                if (Main.enabledTrails.containsKey(target.getUniqueId()))
                {
                    currentTrails = Main.enabledTrails.get(target.getUniqueId());
                }
                if (currentTrails.contains(this))
                {
                    if (Main.oneTrailAtATime)
                    {
                        Methods.clearTrails(target);
                    }
                    currentTrails.remove(this);
                    Main.enabledTrails.put(target.getUniqueId(), currentTrails);

                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "\u00A7").replaceAll("%Target%", args[1]));
                    target.sendMessage(Main.getPlugin().getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                    return true;
                }
                else
                {
                    if (Main.oneTrailAtATime)
                    {
                        Methods.clearTrails(target);
                    }
                    if ((Main.maxTrails < currentTrails.size() && Main.maxTrails != 0) || getPermLimit(target))
                    {
                        player.sendMessage(Main.getPlugin().getConfig().getString("Commands-tooManyTrailsMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                        return true;
                    }
                    currentTrails.add(this);
                    Main.enabledTrails.put(target.getUniqueId(), currentTrails);
                    player.sendMessage(Main.getPlugin().getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name).replaceAll("%Target%", args[1]));
                    target.sendMessage(Main.getPlugin().getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                    return true;
                }
            }
            else
            {
                player.sendMessage(Main.getPlugin().getConfig().getString("Commands-denyPermissionMessage").replaceAll("&", "\u00A7"));
                return true;
            }

        }
        return false;
    }

    public boolean onCommand(CommandSender sender, String[] args)
    {
        if (args[0].equalsIgnoreCase(getName()))
        {
            if (args.length == 2)
            {
                Player target = Bukkit.getServer().getPlayerExact(args[1]);
                if (target == null)
                {
                    sender.sendMessage(Main.getPlugin().getConfig().getString("noTargetMessage").replaceAll("&", "\u00A7").replaceAll("%Target%", args[1]));
                    return true;
                }

                List<Trail> currentTrails = new ArrayList<Trail>();
                if (Main.enabledTrails.containsKey(target.getUniqueId()))
                {
                    currentTrails = Main.enabledTrails.get(target.getUniqueId());
                }
                if (currentTrails.contains(this))
                {
                    if (Main.oneTrailAtATime)
                    {
                        Methods.clearTrails(target);
                    }
                    currentTrails.remove(this);
                    Main.enabledTrails.put(target.getUniqueId(), currentTrails);

                    sender.sendMessage(Main.getPlugin().getConfig().getString("Commands-removeTrailSenderMessage").replaceAll("&", "\u00A7").replaceAll("%Target%", args[1]));
                    target.sendMessage(Main.getPlugin().getConfig().getString("Commands-removeTrailTargetMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                    return true;
                }
                else
                {
                    if (Main.oneTrailAtATime)
                    {
                        Methods.clearTrails(target);
                    }
                    if ((Main.maxTrails < currentTrails.size() && Main.maxTrails != 0) || getPermLimit(target))
                    {
                        sender.sendMessage(Main.getPlugin().getConfig().getString("Commands-tooManyTrailsMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                        return true;
                    }
                    currentTrails.add(this);
                    Main.enabledTrails.put(target.getUniqueId(), currentTrails);
                    sender.sendMessage(Main.getPlugin().getConfig().getString("Commands-selectTrailSenderMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name).replaceAll("%Target%", args[1]));
                    target.sendMessage(Main.getPlugin().getConfig().getString("Commands-selectTrailTargetMessage").replaceAll("&", "\u00A7").replaceAll("%TrailName%", this.name));
                    return true;
                }
            }
            else
            {
                sender.sendMessage("Invalid Args - Please check syntax.");
            }
        }
        return false;
    }

    public TrailDisplayEvent displayEvent(String name, double location, int amount, int cooldown, float speed, int range, Particle type)
    {
        TrailDisplayEvent event = new TrailDisplayEvent(name,
                location, amount, cooldown, speed, range, type);
        Main.getPlugin().getServer().getPluginManager().callEvent(event);
        return event;
    }
}
