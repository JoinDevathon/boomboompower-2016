package org.devathon.contest2016;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TurretCreateEvent extends Event {

    public static HandlerList handlerList = new HandlerList();
    private Location creationLocation;

    public TurretCreateEvent(Location creationLocation) {
        this.creationLocation = creationLocation;
    }

    public Location getLocation() {
        return this.creationLocation;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
