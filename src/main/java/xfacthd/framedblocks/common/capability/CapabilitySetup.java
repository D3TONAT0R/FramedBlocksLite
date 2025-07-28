package xfacthd.framedblocks.common.capability;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import xfacthd.framedblocks.common.FBContent;

public final class CapabilitySetup
{
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event)
    {

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                FBContent.BE_TYPE_FRAMED_CHISELED_BOOKSHELF.value(),
                (be, side) -> be.getItemHandler()
        );
    }



    private CapabilitySetup() { }
}
