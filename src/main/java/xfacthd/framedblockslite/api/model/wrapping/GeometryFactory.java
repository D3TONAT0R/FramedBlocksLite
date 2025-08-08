package xfacthd.framedblockslite.api.model.wrapping;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblockslite.api.model.geometry.Geometry;

public interface GeometryFactory
{
    Geometry create(Context ctx);



    record Context(BlockState state, BakedModel baseModel, ModelLookup modelLookup, TextureLookup textureLookup) { }
}
