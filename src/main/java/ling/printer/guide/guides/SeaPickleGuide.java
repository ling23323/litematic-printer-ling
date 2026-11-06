package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.Action;
import ling.printer.placement.action.ClickAction;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.item.Items;

/**
 * 海泡菜
 */
public class SeaPickleGuide extends Guide {

    public SeaPickleGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionMissingBlock(BlockMatchResult state) {
        // 海泡菜只能放在支撑方块上（珊瑚块/海晶石等），需要从下方点击放置
        return Result.success(new Action()
                .setSides(Direction.DOWN)
                .setRequiresSupport());
    }

    @Override
    protected Result onBuildActionWrongState(BlockMatchResult state) {
        if (currentState.getBlock() instanceof SeaPickleBlock) {
            int currentPickles = getProperty(currentState, SeaPickleBlock.PICKLES).orElse(1);
            int requiredPickles = getProperty(requiredState, SeaPickleBlock.PICKLES).orElse(1);
            if (currentPickles < requiredPickles) {
                return Result.success(new ClickAction().setItem(Items.SEA_PICKLE));
            }
        }
        return Result.SKIP;
    }
}
