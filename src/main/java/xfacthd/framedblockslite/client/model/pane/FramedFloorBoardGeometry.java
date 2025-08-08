package xfacthd.framedblockslite.client.model.pane;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Direction;
import xfacthd.framedblockslite.api.model.data.QuadMap;
import xfacthd.framedblockslite.api.model.geometry.Geometry;
import xfacthd.framedblockslite.api.model.wrapping.GeometryFactory;
import xfacthd.framedblockslite.api.model.quad.Modifiers;
import xfacthd.framedblockslite.api.model.quad.QuadModifier;
import xfacthd.framedblockslite.api.block.FramedProperties;
import xfacthd.framedblockslite.api.util.Utils;

public class FramedFloorBoardGeometry extends Geometry
{
    private final boolean top;

    public FramedFloorBoardGeometry(GeometryFactory.Context ctx)
    {
        this.top = ctx.state().getValue(FramedProperties.TOP);
    }

    @Override
    public void transformQuad(QuadMap quadMap, BakedQuad quad)
    {
        Direction face = quad.getDirection();
        if ((!top && face == Direction.UP) || (top && face == Direction.DOWN))
        {
            QuadModifier.of(quad)
                    .apply(Modifiers.setPosition(1F/16F))
                    .export(quadMap.get(null));
        }
        else if (!Utils.isY(quad.getDirection()))
        {
            QuadModifier.of(quad)
                    .apply(Modifiers.cutSideUpDown(top, 1F/16F))
                    .export(quadMap.get(quad.getDirection()));
        }
    }
}