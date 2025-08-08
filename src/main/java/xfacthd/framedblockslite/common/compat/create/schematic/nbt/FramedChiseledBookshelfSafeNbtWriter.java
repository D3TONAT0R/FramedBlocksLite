package xfacthd.framedblockslite.common.compat.create.schematic.nbt;

import net.minecraft.nbt.CompoundTag;
import xfacthd.framedblockslite.api.block.blockentity.FramedBlockEntity;
import xfacthd.framedblockslite.api.compat.create.FramedBlockSafeNbtWriter;
import xfacthd.framedblockslite.common.blockentity.special.FramedChiseledBookshelfBlockEntity;

public final class FramedChiseledBookshelfSafeNbtWriter extends FramedBlockSafeNbtWriter
{
    @Override
    protected void cleanupTag(FramedBlockEntity fbe, CompoundTag tag)
    {
        tag.remove(FramedChiseledBookshelfBlockEntity.INVENTORY_NBT_KEY);
        tag.putInt(FramedChiseledBookshelfBlockEntity.LAST_SLOT_NBT_KEY, -1);
    }
}
