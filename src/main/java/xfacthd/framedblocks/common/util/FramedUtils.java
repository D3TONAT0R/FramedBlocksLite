package xfacthd.framedblocks.common.util;

import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.neoforged.neoforge.common.util.Lazy;
import xfacthd.framedblocks.common.FBContent;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public final class FramedUtils
{
    public static void enqueueImmediateTask(LevelAccessor level, Runnable task, boolean allowClient)
    {
        if (level.isClientSide() && allowClient)
        {
            task.run();
        }
        else
        {
            enqueueTask(level, task, 0);
        }
    }

    public static void enqueueTask(LevelAccessor level, Runnable task, int delay)
    {
        if (!(level instanceof ServerLevel slevel))
        {
            throw new IllegalArgumentException("Utils#enqueueTask() called with a non-ServerWorld");
        }

        MinecraftServer server = slevel.getServer();
        server.schedule(new TickTask(server.getTickCount() + delay, task));
    }

    public static void addPlayerInvSlots(Consumer<Slot> slotConsumer, Inventory playerInv, int x, int y)
    {
        for (int row = 0; row < 3; ++row)
        {
            for (int col = 0; col < 9; ++col)
            {
                slotConsumer.accept(new Slot(playerInv, col + row * 9 + 9, x + col * 18, y));
            }
            y += 18;
        }

        for (int col = 0; col < 9; ++col)
        {
            slotConsumer.accept(new Slot(playerInv, col, x + col * 18, y + 4));
        }
    }



    private FramedUtils() { }
}
