package xfacthd.framedblockslite.common.data.facepreds.stairs;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblockslite.api.block.FramedProperties;
import xfacthd.framedblockslite.api.predicate.fullface.FullFacePredicate;
import xfacthd.framedblockslite.common.data.PropertyHolder;
import xfacthd.framedblockslite.common.data.property.StairsType;

public final class VerticalDoubleStairsFullFacePredicate implements FullFacePredicate
{
    @Override
    public boolean test(BlockState state, Direction side)
    {
        Direction facing = state.getValue(FramedProperties.FACING_HOR);
        StairsType type = state.getValue(PropertyHolder.STAIRS_TYPE);

        if (side == facing)
        {
            return type == StairsType.VERTICAL || type == StairsType.TOP_CCW || type == StairsType.BOTTOM_CCW;
        }
        if (side == facing.getCounterClockWise())
        {
            return type == StairsType.VERTICAL || type == StairsType.TOP_FWD || type == StairsType.BOTTOM_FWD;
        }
        return false;
    }
}
