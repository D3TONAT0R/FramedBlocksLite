package xfacthd.framedblockslite.common.data.skippreds.pillar;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblockslite.api.block.IFramedBlock;
import xfacthd.framedblockslite.api.predicate.cull.SideSkipPredicate;
import xfacthd.framedblockslite.common.data.BlockType;
import xfacthd.framedblockslite.common.data.skippreds.CullTest;

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