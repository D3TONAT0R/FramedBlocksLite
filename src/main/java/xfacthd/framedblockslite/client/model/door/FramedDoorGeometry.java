package xfacthd.framedblockslite.client.model.door;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import xfacthd.framedblockslite.api.model.data.QuadMap;
import xfacthd.framedblockslite.api.model.geometry.Geometry;
import xfacthd.framedblockslite.api.model.wrapping.GeometryFactory;
import xfacthd.framedblockslite.api.model.quad.Modifiers;
import xfacthd.framedblockslite.api.model.quad.QuadModifier;
import xfacthd.framedblockslite.api.model.wrapping.itemmodel.ItemModelInfo;
import xfacthd.framedblockslite.api.model.wrapping.itemmodel.TranslatedItemModelInfo;
import xfacthd.framedblockslite.api.util.Utils;

public class FramedDoorGeometry extends Geometry
{
    private static final TranslatedItemModelInfo ITEM_MODEL_INFO = TranslatedItemModelInfo.handOrGui(0F, 0F, -.5F);

    private final Direction dir;
    private final boolean hingeRight;
    private final boolean open;

    public FramedDoorGeometry(GeometryFactory.Context ctx)
    {
        this.dir = ctx.state().getValue(BlockStateProperties.HORIZONTAL_FACING);
        this.hingeRight = ctx.state().getValue(BlockStateProperties.DOOR_HINGE) == DoorHingeSide.RIGHT;
        this.open = ctx.state().getValue(BlockStateProperties.OPEN);
    }

    @Override
    public void transformQuad(QuadMap quadMap, BakedQuad quad)
    {
        Direction faceDir = dir;
        if (open) { faceDir = hingeRight ? faceDir.getCounterClockWise() : faceDir.getClockWise(); }

        Direction quadDir = quad.getDirection();
        if (Utils.isY(quadDir))
        {
            QuadModifier.of(quad)
                    .apply(Modifiers.cutTopBottom(faceDir, 3F/16F))
                    .export(quadMap.get(quadDir));
        }
        else
        {
            if (quadDir == faceDir)
            {
                QuadModifier.of(quad)
                        .apply(Modifiers.setPosition(3F/16F))
                        .export(quadMap.get(null));
            }
            else
            {
                QuadModifier.of(quad)
                        .apply(Modifiers.cutSideLeftRight(faceDir, 3F/16F))
                        .export(quadMap.get(quadDir));
            }
        }
    }

    @Override
    public ItemModelInfo getItemModelInfo()
    {
        return super.getItemModelInfo();
    }
}
