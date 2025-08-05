package xfacthd.framedblocks.tests;

import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.neoforge.gametest.GameTestHolder;
import xfacthd.framedblocks.FramedBlocks;
import xfacthd.framedblocks.api.type.IBlockType;
import xfacthd.framedblocks.api.util.FramedConstants;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.data.*;
import xfacthd.framedblocks.api.test.TestUtils;
import xfacthd.framedblocks.common.data.property.*;
import xfacthd.framedblocks.util.TestedType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Tests that are known to fail should be marked as optional.
 * <br>
 * Known failing tests:
 * <ul>
 * </ul>
 */

@GameTestHolder(FramedConstants.MOD_ID)
public final class OcclusionTests
{
    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_CUBE)
    public static void test_Cube(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_CUBE.value().defaultBlockState();
        TestUtils.testBlockOccludesLightBelow(helper, state);
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_SLAB)
    public static void testTop_Slab_Top(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_SLAB.value()
                .defaultBlockState()
                .setValue(FramedProperties.TOP, true);
        TestUtils.testBlockOccludesLightBelow(helper, state);
    }

    @GameTest(template = "box_bottom", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_SLAB)
    public static void testBottom_Slab_Bottom(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_SLAB.value().defaultBlockState();
        TestUtils.testBlockOccludesLightAbove(helper, state);
    }

    @GameTest(template = "box_bottom", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_SLAB)
    public static void testBottom_Slab_Top(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_SLAB.value()
                .defaultBlockState()
                .setValue(FramedProperties.TOP, true);
        TestUtils.testBlockOccludesLightAbove(helper, state);
    }

    @GameTest(template = "box_side", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_PANEL)
    public static void test_Panel_North(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_PANEL.value().defaultBlockState();
        TestUtils.testBlockOccludesLightNorth(helper, state);
    }

    @GameTest(template = "box_side", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_PANEL)
    public static void test_Panel_South(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_PANEL.value()
                .defaultBlockState()
                .setValue(FramedProperties.FACING_HOR, Direction.SOUTH);
        TestUtils.testBlockOccludesLightNorth(helper, state);
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_STAIRS)
    public static void test_Stairs_BottomStraight(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_STAIRS.value().defaultBlockState();
        TestUtils.testBlockOccludesLightBelow(helper, state);
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_STAIRS)
    public static void test_Stairs_TopStraight(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_STAIRS.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.HALF, Half.TOP);
        TestUtils.testBlockOccludesLightBelow(helper, state);
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_STAIRS)
    public static void test_Stairs_BottomInnerLeft(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_STAIRS.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.STAIRS_SHAPE, StairsShape.INNER_LEFT);
        TestUtils.testBlockOccludesLightBelow(helper, state);
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_STAIRS)
    public static void test_Stairs_TopInnerLeft(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_STAIRS.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.STAIRS_SHAPE, StairsShape.INNER_LEFT)
                .setValue(BlockStateProperties.HALF, Half.TOP);
        TestUtils.testBlockOccludesLightBelow(helper, state);
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_STAIRS)
    public static void test_Stairs_BottomInnerRight(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_STAIRS.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.STAIRS_SHAPE, StairsShape.INNER_RIGHT);
        TestUtils.testBlockOccludesLightBelow(helper, state);
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_STAIRS)
    public static void test_Stairs_TopInnerRight(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_STAIRS.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.STAIRS_SHAPE, StairsShape.INNER_RIGHT)
                .setValue(BlockStateProperties.HALF, Half.TOP);
        TestUtils.testBlockOccludesLightBelow(helper, state);
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_STAIRS)
    public static void test_Stairs_BottomOuterLeft(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_STAIRS.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.STAIRS_SHAPE, StairsShape.OUTER_LEFT);
        TestUtils.testBlockOccludesLightBelow(helper, state);
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_STAIRS)
    public static void test_Stairs_TopOuterLeft(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_STAIRS.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.STAIRS_SHAPE, StairsShape.OUTER_LEFT)
                .setValue(BlockStateProperties.HALF, Half.TOP);
        TestUtils.testBlockOccludesLightBelow(helper, state);
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_STAIRS)
    public static void test_Stairs_BottomOuterRight(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_STAIRS.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.STAIRS_SHAPE, StairsShape.OUTER_RIGHT);
        TestUtils.testBlockOccludesLightBelow(helper, state);
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_STAIRS)
    public static void test_Stairs_TopOuterRight(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_STAIRS.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.STAIRS_SHAPE, StairsShape.OUTER_RIGHT)
                .setValue(BlockStateProperties.HALF, Half.TOP);
        TestUtils.testBlockOccludesLightBelow(helper, state);
    }

    @GameTest(template = "box_side", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_DOOR)
    public static void test_Door_Closed(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_DOOR.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH);
        TestUtils.testBlockOccludesLightNorth(helper, state);
    }

    @GameTest(template = "box_side", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_DOOR)
    public static void test_Door_Open(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_DOOR.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)
                .setValue(BlockStateProperties.OPEN, true);
        TestUtils.testBlockOccludesLightNorth(helper, state);
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_TRAPDOOR)
    public static void testTop_Trapdoor_BottomClosed(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_TRAP_DOOR.value().defaultBlockState();
        TestUtils.testBlockOccludesLightBelow(helper, state);
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_TRAPDOOR)
    public static void testTop_Trapdoor_TopClosed(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_TRAP_DOOR.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.HALF, Half.TOP);
        TestUtils.testBlockOccludesLightBelow(helper, state);
    }

    @GameTest(template = "box_bottom", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_TRAPDOOR)
    public static void testBottom_Trapdoor_BottomClosed(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_TRAP_DOOR.value().defaultBlockState();
        TestUtils.testBlockOccludesLightAbove(helper, state);
    }

    @GameTest(template = "box_bottom", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_TRAPDOOR)
    public static void testBottom_Trapdoor_TopClosed(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_TRAP_DOOR.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.HALF, Half.TOP);
        TestUtils.testBlockOccludesLightAbove(helper, state);
    }

    @GameTest(template = "box_side", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_TRAPDOOR)
    public static void test_Trapdoor_NorthBottomOpen(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_TRAP_DOOR.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.OPEN, true);
        TestUtils.testBlockOccludesLightNorth(helper, state);
    }

    @GameTest(template = "box_side", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_TRAPDOOR)
    public static void test_Trapdoor_NorthTopOpen(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_TRAP_DOOR.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.OPEN, true)
                .setValue(BlockStateProperties.HALF, Half.TOP);
        TestUtils.testBlockOccludesLightNorth(helper, state);
    }

    @GameTest(template = "box_side", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_TRAPDOOR)
    public static void test_TrapdoorSouthBottomOpen(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_TRAP_DOOR.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.OPEN, true)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH);
        TestUtils.testBlockOccludesLightNorth(helper, state);
    }

    @GameTest(template = "box_side", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_TRAPDOOR)
    public static void test_Trapdoor_SouthTopOpen(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_TRAP_DOOR.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.OPEN, true)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)
                .setValue(BlockStateProperties.HALF, Half.BOTTOM);
        TestUtils.testBlockOccludesLightNorth(helper, state);
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_DOUBLE_SLAB)
    public static void test_DoubleSlab(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_DOUBLE_SLAB.value().defaultBlockState();
        TestUtils.testDoubleBlockOccludesLightBelow(helper, state, List.of(Direction.UP, Direction.DOWN));
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_DOUBLE_PANEL)
    public static void test_DoublePanel_NorthSouth(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_DOUBLE_PANEL.value().defaultBlockState();
        TestUtils.testDoubleBlockOccludesLightBelow(helper, state, List.of(Direction.NORTH, Direction.SOUTH));
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_DOUBLE_PANEL)
    public static void test_DoublePanel_EastWest(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_DOUBLE_PANEL.value()
                .defaultBlockState()
                .setValue(FramedProperties.FACING_HOR, Direction.EAST);
        TestUtils.testDoubleBlockOccludesLightBelow(helper, state, List.of(Direction.EAST, Direction.WEST));
    }

    @GameTest(template = "box_side", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_IRON_DOOR)
    public static void test_IronDoor_Closed(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_IRON_DOOR.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH);
        TestUtils.testBlockOccludesLightNorth(helper, state);
    }

    @GameTest(template = "box_side", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_IRON_DOOR)
    public static void test_IronDoor_Open(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_IRON_DOOR.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)
                .setValue(BlockStateProperties.OPEN, true);
        TestUtils.testBlockOccludesLightNorth(helper, state);
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_IRON_TRAPDOOR)
    public static void testTop_IronTrapdoor_BottomClosed(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR.value().defaultBlockState();
        TestUtils.testBlockOccludesLightBelow(helper, state);
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_IRON_TRAPDOOR)
    public static void testTop_IronTrapdoor_TopClosed(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.HALF, Half.TOP);
        TestUtils.testBlockOccludesLightBelow(helper, state);
    }

    @GameTest(template = "box_bottom", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_IRON_TRAPDOOR)
    public static void testBottom_IronTrapdoor_BottomClosed(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR.value().defaultBlockState();
        TestUtils.testBlockOccludesLightAbove(helper, state);
    }

    @GameTest(template = "box_bottom", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_IRON_TRAPDOOR)
    public static void testBottom_IronTrapdoor_TopClosed(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.HALF, Half.TOP);
        TestUtils.testBlockOccludesLightAbove(helper, state);
    }

    @GameTest(template = "box_side", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_IRON_TRAPDOOR)
    public static void test_IronTrapdoor_NorthBottomOpen(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.OPEN, true);
        TestUtils.testBlockOccludesLightNorth(helper, state);
    }

    @GameTest(template = "box_side", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_IRON_TRAPDOOR)
    public static void test_IronTrapdoor_NorthTopOpen(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.OPEN, true)
                .setValue(BlockStateProperties.HALF, Half.TOP);
        TestUtils.testBlockOccludesLightNorth(helper, state);
    }

    @GameTest(template = "box_side", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_IRON_TRAPDOOR)
    public static void test_IronTrapdoorSouthBottomOpen(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.OPEN, true)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH);
        TestUtils.testBlockOccludesLightNorth(helper, state);
    }

    @GameTest(template = "box_side", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_IRON_TRAPDOOR)
    public static void test_IronTrapdoor_SouthTopOpen(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR.value()
                .defaultBlockState()
                .setValue(BlockStateProperties.OPEN, true)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)
                .setValue(BlockStateProperties.HALF, Half.BOTTOM);
        TestUtils.testBlockOccludesLightNorth(helper, state);
    }
    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_BOOKSHELF)
    public static void test_Bookshelf(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_BOOKSHELF.value().defaultBlockState();
        TestUtils.testBlockOccludesLightBelow(helper, state);
    }

    @GameTest(template = "box_top", batch = "occlusion")
    @TestedType(type = BlockType.FRAMED_CHISELED_BOOKSHELF)
    public static void test_ChiseledBookshelf(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_CHISELED_BOOKSHELF.value().defaultBlockState();
        TestUtils.testBlockOccludesLightBelow(helper, state);
    }



    private static boolean firstBatch = true;

    @BeforeBatch(batch = "occlusion")
    public static void validateAllBlocksHaveTest(ServerLevel level)
    {
        if (!firstBatch) { return; }
        firstBatch = false;

        Set<BlockType> types = Arrays.stream(OcclusionTests.class.getMethods())
                .map(mth -> mth.getAnnotation(TestedType.class))
                .filter(Objects::nonNull)
                .map(TestedType::type)
                .collect(Collectors.toSet());

        List<BlockType> missing = Arrays.stream(BlockType.values())
                .filter(IBlockType::canOccludeWithSolidCamo)
                .filter(type -> !types.contains(type))
                .toList();

        if (!missing.isEmpty())
        {
            FramedBlocks.LOGGER.warn("Found blocks missing occlusion test {}", missing);
        }
    }

    private OcclusionTests() { }
}
