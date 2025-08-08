package xfacthd.framedblockslite.common.data.facepreds.stairs;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblockslite.api.block.FramedProperties;
import xfacthd.framedblockslite.api.predicate.fullface.FullFacePredicate;
import xfacthd.framedblockslite.common.data.PropertyHolder;
import xfacthd.framedblockslite.common.data.property.StairsType;

public final class VerticalStairsFullFacePredicate implements FullFacePredicate
{
    @Override
    public boolean test(BlockState state, Direction side)
    {
        StairsType type = state.getValue(PropertyHolder.STAIRS_TYPE);
        Direction dir = state.getValue(FramedProperties.FACING_HOR);
        if (side == dir)
        {
            return !type.isForward();
        }
        else if (side == dir.getCounterClockWise())
        {
            return !type.isCounterClockwise();
        }
        return false;
    }
}
