package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.Action;
import ling.printer.placement.action.ClickAction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.item.Items;

/**
 * 花盆
 */
public class FlowerPotGuide extends Guide {

    public FlowerPotGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionMissingBlock(BlockMatchResult state) {
        return Result.success(new Action().setItem(Items.FLOWER_POT));
    }

    @Override
    protected Result onBuildActionWrongBlock(BlockMatchResult state) {
        if (requiredBlock instanceof FlowerPotBlock potBlock) {
            Block content = potBlock.getPotted();
            if (content != Blocks.AIR) {
                return Result.success(new ClickAction().setItem(content.asItem()));
            }
        }
        return Result.SKIP;
    }

    @Override
    protected Result onBuildActionWrongState(BlockMatchResult state) {
        return Result.SKIP;
    }
}
