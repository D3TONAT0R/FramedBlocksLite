package xfacthd.framedblocks.client.data;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.ghost.DoubleBlockGhostRenderBehaviour;
import xfacthd.framedblocks.api.ghost.RegisterGhostRenderBehavioursEvent;
import xfacthd.framedblocks.client.data.ghost.*;
import xfacthd.framedblocks.common.FBContent;

import java.util.List;

public final class GhostRenderBehaviours
{
    public static void onRegisterGhostRenderBehaviours(RegisterGhostRenderBehavioursEvent event)
    {
        //noinspection SuspiciousToArrayCall
        event.registerBlocks(DoubleBlockGhostRenderBehaviour.INSTANCE, FBContent.getRegisteredBlocks()
                .stream()
                .map(Holder::value)
                .filter(IFramedBlock.class::isInstance)
                .filter(b -> ((IFramedBlock) b).getBlockType().isDoubleBlock())
                .toArray(Block[]::new)
        );
        event.registerBlocks(new DoorGhostRenderBehaviour(), List.of(
                FBContent.BLOCK_FRAMED_DOOR,
                FBContent.BLOCK_FRAMED_IRON_DOOR
        ));
        event.registerBlocks(new SlabGhostRenderBehaviour(), FBContent.BLOCK_FRAMED_SLAB.value());
        event.registerItem(new BlueprintGhostRenderBehaviour(), FBContent.ITEM_FRAMED_BLUEPRINT);
        event.registerBlock(new FlowerPotGhostRenderBehaviour(), FBContent.BLOCK_FRAMED_FLOWER_POT);
        event.registerBlocks(AdjustableDoubleBlockGhostRenderBehaviour.standard(), List.of(
                FBContent.BLOCK_FRAMED_ADJ_DOUBLE_SLAB,
                FBContent.BLOCK_FRAMED_ADJ_DOUBLE_PANEL
        ));
        event.registerBlocks(AdjustableDoubleBlockGhostRenderBehaviour.copycat(), List.of(
                FBContent.BLOCK_FRAMED_ADJ_DOUBLE_COPYCAT_SLAB,
                FBContent.BLOCK_FRAMED_ADJ_DOUBLE_COPYCAT_PANEL
        ));
        event.registerBlock(new LayeredCubeGhostRenderBehaviour(), FBContent.BLOCK_FRAMED_LAYERED_CUBE);
    }

    private GhostRenderBehaviours() { }
}
