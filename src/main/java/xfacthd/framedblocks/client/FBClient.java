package xfacthd.framedblocks.client;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.model.standalone.SimpleUnbakedStandaloneModel;
import net.neoforged.neoforge.common.NeoForge;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.block.IFramedDoubleBlock;
import xfacthd.framedblocks.api.block.render.FramedBlockColor;
import xfacthd.framedblocks.api.block.render.FramedClientBlockExtensions;
import xfacthd.framedblocks.api.block.render.FramedClientDoubleBlockExtensions;
import xfacthd.framedblocks.api.block.render.NullCullPredicate;
import xfacthd.framedblocks.api.model.item.DoubleBlockItemModelInfo;
import xfacthd.framedblocks.api.model.item.ItemModelInfo;
import xfacthd.framedblocks.api.model.item.block.BlockItemModelProvider;
import xfacthd.framedblocks.api.model.item.block.RegisterBlockItemModelProvidersEvent;
import xfacthd.framedblocks.api.model.item.tint.FramedBlockItemTintProvider;
import xfacthd.framedblocks.api.model.item.tint.RegisterItemTintProvidersEvent;
import xfacthd.framedblocks.api.model.wrapping.RegisterModelWrappersEvent;
import xfacthd.framedblocks.api.model.wrapping.WrapHelper;
import xfacthd.framedblocks.api.model.wrapping.statemerger.StateMerger;
import xfacthd.framedblocks.api.render.debug.AttachDebugRenderersEvent;
import xfacthd.framedblocks.api.block.IBlockType;
import xfacthd.framedblocks.api.screen.overlay.RegisterBlockInteractOverlaysEvent;
import xfacthd.framedblocks.api.util.ClientUtils;
import xfacthd.framedblocks.api.util.FramedConstants;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.client.data.BlockOutlineRenderers;
import xfacthd.framedblocks.client.data.GhostRenderBehaviours;
import xfacthd.framedblocks.client.data.extensions.block.NoEffectsClientBlockExtensions;
import xfacthd.framedblocks.client.model.FluidModel;
import xfacthd.framedblocks.client.model.ReinforcementModel;
import xfacthd.framedblocks.client.model.baked.FramedBlockModel;
import xfacthd.framedblocks.client.model.geometry.cube.*;
import xfacthd.framedblocks.client.model.geometry.door.*;
import xfacthd.framedblocks.client.model.geometry.interactive.*;
import xfacthd.framedblocks.client.model.geometry.pillar.*;
import xfacthd.framedblocks.client.model.geometry.slab.*;
import xfacthd.framedblocks.client.model.geometry.stairs.*;
import xfacthd.framedblocks.client.model.item.BlockItemModelProviders;
import xfacthd.framedblocks.client.model.item.DynamicItemTintProviders;
import xfacthd.framedblocks.client.model.item.FramedBlockItemModel;
import xfacthd.framedblocks.client.model.item.TankItemModel;
import xfacthd.framedblocks.client.model.item.modelprovider.FenceBlockItemModelProvider;
import xfacthd.framedblocks.client.model.loader.fallback.FallbackLoader;
import xfacthd.framedblocks.client.model.overlaygen.OverlayQuadGenerator;
import xfacthd.framedblocks.client.model.unbaked.FramedBlockModelDefinition;
import xfacthd.framedblocks.client.model.wrapping.ModelWrappingManager;
import xfacthd.framedblocks.client.net.ClientNetworkHandler;
import xfacthd.framedblocks.client.render.debug.FramedBlockDebugRenderer;
import xfacthd.framedblocks.client.render.debug.impl.ConnectionPredicateDebugRenderer;
import xfacthd.framedblocks.client.render.debug.impl.DoubleBlockPartDebugRenderer;
import xfacthd.framedblocks.client.render.debug.impl.QuadWindingDebugRenderer;
import xfacthd.framedblocks.client.render.item.BlueprintProperty;
import xfacthd.framedblocks.client.render.particle.FluidSpriteParticle;
import xfacthd.framedblocks.client.render.special.BlockOutlineRenderer;
import xfacthd.framedblocks.client.render.special.GhostBlockRenderer;
import xfacthd.framedblocks.client.render.util.AnimationSplitterSource;
import xfacthd.framedblocks.client.render.util.FramedRenderPipelines;
import xfacthd.framedblocks.client.screen.FramingSawScreen;
import xfacthd.framedblocks.client.screen.overlay.BlockInteractOverlayLayer;
import xfacthd.framedblocks.client.screen.overlay.impl.*;
import xfacthd.framedblocks.client.screen.pip.BlockPictureInPictureRenderer;
import xfacthd.framedblocks.client.screen.pip.SpinningItemPictureInPictureRenderer;
import xfacthd.framedblocks.client.screen.widget.BlockPreviewTooltipComponent;
import xfacthd.framedblocks.client.util.ClientEventHandler;
import xfacthd.framedblocks.client.util.ClientTaskQueue;
import xfacthd.framedblocks.client.util.KeyMappings;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.block.door.FramedDoorBlock;
import xfacthd.framedblocks.common.block.door.FramedFenceGateBlock;
import xfacthd.framedblocks.common.block.interactive.button.FramedButtonBlock;
import xfacthd.framedblocks.common.block.interactive.button.FramedLargeButtonBlock;
import xfacthd.framedblocks.common.block.stairs.standard.FramedStairsBlock;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.camo.fluid.FluidCamoClientHandler;

