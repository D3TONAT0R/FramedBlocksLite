package xfacthd.framedblockslite.common.data.skippreds.pillar;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import xfacthd.framedblockslite.api.block.IFramedBlock;
import xfacthd.framedblockslite.api.predicate.cull.SideSkipPredicate;
import xfacthd.framedblockslite.common.data.BlockType;
import xfacthd.framedblockslite.common.data.skippreds.CullTest;

/**
 This class is machine-generated, any manual changes to this class will be overwritten.
 */
@CullTest(BlockType.FRAMED_HALF_PILLAR)
public final class HalfPillarSkipPredicate implements SideSkipPredicate
{
    @Override
    public boolean test(BlockGetter level, BlockPos pos, BlockState state, BlockState adjState, Direction side)
    {
        Direction dir = state.getValue(BlockStateProperties.FACING);
        if (PillarDirs.HalfPillar.testEarlyExit(dir, side))
        {
            return false;
        }

        if (adjState.getBlock() instanceof IFramedBlock block && block.getBlockType() instanceof BlockType blockType)
        {
            return switch (blockType)
            {
                case FRAMED_HALF_PILLAR -> testAgainstHalfPillar(
                        dir, adjState, side
                );
                case FRAMED_WALL -> testAgainstWall(
                        dir, adjState, side
                );
                case FRAMED_PILLAR -> testAgainstPillar(
                        dir, adjState, side
                );
                default -> false;
            };
        }
        return false;
    }

    @CullTest.TestTarget(BlockType.FRAMED_HALF_PILLAR)
    private static boolean testAgainstHalfPillar(
            Direction dir, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(BlockStateProperties.FACING);
        return (PillarDirs.HalfPillar.isPillarDir(dir, side) && PillarDirs.HalfPillar.isPillarDir(adjDir, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_WALL)
    private static boolean testAgainstWall(
            Direction dir, BlockState adjState, Direction side
    )
    {
        boolean adjUp = adjState.getValue(BlockStateProperties.UP);
        return (PillarDirs.HalfPillar.isPillarDir(dir, side) && PillarDirs.Wall.isPillarDir(adjUp, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_PILLAR)
    private static boolean testAgainstPillar(
            Direction dir, BlockState adjState, Direction side
    )
    {
        Direction.Axis adjAxis = adjState.getValue(BlockStateProperties.AXIS);
        return (PillarDirs.HalfPillar.isPillarDir(dir, side) && PillarDirs.Pillar.isPillarDir(adjAxis, side.getOpposite()));
    }
}
