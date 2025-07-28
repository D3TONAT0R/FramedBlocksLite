package xfacthd.framedblocks.common.data.skippreds.stairs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.predicate.cull.SideSkipPredicate;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.property.CornerType;
import xfacthd.framedblocks.common.data.property.HorizontalRotation;
import xfacthd.framedblocks.common.data.property.SlopeType;
import xfacthd.framedblocks.common.data.property.StairsType;
import xfacthd.framedblocks.common.data.skippreds.CullTest;
import xfacthd.framedblocks.common.data.skippreds.pillar.PillarDirs;
import xfacthd.framedblocks.common.data.skippreds.slab.SlabDirs;

/**
 This class is machine-generated, any manual changes to this class will be overwritten.
 */
@CullTest(BlockType.FRAMED_VERTICAL_STAIRS)
public final class VerticalStairsSkipPredicate implements SideSkipPredicate
{
    @Override
    public boolean test(BlockGetter level, BlockPos pos, BlockState state, BlockState adjState, Direction side)
    {
        if (adjState.getBlock() instanceof IFramedBlock block && block.getBlockType() instanceof BlockType blockType)
        {
            Direction dir = state.getValue(FramedProperties.FACING_HOR);
            StairsType type = state.getValue(PropertyHolder.STAIRS_TYPE);

            return switch (blockType)
            {
                case FRAMED_VERTICAL_STAIRS -> testAgainstVerticalStairs(
                        dir, type, adjState, side
                );
                case FRAMED_SLAB_EDGE -> testAgainstSlabEdge(
                        dir, type, adjState, side
                );
                case FRAMED_SLAB_CORNER -> testAgainstSlabCorner(
                        dir, type, adjState, side
                );
                case FRAMED_PANEL -> testAgainstPanel(
                        dir, type, adjState, side
                );
                case FRAMED_CORNER_PILLAR -> testAgainstCornerPillar(
                        dir, type, adjState, side
                );
                case FRAMED_STAIRS -> testAgainstStairs(
                        dir, type, adjState, side
                );
                case FRAMED_HALF_STAIRS -> testAgainstHalfStairs(
                        dir, type, adjState, side
                );
                case FRAMED_VERTICAL_HALF_STAIRS -> testAgainstVerticalHalfStairs(
                        dir, type, adjState, side
                );
                default -> false;
            };
        }
        return false;
    }

    @CullTest.TestTarget(BlockType.FRAMED_VERTICAL_STAIRS)
    private static boolean testAgainstVerticalStairs(
            Direction dir, StairsType type, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        StairsType adjType = adjState.getValue(PropertyHolder.STAIRS_TYPE);

        return StairsDirs.VerticalStairs.getStairDir(dir, type, side).isEqualTo(StairsDirs.VerticalStairs.getStairDir(adjDir, adjType, side.getOpposite())) ||
               StairsDirs.VerticalStairs.getHalfDir(dir, type, side).isEqualTo(StairsDirs.VerticalStairs.getHalfDir(adjDir, adjType, side.getOpposite())) ||
               StairsDirs.VerticalStairs.getCornerDir(dir, type, side).isEqualTo(StairsDirs.VerticalStairs.getCornerDir(adjDir, adjType, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_SLAB_EDGE)
    private static boolean testAgainstSlabEdge(
            Direction dir, StairsType type, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);

        return StairsDirs.VerticalStairs.getHalfDir(dir, type, side).isEqualTo(SlabDirs.SlabEdge.getHalfDir(adjDir, adjTop, side.getOpposite())) ||
               StairsDirs.VerticalStairs.getCornerDir(dir, type, side).isEqualTo(SlabDirs.SlabEdge.getCornerDir(adjDir, adjTop, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_SLAB_CORNER)
    private static boolean testAgainstSlabCorner(
            Direction dir, StairsType type, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);

        return StairsDirs.VerticalStairs.getCornerDir(dir, type, side).isEqualTo(SlabDirs.SlabCorner.getCornerDir(adjDir, adjTop, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_PANEL)
    private static boolean testAgainstPanel(
            Direction dir, StairsType type, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        return StairsDirs.VerticalStairs.getHalfDir(dir, type, side).isEqualTo(SlabDirs.Panel.getHalfDir(adjDir, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_CORNER_PILLAR)
    private static boolean testAgainstCornerPillar(
            Direction dir, StairsType type, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        return StairsDirs.VerticalStairs.getHalfDir(dir, type, side).isEqualTo(PillarDirs.CornerPillar.getHalfDir(adjDir, side.getOpposite())) ||
               StairsDirs.VerticalStairs.getCornerDir(dir, type, side).isEqualTo(PillarDirs.CornerPillar.getCornerDir(adjDir, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_STAIRS)
    private static boolean testAgainstStairs(
            Direction dir, StairsType type, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(BlockStateProperties.HORIZONTAL_FACING);
        StairsShape adjShape = adjState.getValue(BlockStateProperties.STAIRS_SHAPE);
        Half adjHalf = adjState.getValue(BlockStateProperties.HALF);

        return StairsDirs.VerticalStairs.getStairDir(dir, type, side).isEqualTo(StairsDirs.Stairs.getStairDir(adjDir, adjShape, adjHalf, side.getOpposite())) ||
               StairsDirs.VerticalStairs.getHalfDir(dir, type, side).isEqualTo(StairsDirs.Stairs.getHalfDir(adjDir, adjShape, adjHalf, side.getOpposite())) ||
               StairsDirs.VerticalStairs.getCornerDir(dir, type, side).isEqualTo(StairsDirs.Stairs.getCornerDir(adjDir, adjShape, adjHalf, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_HALF_STAIRS)
    private static boolean testAgainstHalfStairs(
            Direction dir, StairsType type, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);
        boolean adjRight = adjState.getValue(PropertyHolder.RIGHT);

        return StairsDirs.VerticalStairs.getStairDir(dir, type, side).isEqualTo(StairsDirs.HalfStairs.getStairDir(adjDir, adjTop, adjRight, side.getOpposite())) ||
               StairsDirs.VerticalStairs.getHalfDir(dir, type, side).isEqualTo(StairsDirs.HalfStairs.getHalfDir(adjDir, adjTop, adjRight, side.getOpposite())) ||
               StairsDirs.VerticalStairs.getCornerDir(dir, type, side).isEqualTo(StairsDirs.HalfStairs.getCornerDir(adjDir, adjTop, adjRight, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_VERTICAL_HALF_STAIRS)
    private static boolean testAgainstVerticalHalfStairs(
            Direction dir, StairsType type, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);

        return StairsDirs.VerticalStairs.getStairDir(dir, type, side).isEqualTo(StairsDirs.VerticalHalfStairs.getStairDir(adjDir, adjTop, side.getOpposite())) ||
               StairsDirs.VerticalStairs.getCornerDir(dir, type, side).isEqualTo(StairsDirs.VerticalHalfStairs.getCornerDir(adjDir, adjTop, side.getOpposite()));
    }
}
