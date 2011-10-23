package com.pneumaticraft.blockloot.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BLBlockListener extends BlockListener {
    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        //this.duplicateDrops(event.getDrops());
    }

    private void duplicateDrops(List<ItemStack> drops) {
        drops.addAll(drops);
    }
}
