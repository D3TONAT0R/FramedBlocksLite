package xfacthd.framedblockslite.client;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Holder;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.*;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.Nullable;
import xfacthd.framedblockslite.api.block.IFramedBlock;
import xfacthd.framedblockslite.api.block.render.FramedBlockColor;
import xfacthd.framedblockslite.api.block.render.FramedBlockRenderProperties;
import xfacthd.framedblockslite.api.model.ErrorModel;
import xfacthd.framedblockslite.api.model.wrapping.*;
import xfacthd.framedblockslite.api.model.wrapping.RegisterModelWrappersEvent;
import xfacthd.framedblockslite.api.model.wrapping.TextureLookup;
import xfacthd.framedblockslite.api.model.wrapping.WrapHelper;
import xfacthd.framedblockslite.api.model.wrapping.itemmodel.ItemModelInfo;
import xfacthd.framedblockslite.api.model.wrapping.statemerger.StateMerger;
import xfacthd.framedblockslite.api.render.debug.AttachDebugRenderersEvent;
import xfacthd.framedblockslite.api.util.FramedConstants;
import xfacthd.framedblockslite.api.util.Utils;
import xfacthd.framedblockslite.client.data.*;
import xfacthd.framedblockslite.client.data.ConTexDataHandler;
import xfacthd.framedblockslite.client.data.GhostRenderBehaviours;
import xfacthd.framedblockslite.client.loader.fallback.FallbackLoader;
import xfacthd.framedblockslite.client.loader.overlay.OverlayLoader;
import xfacthd.framedblockslite.client.model.*;
import xfacthd.framedblockslite.client.model.cube.*;
import xfacthd.framedblockslite.client.model.door.*;
import xfacthd.framedblockslite.client.model.interactive.*;
import xfacthd.framedblockslite.client.model.pane.*;
import xfacthd.framedblockslite.client.model.pillar.*;
import xfacthd.framedblockslite.client.model.slab.*;
import xfacthd.framedblockslite.client.model.slopepanelcorner.*;
import xfacthd.framedblockslite.client.model.stairs.*;
import xfacthd.framedblockslite.client.model.DoubleBlockItemModelInfo;
import xfacthd.framedblockslite.client.model.FluidModel;
import xfacthd.framedblockslite.client.model.FramedDoubleBlockModel;
import xfacthd.framedblockslite.client.model.cube.FramedBookshelfGeometry;
import xfacthd.framedblockslite.client.model.cube.FramedCubeGeometry;
import xfacthd.framedblockslite.client.model.cube.FramedMarkedCubeGeometry;
import xfacthd.framedblockslite.client.model.cube.FramedTargetGeometry;
import xfacthd.framedblockslite.client.model.door.*;
import xfacthd.framedblockslite.client.model.interactive.FramedButtonGeometry;
import xfacthd.framedblockslite.client.model.interactive.FramedMarkedPressurePlateGeometry;
import xfacthd.framedblockslite.client.model.interactive.FramedPressurePlateGeometry;
import xfacthd.framedblockslite.client.model.interactive.FramedStoneButtonGeometry;
import xfacthd.framedblockslite.client.model.pillar.*;
import xfacthd.framedblockslite.client.model.slab.FramedPanelGeometry;
import xfacthd.framedblockslite.client.model.slab.FramedSlabCornerGeometry;
import xfacthd.framedblockslite.client.model.slab.FramedSlabEdgeGeometry;
import xfacthd.framedblockslite.client.model.slab.FramedSlabGeometry;
import xfacthd.framedblockslite.client.model.stairs.FramedHalfStairsGeometry;
import xfacthd.framedblockslite.client.model.stairs.FramedStairsGeometry;
import xfacthd.framedblockslite.client.model.stairs.FramedVerticalHalfStairsGeometry;
import xfacthd.framedblockslite.client.model.stairs.FramedVerticalStairsGeometry;
import xfacthd.framedblockslite.client.modelwrapping.ModelWrappingManager;
import xfacthd.framedblockslite.client.modelwrapping.StateLocationCache;
import xfacthd.framedblockslite.client.overlaygen.OverlayQuadGenerator;
import xfacthd.framedblockslite.client.render.debug.FramedBlockDebugRenderer;
import xfacthd.framedblockslite.client.render.debug.impl.*;
import xfacthd.framedblockslite.client.render.debug.impl.ConnectionPredicateDebugRenderer;
import xfacthd.framedblockslite.client.render.debug.impl.DoubleBlockPartDebugRenderer;
import xfacthd.framedblockslite.client.render.debug.impl.QuadWindingDebugRenderer;
import xfacthd.framedblockslite.client.render.item.BlueprintPropertyOverride;
import xfacthd.framedblockslite.client.render.particle.FluidSpriteParticle;
import xfacthd.framedblockslite.client.render.special.*;
import xfacthd.framedblockslite.client.render.special.BlockOutlineRenderer;
import xfacthd.framedblockslite.client.render.special.GhostBlockRenderer;
import xfacthd.framedblockslite.client.render.util.AnimationSplitterSource;
import xfacthd.framedblockslite.client.screen.overlay.BlockInteractOverlayLayer;
import xfacthd.framedblockslite.client.screen.widget.BlockPreviewTooltipComponent;
import xfacthd.framedblockslite.client.util.*;
import xfacthd.framedblockslite.client.util.ClientEventHandler;
import xfacthd.framedblockslite.client.util.ClientTaskQueue;
import xfacthd.framedblockslite.client.util.KeyMappings;
import xfacthd.framedblockslite.common.FBContent;
import xfacthd.framedblockslite.common.block.IFramedDoubleBlock;
import xfacthd.framedblockslite.common.block.door.FramedDoorBlock;
import xfacthd.framedblockslite.common.block.door.FramedFenceGateBlock;
import xfacthd.framedblockslite.common.block.interactive.button.FramedButtonBlock;
import xfacthd.framedblockslite.common.block.stairs.standard.FramedStairsBlock;
import xfacthd.framedblockslite.common.compat.amendments.AmendmentsCompat;
import xfacthd.framedblockslite.common.data.StateCacheBuilder;
import xfacthd.framedblockslite.common.data.camo.fluid.FluidCamoClientHandler;
import xfacthd.framedblockslite.common.data.doubleblock.FramedDoubleBlockRenderProperties;
import xfacthd.framedblockslite.common.data.doubleblock.NullCullPredicate;

