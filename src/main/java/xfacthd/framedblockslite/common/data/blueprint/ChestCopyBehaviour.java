package xfacthd.framedblockslite.common.data.blueprint;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import xfacthd.framedblockslite.api.blueprint.BlueprintCopyBehaviour;
import xfacthd.framedblockslite.common.data.PropertyHolder;

import java.util.List;

public final class ChestCopyBehaviour implements BlueprintCopyBehaviour
{
    @Override
    public List<Property<?>> getPropertiesToCopy(BlockState state)
    {
        return List.of(PropertyHolder.LATCH_TYPE);
    }
}
