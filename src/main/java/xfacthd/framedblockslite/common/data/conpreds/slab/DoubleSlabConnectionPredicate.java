package xfacthd.framedblockslite.common.data.conpreds.slab;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import xfacthd.framedblockslite.api.predicate.contex.NonDetailedConnectionPredicate;
import xfacthd.framedblockslite.api.util.Utils;

public final class DoubleSlabConnectionPredicate extends NonDetailedConnectionPredicate
{
    public static final DoubleSlabConnectionPredicate INSTANCE = new DoubleSlabConnectionPredicate();

    private DoubleSlabConnectionPredicate() { }

    @Override
    public boolean canConnectFullEdge(BlockState state, Direction side, @Nullable Direction edge)
    {
        return Utils.isY(side) || (edge != null && Utils.isY(edge));
    }
}
