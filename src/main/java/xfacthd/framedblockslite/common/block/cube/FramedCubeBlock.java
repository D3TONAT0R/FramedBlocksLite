package xfacthd.framedblockslite.common.block.cube;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import xfacthd.framedblockslite.api.block.FramedProperties;
import xfacthd.framedblockslite.common.block.FramedBlock;
import xfacthd.framedblockslite.common.data.BlockType;
import xfacthd.framedblockslite.common.data.PropertyHolder;

public class FramedCubeBlock extends FramedBlock
{
    public FramedCubeBlock()
    {
        super(BlockType.FRAMED_CUBE);
        registerDefaultState(defaultBlockState()
                .setValue(PropertyHolder.ALT, false)
                .setValue(PropertyHolder.SOLID_BG, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(FramedProperties.SOLID, PropertyHolder.ALT, PropertyHolder.SOLID_BG);
    }

    @Override
    public BlockState getItemModelSource()
    {
        return defaultBlockState();
    }

    @Override
    public BlockState getJadeRenderState(BlockState state)
    {
        return state;
    }
}
