package xfacthd.framedblockslite.client.model.cube;

import net.minecraft.client.renderer.block.model.BakedQuad;
import xfacthd.framedblockslite.api.model.data.QuadMap;
import xfacthd.framedblockslite.api.model.geometry.Geometry;
import xfacthd.framedblockslite.api.model.wrapping.GeometryFactory;

public class FramedCubeGeometry extends Geometry
{
    public FramedCubeGeometry(@SuppressWarnings("unused") GeometryFactory.Context ctx) { }

    @Override
    public void transformQuad(QuadMap quadMap, BakedQuad quad) { }

    @Override
    public boolean forceUngeneratedBaseModel()
    {
        return true;
    }
}
