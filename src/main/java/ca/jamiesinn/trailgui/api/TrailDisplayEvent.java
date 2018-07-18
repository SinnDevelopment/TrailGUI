package ca.jamiesinn.trailgui.api;

import org.bukkit.Particle;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TrailDisplayEvent extends Event implements Cancellable
{
    private static final HandlerList handlers = new HandlerList();
    private String name;
    private double displayLocation;
    private int amount;
    private int cooldown;
    private float speed;
    private int range;
    private Particle type;
    private boolean cancelled;

    public TrailDisplayEvent(String name, double displayLocation, int amount, int cooldown, float speed, int range, Particle type)
    {
        this.name = name;
        this.displayLocation = displayLocation;
        this.amount = amount;
        this.cooldown = cooldown;
        this.speed = speed;
        this.range = range;
        this.type = type;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }


    public double getDisplayLocation()
    {
        return displayLocation;
    }

    public int getAmount()
    {
        return amount;
    }

    public int getCooldown()
    {
        return cooldown;
    }

    public float getSpeed()
    {
        return speed;
    }

    public int getRange()
    {
        return range;
    }

    public String getName()
    {
        return name;
    }

    public Particle getType()
    {
        return type;
    }

    public boolean isCancelled()
    {
        return cancelled;
    }

    public void setCancelled(boolean cancelled)
    {
        this.cancelled = cancelled;
    }
}
