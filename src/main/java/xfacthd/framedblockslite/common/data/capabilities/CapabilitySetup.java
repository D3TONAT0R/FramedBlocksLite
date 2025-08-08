package xfacthd.framedblockslite.common.data.capabilities;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import xfacthd.framedblockslite.common.FBContent;

public final class CapabilitySetup
{
    public static void onRegisterCapabilities(final RegisterCapabilitiesEvent event)
    {

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                FBContent.BE_TYPE_FRAMED_CHISELED_BOOKSHELF.value(),
                (be, side) -> be.getItemHandler()
        );
    }



    private CapabilitySetup() { }
}
