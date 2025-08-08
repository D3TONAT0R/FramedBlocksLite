package xfacthd.framedblockslite.client.data.ghost;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import xfacthd.framedblockslite.api.block.blockentity.IFramedDoubleBlockEntity;
import xfacthd.framedblockslite.api.ghost.GhostRenderBehaviour;
import xfacthd.framedblockslite.api.model.data.FramedBlockData;
import xfacthd.framedblockslite.api.util.CamoList;

public class DoubleBlockGhostRenderBehaviour implements GhostRenderBehaviour
{
    @Override
    public ModelData buildModelData(ItemStack stack, ItemStack proxiedStack, BlockPlaceContext ctx, BlockState renderState, int renderPass, CamoList camo)
    {
        return buildModelData(camo);
    }

    public static ModelData buildModelData(CamoList camo)
    {
        return ModelData.builder()
                .with(IFramedDoubleBlockEntity.DATA_ONE, ModelData.builder()
                        .with(FramedBlockData.PROPERTY, new FramedBlockData(camo.getCamo(0).getContent(), false))
                        .build()
                )
                .with(IFramedDoubleBlockEntity.DATA_TWO, ModelData.builder()
                        .with(FramedBlockData.PROPERTY, new FramedBlockData(camo.getCamo(1).getContent(), true))
                        .build()
                )
                .build();
    }
}
