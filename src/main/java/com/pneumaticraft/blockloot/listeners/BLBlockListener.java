import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BLBlockListener extends BlockListener {

    public static final int DEFAULT_BLOCK_DROP_PROBABILITY = 10;

    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        //this.duplicateDrops(event.getDrops());

        // 10% of the time, drop an experience orb
        Block b = event.getBlock();
        if((new Random()).nextInt(100) < this.getExpDropProbability(b.getType())) {
            ((ExperienceOrb)b.getWorld().spawn(b.getLocation(), ExperienceOrb.class)).setExperience(1);
        }
    }

    private int getExpDropProbability(Material m) {
        Map<Material, Integer> ores = new HashMap<Material, Integer>() {{
            put(Material.COAL_ORE, 15);
            put(Material.IRON_ORE, 25);
            put(Material.REDSTONE_ORE, 30);
            put(Material.GOLD_ORE, 50);
            put(Material.LAPIS_ORE, 70);
            put(Material.DIAMOND_ORE, 75);
        }};

        Integer oreProbability = ores.get(m);
        if(oreProbability == null) {
            oreProbability = DEFAULT_BLOCK_DROP_PROBABILITY;
        }
        return oreProbability;
    }

    private void duplicateDrops(List<ItemStack> drops) {
        drops.addAll(drops);
    }
}
