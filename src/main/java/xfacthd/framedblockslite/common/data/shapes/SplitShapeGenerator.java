package xfacthd.framedblockslite.common.data.shapes;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblockslite.api.shapes.ShapeGenerator;
import xfacthd.framedblockslite.api.shapes.ShapeProvider;

public interface SplitShapeGenerator extends ShapeGenerator
{
    ShapeProvider generateOcclusionShapes(ImmutableList<BlockState> states);
}
