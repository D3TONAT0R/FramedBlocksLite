package xfacthd.framedblockslite.common.block.pillar;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import xfacthd.framedblockslite.api.block.PlacementStateBuilder;
import xfacthd.framedblockslite.api.util.Utils;
import xfacthd.framedblockslite.common.block.FramedBlock;
import xfacthd.framedblockslite.common.block.IPillarLikeBlock;
import xfacthd.framedblockslite.common.data.BlockType;
import xfacthd.framedblockslite.common.data.property.PillarConnection;

public class FramedPillarBlock extends FramedBlock implements IPillarLikeBlock
{
    private final PillarConnection pillarConnection;

    public FramedPillarBlock(BlockType blockType)
    {
        super(blockType);
        this.pillarConnection = switch (blockType)
        {
            case FRAMED_PILLAR -> PillarConnection.PILLAR;
            default -> throw new IllegalArgumentException("Unexpected BlockType in FramedPillarBlock: " + blockType);
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.AXIS, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx)
    {
        return PlacementStateBuilder.of(this, ctx)
                .withCustom((state, modCtx) ->
                        state.setValue(BlockStateProperties.AXIS, modCtx.getClickedFace().getAxis())
                )
                .withWater()
                .build();
    }

    @Override
    public BlockState rotate(BlockState state, Direction side, Rotation rot)
    {
        if (rot != Rotation.NONE)
        {
            return state.cycle(BlockStateProperties.AXIS);
        }
        return state;
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rot)
    {
        Direction.Axis axis = state.getValue(BlockStateProperties.AXIS);
        if (axis != Direction.Axis.Y && rot != Rotation.NONE && rot != Rotation.CLOCKWISE_180)
        {
            axis = Utils.nextAxisNotEqualTo(axis, Direction.Axis.Y);
            return state.setValue(BlockStateProperties.AXIS, axis);
        }
        return state;
    }

    @Override
    public BlockState getItemModelSource()
    {
        return defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Y);
    }

    @Override
    public BlockState getJadeRenderState(BlockState state)
    {
        return defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Y);
    }

    @Override
    public PillarConnection getPillarConnection(BlockState state, Direction side)
    {
        return side.getAxis() == state.getValue(BlockStateProperties.AXIS) ? pillarConnection : PillarConnection.NONE;
    }
}
