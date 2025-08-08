package xfacthd.framedblockslite.common.data.conpreds.slab;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import xfacthd.framedblockslite.api.block.FramedProperties;
import xfacthd.framedblockslite.api.predicate.contex.NonDetailedConnectionPredicate;

public final class DoublePanelConnectionPredicate extends NonDetailedConnectionPredicate
{
    public static final DoublePanelConnectionPredicate INSTANCE = new DoublePanelConnectionPredicate();

    private DoublePanelConnectionPredicate() { }

    @Override
    public boolean canConnectFullEdge(BlockState state, Direction side, @Nullable Direction edge)
    {
        Direction facing = state.getValue(FramedProperties.FACING_HOR);
        if (side.getAxis() == facing.getAxis())
        {
            return true;
        }
        return edge != null && edge.getAxis() == facing.getAxis();
    }
}
