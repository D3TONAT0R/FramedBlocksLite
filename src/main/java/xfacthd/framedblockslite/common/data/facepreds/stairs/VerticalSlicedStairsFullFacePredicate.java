package xfacthd.framedblockslite.common.data.facepreds.stairs;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblockslite.api.block.FramedProperties;
import xfacthd.framedblockslite.api.predicate.fullface.FullFacePredicate;
import xfacthd.framedblockslite.common.data.PropertyHolder;
import xfacthd.framedblockslite.common.data.property.StairsType;

public final class VerticalSlicedStairsFullFacePredicate implements FullFacePredicate
{
    @Override
    public boolean test(BlockState state, Direction side)
    {
        Direction dir = state.getValue(FramedProperties.FACING_HOR);
        StairsType type = state.getValue(PropertyHolder.STAIRS_TYPE);
        boolean right = state.getValue(PropertyHolder.RIGHT);

        if (right)
        {
            return side == dir.getCounterClockWise() && !type.isCounterClockwise();
        }
        else
        {
            return side == dir && !type.isForward();
        }
    }
}
