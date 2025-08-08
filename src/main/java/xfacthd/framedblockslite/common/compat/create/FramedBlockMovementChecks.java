package xfacthd.framedblockslite.common.compat.create;

import com.simibubi.create.api.contraption.BlockMovementChecks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblockslite.api.block.IFramedBlock;
import xfacthd.framedblockslite.common.data.BlockType;

public final class FramedBlockMovementChecks implements
        BlockMovementChecks.MovementNecessaryCheck,
        BlockMovementChecks.MovementAllowedCheck,
        BlockMovementChecks.BrittleCheck,
        BlockMovementChecks.AttachedCheck,
        BlockMovementChecks.NotSupportiveCheck
{
    @Override
    public BlockMovementChecks.CheckResult isBlockAttachedTowards(BlockState state, Level level, BlockPos pos, Direction side)
    {
        if (state.getBlock() instanceof IFramedBlock block && block.getBlockType() instanceof BlockType type)
        {
            return BlockMovementChecks.CheckResult.PASS;
        }
        return BlockMovementChecks.CheckResult.PASS;
    }

    @Override
    public BlockMovementChecks.CheckResult isBrittle(BlockState state)
    {
        Block block = state.getBlock();
        return BlockMovementChecks.CheckResult.PASS;
    }

    @Override
    public BlockMovementChecks.CheckResult isMovementAllowed(BlockState state, Level level, BlockPos pos)
    {
        return BlockMovementChecks.CheckResult.PASS;
    }

    @Override
    public BlockMovementChecks.CheckResult isMovementNecessary(BlockState state, Level level, BlockPos pos)
    {
        return BlockMovementChecks.CheckResult.PASS;
    }

    @Override
    public BlockMovementChecks.CheckResult isNotSupportive(BlockState state, Direction side)
    {
        return BlockMovementChecks.CheckResult.PASS;
    }



    private static BlockMovementChecks.CheckResult result(boolean value)
    {
        return BlockMovementChecks.CheckResult.of(value);
    }
}
