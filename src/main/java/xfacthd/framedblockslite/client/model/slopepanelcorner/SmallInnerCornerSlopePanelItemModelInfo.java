package xfacthd.framedblockslite.client.model.slopepanelcorner;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemDisplayContext;
import xfacthd.framedblockslite.api.model.wrapping.itemmodel.ItemModelInfo;
import xfacthd.framedblockslite.api.render.Quaternions;
import xfacthd.framedblockslite.api.util.Utils;

public final class SmallInnerCornerSlopePanelItemModelInfo implements ItemModelInfo
{
    public static final SmallInnerCornerSlopePanelItemModelInfo INSTANCE = new SmallInnerCornerSlopePanelItemModelInfo();

    private SmallInnerCornerSlopePanelItemModelInfo() { }

    @Override
    public void applyItemTransform(PoseStack poseStack, ItemDisplayContext ctx, boolean leftHand)
    {
        if (Utils.isHandContext(ctx))
        {
            poseStack.mulPose(Quaternions.YP_90);
        }
    }
}
