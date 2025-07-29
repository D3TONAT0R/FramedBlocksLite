package xfacthd.framedblocks.tests;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblocks.api.block.blockentity.FramedBlockEntity;
import xfacthd.framedblocks.api.camo.block.BlockCamoContent;
import xfacthd.framedblocks.api.util.FramedConstants;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.test.TestUtils;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.data.property.LatchType;
import xfacthd.framedblocks.common.data.PropertyHolder;

import java.util.List;

//@GameTestHolder(FramedConstants.MOD_ID)
public final class InteractionTests
{
    private static final BlockPos POS_ABOVE_FLOOR = new BlockPos(0, 2, 0);

    //@GameTest(template = "floor_1x1", batch = "interaction")

    //@GameTest(template = "floor_1x1", batch = "interaction")
    public static void testButtonPress(GameTestHelper helper)
    {
        TestUtils.chainTasks(helper, List.of(
                () -> helper.setBlock(POS_ABOVE_FLOOR, FBContent.BLOCK_FRAMED_BUTTON.value()),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, ButtonBlock.POWERED, false),
                () -> helper.useBlock(POS_ABOVE_FLOOR),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, ButtonBlock.POWERED, true)
        ));

        helper.runAfterDelay(33, () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, ButtonBlock.POWERED, false));
        helper.runAfterDelay(34, helper::succeed);
    }

    //@GameTest(template = "floor_1x1", batch = "interaction")
    public static void testStoneButtonPress(GameTestHelper helper)
    {
        TestUtils.chainTasks(helper, List.of(
                () -> helper.setBlock(POS_ABOVE_FLOOR, FBContent.BLOCK_FRAMED_STONE_BUTTON.value()),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, ButtonBlock.POWERED, false),
                () -> helper.useBlock(POS_ABOVE_FLOOR),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, ButtonBlock.POWERED, true)
        ));

        helper.runAfterDelay(23, () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, ButtonBlock.POWERED, false));
        helper.runAfterDelay(24, helper::succeed);
    }

    //@GameTest(template = "floor_1x1", batch = "interaction")
    public static void testLeverFlip(GameTestHelper helper)
    {
        TestUtils.chainTasks(helper, List.of(
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, LeverBlock.POWERED, false),
                () -> helper.useBlock(POS_ABOVE_FLOOR),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, LeverBlock.POWERED, true),
                () -> helper.useBlock(POS_ABOVE_FLOOR),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, LeverBlock.POWERED, false),
                helper::succeed
        ));
    }

    //@GameTest(template = "floor_1x1", batch = "interaction")
    public static void testDoorInteract(GameTestHelper helper)
    {
        TestUtils.chainTasks(helper, List.of(
                () -> helper.setBlock(POS_ABOVE_FLOOR, FBContent.BLOCK_FRAMED_DOOR.value()),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, DoorBlock.OPEN, false),
                () -> helper.useBlock(POS_ABOVE_FLOOR),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, DoorBlock.OPEN, true),
                () -> helper.useBlock(POS_ABOVE_FLOOR),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, DoorBlock.OPEN, false),
                helper::succeed
        ));
    }

    //@GameTest(template = "floor_1x1", batch = "interaction")
    public static void testTrapDoorInteract(GameTestHelper helper)
    {
        TestUtils.chainTasks(helper, List.of(
                () -> helper.setBlock(POS_ABOVE_FLOOR, FBContent.BLOCK_FRAMED_TRAP_DOOR.value()),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, TrapDoorBlock.OPEN, false),
                () -> helper.useBlock(POS_ABOVE_FLOOR),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, TrapDoorBlock.OPEN, true),
                () -> helper.useBlock(POS_ABOVE_FLOOR),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, TrapDoorBlock.OPEN, false),
                helper::succeed
        ));
    }

    //@GameTest(template = "floor_1x1", batch = "interaction")
    public static void testFenceGateInteract(GameTestHelper helper)
    {
        TestUtils.chainTasks(helper, List.of(
                () -> helper.setBlock(POS_ABOVE_FLOOR, FBContent.BLOCK_FRAMED_FENCE_GATE.value()),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, FenceGateBlock.OPEN, false),
                () -> helper.useBlock(POS_ABOVE_FLOOR),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, FenceGateBlock.OPEN, true),
                () -> helper.useBlock(POS_ABOVE_FLOOR),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, FenceGateBlock.OPEN, false),
                helper::succeed
        ));
    }

    //@GameTest(template = "floor_slab_1x1", batch = "interaction")
    public static void testPressurePlateInteract(GameTestHelper helper)
    {
        int delay = TestUtils.chainTasks(helper, List.of(
                () -> helper.setBlock(POS_ABOVE_FLOOR, FBContent.BLOCK_FRAMED_PRESSURE_PLATE.value()),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, PressurePlateBlock.POWERED, false),
                () -> TestUtils.spawnItemCentered(helper, Items.IRON_INGOT, POS_ABOVE_FLOOR),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, PressurePlateBlock.POWERED, true),
                helper::killAllEntities
        ));

        helper.runAfterDelay(delay + 20, () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, PressurePlateBlock.POWERED, false));
        helper.runAfterDelay(delay + 21, helper::succeed);
    }

    //@GameTest(template = "floor_1x1", batch = "interaction")
    public static void testBuildDoubleSlab(GameTestHelper helper)
    {
        TestUtils.chainTasks(helper, List.of(
                () -> helper.setBlock(POS_ABOVE_FLOOR, FBContent.BLOCK_FRAMED_SLAB.value()),
                () -> TestUtils.clickWithItem(helper, POS_ABOVE_FLOOR, FBContent.BLOCK_FRAMED_SLAB.value()),
                () -> helper.succeedWhenBlockPresent(FBContent.BLOCK_FRAMED_DOUBLE_SLAB.value(), POS_ABOVE_FLOOR)
        ));
    }

    //@GameTest(template = "floor_1x1", batch = "interaction")
    public static void testBuildDoublePanelNorthSouth(GameTestHelper helper)
    {
        TestUtils.chainTasks(helper, List.of(
                () -> helper.setBlock(POS_ABOVE_FLOOR, FBContent.BLOCK_FRAMED_PANEL.value()),
                () -> TestUtils.clickWithItem(helper, POS_ABOVE_FLOOR, FBContent.BLOCK_FRAMED_PANEL.value(), Direction.SOUTH),
                () -> helper.assertBlockPresent(FBContent.BLOCK_FRAMED_DOUBLE_PANEL.value(), POS_ABOVE_FLOOR),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, FramedProperties.FACING_HOR, Direction.NORTH),
                helper::succeed
        ));
    }

    //@GameTest(template = "floor_1x1", batch = "interaction")
    public static void testBuildDoublePanelEastWest(GameTestHelper helper)
    {
        BlockState state = FBContent.BLOCK_FRAMED_PANEL.value()
                .defaultBlockState()
                .setValue(FramedProperties.FACING_HOR, Direction.EAST);

        TestUtils.chainTasks(helper, List.of(
                () -> helper.setBlock(POS_ABOVE_FLOOR, state),
                () -> TestUtils.clickWithItem(helper, POS_ABOVE_FLOOR, FBContent.BLOCK_FRAMED_PANEL.value(), Direction.WEST),
                () -> helper.assertBlockPresent(FBContent.BLOCK_FRAMED_DOUBLE_PANEL.value(), POS_ABOVE_FLOOR),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, FramedProperties.FACING_HOR, Direction.EAST),
                helper::succeed
        ));
    }

    //@GameTest(template = "floor_1x1", batch = "interaction")
    public static void testRotateCamo(GameTestHelper helper)
    {
        TestUtils.chainTasks(helper, List.of(
                () -> helper.setBlock(POS_ABOVE_FLOOR, FBContent.BLOCK_FRAMED_CUBE.value()),
                () -> TestUtils.applyCamo(helper, POS_ABOVE_FLOOR, Blocks.OAK_LOG),
                () ->
                {
                    FramedBlockEntity be = helper.getBlockEntity(POS_ABOVE_FLOOR, FramedBlockEntity.class);
                    TestUtils.assertTrue(
                            helper,
                            POS_ABOVE_FLOOR,
                            be.getCamo().getContent().equals(new BlockCamoContent(Blocks.OAK_LOG.defaultBlockState())),
                            () -> String.format("Expected oak log default state as camo, got %s", be.getCamo().getContent())
                    );
                },
                () -> TestUtils.clickWithItem(helper, POS_ABOVE_FLOOR, FBContent.ITEM_FRAMED_SCREWDRIVER.value()),
                () ->
                {
                    FramedBlockEntity be = helper.getBlockEntity(POS_ABOVE_FLOOR, FramedBlockEntity.class);
                    TestUtils.assertTrue(
                            helper,
                            POS_ABOVE_FLOOR,
                            be.getCamo().getContent().equals(new BlockCamoContent(Blocks.OAK_LOG.defaultBlockState().cycle(RotatedPillarBlock.AXIS))),
                            () -> String.format("Expected oak log rotated once as camo, got %s", be.getCamo().getContent())
                    );
                },
                helper::succeed
        ));
    }

    private static void testSwitchOffset(GameTestHelper helper, Holder<Block> block)
    {
        TestUtils.chainTasks(helper, List.of(
                () -> helper.setBlock(POS_ABOVE_FLOOR, block.value()),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, FramedProperties.OFFSET, false),
                () -> TestUtils.attackWithItem(helper, POS_ABOVE_FLOOR, FBContent.ITEM_FRAMED_HAMMER.value()),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, FramedProperties.OFFSET, true),
                () -> TestUtils.attackWithItem(helper, POS_ABOVE_FLOOR, FBContent.ITEM_FRAMED_HAMMER.value()),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, FramedProperties.OFFSET, false),
                helper::succeed
        ));
    }

    //@GameTest(template = "floor_1x1", batch = "interaction")
    public static void testIronDoorInteract(GameTestHelper helper)
    {
        TestUtils.chainTasks(helper, List.of(
                () -> helper.setBlock(POS_ABOVE_FLOOR, FBContent.BLOCK_FRAMED_IRON_DOOR.value()),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, DoorBlock.OPEN, false),
                () -> helper.useBlock(POS_ABOVE_FLOOR),
                //No, this is not a typo, the door must not budge ;)
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, DoorBlock.OPEN, false),
                helper::succeed
        ));
    }

    //@GameTest(template = "floor_1x1", batch = "interaction")
    public static void testIronTrapDoorInteract(GameTestHelper helper)
    {
        TestUtils.chainTasks(helper, List.of(
                () -> helper.setBlock(POS_ABOVE_FLOOR, FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR.value()),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, TrapDoorBlock.OPEN, false),
                () -> helper.useBlock(POS_ABOVE_FLOOR),
                //No, this is not a typo, the trapdoor must not budge ;)
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, TrapDoorBlock.OPEN, false),
                helper::succeed
        ));
    }

    //@GameTest(template = "floor_1x1", batch = "interaction")
    public static void testLargeButtonPress(GameTestHelper helper)
    {
        TestUtils.chainTasks(helper, List.of(
                () -> helper.setBlock(POS_ABOVE_FLOOR, FBContent.BLOCK_FRAMED_LARGE_BUTTON.value()),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, ButtonBlock.POWERED, false),
                () -> helper.useBlock(POS_ABOVE_FLOOR),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, ButtonBlock.POWERED, true)
        ));

        helper.runAfterDelay(33, () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, ButtonBlock.POWERED, false));
        helper.runAfterDelay(34, helper::succeed);
    }

    //@GameTest(template = "floor_1x1", batch = "interaction")
    public static void testLargeStoneButtonPress(GameTestHelper helper)
    {
        TestUtils.chainTasks(helper, List.of(
                () -> helper.setBlock(POS_ABOVE_FLOOR, FBContent.BLOCK_FRAMED_LARGE_STONE_BUTTON.value()),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, ButtonBlock.POWERED, false),
                () -> helper.useBlock(POS_ABOVE_FLOOR),
                () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, ButtonBlock.POWERED, true)
        ));

        helper.runAfterDelay(23, () -> helper.assertBlockProperty(POS_ABOVE_FLOOR, ButtonBlock.POWERED, false));
        helper.runAfterDelay(24, helper::succeed);
    }



    private InteractionTests() { }
}
