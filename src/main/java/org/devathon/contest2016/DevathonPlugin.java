package org.devathon.contest2016;

import org.bukkit.plugin.java.JavaPlugin;

public class DevathonPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Utils.broadcast("&bPlugin loading", Utils.Type.NORMAL);
        new CowListener(this);
    }

    @Override
    public void onDisable() {
        Utils.broadcast("&bPlugin shutting down", Utils.Type.NORMAL);
    }
}

