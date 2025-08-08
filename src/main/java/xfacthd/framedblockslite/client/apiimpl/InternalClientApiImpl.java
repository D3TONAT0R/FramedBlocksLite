package xfacthd.framedblockslite.client.apiimpl;

import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import xfacthd.framedblockslite.FramedBlocks;
import xfacthd.framedblockslite.api.block.blockentity.FramedBlockEntity;
import xfacthd.framedblockslite.api.internal.InternalClientAPI;
import xfacthd.framedblockslite.api.model.wrapping.*;
import xfacthd.framedblockslite.api.model.wrapping.GeometryFactory;
import xfacthd.framedblockslite.api.model.wrapping.ModelFactory;
import xfacthd.framedblockslite.api.model.wrapping.statemerger.StateMerger;
import xfacthd.framedblockslite.api.render.debug.BlockDebugRenderer;
import xfacthd.framedblockslite.api.util.Utils;
import xfacthd.framedblockslite.client.model.FramedBlockModel;
import xfacthd.framedblockslite.client.modelwrapping.*;
import xfacthd.framedblockslite.client.modelwrapping.CopyingModelFactory;
import xfacthd.framedblockslite.client.modelwrapping.ModelWrappingHandler;
import xfacthd.framedblockslite.client.modelwrapping.ModelWrappingManager;
import xfacthd.framedblockslite.client.render.debug.impl.ConnectionPredicateDebugRenderer;
import xfacthd.framedblockslite.client.render.debug.impl.QuadWindingDebugRenderer;
import xfacthd.framedblockslite.client.util.ClientTaskQueue;
import xfacthd.framedblockslite.common.config.DevToolsConfig;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class InternalClientApiImpl implements InternalClientAPI
{
    @Override
    public void registerModelWrapper(Holder<Block> block, GeometryFactory geometryFactory, StateMerger stateMerger)
    {
        registerSpecialModelWrapper(
                block,
                ctx -> new FramedBlockModel(ctx, geometryFactory.create(ctx)),
                stateMerger
        );
    }

    @Override
    public void registerSpecialModelWrapper(Holder<Block> block, ModelFactory modelFactory, StateMerger stateMerger)
    {
        debugStateMerger(block, stateMerger);

        ModelWrappingManager.register(block, new ModelWrappingHandler(block, modelFactory, stateMerger));
    }

    @Override
    public void registerCopyingModelWrapper(Holder<Block> block, Holder<Block> srcBlock, StateMerger stateMerger)
    {
        registerSpecialModelWrapper(block, new CopyingModelFactory(srcBlock), stateMerger);
    }

    @Override
    public BlockDebugRenderer<FramedBlockEntity> getConnectionDebugRenderer()
    {
        return ConnectionPredicateDebugRenderer.INSTANCE;
    }

    @Override
    public BlockDebugRenderer<FramedBlockEntity> getQuadWindingDebugRenderer()
    {
        return QuadWindingDebugRenderer.INSTANCE;
    }

    @Override
    public void enqueueClientTask(int delay, Runnable task)
    {
        ClientTaskQueue.enqueueClientTask(delay, task);
    }



    private static void debugStateMerger(Holder<Block> block, StateMerger stateMerger)
    {
        if (!DevToolsConfig.VIEW.isStateMergerDebugLoggingEnabled()) return;

        Pattern debugFilterPattern = DevToolsConfig.VIEW.getStateMergerDebugFilter();
        if (debugFilterPattern != null)
        {
            String key = Utils.getKeyOrThrow(block).location().toString();
            if (!debugFilterPattern.matcher(key).matches()) return;
        }

        Set<Property<?>> props = new HashSet<>(block.value().getStateDefinition().getProperties());
        Set<Property<?>> ignoredProps = stateMerger.getHandledProperties(block);

        props.removeAll(ignoredProps);

        FramedBlocks.LOGGER.info("%-70s | %-150s | %-150s".formatted(
                block.value(), propsToString(props), propsToString(ignoredProps)
        ));
    }

    private static String propsToString(Collection<Property<?>> properties)
    {
        return properties.stream()
                .map(Property::getName)
                .collect(Collectors.joining(", ", "[ ", " ]"));
    }
}
