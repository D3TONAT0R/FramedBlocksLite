package xfacthd.framedblockslite.common.data.facepreds.stairs;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblockslite.api.block.FramedProperties;
import xfacthd.framedblockslite.api.predicate.fullface.FullFacePredicate;
import xfacthd.framedblockslite.common.data.PropertyHolder;
import xfacthd.framedblockslite.common.data.property.HorizontalRotation;

public final class VerticalSlicedSlopedStairsSlopeFullFacePredicate implements FullFacePredicate
{
    @Override
    public boolean test(BlockState state, Direction side)
    {
        Direction facing = state.getValue(FramedProperties.FACING_HOR);
        HorizontalRotation rot = state.getValue(PropertyHolder.ROTATION);
        return side == rot.withFacing(facing) || side == rot.rotate(Rotation.COUNTERCLOCKWISE_90).withFacing(facing);
    }
}
