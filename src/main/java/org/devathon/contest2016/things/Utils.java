package org.devathon.contest2016.things;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class Utils {

    private Utils() {}

    public static void breakRelative(Block block, int around) {
        for (int x = -around; x <= around; x++) {
            for (int y = -around; y <= around; y++) {
                for (int z = -around; z <= around; z++) {
                    if (block.getType() != Material.BEDROCK && block.getType() != Material.BARRIER) {
                        block.getRelative(x, y, z).breakNaturally();
                    }
                }
            }
        }
    }
}
