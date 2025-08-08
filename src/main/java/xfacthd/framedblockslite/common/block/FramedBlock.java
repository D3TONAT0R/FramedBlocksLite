package xfacthd.framedblockslite.common.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import xfacthd.framedblockslite.api.block.AbstractFramedBlock;
import xfacthd.framedblockslite.api.block.IFramedBlock;
import xfacthd.framedblockslite.common.data.*;
import xfacthd.framedblockslite.common.data.BlockType;

import java.util.function.UnaryOperator;

public abstract class FramedBlock extends AbstractFramedBlock
{
    protected FramedBlock(BlockType blockType)
    {
        super(blockType, IFramedBlock.createProperties(blockType));
    }

    protected FramedBlock(BlockType blockType, UnaryOperator<Properties> propertyModifier)
    {
        super(blockType, propertyModifier);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type)
    {
        if (getBlockType() != BlockType.FRAMED_CUBE)
        {
            return false;
        }
        return super.isPathfindable(state, type);
    }

    @Override
    public BlockType getBlockType()
    {
        return (BlockType) super.getBlockType();
    }
}
