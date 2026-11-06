package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.Action;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.AmethystClusterBlock;

/**
 * 紫水晶芽
 */
public class AmethystGuide extends Guide {

    public AmethystGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionMissingBlock(BlockMatchResult state) {
        Direction attachDirection = getProperty(requiredState, AmethystClusterBlock.FACING)
                .orElseThrow()
                .getOpposite();

        return Result.success(new Action()
                .setSides(attachDirection)
                .setRequiresSupport());
    }
}
