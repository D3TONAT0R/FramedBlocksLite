package xfacthd.framedblockslite.common.data.blueprint;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import xfacthd.framedblockslite.api.blueprint.*;
import xfacthd.framedblockslite.api.blueprint.AuxBlueprintData;
import xfacthd.framedblockslite.api.blueprint.BlueprintCopyBehaviour;
import xfacthd.framedblockslite.api.blueprint.BlueprintData;

public class DummyDataHandlingCopyBehaviour<T extends AuxBlueprintData<T>> implements BlueprintCopyBehaviour
{
    private final DataComponentType<T> componentType;
    private final T auxDefaultValue;

    public DummyDataHandlingCopyBehaviour(DataComponentType<T> componentType, T auxDefaultValue)
    {
        this.componentType = componentType;
        this.auxDefaultValue = auxDefaultValue;
    }

    @Override
    public final void attachDataToDummyRenderStack(ItemStack stack, BlueprintData data)
    {
        T toCopy = data.getAuxDataOrDefault(auxDefaultValue);
        stack.set(componentType, toCopy);
    }
}
