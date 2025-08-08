package xfacthd.framedblockslite.common.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import xfacthd.framedblockslite.mixin.AccessorIngredient;
import xfacthd.framedblockslite.mixin.AccessorStateDefinitionBuilder;

import java.util.*;
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
        server.tell(new TickTask(server.getTickCount() + delay, task));
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

    public static boolean hasProperty(StateDefinition.Builder<Block, BlockState> builder, Property<?> property)
    {
        return ((AccessorStateDefinitionBuilder) builder).framedblocks$getProperties().containsKey(property.getName());
    }

    public static void removeProperty(StateDefinition.Builder<Block, BlockState> builder, Property<?> property)
    {
        Map<String, Property<?>> properties = ((AccessorStateDefinitionBuilder) builder).framedblocks$getProperties();
        properties.remove(property.getName());
    }

    // The cast is considered invalid due to Ingredient being final
    @SuppressWarnings({ "UnreachableCode", "DataFlowIssue" })
    public static Ingredient.Value getSingleIngredientValue(Ingredient ing)
    {
        Ingredient.Value[] values = ((AccessorIngredient)(Object) ing).framedblocks$getValues();
        return values.length == 1 ? values[0] : null;
    }



    private FramedUtils() { }
}
