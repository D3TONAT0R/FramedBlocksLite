package xfacthd.framedblockslite.common.block.cube;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import xfacthd.framedblockslite.api.block.FramedProperties;
import xfacthd.framedblockslite.common.block.FramedBlock;
import xfacthd.framedblockslite.common.data.BlockType;

public class FramedBookshelfBlock extends FramedBlock
{
    public FramedBookshelfBlock()
    {
        super(BlockType.FRAMED_BOOKSHELF);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(FramedProperties.SOLID);
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
