package ling.printer.guide.guides;

import ling.printer.config.Configs;
import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.ClickAction;
import net.minecraft.world.level.block.NoteBlock;

/**
 * 音符盒
 */
public class NoteBlockGuide extends Guide {

    public NoteBlockGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionWrongState(BlockMatchResult state) {
        if (Configs.Print.NOTE_BLOCK_TUNING.getBooleanValue()
                && !getProperty(requiredState, NoteBlock.NOTE).equals(getProperty(currentState, NoteBlock.NOTE))) {
            return Result.success(new ClickAction());
        }
        return Result.SKIP;
    }
}