import java.util.Set;
import java.util.function.Function;

@Mod(value = FramedConstants.MOD_ID, dist = Dist.CLIENT)
public final class FBClient
{
    public FBClient(IEventBus modBus, ModContainer container)
    {
        modBus.addListener(FBClient::onRegisterConditionalItemModelProperties);
        modBus.addListener(FBClient::onRegisterItemModels);
        modBus.addListener(FBClient::onRegisterSpecialModelRenderers);
        modBus.addListener(FBClient::onRegisterMenuScreens);
        modBus.addListener(FBClient::onAttachDebugRenderers);
        modBus.addListener(FBClient::onRegisterRenderers);
        modBus.addListener(FBClient::onRegisterBlockColors);
        modBus.addListener(FBClient::onRegisterBlockItemModelProviders);
        modBus.addListener(FBClient::onRegisterItemTintProviders);
        modBus.addListener(FBClient::onRegisterGuiLayers);
        modBus.addListener(FBClient::onRegisterBlockInteractOverlays);
        modBus.addListener(FBClient::onGeometryLoaderRegister);
        modBus.addListener(FBClient::onRegisterModelWrappers);
        modBus.addListener(FBClient::onBlockStateModelRegister);
        modBus.addListener(FBClient::onModelRegister);
        modBus.addListener(FBClient::onModelsLoaded);
        modBus.addListener(FBClient::onRegisterReloadListener);
        modBus.addListener(FBClient::onInitClientRegistries);
        modBus.addListener(FBClient::onRegisterSpriteSources);
        modBus.addListener(FBClient::onTexturesStitched);
        modBus.addListener(FBClient::onRegisterParticleProviders);
        modBus.addListener(FBClient::onRegisterClientExtensions);
        modBus.addListener(FBClient::onRegisterClientTooltipComponentFactories);
        modBus.addListener(FBClient::onRegisterPictureInPictureRenderers);
        modBus.addListener(KeyMappings::onRegisterKeyMappings);
        modBus.addListener(BlockOutlineRenderers::onRegisterOutlineRenderers);
        modBus.addListener(GhostRenderBehaviours::onRegisterGhostRenderBehaviours);
        modBus.addListener(FramedRenderPipelines::onRegisterRenderPipelines);
        modBus.addListener(ClientNetworkHandler::onRegisterPayloadHandlers);

        NeoForge.EVENT_BUS.addListener(ClientTaskQueue::onClientTick);
        NeoForge.EVENT_BUS.addListener(BlockOutlineRenderer::onRenderBlockHighlight);
        NeoForge.EVENT_BUS.addListener(KeyMappings::onClientTick);
        NeoForge.EVENT_BUS.addListener(GhostBlockRenderer::onRenderLevelStage);
        NeoForge.EVENT_BUS.addListener(ClientEventHandler::onClientDisconnect);

        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    private static void onRegisterConditionalItemModelProperties(RegisterConditionalItemModelPropertyEvent event)
    {
        event.register(BlueprintProperty.HAS_DATA, BlueprintProperty.TYPE);
    }

    private static void onRegisterItemModels(RegisterItemModelsEvent event)
    {
        event.register(FramedBlockItemModel.Unbaked.ID, FramedBlockItemModel.Unbaked.CODEC);
        event.register(TankItemModel.Unbaked.ID, TankItemModel.Unbaked.CODEC);
    }

    private static void onRegisterSpecialModelRenderers(RegisterSpecialModelRendererEvent event)
    {
    }

    private static void onRegisterMenuScreens(RegisterMenuScreensEvent event)
    {
        event.register(FBContent.MENU_TYPE_FRAMING_SAW.value(), FramingSawScreen::create);
    }

    private static void onAttachDebugRenderers(AttachDebugRenderersEvent event)
    {
        FBContent.getBlockEntities().forEach(type -> event.attach(type.value(), ConnectionPredicateDebugRenderer.INSTANCE));
        FBContent.getBlockEntities().forEach(type -> event.attach(type.value(), QuadWindingDebugRenderer.INSTANCE));
        FBContent.getDoubleBlockEntities().forEach(type -> event.attach(type.value(), DoubleBlockPartDebugRenderer.INSTANCE));
    }

    private static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
    }