import java.util.Map;
import java.util.Set;

@Mod(value = FramedConstants.MOD_ID, dist = Dist.CLIENT)
public final class FBClient
{
    public FBClient(IEventBus modBus, ModContainer container)
    {
        modBus.addListener(FBClient::onClientSetup);
        modBus.addListener(FBClient::onRegisterMenuScreens);
        modBus.addListener(FBClient::onImcMessageReceived);
        modBus.addListener(FBClient::onLoadComplete);
        modBus.addListener(FBClient::onRegisterKeyMappings);
        modBus.addListener(FBClient::onAttachDebugRenderers);
        modBus.addListener(FBClient::onRegisterRenderers);
        modBus.addListener(FBClient::onBlockColors);
        modBus.addListener(FBClient::onItemColors);
        modBus.addListener(FBClient::onOverlayRegister);
        modBus.addListener(FBClient::onGeometryLoaderRegister);
        modBus.addListener(FBClient::onRegisterModelWrappers);
        modBus.addListener(FBClient::onModelRegister);
        modBus.addListener(FBClient::onModifyBakingResult);
        modBus.addListener(FBClient::onModelsLoaded);
        modBus.addListener(FBClient::onRegisterReloadListener);
        modBus.addListener(FBClient::onRegisterSpriteSources);
        modBus.addListener(FBClient::onTexturesStitched);
        modBus.addListener(FBClient::onRegisterParticleProviders);
        modBus.addListener(FBClient::onRegisterClientExtensions);
        modBus.addListener(FBClient::onRegisterClientTooltipComponentFactories);
        modBus.addListener(GhostRenderBehaviours::onRegisterGhostRenderBehaviours);

        NeoForge.EVENT_BUS.addListener(ClientTaskQueue::onClientTick);
        NeoForge.EVENT_BUS.addListener(BlockOutlineRenderer::onRenderBlockHighlight);
        NeoForge.EVENT_BUS.addListener(KeyMappings::onClientTick);
        NeoForge.EVENT_BUS.addListener(GhostBlockRenderer::onRenderLevelStage);
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGH, ClientEventHandler::onRecipesUpdated);
        NeoForge.EVENT_BUS.addListener(ClientEventHandler::onClientDisconnect);

        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    private static void onClientSetup(final FMLClientSetupEvent event)
    {
        event.enqueueWork(BlueprintPropertyOverride::register);
    }

