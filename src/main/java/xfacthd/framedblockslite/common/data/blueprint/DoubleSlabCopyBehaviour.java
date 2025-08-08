package xfacthd.framedblockslite.common.data.blueprint;

import net.minecraft.world.item.ItemStack;
import xfacthd.framedblockslite.api.blueprint.BlueprintCopyBehaviour;
import xfacthd.framedblockslite.api.blueprint.BlueprintData;
import xfacthd.framedblockslite.common.FBContent;

public final class DoubleSlabCopyBehaviour implements BlueprintCopyBehaviour
{
    @Override
    public ItemStack getBlockItem(BlueprintData blueprintData)
    {
        return new ItemStack(FBContent.BLOCK_FRAMED_SLAB.value(), 2);
    }
}
