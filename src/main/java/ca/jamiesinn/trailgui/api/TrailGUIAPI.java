package ca.jamiesinn.trailgui.api;

import ca.jamiesinn.trailgui.TrailGUI;
import ca.jamiesinn.trailgui.trails.Trail;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TrailGUIAPI
{
    private TrailGUI plugin;

    public TrailGUIAPI(TrailGUI plugin)
    {
        this.plugin = plugin;
    }


    public void addTrail(String name, Player player)
    {
        List<Trail> trails = new ArrayList<>(TrailGUI.trailTypes.values());
        trails.forEach(trail ->
        {
            if (trail.getName().equalsIgnoreCase(name))
            {
                List<Trail> currentTrails = new ArrayList<Trail>();
                if (TrailGUI.enabledTrails.containsKey(player.getUniqueId()))
                {
                    currentTrails = TrailGUI.enabledTrails.get(player.getUniqueId());
                }
                if (currentTrails.contains(trail))
                {
                    return;
                }
                currentTrails.add(trail);
                Trail.enableEvent(player, currentTrails);
                TrailGUI.enabledTrails.put(player.getUniqueId(), currentTrails);
            }
        });
    }

    public void removeTrail(String name, Player player)
    {
        List<Trail> trails = new ArrayList<>(TrailGUI.trailTypes.values());
        trails.forEach(t ->
        {
            if(t.getName().equalsIgnoreCase(name))
            {
                List<Trail> currentTrails = new ArrayList<Trail>();
                if (TrailGUI.enabledTrails.containsKey(player.getUniqueId()))
                {
                    currentTrails = TrailGUI.enabledTrails.get(player.getUniqueId());
                }
                currentTrails.removeIf(tr->tr==t);
                Trail.disableEvent(player, t);
                TrailGUI.enabledTrails.put(player.getUniqueId(), currentTrails);
            }
        });
    }
}