    private static void onRegisterBlockColors(RegisterColorHandlersEvent.Block event)
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

    private static void onRegisterBlockItemModelProviders(RegisterBlockItemModelProvidersEvent event)
    {
        event.register(Utils.rl("default"), BlockItemModelProvider.DEFAULT);
        event.register(Utils.rl("fence"), FenceBlockItemModelProvider.INSTANCE);
    }

    private static void onRegisterItemTintProviders(RegisterItemTintProvidersEvent event)
    {
        event.register(Utils.rl("single"), FramedBlockItemTintProvider.INSTANCE_SINGLE);
        event.register(Utils.rl("double"), FramedBlockItemTintProvider.INSTANCE_DOUBLE);
    }

    private static void onRegisterGuiLayers(RegisterGuiLayersEvent event)
    {
        BlockInteractOverlayLayer.init();

        event.registerAboveAll(Utils.rl("block_interact"), new BlockInteractOverlayLayer());
    }

    private static void onRegisterBlockInteractOverlays(RegisterBlockInteractOverlaysEvent event)
    {
        event.register("state_lock", new StateLockOverlay());
        event.register("toggle_waterloggable", new ToggleWaterloggableOverlay());
        event.register("toggle_y_slope", new ToggleYSlopeOverlay());
        event.register("reinforcement", new ReinforcementOverlay());
        event.register("prism_offset", new PrismOffsetOverlay());
        event.register("split_line", new SplitLineOverlay());
        event.register("one_way_window", new OneWayWindowOverlay());
        event.register("frame_background", new FrameBackgroundOverlay());
        event.register("camo_rotation", new CamoRotationOverlay());
        event.register("trapdoor_texture_rotation", new TrapdoorTextureRotationOverlay());
        event.register("copycat_style", new CopycatStyleOverlay());
    }

    private static void onGeometryLoaderRegister(ModelEvent.RegisterLoaders event)
    {
        event.register(FallbackLoader.ID, new FallbackLoader());
    }

