package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.ClickAction;
import ling.printer.Reference;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.item.Items;

/**
 * 篝火
 */
public class CampfireGuide extends Guide {

    public CampfireGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionWrongState(BlockMatchResult state) {
        boolean requiredLit = getProperty(requiredState, CampfireBlock.LIT).orElseThrow();
        boolean currentLit = getProperty(currentState, CampfireBlock.LIT).orElseThrow();

        if (!requiredLit && currentLit) {
            return Result.success(new ClickAction().setItems(Reference.SHOVEL_ITEMS).setSides(Direction.UP));
        }
        if (requiredLit && !currentLit) {
            return Result.success(new ClickAction().setItems(Items.FLINT_AND_STEEL, Items.FIRE_CHARGE));
        }

        return Result.SKIP;
    }
}