    private static void onRegisterMenuScreens(final RegisterMenuScreensEvent event)
    {
    }

    private static void onImcMessageReceived(final InterModProcessEvent event)
    {
        event.getIMCStream(FramedConstants.IMC_METHOD_ADD_PROPERTY::equals).forEach(msg ->
        {
            if (msg.messageSupplier().get() instanceof ModelProperty<?> prop)
            {
                ConTexDataHandler.addConTexProperty(msg.senderModId(), prop);
            }
        });
    }

    private static void onLoadComplete(final FMLLoadCompleteEvent event)
    {
        ConTexDataHandler.lockRegistration();
    }

    private static void onRegisterKeyMappings(final RegisterKeyMappingsEvent event)
    {
        event.register(KeyMappings.KEYMAPPING_UPDATE_CULLING.get());
        event.register(KeyMappings.KEYMAPPING_WIPE_CACHE.get());
    }

    private static void onAttachDebugRenderers(final AttachDebugRenderersEvent event)
    {
        FBContent.getBlockEntities().forEach(type -> event.attach(type.value(), ConnectionPredicateDebugRenderer.INSTANCE));
        FBContent.getBlockEntities().forEach(type -> event.attach(type.value(), QuadWindingDebugRenderer.INSTANCE));
        FBContent.getDoubleBlockEntities().forEach(type -> event.attach(type.value(), DoubleBlockPartDebugRenderer.INSTANCE));
    }

