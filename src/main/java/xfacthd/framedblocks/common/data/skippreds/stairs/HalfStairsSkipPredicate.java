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
@CullTest(BlockType.FRAMED_HALF_STAIRS)
public final class HalfStairsSkipPredicate implements SideSkipPredicate
{
    @Override
    public boolean test(BlockGetter level, BlockPos pos, BlockState state, BlockState adjState, Direction side)
    {
        if (adjState.getBlock() instanceof IFramedBlock block && block.getBlockType() instanceof BlockType blockType)
        {
            Direction dir = state.getValue(FramedProperties.FACING_HOR);
            boolean top = state.getValue(FramedProperties.TOP);
            boolean right = state.getValue(PropertyHolder.RIGHT);

            return switch (blockType)
            {
                case FRAMED_HALF_STAIRS -> testAgainstHalfStairs(
                        dir, top, right, adjState, side
                );
                case FRAMED_SLAB_EDGE -> testAgainstSlabEdge(
                        dir, top, right, adjState, side
                );
                case FRAMED_SLAB_CORNER -> testAgainstSlabCorner(
                        dir, top, right, adjState, side
                );
                case FRAMED_PANEL -> testAgainstPanel(
                        dir, top, right, adjState, side
                );
                case FRAMED_CORNER_PILLAR -> testAgainstCornerPillar(
                        dir, top, right, adjState, side
                );
                case FRAMED_STAIRS -> testAgainstStairs(
                        dir, top, right, adjState, side
                );
                case FRAMED_VERTICAL_STAIRS -> testAgainstVerticalStairs(
                        dir, top, right, adjState, side
                );
                case FRAMED_VERTICAL_HALF_STAIRS -> testAgainstVerticalHalfStairs(
                        dir, top, right, adjState, side
                );
                default -> false;
            };
        }
        return false;
    }

    @CullTest.TestTarget(BlockType.FRAMED_HALF_STAIRS)
    private static boolean testAgainstHalfStairs(
            Direction dir, boolean top, boolean right, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);
        boolean adjRight = adjState.getValue(PropertyHolder.RIGHT);

        return StairsDirs.HalfStairs.getStairDir(dir, top, right, side).isEqualTo(StairsDirs.HalfStairs.getStairDir(adjDir, adjTop, adjRight, side.getOpposite())) ||
               StairsDirs.HalfStairs.getHalfDir(dir, top, right, side).isEqualTo(StairsDirs.HalfStairs.getHalfDir(adjDir, adjTop, adjRight, side.getOpposite())) ||
               StairsDirs.HalfStairs.getCornerDir(dir, top, right, side).isEqualTo(StairsDirs.HalfStairs.getCornerDir(adjDir, adjTop, adjRight, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_SLAB_EDGE)
    private static boolean testAgainstSlabEdge(
            Direction dir, boolean top, boolean right, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);

        return StairsDirs.HalfStairs.getHalfDir(dir, top, right, side).isEqualTo(SlabDirs.SlabEdge.getHalfDir(adjDir, adjTop, side.getOpposite())) ||
               StairsDirs.HalfStairs.getCornerDir(dir, top, right, side).isEqualTo(SlabDirs.SlabEdge.getCornerDir(adjDir, adjTop, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_SLAB_CORNER)
    private static boolean testAgainstSlabCorner(
            Direction dir, boolean top, boolean right, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);

        return StairsDirs.HalfStairs.getCornerDir(dir, top, right, side).isEqualTo(SlabDirs.SlabCorner.getCornerDir(adjDir, adjTop, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_PANEL)
    private static boolean testAgainstPanel(
            Direction dir, boolean top, boolean right, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        return StairsDirs.HalfStairs.getHalfDir(dir, top, right, side).isEqualTo(SlabDirs.Panel.getHalfDir(adjDir, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_CORNER_PILLAR)
    private static boolean testAgainstCornerPillar(
            Direction dir, boolean top, boolean right, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        return StairsDirs.HalfStairs.getHalfDir(dir, top, right, side).isEqualTo(PillarDirs.CornerPillar.getHalfDir(adjDir, side.getOpposite())) ||
               StairsDirs.HalfStairs.getCornerDir(dir, top, right, side).isEqualTo(PillarDirs.CornerPillar.getCornerDir(adjDir, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_STAIRS)
    private static boolean testAgainstStairs(
            Direction dir, boolean top, boolean right, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(BlockStateProperties.HORIZONTAL_FACING);
        StairsShape adjShape = adjState.getValue(BlockStateProperties.STAIRS_SHAPE);
        Half adjHalf = adjState.getValue(BlockStateProperties.HALF);

        return StairsDirs.HalfStairs.getStairDir(dir, top, right, side).isEqualTo(StairsDirs.Stairs.getStairDir(adjDir, adjShape, adjHalf, side.getOpposite())) ||
               StairsDirs.HalfStairs.getHalfDir(dir, top, right, side).isEqualTo(StairsDirs.Stairs.getHalfDir(adjDir, adjShape, adjHalf, side.getOpposite())) ||
               StairsDirs.HalfStairs.getCornerDir(dir, top, right, side).isEqualTo(StairsDirs.Stairs.getCornerDir(adjDir, adjShape, adjHalf, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_VERTICAL_STAIRS)
    private static boolean testAgainstVerticalStairs(
            Direction dir, boolean top, boolean right, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        StairsType adjType = adjState.getValue(PropertyHolder.STAIRS_TYPE);

        return StairsDirs.HalfStairs.getStairDir(dir, top, right, side).isEqualTo(StairsDirs.VerticalStairs.getStairDir(adjDir, adjType, side.getOpposite())) ||
               StairsDirs.HalfStairs.getHalfDir(dir, top, right, side).isEqualTo(StairsDirs.VerticalStairs.getHalfDir(adjDir, adjType, side.getOpposite())) ||
               StairsDirs.HalfStairs.getCornerDir(dir, top, right, side).isEqualTo(StairsDirs.VerticalStairs.getCornerDir(adjDir, adjType, side.getOpposite()));
    }

    @CullTest.TestTarget(BlockType.FRAMED_VERTICAL_HALF_STAIRS)
    private static boolean testAgainstVerticalHalfStairs(
            Direction dir, boolean top, boolean right, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);

        return StairsDirs.HalfStairs.getCornerDir(dir, top, right, side).isEqualTo(StairsDirs.VerticalHalfStairs.getCornerDir(adjDir, adjTop, side.getOpposite()));
    }
}
