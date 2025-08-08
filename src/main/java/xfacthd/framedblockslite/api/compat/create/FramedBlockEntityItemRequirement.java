package xfacthd.framedblockslite.api.compat.create;

import com.simibubi.create.api.schematic.requirement.SchematicRequirementRegistries;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblockslite.api.block.blockentity.FramedBlockEntity;
import xfacthd.framedblockslite.api.block.blockentity.IFramedDoubleBlockEntity;
import xfacthd.framedblockslite.api.camo.CamoContainer;
import xfacthd.framedblockslite.api.camo.CamoContainerHelper;

import java.util.ArrayList;
import java.util.List;

public class FramedBlockEntityItemRequirement implements SchematicRequirementRegistries.BlockEntityRequirement
{
    public static final FramedBlockEntityItemRequirement INSTANCE = new FramedBlockEntityItemRequirement();

    protected FramedBlockEntityItemRequirement() { }

    @Override
    public final ItemRequirement getRequiredItems(BlockEntity blockEntity, BlockState state)
    {
        if (blockEntity instanceof FramedBlockEntity fbe)
        {
            List<ItemRequirement.StackRequirement> requirements = new ArrayList<>();

            CamoContainer<?, ?> camoOne = fbe.getCamo();
            if (!camoOne.isEmpty() && camoOne.canTriviallyConvertToItemStack())
            {
                requirements.add(consume(CamoContainerHelper.dropCamo(camoOne)));
            }
            if (fbe instanceof IFramedDoubleBlockEntity fdbe)
            {
                CamoContainer<?, ?> camoTwo = fdbe.getCamoTwo();
                if (!camoTwo.isEmpty() && camoTwo.canTriviallyConvertToItemStack())
                {
                    requirements.add(consume(CamoContainerHelper.dropCamo(camoTwo)));
                }
            }

            if (fbe.isGlowing())
            {
                requirements.add(consume(Items.GLOWSTONE_DUST));
            }

            collectAdditionalRequirements(fbe, requirements);

            return new ItemRequirement(requirements);
        }
        return ItemRequirement.NONE;
    }

    protected void collectAdditionalRequirements(FramedBlockEntity blockEntity, List<ItemRequirement.StackRequirement> requirements) { }



    protected static ItemRequirement.StackRequirement consume(ItemLike item)
    {
        return consume(new ItemStack(item));
    }

    protected static ItemRequirement.StackRequirement consume(ItemStack stack)
    {
        return new ItemRequirement.StackRequirement(stack, ItemRequirement.ItemUseType.CONSUME);
    }

    protected static ItemRequirement.StackRequirement consumeStrict(ItemStack stack)
    {
        return new ItemRequirement.StrictNbtStackRequirement(stack, ItemRequirement.ItemUseType.CONSUME);
    }
}
