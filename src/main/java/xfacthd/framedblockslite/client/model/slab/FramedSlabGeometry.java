package xfacthd.framedblockslite.client.model.slab;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Direction;
import xfacthd.framedblockslite.api.model.data.QuadMap;
import xfacthd.framedblockslite.api.model.geometry.Geometry;
import xfacthd.framedblockslite.api.model.wrapping.GeometryFactory;
import xfacthd.framedblockslite.api.model.quad.Modifiers;
import xfacthd.framedblockslite.api.model.quad.QuadModifier;
import xfacthd.framedblockslite.api.block.FramedProperties;
import xfacthd.framedblockslite.api.util.Utils;

public class FramedSlabGeometry extends Geometry
{
    private final boolean top;

    public FramedSlabGeometry(GeometryFactory.Context ctx)
    {
        this.top = ctx.state().getValue(FramedProperties.TOP);
    }

    @Override
    public void transformQuad(QuadMap quadMap, final BakedQuad quad)
    {
        if ((top && quad.getDirection() == Direction.DOWN) || (!top && quad.getDirection() == Direction.UP))
        {
            QuadModifier.of(quad)
                    .apply(Modifiers.setPosition(.5F))
                    .export(quadMap.get(null));
        }
        else if (!Utils.isY(quad.getDirection()))
        {
            QuadModifier.of(quad)
                    .apply(Modifiers.cutSideUpDown(top, .5F))
                    .export(quadMap.get(quad.getDirection()));
        }
    }
}