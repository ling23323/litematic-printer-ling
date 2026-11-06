package ling.printer.guide;

import ling.printer.enums.BlockMatchResult;
import ling.printer.placement.SchematicBlockContext;

public class SkipGuide extends Guide {

    public SkipGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildAction(BlockMatchResult state) {
        return Result.SKIP;
    }
}