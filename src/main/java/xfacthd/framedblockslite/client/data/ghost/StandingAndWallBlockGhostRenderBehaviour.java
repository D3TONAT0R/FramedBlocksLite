package xfacthd.framedblockslite.client.data.ghost;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import xfacthd.framedblockslite.api.ghost.GhostRenderBehaviour;
import xfacthd.framedblockslite.mixin.InvokerBlockItem;

public sealed class StandingAndWallBlockGhostRenderBehaviour implements GhostRenderBehaviour
        permits StandingAndWallDoubleBlockGhostRenderBehaviour
{
    @Override
    @Nullable
    public BlockState getRenderState(
            ItemStack stack,
            @Nullable ItemStack proxiedStack,
            BlockHitResult hit,
            BlockPlaceContext ctx,
            BlockState hitState,
            int renderPass
    )
    {
        return ((InvokerBlockItem) stack.getItem()).framedblocks$callGetPlacementState(ctx);
    }
}
