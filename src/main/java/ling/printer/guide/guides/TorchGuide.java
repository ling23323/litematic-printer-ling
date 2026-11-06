package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.Action;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.WallTorchBlock;

/**
 * 火把
 */
public class TorchGuide extends Guide {

    public TorchGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionMissingBlock(BlockMatchResult state) {
        Direction facing = getProperty(requiredState, WallTorchBlock.FACING).orElse(Direction.UP);
        return Result.success(new Action()
                .setSides(facing.getOpposite())
                .setRequiresSupport());
    }
}
