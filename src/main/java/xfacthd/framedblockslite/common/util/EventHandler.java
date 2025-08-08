package xfacthd.framedblockslite.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import xfacthd.framedblockslite.api.block.IFramedBlock;
import xfacthd.framedblockslite.client.util.ClientAccess;

public final class EventHandler
{
    public static void onBlockLeftClick(final PlayerInteractEvent.LeftClickBlock event)
    {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        if (state.getBlock() instanceof IFramedBlock block)
        {
            if (block.handleBlockLeftClick(state, level, pos, event.getEntity()))
            {
                event.setCanceled(true);

                if (FMLEnvironment.dist.isClient() && level.isClientSide())
                {
                    ClientAccess.resetDestroyDelay();
                }
            }
        }
    }

    public static void onServerShutdown(@SuppressWarnings("unused") final ServerStoppedEvent event)
    {
    }



    private EventHandler() { }
}