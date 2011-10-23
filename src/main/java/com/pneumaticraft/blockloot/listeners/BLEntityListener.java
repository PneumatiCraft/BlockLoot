package com.pneumaticraft.blockloot.listeners;

import org.bukkit.Difficulty;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BLEntityListener extends EntityListener {
    @Override
    public void onEntityDeath(EntityDeathEvent event) {
//        System.out.print("=========");
        if (event.getEntity() instanceof Player || event.getDroppedExp() == 0) {
            return;
        }
        int itemMultiplier = this.getItemMultiplier(event.getEntity().getLocation().getWorld().getDifficulty());

        // Take one away, the drops already contain 1
//        System.out.print("Item Multiplier: " + itemMultiplier);
        // Iterate, duping the items.
        List<ItemStack> stackCopy = new ArrayList<ItemStack>();
        stackCopy.addAll(event.getDrops());
        int i = 1;
        while (i < itemMultiplier) {
            event.getDrops().addAll(stackCopy);
            i++;
        }
        event.setDroppedExp((int) Math.pow((double) event.getDroppedExp(), (double) event.getEntity().getLocation().getWorld().getDifficulty().getValue()));
//        System.out.print("");
//        System.out.print("");
    }

    private int getItemMultiplier(Difficulty diff) {
        int maxYeild = diff.getValue();
        // Extra bonus for playing on hardest
        maxYeild = (maxYeild == 3) ? 4 : maxYeild;
        Random random = new Random();
        int value = random.nextInt(99) + 1;
//        System.out.print("1st Random was: " + value);
//        System.out.print("maxYeild started at: " + maxYeild);
        // Get a random value between 1 and 100.
        // If that value is above 87, then the user
        // has the posibility of gaining 1 -  difficulty items.
        // Each lower tier takes one away,
        if (value <= 87) {
            maxYeild--;
        }
        if (value <= 75) {
            maxYeild--;
        }
        if (value <= 50) {
            maxYeild--;
        }
//        System.out.print("maxYeild ended at: " + maxYeild);
        if (maxYeild < 1) {
            // If the given yeild is less than one, then the user gets 1 item
            return 1;
        }

        int second = random.nextInt(maxYeild) + 1;
//        System.out.print("2nd Random was: " + second);
        return second;
    }
}