    private static void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event)
    {
    }

    private static void onBlockColors(final RegisterColorHandlersEvent.Block event)
    {
        //noinspection SuspiciousToArrayCall
        Block[] blocks = FBContent.getRegisteredBlocks()
                .stream()
                .map(Holder::value)
                .filter(IFramedBlock.class::isInstance)
                .map(IFramedBlock.class::cast)
                .filter(FBClient::useDefaultColorHandler)
                .toArray(Block[]::new);

        event.register(FramedBlockColor.INSTANCE, blocks);
    }

    private static void onItemColors(final RegisterColorHandlersEvent.Item event)
    {
        //noinspection SuspiciousToArrayCall
        ItemLike[] blocks = FBContent.getRegisteredBlocks()
                .stream()
                .map(Holder::value)
                .filter(IFramedBlock.class::isInstance)
                .map(IFramedBlock.class::cast)
                .filter(FBClient::useDefaultColorHandler)
                .toArray(ItemLike[]::new);

        event.register(FramedBlockColor.INSTANCE, blocks);
    }

    private static void onOverlayRegister(final RegisterGuiLayersEvent event)
    {
        event.registerAboveAll(Utils.rl("block_interact"), new BlockInteractOverlayLayer());
    }

    private static void onGeometryLoaderRegister(final ModelEvent.RegisterGeometryLoaders event)
    {
        event.register(OverlayLoader.ID, new OverlayLoader());
        event.register(FallbackLoader.ID, new FallbackLoader());
    }

    private static void onRegisterModelWrappers(final RegisterModelWrappersEvent event)
    {
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_CUBE, FramedCubeGeometry::new, WrapHelper.IGNORE_SOLID);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_SLAB, FramedSlabGeometry::new, WrapHelper.IGNORE_DEFAULT);
        wrapDoubleModel(FBContent.BLOCK_FRAMED_DOUBLE_SLAB, NullCullPredicate.ALWAYS, WrapHelper.IGNORE_SOLID);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_SLAB_EDGE, FramedSlabEdgeGeometry::new, WrapHelper.IGNORE_WATERLOGGED);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_SLAB_CORNER, FramedSlabCornerGeometry::new, WrapHelper.IGNORE_WATERLOGGED);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_PANEL, FramedPanelGeometry::new, WrapHelper.IGNORE_DEFAULT);
        wrapDoubleModel(FBContent.BLOCK_FRAMED_DOUBLE_PANEL, NullCullPredicate.ALWAYS, WrapHelper.IGNORE_SOLID);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_CORNER_PILLAR, FramedCornerPillarGeometry::new, WrapHelper.IGNORE_WATERLOGGED);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_STAIRS, FramedStairsGeometry::new, new FramedStairsBlock.StairStateMerger());
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_HALF_STAIRS, FramedHalfStairsGeometry::new, WrapHelper.IGNORE_WATERLOGGED);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_VERTICAL_STAIRS, FramedVerticalStairsGeometry::new, WrapHelper.IGNORE_DEFAULT_LOCK);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_VERTICAL_HALF_STAIRS, FramedVerticalHalfStairsGeometry::new, WrapHelper.IGNORE_WATERLOGGED);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_WALL, FramedWallGeometry::new, WrapHelper.IGNORE_WATERLOGGED_LOCK);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_FENCE, FramedFenceGeometry::new, WrapHelper.IGNORE_WATERLOGGED_LOCK);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_FENCE_GATE, FramedFenceGateGeometry::new, FramedFenceGateBlock.FenceGateStateMerger.INSTANCE);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_DOOR, FramedDoorGeometry::new, FramedDoorBlock.DoorStateMerger.INSTANCE);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_IRON_DOOR, FramedIronDoorGeometry::new, FramedDoorBlock.DoorStateMerger.INSTANCE);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_TRAP_DOOR, FramedTrapDoorGeometry::new, Utils.concat(Set.of(BlockStateProperties.POWERED), WrapHelper.IGNORE_DEFAULT));
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR, FramedIronTrapDoorGeometry::new, Utils.concat(Set.of(BlockStateProperties.POWERED), WrapHelper.IGNORE_DEFAULT));
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_PRESSURE_PLATE, FramedPressurePlateGeometry::new, WrapHelper.IGNORE_ALWAYS);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_STONE_PRESSURE_PLATE, FramedMarkedPressurePlateGeometry::stone, WrapHelper.IGNORE_ALWAYS);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_BUTTON, FramedButtonGeometry::new, FramedButtonBlock.STATE_MERGER);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_STONE_BUTTON, FramedStoneButtonGeometry::create, FramedButtonBlock.STATE_MERGER);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_PILLAR, FramedPillarGeometry::new, WrapHelper.IGNORE_WATERLOGGED);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_HALF_PILLAR, FramedHalfPillarGeometry::new, WrapHelper.IGNORE_WATERLOGGED);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_BOOKSHELF, FramedBookshelfGeometry::normal, WrapHelper.IGNORE_SOLID);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_CHISELED_BOOKSHELF, FramedBookshelfGeometry::chiseled, WrapHelper.IGNORE_SOLID);
    }

    private static void onModelRegister(final ModelEvent.RegisterAdditional event)
    {
        event.register(FluidModel.BARE_MODEL);
        event.register(FramedMarkedCubeGeometry.SLIME_FRAME_LOCATION);
        event.register(FramedMarkedCubeGeometry.REDSTONE_FRAME_LOCATION);
        event.register(FramedTargetGeometry.OVERLAY_LOCATION);
        event.register(ErrorModel.LOCATION);

        if (AmendmentsCompat.isLoaded())
        {
            event.register(AmendmentsCompat.Client.HANGING_MODEL_LOCATION);
        }

        ModelWrappingManager.reset();
    }

    private static void onModifyBakingResult(final ModelEvent.ModifyBakingResult event)
    {
        StateCacheBuilder.ensureStateCachesInitialized();

        Map<ModelResourceLocation, BakedModel> registry = event.getModels();
        TextureLookup textureLookup = TextureLookup.bindBlockAtlas(event.getTextureGetter());

        ModelWrappingManager.handleAll(registry, textureLookup);
    }

    private static void onModelsLoaded(final ModelEvent.BakingCompleted event)
    {
        StateLocationCache.clear();
        FluidCamoClientHandler.clearModelCache();
        ErrorModel.reload(event.getModels());
    }

    private static void onRegisterReloadListener(final RegisterClientReloadListenersEvent event)
    {
        event.registerReloadListener((ResourceManagerReloadListener) BlockInteractOverlayLayer::onResourceReload);
        event.registerReloadListener((ResourceManagerReloadListener) OverlayQuadGenerator::onResourceReload);

        ModelWrappingManager.fireRegistration();
        FramedBlockDebugRenderer.init();
        BlockOutlineRenderer.init();
        GhostBlockRenderer.init();
    }

    private static void onRegisterSpriteSources(final RegisterSpriteSourceTypesEvent event)
    {
        event.register(Utils.rl("anim_splitter"), AnimationSplitterSource.TYPE);
    }

    private static void onTexturesStitched(final TextureAtlasStitchedEvent event)
    {
        //noinspection deprecation
        if (event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS))
        {
            ConnectionPredicateDebugRenderer.captureDummySprite(event.getAtlas());
        }
    }

    private static void onRegisterParticleProviders(final RegisterParticleProvidersEvent event)
    {
        event.registerSpecial(FBContent.FLUID_PARTICLE.get(), new FluidSpriteParticle.Provider());
    }

    private static void onRegisterClientExtensions(final RegisterClientExtensionsEvent event)
    {
        FBContent.getRegisteredBlocks()
                .stream()
                .map(Holder::value)
                .filter(IFramedBlock.class::isInstance)
                .map(block -> Pair.of(block, switch (block)
                {
                    case IFramedDoubleBlock ignored -> FramedDoubleBlockRenderProperties.INSTANCE;
                    default -> FramedBlockRenderProperties.INSTANCE;
                }))
                .forEach(pair -> event.registerBlock(pair.getSecond(), pair.getFirst()));

    }

    private static void onRegisterClientTooltipComponentFactories(RegisterClientTooltipComponentFactoriesEvent event)
    {
        event.register(BlockPreviewTooltipComponent.Component.class, BlockPreviewTooltipComponent::new);
    }



    private static void wrapDoubleModel(
            Holder<Block> block,
            NullCullPredicate nullCullPredicate,
            @Nullable Set<Property<?>> ignoredProps
    )
    {
        wrapDoubleModel(block, nullCullPredicate, DoubleBlockItemModelInfo.INSTANCE, ignoredProps);
    }

    private static void wrapDoubleModel(
            Holder<Block> block,
            NullCullPredicate nullCullPredicate,
            ItemModelInfo itemModelInfo,
            @Nullable Set<Property<?>> ignoredProps
    )
    {
        WrapHelper.wrapSpecial(
                block,
                ctx -> new FramedDoubleBlockModel(ctx, nullCullPredicate, itemModelInfo),
                StateMerger.ignoring(ignoredProps)
        );
    }

    private static boolean useDefaultColorHandler(IFramedBlock block)
    {
        return true;
    }
}