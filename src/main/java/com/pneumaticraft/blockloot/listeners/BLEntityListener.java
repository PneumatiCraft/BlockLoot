package com.pneumaticraft.blockloot.listeners;

import org.bukkit.Difficulty;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BLEntityListener implements Listener {

    @EventHandler
    public void entityDeath(EntityDeathEvent event) {
        // Don't dupe if it's a player
        if (event.getEntity() instanceof Player || event.getDroppedExp() == 0) {
            return;
        }
        int itemMultiplier = this.getItemMultiplier(event.getEntity().getLocation().getWorld().getDifficulty());

        // Take one away, the drops already contain 1
        // Iterate, duping the items.
        List<ItemStack> stackCopy = new ArrayList<ItemStack>();
        stackCopy.addAll(event.getDrops());
        int i = 1;
        while (i < itemMultiplier) {
            event.getDrops().addAll(stackCopy);
            i++;
        }
        // Take the dropped xp
        int xpDropped = (int) Math.pow((double) event.getDroppedExp(), (double) event.getEntity().getLocation().getWorld().getDifficulty().getValue());
        // Final xp is: Dropped ^ Difficulty
        // This means peaceful will ALWAYS be 1
        event.setDroppedExp(xpDropped);
    }

    private int getItemMultiplier(Difficulty diff) {
        int maxYield = diff.getValue();
        // Extra bonus for playing on hardest
        maxYield = (maxYield == 3) ? 4 : maxYield;
        Random random = new Random();
        // Grab a new random number between 1 - 100
        int value = random.nextInt(99) + 1;
        // If that value is above 87, then the user
        // has the posibility of gaining 1 -  difficulty items.
        // Each lower tier takes one away,
        if (value <= 87) {
            maxYield--;
        }
        if (value <= 75) {
            maxYield--;
        }
        if (value <= 50) {
            maxYield--;
        }

        if (maxYield < 1) {
            // If the given yield is less than one, then the user gets 1 item
            return 1;
        }

        int second = random.nextInt(maxYield) + 1;
        return second;
    }
}
