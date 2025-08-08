package xfacthd.framedblockslite.api.internal;

import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;
import xfacthd.framedblockslite.api.block.blockentity.FramedBlockEntity;
import xfacthd.framedblockslite.api.model.wrapping.*;
import xfacthd.framedblockslite.api.model.wrapping.GeometryFactory;
import xfacthd.framedblockslite.api.model.wrapping.ModelFactory;
import xfacthd.framedblockslite.api.model.wrapping.statemerger.StateMerger;
import xfacthd.framedblockslite.api.render.debug.BlockDebugRenderer;
import xfacthd.framedblockslite.api.util.Utils;

@ApiStatus.Internal
public interface InternalClientAPI
{
    InternalClientAPI INSTANCE = Utils.loadService(InternalClientAPI.class);



    void registerModelWrapper(Holder<Block> block, GeometryFactory geometryFactory, StateMerger stateMerger);

    void registerSpecialModelWrapper(Holder<Block> block, ModelFactory modelFactory, StateMerger stateMerger);

    void registerCopyingModelWrapper(Holder<Block> block, Holder<Block> srcBlock, StateMerger stateMerger);

    BlockDebugRenderer<FramedBlockEntity> getConnectionDebugRenderer();

    BlockDebugRenderer<FramedBlockEntity> getQuadWindingDebugRenderer();

    void enqueueClientTask(int delay, Runnable task);
}
