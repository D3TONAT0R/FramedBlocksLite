package xfacthd.framedblocks.tests;

import com.google.common.base.Preconditions;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.block.IBlockType;
import xfacthd.framedblocks.api.util.FramedConstants;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.test.TestUtils;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.property.CompoundDirection;
import xfacthd.framedblocks.common.data.property.DirectionAxis;
import xfacthd.framedblocks.common.data.property.HorizontalRotation;

import java.util.Set;

//@GameTestHolder(FramedConstants.MOD_ID)
public final class BeaconTintTests
{
    private static final String BATCH_NAME = "beacon_tint";
    private static final String STRUCTURE_NAME = FramedConstants.MOD_ID + ":floor_slab_1x1";

    // All blocks that are completely unable to apply a tint to the beacon beam
    private static final Set<BlockType> NON_TINTING = Set.of(
            BlockType.FRAMED_SLAB_EDGE,
            BlockType.FRAMED_SLAB_CORNER,
            BlockType.FRAMED_PANEL,
            BlockType.FRAMED_CORNER_PILLAR,
            BlockType.FRAMED_FENCE,
            BlockType.FRAMED_FENCE_GATE,
            BlockType.FRAMED_DOOR,
            BlockType.FRAMED_IRON_DOOR,
            BlockType.FRAMED_BUTTON,
            BlockType.FRAMED_STONE_BUTTON,
            BlockType.FRAMED_VERTICAL_STAIRS,
            BlockType.FRAMED_HALF_STAIRS,
            BlockType.FRAMED_GATE,
            BlockType.FRAMED_IRON_GATE
    );

    /*@GameTestGenerator
    public static Collection<TestFunction> generateBeaconTintTests()
    {
        return Arrays.stream(BlockType.values())
                .filter(type -> !NON_TINTING.contains(type))
                .map(type -> Utils.rl(type.getName()))
                .map(BuiltInRegistries.BLOCK::getValue)
                .filter(b -> b != Blocks.AIR)
                .map(BeaconTintTests::getTestState)
                .map(state -> new TestFunction(
                        BATCH_NAME,
                        getTestName(state),
                        STRUCTURE_NAME,
                        100,
                        0,
                        true,
                        helper -> TestUtils.testBeaconBeamTinting(helper, state)
                ))
                .toList();
    }*/

    private static BlockState getTestState(Block block)
    {
        Preconditions.checkArgument(block instanceof IFramedBlock);

        IBlockType type = ((IFramedBlock) block).getBlockType();
        if (type instanceof BlockType blockType)
        {
            BlockState state = block.defaultBlockState();
            return switch (blockType)
            {
                case FRAMED_PILLAR -> state.setValue(BlockStateProperties.AXIS, Direction.Axis.Y);
                case FRAMED_HALF_PILLAR -> state.setValue(BlockStateProperties.FACING, Direction.DOWN);
                case FRAMED_LARGE_BUTTON, FRAMED_LARGE_STONE_BUTTON -> state.setValue(BlockStateProperties.ATTACH_FACE, AttachFace.FLOOR);
                default -> state;
            };
        }
        return block.defaultBlockState();
    }

    private static String getTestName(BlockState state)
    {
        ResourceLocation regName = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        return String.format("beacontinttests.test_%s", regName.getPath());
    }



    private BeaconTintTests() { }
}
