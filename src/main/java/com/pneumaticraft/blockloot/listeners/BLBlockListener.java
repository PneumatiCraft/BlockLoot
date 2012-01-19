package com.pneumaticraft.blockloot.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class BLBlockListener extends BlockListener {
    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        //this.duplicateDrops(event.getDrops());
        
        // 10% of the time, drop an experience orb
        if((new Random()).nextInt(10) == 0) {
            Block b = event.getBlock();
            ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), ExperienceOrb.class)).setExperience(1);
        }
    }

    private void duplicateDrops(List<ItemStack> drops) {
        drops.addAll(drops);
    }
}
