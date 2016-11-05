package org.devathon.contest2016;

import org.bukkit.plugin.java.JavaPlugin;

public class DevathonPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        new PlayerListener(this);
    }

    @Override
    public void onDisable() {}
}

