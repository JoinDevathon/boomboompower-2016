package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.commands.Remove;
import org.devathon.contest2016.utils.CustomLogging;
import org.devathon.contest2016.utils.Items;

public class DevathonPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        long val = System.currentTimeMillis();
        CustomLogging.console("&aStarting plugin");
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        getCommand("remove").setExecutor(new Remove());
        CustomLogging.console("&aDone. Took " + (System.currentTimeMillis() - val) + "ms !");
    }

    @Override
    public void onDisable() {
        CustomLogging.console("&aShutting down");
        PlayerListener.removeStands();
        Items.closeAll();
    }
}

