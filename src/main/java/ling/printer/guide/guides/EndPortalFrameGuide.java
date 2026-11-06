package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.ClickAction;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.item.Items;

/**
 * 末地传送门框架
 */
public class EndPortalFrameGuide extends Guide {

    public EndPortalFrameGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionWrongState(BlockMatchResult state) {
        boolean requiredHasEye = getProperty(requiredState, EndPortalFrameBlock.HAS_EYE).orElseThrow();
        boolean currentHasEye = getProperty(currentState, EndPortalFrameBlock.HAS_EYE).orElseThrow();
        if (requiredHasEye && !currentHasEye) {
            return Result.success(new ClickAction().setItem(Items.ENDER_EYE));
        }
        return Result.SKIP;
    }
}