    private static void onRegisterModelWrappers(RegisterModelWrappersEvent event)
    {
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_CUBE, FramedCubeGeometry::new, WrapHelper.IGNORE_SOLID);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_SLAB, FramedSlabGeometry::new, WrapHelper.IGNORE_DEFAULT);
        wrapDoubleModel(FBContent.BLOCK_FRAMED_DOUBLE_SLAB, NullCullPredicate.ALWAYS, WrapHelper.IGNORE_SOLID);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_SLAB_EDGE, FramedSlabEdgeGeometry::new, WrapHelper.IGNORE_WATERLOGGED);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_SLAB_CORNER, FramedSlabCornerGeometry::new, WrapHelper.IGNORE_WATERLOGGED);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_PANEL, FramedPanelGeometry::new, WrapHelper.IGNORE_DEFAULT);
        wrapDoubleModel(FBContent.BLOCK_FRAMED_DOUBLE_PANEL, NullCullPredicate.ALWAYS, WrapHelper.IGNORE_SOLID);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_CORNER_PILLAR, FramedCornerPillarGeometry::new, WrapHelper.IGNORE_WATERLOGGED);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_STAIRS, FramedStairsGeometry::new, FramedStairsBlock.STATE_MERGER);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_HALF_STAIRS, FramedHalfStairsGeometry::new, WrapHelper.IGNORE_WATERLOGGED);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_VERTICAL_STAIRS, FramedVerticalStairsGeometry::new, WrapHelper.IGNORE_DEFAULT_LOCK);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_VERTICAL_HALF_STAIRS, FramedVerticalHalfStairsGeometry::new, WrapHelper.IGNORE_WATERLOGGED);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_WALL, FramedWallGeometry::new, WrapHelper.IGNORE_WATERLOGGED_LOCK);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_FENCE, FramedFenceGeometry::new, WrapHelper.IGNORE_WATERLOGGED_LOCK);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_FENCE_GATE, FramedFenceGateGeometry::new, FramedFenceGateBlock.FenceGateStateMerger.INSTANCE);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_DOOR, FramedDoorGeometry::wood, FramedDoorBlock.DoorStateMerger.INSTANCE);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_IRON_DOOR, FramedDoorGeometry::iron, FramedDoorBlock.DoorStateMerger.INSTANCE);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_TRAP_DOOR, FramedTrapDoorGeometry::wood, Utils.concat(Set.of(BlockStateProperties.POWERED), WrapHelper.IGNORE_DEFAULT));
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR, FramedTrapDoorGeometry::iron, Utils.concat(Set.of(BlockStateProperties.POWERED), WrapHelper.IGNORE_DEFAULT));
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_PRESSURE_PLATE, FramedPressurePlateGeometry::new, WrapHelper.IGNORE_ALWAYS);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_STONE_PRESSURE_PLATE, FramedMarkedPressurePlateGeometry::stone, WrapHelper.IGNORE_ALWAYS);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_BUTTON, FramedButtonGeometry::new, FramedButtonBlock.STATE_MERGER);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_STONE_BUTTON, FramedStoneButtonGeometry::create, FramedButtonBlock.STATE_MERGER);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_LARGE_BUTTON, FramedLargeButtonGeometry::new, FramedLargeButtonBlock.LARGE_STATE_MERGER);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_LARGE_STONE_BUTTON, FramedLargeStoneButtonGeometry::create, FramedLargeButtonBlock.LARGE_STATE_MERGER);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_PILLAR, FramedPillarGeometry::new, WrapHelper.IGNORE_WATERLOGGED);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_HALF_PILLAR, FramedHalfPillarGeometry::new, WrapHelper.IGNORE_WATERLOGGED);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_GATE, FramedDoorGeometry::wood, FramedDoorBlock.DoorStateMerger.INSTANCE);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_IRON_GATE, FramedDoorGeometry::iron, FramedDoorBlock.DoorStateMerger.INSTANCE);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_BOOKSHELF, FramedBookshelfGeometry::normal, WrapHelper.IGNORE_SOLID);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_CHISELED_BOOKSHELF, FramedBookshelfGeometry::chiseled, WrapHelper.IGNORE_SOLID);
    }

    private static void onBlockStateModelRegister(RegisterBlockStateModels event)
    {
        event.registerDefinition(Utils.rl("wrapper"), FramedBlockModelDefinition.CODEC);
    }

    private static void onModelRegister(ModelEvent.RegisterStandalone event)
    {
        event.register(FluidModel.BARE_MODEL_KEY, FluidModel.BARE_UNBAKED_MODEL);
        event.register(FluidModel.BARE_MODEL_SINGLE_KEY, FluidModel.BARE_UNBAKED_MODEL_SINGLE);
        event.register(ReinforcementModel.MODEL_KEY, SimpleUnbakedStandaloneModel.quadCollection(ReinforcementModel.MODEL_ID));
    }

    private static void onModelsLoaded(ModelEvent.BakingCompleted event)
    {
        FluidCamoClientHandler.clearModelCache();
        ReinforcementModel.reload(event.getBakingResult().standaloneModels());
        FramedBlockModel.collectCubeBaseModels(event.getBakingResult().blockStateModels());

        ModelWrappingManager.printWrappingInfo(event.getBakingResult().blockStateModels());
    }

    private static void onRegisterReloadListener(AddClientReloadListenersEvent event)
    {
        event.addListener(BlockInteractOverlayLayer.LISTENER_ID, (ResourceManagerReloadListener) BlockInteractOverlayLayer::onResourceReload);
        event.addListener(OverlayQuadGenerator.LISTENER_ID, (ResourceManagerReloadListener) OverlayQuadGenerator::onResourceReload);
    }

    private static void onInitClientRegistries(InitializeClientRegistriesEvent event)
    {
        ModelWrappingManager.fireRegistration();
        FramedBlockDebugRenderer.init();
        BlockOutlineRenderer.init();
        GhostBlockRenderer.init();
        BlockItemModelProviders.init();
        DynamicItemTintProviders.init();
    }

    private static void onRegisterSpriteSources(RegisterSpriteSourcesEvent event)
    {
        event.register(Utils.rl("anim_splitter"), AnimationSplitterSource.CODEC);
    }

    private static void onTexturesStitched(TextureAtlasStitchedEvent event)
    {
        if (event.getAtlas().location().equals(ClientUtils.BLOCK_ATLAS))
        {
            ConnectionPredicateDebugRenderer.captureDummySprite(event.getAtlas());
        }
    }

    private static void onRegisterParticleProviders(RegisterParticleProvidersEvent event)
    {
        event.registerSpecial(FBContent.FLUID_PARTICLE.get(), new FluidSpriteParticle.Provider());
    }

    private static void onRegisterClientExtensions(RegisterClientExtensionsEvent event)
    {
        FBContent.getRegisteredBlocks()
                .stream()
                .map(Holder::value)
                .filter(IFramedBlock.class::isInstance)
                .map(block -> Pair.of(block, switch (block)
                {
                    case IFramedDoubleBlock ignored -> FramedClientDoubleBlockExtensions.INSTANCE;
                    default -> FramedClientBlockExtensions.INSTANCE;
                }))
                .forEach(pair -> event.registerBlock(pair.getSecond(), pair.getFirst()));
    }

    private static void onRegisterClientTooltipComponentFactories(RegisterClientTooltipComponentFactoriesEvent event)
    {
        event.register(BlockPreviewTooltipComponent.class, Function.identity());
    }

    private static void onRegisterPictureInPictureRenderers(RegisterPictureInPictureRenderersEvent event)
    {
        event.register(SpinningItemPictureInPictureRenderer.RenderState.class, SpinningItemPictureInPictureRenderer::new);
        event.register(BlockPictureInPictureRenderer.RenderState.class, BlockPictureInPictureRenderer::new);
    }



    private static void wrapDoubleModel(Holder<Block> block, NullCullPredicate nullCullPredicate, Set<Property<?>> ignoredProps)
    {
        wrapDoubleModel(block, nullCullPredicate, DoubleBlockItemModelInfo.INSTANCE, ignoredProps);
    }

    private static void wrapDoubleModel(Holder<Block> block, NullCullPredicate nullCullPredicate, ItemModelInfo itemModelInfo, Set<Property<?>> ignoredProps)
    {
        WrapHelper.wrapDouble(block, nullCullPredicate, itemModelInfo, ignoredProps);
    }

    private static boolean useDefaultColorHandler(IFramedBlock block)
    {
        IBlockType type = block.getBlockType();
        return type != BlockType.FRAMED_FLOWER_POT && type != BlockType.FRAMED_TARGET;
    }
}
