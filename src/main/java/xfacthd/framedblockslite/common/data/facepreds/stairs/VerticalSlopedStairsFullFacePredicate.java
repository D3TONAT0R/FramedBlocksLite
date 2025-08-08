package xfacthd.framedblockslite.common.data.facepreds.stairs;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblockslite.api.block.FramedProperties;
import xfacthd.framedblockslite.api.predicate.fullface.FullFacePredicate;
import xfacthd.framedblockslite.common.data.PropertyHolder;
import xfacthd.framedblockslite.common.data.property.HorizontalRotation;

public final class VerticalSlopedStairsFullFacePredicate implements FullFacePredicate
{
    @Override
    public boolean test(BlockState state, Direction side)
    {
        Direction facing = state.getValue(FramedProperties.FACING_HOR);
        if (side == facing)
        {
            return true;
        }

        HorizontalRotation rot = state.getValue(PropertyHolder.ROTATION);
        Direction rotDir = rot.getOpposite().withFacing(facing);
        Direction perpRotDir = rot.rotate(Rotation.CLOCKWISE_90).withFacing(facing);
        return side == rotDir || side == perpRotDir;
    }
}
