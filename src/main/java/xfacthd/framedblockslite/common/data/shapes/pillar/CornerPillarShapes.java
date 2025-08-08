package xfacthd.framedblockslite.common.data.shapes.pillar;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblockslite.api.block.FramedProperties;
import xfacthd.framedblockslite.api.shapes.CommonShapes;
import xfacthd.framedblockslite.api.shapes.ShapeProvider;

public final class CornerPillarShapes
{
    public static ShapeProvider generate(ImmutableList<BlockState> states)
    {
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for (BlockState state : states)
        {
            Direction dir = state.getValue(FramedProperties.FACING_HOR);
            builder.put(state, CommonShapes.CORNER_PILLAR.get(dir));
        }

        return ShapeProvider.of(builder.build());
    }



    private CornerPillarShapes() { }
}
