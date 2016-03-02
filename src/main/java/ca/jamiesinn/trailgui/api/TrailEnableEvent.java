package ca.jamiesinn.trailgui.api;

import ca.jamiesinn.trailgui.trails.Trail;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class TrailEnableEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private Trail trail;
    private Player player;
    private List<Trail> trails;
    public TrailEnableEvent(Player player, List<Trail> trails)
    {
        this.player = player;
        this.trails = trails;

    }
    public TrailEnableEvent(Player player, Trail trail)
    {
        this.trail = trail;
        this.player = player;
    }

    @Override public HandlerList getHandlers()
    {
        return handlers;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Trail getTrail()
    {
        return trail;
    }

    public List<Trail> getTrails()
    {
        if(trails.isEmpty())
            trails.add(trail);
        return trails;
    }
}
