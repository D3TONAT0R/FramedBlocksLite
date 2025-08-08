package xfacthd.framedblockslite.common.data;

import xfacthd.framedblockslite.api.blueprint.RegisterBlueprintCopyBehavioursEvent;
import xfacthd.framedblockslite.common.FBContent;
import xfacthd.framedblockslite.common.data.blueprint.*;
import xfacthd.framedblockslite.common.data.blueprint.DoorCopyBehaviour;
import xfacthd.framedblockslite.common.data.blueprint.DoublePanelCopyBehaviour;
import xfacthd.framedblockslite.common.data.blueprint.DoubleSlabCopyBehaviour;

import java.util.List;

public final class BlueprintBehaviours
{
    public static void onRegisterBlueprintCopyBehaviours(RegisterBlueprintCopyBehavioursEvent event)
    {
        event.register(new DoubleSlabCopyBehaviour(), FBContent.BLOCK_FRAMED_DOUBLE_SLAB);
        event.register(new DoublePanelCopyBehaviour(), FBContent.BLOCK_FRAMED_DOUBLE_PANEL);
        event.register(new DoorCopyBehaviour(), List.of(FBContent.BLOCK_FRAMED_DOOR, FBContent.BLOCK_FRAMED_IRON_DOOR));
    }

    private BlueprintBehaviours() { }
}
