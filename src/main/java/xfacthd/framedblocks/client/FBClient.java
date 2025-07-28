package xfacthd.framedblocks.client;

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
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.block.render.FramedBlockColor;
import xfacthd.framedblocks.api.block.render.FramedBlockRenderProperties;
import xfacthd.framedblocks.api.model.ErrorModel;
import xfacthd.framedblocks.api.model.wrapping.*;
import xfacthd.framedblocks.api.model.wrapping.itemmodel.ItemModelInfo;
import xfacthd.framedblocks.api.model.wrapping.statemerger.StateMerger;
import xfacthd.framedblocks.api.render.debug.AttachDebugRenderersEvent;
import xfacthd.framedblocks.api.type.IBlockType;
import xfacthd.framedblocks.api.util.FramedConstants;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.client.data.*;
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
import xfacthd.framedblocks.client.util.*;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.block.IFramedDoubleBlock;
import xfacthd.framedblocks.common.block.cube.FramedMiniCubeBlock;
import xfacthd.framedblocks.common.block.cube.FramedOneWayWindowBlock;
import xfacthd.framedblocks.common.block.door.FramedDoorBlock;
import xfacthd.framedblocks.common.block.door.FramedFenceGateBlock;
import xfacthd.framedblocks.common.block.interactive.button.FramedButtonBlock;
import xfacthd.framedblocks.common.block.interactive.button.FramedLargeButtonBlock;
import xfacthd.framedblocks.common.block.stairs.standard.FramedStairsBlock;
import xfacthd.framedblocks.common.compat.amendments.AmendmentsCompat;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.StateCacheBuilder;
import xfacthd.framedblocks.common.data.camo.fluid.FluidCamoClientHandler;
import xfacthd.framedblocks.common.data.doubleblock.FramedDoubleBlockRenderProperties;
import xfacthd.framedblocks.common.data.doubleblock.NullCullPredicate;

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
        modBus.addListener(BlockOutlineRenderers::onRegisterOutlineRenderers);
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

        event.register(FramedTargetBlockColor.INSTANCE, FBContent.BLOCK_FRAMED_TARGET.value());
    }

    private static void onOverlayRegister(final RegisterGuiLayersEvent event)
    {
        event.register(Utils.rl("single"), FramedBlockItemTintProvider.INSTANCE_SINGLE);
        event.register(Utils.rl("double"), FramedBlockItemTintProvider.INSTANCE_DOUBLE);
    }

    private static void onRegisterGuiLayers(RegisterGuiLayersEvent event)
    {
        BlockInteractOverlayLayer.init();

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
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_STAIRS, FramedStairsGeometry::new, FramedStairsBlock.STATE_MERGER);
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
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_LARGE_BUTTON, FramedLargeButtonGeometry::new, FramedLargeButtonBlock.LARGE_STATE_MERGER);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_LARGE_STONE_BUTTON, FramedLargeStoneButtonGeometry::create, FramedLargeButtonBlock.LARGE_STATE_MERGER);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_PILLAR, FramedPillarGeometry::new, WrapHelper.IGNORE_WATERLOGGED);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_HALF_PILLAR, FramedHalfPillarGeometry::new, WrapHelper.IGNORE_WATERLOGGED);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_GATE, FramedDoorGeometry::wood, FramedDoorBlock.DoorStateMerger.INSTANCE);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_IRON_GATE, FramedDoorGeometry::iron, FramedDoorBlock.DoorStateMerger.INSTANCE);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_BOOKSHELF, FramedBookshelfGeometry::normal, WrapHelper.IGNORE_SOLID);
        WrapHelper.wrap(FBContent.BLOCK_FRAMED_CHISELED_BOOKSHELF, FramedBookshelfGeometry::chiseled, WrapHelper.IGNORE_SOLID);
    }

    private static void onModelRegister(final ModelEvent.RegisterAdditional event)
    {
        event.register(FluidModel.BARE_MODEL);
        event.register(ReinforcementModel.LOCATION);
        event.register(FramedMarkedCubeGeometry.SLIME_FRAME_LOCATION);
        event.register(FramedMarkedCubeGeometry.REDSTONE_FRAME_LOCATION);
        event.register(FramedTargetGeometry.OVERLAY_LOCATION);
        event.register(FramedCollapsibleBlockGeometry.ALT_BASE_MODEL_LOC);
        event.register(FramedCollapsibleCopycatBlockGeometry.ALT_BASE_MODEL_LOC);
        event.register(FramedLanternGeometry.STANDING_CHAIN_LOCATION);
        event.register(FramedLanternGeometry.HANGING_CHAIN_LOCATION);
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

        FramedTankItemModel.wrap(registry);
    }

    private static void onModelsLoaded(final ModelEvent.BakingCompleted event)
    {
        StateLocationCache.clear();
        FluidCamoClientHandler.clearModelCache();
        ReinforcementModel.reload(event.getBakingResult().standaloneModels());
        FramedBlockModel.collectCubeBaseModels(event.getBakingResult().blockStateModels());

        ModelWrappingManager.printWrappingInfo(event.getBakingResult().blockStateModels());
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
                    case IFramedDoubleBlock ignored -> FramedClientDoubleBlockExtensions.INSTANCE;
                    default -> FramedClientBlockExtensions.INSTANCE;
                }))
                .forEach(pair -> event.registerBlock(pair.getSecond(), pair.getFirst()));

        event.registerItem(new TankClientItemExtensions(), FBContent.BLOCK_FRAMED_TANK.value().asItem());
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
        IBlockType type = block.getBlockType();
        return type != BlockType.FRAMED_FLOWER_POT && type != BlockType.FRAMED_TARGET;
    }
}
