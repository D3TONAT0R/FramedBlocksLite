package xfacthd.framedblocks.common.data.skippreds.pillar;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.predicate.cull.SideSkipPredicate;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.property.PillarConnection;
import xfacthd.framedblocks.common.data.skippreds.CullTest;
import xfacthd.framedblocks.common.data.skippreds.slope.SlopeDirs;

/**
 This class is machine-generated, any manual changes to this class will be overwritten.
 */
@CullTest(BlockType.FRAMED_FENCE)
public final class FenceSkipPredicate implements SideSkipPredicate
{
    @Override
    public boolean test(BlockGetter level, BlockPos pos, BlockState state, BlockState adjState, Direction side)
    {
        if (adjState.getBlock() instanceof IFramedBlock block && block.getBlockType() instanceof BlockType blockType)
        {
            return switch (blockType)
            {
                case FRAMED_FENCE -> testAgainstFence(
                        state, adjState, side
                );
                case FRAMED_FENCE_GATE -> testAgainstFenceGate(
                        state, adjState, side
                );
                default -> false;
            };
        }
        return false;
    }

    @CullTest.TestTarget(BlockType.FRAMED_FENCE)
    private static boolean testAgainstFence(
            BlockState state, BlockState adjState, Direction side
    )
    {
        return PillarDirs.Fence.testFenceArmDir(state, adjState, side) ||
               (PillarDirs.Fence.isPostDir(side) && PillarDirs.Fence.isPostDir(side.getOpposite()));
    }

    @CullTest.TestTarget(value = BlockType.FRAMED_FENCE_GATE, oneWay = true)
    private static boolean testAgainstFenceGate(
            BlockState state, BlockState adjState, Direction side
    )
    {
        return PillarDirs.Fence.testFenceArmToGateDir(state, adjState, side);
    }
}
