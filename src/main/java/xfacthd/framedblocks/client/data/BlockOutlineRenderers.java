package xfacthd.framedblocks.client.data;

import xfacthd.framedblocks.api.render.OutlineRenderer;
import xfacthd.framedblocks.api.render.RegisterOutlineRenderersEvent;
import xfacthd.framedblocks.common.data.BlockType;

public final class BlockOutlineRenderers
{
    public static void onRegisterOutlineRenderers(RegisterOutlineRenderersEvent event)
    {
        event.register(BlockType.FRAMED_ITEM_FRAME, OutlineRenderer.NO_OP);
        event.register(BlockType.FRAMED_GLOWING_ITEM_FRAME, OutlineRenderer.NO_OP);
    }



    private BlockOutlineRenderers() { }
}
