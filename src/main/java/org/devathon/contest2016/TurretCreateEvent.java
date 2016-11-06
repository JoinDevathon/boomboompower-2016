package org.devathon.contest2016;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TurretCreateEvent extends Event {

    public static HandlerList handlerList = new HandlerList();
    private Location topBlock;
    private Location bottomBlock;

    public TurretCreateEvent(Location topBlock, Location bottomBlock) {
        this.topBlock = topBlock;
        this.bottomBlock = bottomBlock;
    }

    public Location getTopBlock() {
        return this.topBlock;
    }

    public Location getBottomBlock() {
        return this.bottomBlock;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
