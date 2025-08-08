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

public class FramedSlabEdgeGeometry extends Geometry
{
    private final Direction dir;
    private final boolean top;

    public FramedSlabEdgeGeometry(GeometryFactory.Context ctx)
    {
        this.dir = ctx.state().getValue(FramedProperties.FACING_HOR);
        this.top = ctx.state().getValue(FramedProperties.TOP);
    }

    @Override
    public void transformQuad(QuadMap quadMap, final BakedQuad quad)
    {
        Direction quadDir = quad.getDirection();
        if (Utils.isY(quadDir))
        {
            boolean inset = (quadDir == Direction.DOWN) == top;

            QuadModifier.of(quad)
                    .apply(Modifiers.cutTopBottom(dir.getOpposite(), .5F))
                    .applyIf(Modifiers.setPosition(.5F), inset)
                    .export(quadMap.get(inset ? null : quadDir));
        }
        else
        {
            boolean inset = quadDir == dir.getOpposite();
            boolean side = quadDir.getAxis() != dir.getAxis();

            QuadModifier.of(quad)
                    .apply(Modifiers.cutSideUpDown(top, .5F))
                    .applyIf(Modifiers.cutSideLeftRight(dir.getOpposite(), .5F), side)
                    .applyIf(Modifiers.setPosition(.5F), inset)
                    .export(quadMap.get(inset ? null : quadDir));
        }
    }
}