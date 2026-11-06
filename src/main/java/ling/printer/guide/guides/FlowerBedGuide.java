package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.ClickAction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/**
 * 花簇
 */
//#if MC >= 11904
public class FlowerBedGuide extends Guide {

    public FlowerBedGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionWrongState(BlockMatchResult state) {
        int requiredAmount = getProperty(requiredState, BlockStateProperties.FLOWER_AMOUNT).orElse(1);
        int currentAmount = getProperty(currentState, BlockStateProperties.FLOWER_AMOUNT).orElse(1);
        if (currentAmount <= requiredAmount) {
            return Result.success(new ClickAction().setItem(requiredBlock.asItem()));
        }
        return Result.SKIP;
    }
}
//#endif
