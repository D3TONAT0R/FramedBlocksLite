package xfacthd.framedblocks.common;

import com.google.common.base.Preconditions;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.block.blockentity.FramedBlockEntity;
import xfacthd.framedblocks.api.block.blockentity.FramedDoubleBlockEntity;
import xfacthd.framedblocks.api.blueprint.BlueprintData;
import xfacthd.framedblocks.api.camo.CamoContainerFactory;
import xfacthd.framedblocks.api.camo.CamoList;
import xfacthd.framedblocks.api.camo.empty.EmptyCamoContainerFactory;
import xfacthd.framedblocks.api.component.FrameConfig;
import xfacthd.framedblocks.api.datagen.loot.objects.NonTrivialCamoLootCondition;
import xfacthd.framedblocks.api.datagen.loot.objects.SplitCamoLootFunction;
import xfacthd.framedblocks.api.block.IBlockType;
import xfacthd.framedblocks.api.util.FramedConstants;
import xfacthd.framedblocks.api.util.registration.DeferredAuxDataType;
import xfacthd.framedblocks.api.util.registration.DeferredBlockEntity;
import xfacthd.framedblocks.api.util.registration.DeferredDataComponentType;
import xfacthd.framedblocks.api.util.registration.DeferredLootFunction;
import xfacthd.framedblocks.api.util.registration.DeferredMenuType;
import xfacthd.framedblocks.api.util.registration.DeferredParticleType;
import xfacthd.framedblocks.api.util.registration.DeferredRecipeSerializer;
import xfacthd.framedblocks.api.util.registration.DeferredRecipeType;
import xfacthd.framedblocks.common.block.cube.*;
import xfacthd.framedblocks.common.block.door.*;
import xfacthd.framedblocks.common.block.interactive.*;
import xfacthd.framedblocks.common.block.interactive.button.*;
import xfacthd.framedblocks.common.block.interactive.pressureplate.*;
import xfacthd.framedblocks.common.block.pane.*;
import xfacthd.framedblocks.common.block.pillar.*;
import xfacthd.framedblocks.common.block.prism.*;
import xfacthd.framedblocks.common.block.rail.fancy.*;
import xfacthd.framedblocks.common.block.rail.vanillaslope.*;
import xfacthd.framedblocks.common.block.sign.*;
import xfacthd.framedblocks.common.block.slab.*;
import xfacthd.framedblocks.common.block.slope.*;
import xfacthd.framedblocks.common.block.slopeedge.*;
import xfacthd.framedblocks.common.block.slopepanel.*;
import xfacthd.framedblocks.common.block.slopepanelcorner.*;
import xfacthd.framedblocks.common.block.slopeslab.*;
import xfacthd.framedblocks.common.block.special.*;
import xfacthd.framedblocks.common.block.stairs.standard.*;
import xfacthd.framedblocks.common.block.stairs.vertical.*;
import xfacthd.framedblocks.common.block.torch.*;
import xfacthd.framedblocks.common.blockentity.doubled.prism.*;
import xfacthd.framedblocks.common.blockentity.doubled.rail.*;
import xfacthd.framedblocks.common.blockentity.doubled.slab.*;
import xfacthd.framedblocks.common.blockentity.doubled.slope.*;
import xfacthd.framedblocks.common.blockentity.doubled.slopeedge.*;
import xfacthd.framedblocks.common.blockentity.doubled.slopepanel.*;
import xfacthd.framedblocks.common.blockentity.doubled.slopepanelcorner.*;
import xfacthd.framedblocks.common.blockentity.doubled.slopeslab.*;
import xfacthd.framedblocks.common.blockentity.doubled.stairs.*;
import xfacthd.framedblocks.common.blockentity.special.*;
import xfacthd.framedblocks.common.compat.jei.camo.JeiCamoApplicationDummyIngredient;
import xfacthd.framedblocks.common.compat.jei.camo.JeiCamoApplicationRecipe;
import xfacthd.framedblocks.common.crafting.camo.CamoApplicationRecipe;
import xfacthd.framedblocks.common.crafting.rotation.ShapeRotationRecipe;
import xfacthd.framedblocks.common.crafting.saw.FramingSawRecipe;
import xfacthd.framedblocks.common.crafting.saw.FramingSawRecipeDisplay;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.FramedRegistries;
import xfacthd.framedblocks.common.data.FramedToolType;
import xfacthd.framedblocks.common.data.blueprint.auxdata.DoorAuxBlueprintData;
import xfacthd.framedblocks.common.data.camo.block.BlockCamoContainerFactory;
import xfacthd.framedblocks.common.data.camo.fluid.FluidCamoContainerFactory;
import xfacthd.framedblocks.common.data.component.AdjustableDoubleBlockData;
import xfacthd.framedblocks.common.data.component.CollapsibleBlockData;
import xfacthd.framedblocks.common.data.component.CollapsibleCopycatBlockData;
import xfacthd.framedblocks.common.data.component.FramedMap;
import xfacthd.framedblocks.common.data.component.PottedFlower;
import xfacthd.framedblocks.common.data.component.TargetColor;
import xfacthd.framedblocks.common.data.loot.BoardAdditionalItemCountNumberProvider;
import xfacthd.framedblocks.common.data.loot.LayeredCubeAdditionalItemCountNumberProvider;
import xfacthd.framedblocks.common.item.FramedBlueprintItem;
import xfacthd.framedblocks.common.item.FramedToolItem;
import xfacthd.framedblocks.common.item.PhantomPasteItem;
import xfacthd.framedblocks.common.menu.FramedStorageMenu;
import xfacthd.framedblocks.common.menu.FramingSawMenu;
import xfacthd.framedblocks.common.menu.PoweredFramingSawMenu;
import xfacthd.framedblocks.common.particle.FluidParticleOptions;
import xfacthd.framedblocks.common.util.FramedCreativeTab;
import xfacthd.framedblocks.common.util.registration.DeferredAuxDataTypeRegister;
import xfacthd.framedblocks.common.util.registration.DeferredBlockEntityRegister;
import xfacthd.framedblocks.common.util.registration.DeferredDataComponentTypeRegister;
import xfacthd.framedblocks.common.util.registration.DeferredLootFunctionRegister;
import xfacthd.framedblocks.common.util.registration.DeferredMenuTypeRegister;
import xfacthd.framedblocks.common.util.registration.DeferredParticleTypeRegister;
import xfacthd.framedblocks.common.util.registration.DeferredRecipeSerializerRegister;
import xfacthd.framedblocks.common.util.registration.DeferredRecipeTypeRegister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public final class FBContent
{
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FramedConstants.MOD_ID);
    private static final DeferredDataComponentTypeRegister DATA_COMPONENTS = DeferredDataComponentTypeRegister.create(FramedConstants.MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FramedConstants.MOD_ID);
    private static final DeferredBlockEntityRegister BE_TYPES = DeferredBlockEntityRegister.create(FramedConstants.MOD_ID);
    private static final DeferredMenuTypeRegister MENU_TYPES = DeferredMenuTypeRegister.create(FramedConstants.MOD_ID);
    private static final DeferredRecipeTypeRegister RECIPE_TYPES = DeferredRecipeTypeRegister.create(FramedConstants.MOD_ID);
    private static final DeferredRecipeSerializerRegister RECIPE_SERIALIZERS = DeferredRecipeSerializerRegister.create(FramedConstants.MOD_ID);
    private static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES = register(Registries.RECIPE_BOOK_CATEGORY);
    private static final DeferredRegister<RecipeDisplay.Type<?>> RECIPE_DISPLAY_TYPES = register(Registries.RECIPE_DISPLAY);
    private static final DeferredRegister<IngredientType<?>> INGREDIENT_TYPES = register(NeoForgeRegistries.Keys.INGREDIENT_TYPES);
    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = register(Registries.CREATIVE_MODE_TAB);
    private static final DeferredParticleTypeRegister PARTICLE_TYPES = DeferredParticleTypeRegister.create(FramedConstants.MOD_ID);
    private static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS = register(Registries.LOOT_CONDITION_TYPE);
    private static final DeferredLootFunctionRegister LOOT_FUNCTIONS = DeferredLootFunctionRegister.create(FramedConstants.MOD_ID);
    private static final DeferredRegister<LootNumberProviderType> LOOT_NUMBER_PROVIDERS = register(Registries.LOOT_NUMBER_PROVIDER_TYPE);
    private static final DeferredRegister<CamoContainerFactory<?>> CAMO_CONTAINER_FACTORIES = register(FramedConstants.CAMO_CONTAINER_FACTORY_REGISTRY_KEY);
    private static final DeferredAuxDataTypeRegister AUX_BLUEPRINT_DATA_TYPES = DeferredAuxDataTypeRegister.create(FramedConstants.MOD_ID);

    private static final Map<BlockType, Holder<Block>> BLOCKS_BY_TYPE = new EnumMap<>(BlockType.class);
    private static final Map<FramedToolType, Holder<Item>> TOOLS_BY_TYPE = new EnumMap<>(FramedToolType.class);
    private static final List<DeferredBlockEntity<? extends FramedBlockEntity>> FRAMED_BLOCK_ENTITIES = new ArrayList<>();
    private static final List<DeferredBlockEntity<? extends FramedDoubleBlockEntity>> DOUBLE_BLOCK_ENTITIES = new ArrayList<>();

    // region Blocks
    public static final Holder<Block> BLOCK_FRAMED_CUBE = registerBlock(FramedCubeBlock::new, BlockType.FRAMED_CUBE);
    public static final Holder<Block> BLOCK_FRAMED_SLAB = registerBlock(FramedSlabBlock::new, BlockType.FRAMED_SLAB);
    public static final Holder<Block> BLOCK_FRAMED_DOUBLE_SLAB = registerBlock(FramedDoubleSlabBlock::new, BlockType.FRAMED_DOUBLE_SLAB);
    public static final Holder<Block> BLOCK_FRAMED_SLAB_EDGE = registerBlock(FramedSlabEdgeBlock::new, BlockType.FRAMED_SLAB_EDGE);
    public static final Holder<Block> BLOCK_FRAMED_SLAB_CORNER = registerBlock(FramedSlabCornerBlock::new, BlockType.FRAMED_SLAB_CORNER);
    public static final Holder<Block> BLOCK_FRAMED_PANEL = registerBlock(FramedPanelBlock::new, BlockType.FRAMED_PANEL);
    public static final Holder<Block> BLOCK_FRAMED_DOUBLE_PANEL = registerBlock(FramedDoublePanelBlock::new, BlockType.FRAMED_DOUBLE_PANEL);
    public static final Holder<Block> BLOCK_FRAMED_CORNER_PILLAR = registerBlock(FramedCornerPillarBlock::new, BlockType.FRAMED_CORNER_PILLAR);
    public static final Holder<Block> BLOCK_FRAMED_STAIRS = registerBlock(FramedStairsBlock::new, BlockType.FRAMED_STAIRS);
    public static final Holder<Block> BLOCK_FRAMED_HALF_STAIRS = registerBlock(FramedHalfStairsBlock::new, BlockType.FRAMED_HALF_STAIRS);
    public static final Holder<Block> BLOCK_FRAMED_VERTICAL_STAIRS = registerBlock(FramedVerticalStairsBlock::new, BlockType.FRAMED_VERTICAL_STAIRS);
    public static final Holder<Block> BLOCK_FRAMED_VERTICAL_HALF_STAIRS = registerBlock(FramedVerticalHalfStairsBlock::new, BlockType.FRAMED_VERTICAL_HALF_STAIRS);
    public static final Holder<Block> BLOCK_FRAMED_WALL = registerBlock(FramedWallBlock::new, BlockType.FRAMED_WALL);
    public static final Holder<Block> BLOCK_FRAMED_FENCE = registerBlock(FramedFenceBlock::new, BlockType.FRAMED_FENCE);
    public static final Holder<Block> BLOCK_FRAMED_FENCE_GATE = registerBlock(FramedFenceGateBlock::new, BlockType.FRAMED_FENCE_GATE);
    public static final Holder<Block> BLOCK_FRAMED_DOOR = registerBlock(FramedDoorBlock::wood, BlockType.FRAMED_DOOR);
    public static final Holder<Block> BLOCK_FRAMED_IRON_DOOR = registerBlock(FramedDoorBlock::iron, BlockType.FRAMED_IRON_DOOR);
    public static final Holder<Block> BLOCK_FRAMED_TRAP_DOOR = registerBlock(FramedTrapDoorBlock::wood, BlockType.FRAMED_TRAPDOOR);
    public static final Holder<Block> BLOCK_FRAMED_IRON_TRAP_DOOR = registerBlock(FramedTrapDoorBlock::iron, BlockType.FRAMED_IRON_TRAPDOOR);
    public static final Holder<Block> BLOCK_FRAMED_PRESSURE_PLATE = registerBlock(FramedPressurePlateBlock::wood, BlockType.FRAMED_PRESSURE_PLATE);
    public static final Holder<Block> BLOCK_FRAMED_STONE_PRESSURE_PLATE = registerBlock(FramedPressurePlateBlock::stone, BlockType.FRAMED_STONE_PRESSURE_PLATE);
    public static final Holder<Block> BLOCK_FRAMED_BUTTON = registerBlock(FramedButtonBlock::wood, BlockType.FRAMED_BUTTON);
    public static final Holder<Block> BLOCK_FRAMED_STONE_BUTTON = registerBlock(FramedButtonBlock::stone, BlockType.FRAMED_STONE_BUTTON);
    public static final Holder<Block> BLOCK_FRAMED_LARGE_BUTTON = registerBlock(FramedLargeButtonBlock::largeWood, BlockType.FRAMED_LARGE_BUTTON);
    public static final Holder<Block> BLOCK_FRAMED_LARGE_STONE_BUTTON = registerBlock(FramedLargeButtonBlock::largeStone, BlockType.FRAMED_LARGE_STONE_BUTTON);
    public static final Holder<Block> BLOCK_FRAMED_BOARD = registerBlock(FramedBoardBlock::new, BlockType.FRAMED_BOARD);
    public static final Holder<Block> BLOCK_FRAMED_CORNER_STRIP = registerBlock(FramedCornerStripBlock::new, BlockType.FRAMED_CORNER_STRIP);
    public static final Holder<Block> BLOCK_FRAMED_PILLAR = registerBlock(FramedPillarBlock::new, BlockType.FRAMED_PILLAR);
    public static final Holder<Block> BLOCK_FRAMED_HALF_PILLAR = registerBlock(FramedHalfPillarBlock::new, BlockType.FRAMED_HALF_PILLAR);
    public static final Holder<Block> BLOCK_FRAMED_GATE = registerBlock(FramedGateBlock::wood, BlockType.FRAMED_GATE);
    public static final Holder<Block> BLOCK_FRAMED_IRON_GATE = registerBlock(FramedGateBlock::iron, BlockType.FRAMED_IRON_GATE);
    public static final Holder<Block> BLOCK_FRAMED_BOOKSHELF = registerBlock(FramedBookshelfBlock::new, BlockType.FRAMED_BOOKSHELF);
    public static final Holder<Block> BLOCK_FRAMED_CHISELED_BOOKSHELF = registerBlock(FramedChiseledBookshelfBlock::new, BlockType.FRAMED_CHISELED_BOOKSHELF);
    // endregion

    // region Special Blocks
    public static final Holder<Block> BLOCK_FRAMING_SAW = registerBlock("framing_saw", FramingSawBlock::new);
    // endregion

    // region DataComponentTypes
    public static final DeferredDataComponentType<CamoList> DC_TYPE_CAMO_LIST = DATA_COMPONENTS.registerComponentType(
            "camo_list",
            builder -> builder.persistent(CamoList.CODEC).networkSynchronized(CamoList.STREAM_CODEC).cacheEncoding()
    );
    public static final DeferredDataComponentType<FrameConfig> DC_TYPE_FRAME_CONFIG = DATA_COMPONENTS.registerComponentType(
            "frame_config",
            builder -> builder.persistent(FrameConfig.CODEC).networkSynchronized(FrameConfig.STREAM_CODEC)
    );
    public static final DeferredDataComponentType<BlueprintData> DC_TYPE_BLUEPRINT_DATA = DATA_COMPONENTS.registerComponentType(
            "blueprint_data",
            builder -> builder.persistent(BlueprintData.CODEC).networkSynchronized(BlueprintData.STREAM_CODEC).cacheEncoding()
    );
    public static final DeferredDataComponentType<FramedMap> DC_TYPE_FRAMED_MAP = DATA_COMPONENTS.registerComponentType(
            "framed_map",
            builder -> builder.persistent(FramedMap.CODEC).networkSynchronized(FramedMap.STREAM_CODEC)
    );
    public static final DeferredDataComponentType<CollapsibleBlockData> DC_TYPE_COLLAPSIBLE_BLOCK_DATA = DATA_COMPONENTS.registerComponentType(
            "collapsible_block",
            builder -> builder.persistent(CollapsibleBlockData.CODEC).networkSynchronized(CollapsibleBlockData.STREAM_CODEC)
    );
    public static final DeferredDataComponentType<CollapsibleCopycatBlockData> DC_TYPE_COLLAPSIBLE_COPYCAT_BLOCK_DATA = DATA_COMPONENTS.registerComponentType(
            "collapsible_copycat_block",
            builder -> builder.persistent(CollapsibleCopycatBlockData.CODEC).networkSynchronized(CollapsibleCopycatBlockData.STREAM_CODEC)
    );
    public static final DeferredDataComponentType<PottedFlower> DC_TYPE_POTTED_FLOWER = DATA_COMPONENTS.registerComponentType(
            "potted_flower",
            builder -> builder.persistent(PottedFlower.CODEC).networkSynchronized(PottedFlower.STREAM_CODEC)
    );
    public static final DeferredDataComponentType<TargetColor> DC_TYPE_TARGET_COLOR = DATA_COMPONENTS.registerComponentType(
            "target_color",
            builder -> builder.persistent(TargetColor.CODEC).networkSynchronized(TargetColor.STREAM_CODEC)
    );
    public static final DeferredDataComponentType<AdjustableDoubleBlockData> DC_TYPE_ADJ_DOUBLE_BLOCK_DATA = DATA_COMPONENTS.registerComponentType(
            "adjustable_double_block",
            builder -> builder.persistent(AdjustableDoubleBlockData.CODEC).networkSynchronized(AdjustableDoubleBlockData.STREAM_CODEC)
    );
    public static final DeferredDataComponentType<SimpleFluidContent> DC_TYPE_TANK_CONTENTS = DATA_COMPONENTS.registerComponentType(
            "tank_contents",
            builder -> builder.persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC)
    );
    // endregion

    // region Items
    public static final Holder<Item> ITEM_FRAMED_HAMMER = registerToolItem(FramedToolItem::new, FramedToolType.HAMMER);
    public static final Holder<Item> ITEM_FRAMED_WRENCH = registerToolItem(FramedToolItem::new, FramedToolType.WRENCH);
    public static final Holder<Item> ITEM_FRAMED_BLUEPRINT = registerToolItem(FramedBlueprintItem::new, FramedToolType.BLUEPRINT);
    public static final Holder<Item> ITEM_FRAMED_KEY = registerToolItem(FramedToolItem::new, FramedToolType.KEY);
    public static final Holder<Item> ITEM_FRAMED_SCREWDRIVER = registerToolItem(FramedToolItem::new, FramedToolType.SCREWDRIVER);
    public static final Holder<Item> ITEM_FRAMED_REINFORCEMENT = ITEMS.registerSimpleItem("framed_reinforcement");
    public static final Holder<Item> ITEM_PHANTOM_PASTE = ITEMS.registerItem("phantom_paste", PhantomPasteItem::new);
    public static final Holder<Item> ITEM_GLOW_PASTE = ITEMS.registerSimpleItem("glow_paste");
    // endregion

    // region BlockEntityTypes
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_BLOCK = registerBlockEntity(
            getDefaultBlockEntityFactory(),
            "framed_tile",
            getDefaultEntityBlocks(),
            true
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_DOUBLE_BLOCK = registerBlockEntity(
            getDefaultDoubleBlockEntityFactory(),
            "framed_double_tile",
            getDefaultDoubleEntityBlocks(),
            true
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_DOUBLE_FRAMED_SLOPE = registerBlockEntity(
            FramedDoubleSlopeBlockEntity::new,
            BlockType.FRAMED_DOUBLE_SLOPE
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_DOUBLE_HALF_SLOPE = registerBlockEntity(
            FramedDoubleHalfSlopeBlockEntity::new,
            BlockType.FRAMED_DOUBLE_HALF_SLOPE
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_VERTICAL_DOUBLE_HALF_SLOPE = registerBlockEntity(
            FramedVerticalDoubleHalfSlopeBlockEntity::new,
            BlockType.FRAMED_VERTICAL_DOUBLE_HALF_SLOPE
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_DOUBLE_FRAMED_CORNER = registerBlockEntity(
            FramedDoubleCornerBlockEntity::new,
            BlockType.FRAMED_DOUBLE_CORNER
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_DOUBLE_FRAMED_THREEWAY_CORNER = registerBlockEntity(
            FramedDoubleThreewayCornerBlockEntity::new,
            BlockType.FRAMED_DOUBLE_THREEWAY_CORNER, BlockType.FRAMED_DOUBLE_PRISM_CORNER
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_ELEVATED_DOUBLE_SLOPE_EDGE = registerBlockEntity(
            FramedElevatedDoubleSlopeEdgeBlockEntity::new,
            BlockType.FRAMED_ELEVATED_DOUBLE_SLOPE_EDGE
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_ELEVATED_DOUBLE_CORNER_SLOPE_EDGE = registerBlockEntity(
            FramedElevatedDoubleCornerSlopeEdgeBlockEntity::new,
            BlockType.FRAMED_ELEV_DOUBLE_CORNER_SLOPE_EDGE
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_ELEVATED_DOUBLE_INNER_CORNER_SLOPE_EDGE = registerBlockEntity(
            FramedElevatedDoubleInnerCornerSlopeEdgeBlockEntity::new,
            BlockType.FRAMED_ELEV_DOUBLE_INNER_CORNER_SLOPE_EDGE
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_ADJ_DOUBLE_BLOCK = registerBlockEntity(
            FramedAdjustableDoubleBlockEntity::standard,
            BlockType.FRAMED_ADJ_DOUBLE_SLAB, BlockType.FRAMED_ADJ_DOUBLE_PANEL
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_ADJ_DOUBLE_COPYCAT_BLOCK = registerBlockEntity(
            FramedAdjustableDoubleBlockEntity::copycat,
            BlockType.FRAMED_ADJ_DOUBLE_COPYCAT_SLAB, BlockType.FRAMED_ADJ_DOUBLE_COPYCAT_PANEL
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_SLOPED_DOUBLE_STAIRS = registerBlockEntity(
            FramedSlopedDoubleStairsBlockEntity::new,
            BlockType.FRAMED_SLOPED_DOUBLE_STAIRS
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_SLICED_SLOPED_DOUBLE_STAIRS_SLAB = registerBlockEntity(
            FramedSlicedSlopedStairsSlabBlockEntity::new,
            BlockType.FRAMED_SLICED_SLOPED_STAIRS_SLAB
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_SLICED_SLOPED_DOUBLE_STAIRS_SLOPE = registerBlockEntity(
            FramedSlicedSlopedStairsSlopeBlockEntity::new,
            BlockType.FRAMED_SLICED_SLOPED_STAIRS_SLOPE
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_VERTICAL_SLOPED_DOUBLE_STAIRS = registerBlockEntity(
            FramedVerticalSlopedDoubleStairsBlockEntity::new,
            BlockType.FRAMED_VERTICAL_SLOPED_DOUBLE_STAIRS
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_VERTICAL_SLICED_SLOPED_DOUBLE_STAIRS_PANEL = registerBlockEntity(
            FramedVerticalSlicedSlopedStairsPanelBlockEntity::new,
            BlockType.FRAMED_VERTICAL_SLICED_SLOPED_STAIRS_PANEL
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_VERTICAL_SLICED_SLOPED_DOUBLE_STAIRS_SLOPE = registerBlockEntity(
            FramedVerticalSlicedSlopedStairsSlopeBlockEntity::new,
            BlockType.FRAMED_VERTICAL_SLICED_SLOPED_STAIRS_SLOPE
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_DOOR = registerBlockEntity(
            FramedDoorBlockEntity::new,
            BlockType.FRAMED_DOOR, BlockType.FRAMED_IRON_DOOR
    );
    public static final DeferredBlockEntity<FramedSignBlockEntity> BE_TYPE_FRAMED_SIGN = registerBlockEntity(
            FramedSignBlockEntity::normalSign,
            true,
            BlockType.FRAMED_SIGN, BlockType.FRAMED_WALL_SIGN
    );
    public static final DeferredBlockEntity<FramedSignBlockEntity> BE_TYPE_FRAMED_HANGING_SIGN = registerBlockEntity(
            FramedSignBlockEntity::hangingSign,
            true,
            BlockType.FRAMED_HANGING_SIGN, BlockType.FRAMED_WALL_HANGING_SIGN
    );
    public static final DeferredBlockEntity<FramedChestBlockEntity> BE_TYPE_FRAMED_CHEST = registerBlockEntity(
            FramedChestBlockEntity::new,
            BlockType.FRAMED_CHEST
    );
    public static final DeferredBlockEntity<FramedStorageBlockEntity> BE_TYPE_FRAMED_SECRET_STORAGE = registerBlockEntity(
            FramedStorageBlockEntity::new,
            BlockType.FRAMED_SECRET_STORAGE
    );
    public static final DeferredBlockEntity<FramedTankBlockEntity> BE_TYPE_FRAMED_TANK = registerBlockEntity(
            FramedTankBlockEntity::new,
            BlockType.FRAMED_TANK
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_FANCY_RAIL_SLOPE = registerBlockEntity(
            FramedFancyRailSlopeBlockEntity::new,
            BlockType.FRAMED_FANCY_RAIL_SLOPE,
            BlockType.FRAMED_FANCY_POWERED_RAIL_SLOPE,
            BlockType.FRAMED_FANCY_DETECTOR_RAIL_SLOPE,
            BlockType.FRAMED_FANCY_ACTIVATOR_RAIL_SLOPE
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_FLOWER_POT = registerBlockEntity(
            FramedFlowerPotBlockEntity::new,
            BlockType.FRAMED_FLOWER_POT
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_COLLAPSIBLE_BLOCK = registerBlockEntity(
            FramedCollapsibleBlockEntity::new,
            BlockType.FRAMED_COLLAPSIBLE_BLOCK
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_COLLAPSIBLE_COPYCAT_BLOCK = registerBlockEntity(
            FramedCollapsibleCopycatBlockEntity::new,
            BlockType.FRAMED_COLLAPSIBLE_COPYCAT_BLOCK
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_ELEVATED_DOUBLE_PRISM = registerBlockEntity(
            FramedElevatedDoublePrismBlockEntity::new,
            BlockType.FRAMED_ELEVATED_INNER_DOUBLE_PRISM
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_ELEVATED_DOUBLE_SLOPED_PRISM = registerBlockEntity(
            FramedElevatedDoubleSlopedPrismBlockEntity::new,
            BlockType.FRAMED_ELEVATED_INNER_DOUBLE_SLOPED_PRISM
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_DOUBLE_SLOPE_SLAB = registerBlockEntity(
            FramedDoubleSlopeSlabBlockEntity::new,
            BlockType.FRAMED_DOUBLE_SLOPE_SLAB
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_ELEVATED_DOUBLE_SLOPE_SLAB = registerBlockEntity(
            FramedElevatedDoubleSlopeSlabBlockEntity::new,
            BlockType.FRAMED_ELEVATED_DOUBLE_SLOPE_SLAB
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_FLAT_DOUBLE_SLOPE_SLAB_CORNER = registerBlockEntity(
            FramedFlatDoubleSlopeSlabCornerBlockEntity::new,
            BlockType.FRAMED_FLAT_DOUBLE_SLOPE_SLAB_CORNER
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_FLAT_ELEVATED_DOUBLE_SLOPE_SLAB_CORNER = registerBlockEntity(
            FramedFlatElevatedDoubleSlopeSlabCornerBlockEntity::new,
            BlockType.FRAMED_FLAT_ELEV_DOUBLE_SLOPE_SLAB_CORNER, BlockType.FRAMED_FLAT_ELEV_INNER_DOUBLE_SLOPE_SLAB_CORNER
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_DOUBLE_SLOPE_PANEL = registerBlockEntity(
            FramedDoubleSlopePanelBlockEntity::new,
            BlockType.FRAMED_DOUBLE_SLOPE_PANEL
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_EXTENDED_DOUBLE_SLOPE_PANEL = registerBlockEntity(
            FramedExtendedDoubleSlopePanelBlockEntity::new,
            BlockType.FRAMED_EXTENDED_DOUBLE_SLOPE_PANEL
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_FLAT_DOUBLE_SLOPE_PANEL_CORNER = registerBlockEntity(
            FramedFlatDoubleSlopePanelCornerBlockEntity::new,
            BlockType.FRAMED_FLAT_DOUBLE_SLOPE_PANEL_CORNER
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_FLAT_EXTENDED_DOUBLE_SLOPE_PANEL_CORNER = registerBlockEntity(
            FramedFlatExtendedDoubleSlopePanelCornerBlockEntity::new,
            BlockType.FRAMED_FLAT_EXT_DOUBLE_SLOPE_PANEL_CORNER, BlockType.FRAMED_FLAT_EXT_INNER_DOUBLE_SLOPE_PANEL_CORNER
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_SMALL_DOUBLE_CORNER_SLOPE_PANEL = registerBlockEntity(
            FramedSmallDoubleCornerSlopePanelBlockEntity::new,
            BlockType.FRAMED_SMALL_DOUBLE_CORNER_SLOPE_PANEL
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_SMALL_DOUBLE_CORNER_SLOPE_PANEL_WALL = registerBlockEntity(
            FramedSmallDoubleCornerSlopePanelWallBlockEntity::new,
            BlockType.FRAMED_SMALL_DOUBLE_CORNER_SLOPE_PANEL_W
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_LARGE_DOUBLE_CORNER_SLOPE_PANEL = registerBlockEntity(
            FramedLargeDoubleCornerSlopePanelBlockEntity::new,
            BlockType.FRAMED_LARGE_DOUBLE_CORNER_SLOPE_PANEL
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_LARGE_DOUBLE_CORNER_SLOPE_PANEL_WALL = registerBlockEntity(
            FramedLargeDoubleCornerSlopePanelWallBlockEntity::new,
            BlockType.FRAMED_LARGE_DOUBLE_CORNER_SLOPE_PANEL_W
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_EXTENDED_DOUBLE_CORNER_SLOPE_PANEL = registerBlockEntity(
            FramedExtendedDoubleCornerSlopePanelBlockEntity::new,
            BlockType.FRAMED_EXT_DOUBLE_CORNER_SLOPE_PANEL
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_EXTENDED_DOUBLE_CORNER_SLOPE_PANEL_WALL = registerBlockEntity(
            FramedExtendedDoubleCornerSlopePanelWallBlockEntity::new,
            BlockType.FRAMED_EXT_DOUBLE_CORNER_SLOPE_PANEL_W
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_EXTENDED_INNER_DOUBLE_CORNER_SLOPE_PANEL = registerBlockEntity(
            FramedExtendedInnerDoubleCornerSlopePanelBlockEntity::new,
            BlockType.FRAMED_EXT_INNER_DOUBLE_CORNER_SLOPE_PANEL
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_EXTENDED_INNER_DOUBLE_CORNER_SLOPE_PANEL_WALL = registerBlockEntity(
            FramedExtendedInnerDoubleCornerSlopePanelWallBlockEntity::new,
            BlockType.FRAMED_EXT_INNER_DOUBLE_CORNER_SLOPE_PANEL_W
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_TARGET = registerBlockEntity(
            FramedTargetBlockEntity::new,
            BlockType.FRAMED_TARGET
    );
    public static final DeferredBlockEntity<FramedItemFrameBlockEntity> BE_TYPE_FRAMED_ITEM_FRAME = registerBlockEntity(
            FramedItemFrameBlockEntity::new,
            BlockType.FRAMED_ITEM_FRAME, BlockType.FRAMED_GLOWING_ITEM_FRAME
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_OWNABLE_BLOCK = registerBlockEntity(
            FramedOwnableBlockEntity::new,
            BlockType.FRAMED_ONE_WAY_WINDOW
    );
    public static final DeferredBlockEntity<FramedChiseledBookshelfBlockEntity> BE_TYPE_FRAMED_CHISELED_BOOKSHELF = registerBlockEntity(
            FramedChiseledBookshelfBlockEntity::new,
            BlockType.FRAMED_CHISELED_BOOKSHELF
    );
    public static final DeferredBlockEntity<FramedHopperBlockEntity> BE_TYPE_FRAMED_HOPPER = registerBlockEntity(
            FramedHopperBlockEntity::new,
            BlockType.FRAMED_HOPPER
    );
    // endregion

    // region Special BlockEntities
    public static final DeferredBlockEntity<PoweredFramingSawBlockEntity> BE_TYPE_POWERED_FRAMING_SAW = registerBlockEntity(
            PoweredFramingSawBlockEntity::new,
            "powered_framing_saw",
            () -> Set.of(BLOCK_POWERED_FRAMING_SAW.value()),
            false
    );
    // endregion

    // region MenuTypes
    public static final DeferredMenuType<FramingSawMenu> MENU_TYPE_FRAMING_SAW = MENU_TYPES.registerSimpleMenuType(
            "framing_saw", FramingSawMenu::createClient
    );
    // endregion

    // region RecipeTypes
    public static final DeferredRecipeType<FramingSawRecipe> RECIPE_TYPE_FRAMING_SAW_RECIPE = registerRecipeType("frame");
    // endregion

    // region RecipeSerializers
    public static final DeferredRecipeSerializer<FramingSawRecipe> RECIPE_SERIALIZER_FRAMING_SAW_RECIPE = registerRecipeSerializer(
            "frame", FramingSawRecipe.CODEC, FramingSawRecipe.STREAM_CODEC
    );
    public static final DeferredRecipeSerializer<CamoApplicationRecipe> RECIPE_SERIALIZER_APPLY_CAMO = registerRecipeSerializer(
            "apply_camo", CamoApplicationRecipe.CODEC, CamoApplicationRecipe.STREAM_CODEC
    );
    public static final DeferredRecipeSerializer<JeiCamoApplicationRecipe> RECIPE_SERIALIZER_JEI_CAMO = registerRecipeSerializer(
            "jei_camo", JeiCamoApplicationRecipe.CODEC, JeiCamoApplicationRecipe.STREAM_CODEC
    );
    public static final DeferredRecipeSerializer<ShapeRotationRecipe> RECIPE_SERIALIZER_SHAPE_ROTATION = registerRecipeSerializer(
            "rotate_shape", ShapeRotationRecipe.CODEC, ShapeRotationRecipe.STREAM_CODEC
    );
    // endregion

    // region RecipeBookCategories
    public static final Holder<RecipeBookCategory> RECIPE_BOOK_CATEGORY_FRAMING_SAW = RECIPE_BOOK_CATEGORIES.register(
            "framing_saw", RecipeBookCategory::new
    );
    // endregion

    // region RecipeDisplay.Types
    public static final Holder<RecipeDisplay.Type<?>> RECIPE_DISPLAY_TYPE_FRAMING_SAW = RECIPE_DISPLAY_TYPES.register(
            "framing_saw", () -> new RecipeDisplay.Type<>(FramingSawRecipeDisplay.CODEC, FramingSawRecipeDisplay.STREAM_CODEC)
    );
    // endregion

    // region IngredientTypes
    public static final Holder<IngredientType<?>> INGREDIENT_TYPE_JEI_CAMO_DUMMY = INGREDIENT_TYPES.register(
            "jei_camo_dummy", () -> new IngredientType<>(JeiCamoApplicationDummyIngredient.CODEC, JeiCamoApplicationDummyIngredient.STREAM_CODEC)
    );
    // endregion

    // region CreativeModeTabs
    public static final Holder<CreativeModeTab> MAIN_TAB = CREATIVE_TABS.register(
            "framed_blocks", FramedCreativeTab::makeTab
    );
    // endregion

    // region ParticleTypes
    public static final DeferredParticleType<FluidParticleOptions> FLUID_PARTICLE = PARTICLE_TYPES.registerParticleType(
            "fluid", false, FluidParticleOptions.CODEC, FluidParticleOptions.STREAM_CODEC
    );
    // endregion

    // region LootItemConditions
    public static final Holder<LootItemConditionType> NON_TRIVIAL_CAMO_LOOT_CONDITION = registerLootCondition(
            "non_trivial_camo", NonTrivialCamoLootCondition.MAP_CODEC
    );
    // endregion

    // region LootItemFunctions
    public static final DeferredLootFunction<SplitCamoLootFunction> SPLIT_CAMO_LOOT_FUNCTION = LOOT_FUNCTIONS.registerLootFunction(
            "split_camo", SplitCamoLootFunction.MAP_CODEC
    );
    // endregion

    // region LootNumberProviderTypes
    public static final Holder<LootNumberProviderType> BOARD_ADDITIONAL_ITEM_COUNT_NUMBER_PROVIDER = registerLootNumberProvider(
            "board", MapCodec.unit(BoardAdditionalItemCountNumberProvider.INSTANCE)
    );
    public static final Holder<LootNumberProviderType> LAYERED_CUBE_ADDITIONAL_ITEM_COUNT_NUMBER_PROVIDER = registerLootNumberProvider(
            "layered_cube", MapCodec.unit(LayeredCubeAdditionalItemCountNumberProvider.INSTANCE)
    );
    // endregion

    // region CamoContainer.Factories
    public static final DeferredHolder<CamoContainerFactory<?>, EmptyCamoContainerFactory> FACTORY_EMPTY = CAMO_CONTAINER_FACTORIES.register(
            "empty", EmptyCamoContainerFactory::new
    );
    public static final DeferredHolder<CamoContainerFactory<?>, BlockCamoContainerFactory> FACTORY_BLOCK = CAMO_CONTAINER_FACTORIES.register(
            "block", BlockCamoContainerFactory::new
    );
    public static final DeferredHolder<CamoContainerFactory<?>, FluidCamoContainerFactory> FACTORY_FLUID = CAMO_CONTAINER_FACTORIES.register(
            "fluid", FluidCamoContainerFactory::new
    );
    // endregion

    // region AuxBlueprintData.Types
    public static final DeferredAuxDataType<DoorAuxBlueprintData> AUX_TYPE_DOOR_DATA = AUX_BLUEPRINT_DATA_TYPES.registerAuxDataType(
            "door", DoorAuxBlueprintData.CODEC, DoorAuxBlueprintData.STREAM_CODEC
    );
    public static final DeferredAuxDataType<TargetColor> AUX_TYPE_TARGET_COLOR = AUX_BLUEPRINT_DATA_TYPES.registerAuxDataType(
            "target_color", TargetColor.MAP_CODEC, TargetColor.STREAM_CODEC
    );
    // endregion



    public static void init(IEventBus modBus)
    {
        modBus.addListener(FramedRegistries::onRegisterNewRegistries);

        BLOCKS.register(modBus);
        DATA_COMPONENTS.register(modBus);
        ITEMS.register(modBus);
        BE_TYPES.register(modBus);
        MENU_TYPES.register(modBus);
        RECIPE_TYPES.register(modBus);
        RECIPE_SERIALIZERS.register(modBus);
        RECIPE_BOOK_CATEGORIES.register(modBus);
        RECIPE_DISPLAY_TYPES.register(modBus);
        INGREDIENT_TYPES.register(modBus);
        CREATIVE_TABS.register(modBus);
        PARTICLE_TYPES.register(modBus);
        LOOT_CONDITIONS.register(modBus);
        LOOT_FUNCTIONS.register(modBus);
        LOOT_NUMBER_PROVIDERS.register(modBus);
        CAMO_CONTAINER_FACTORIES.register(modBus);
        AUX_BLUEPRINT_DATA_TYPES.register(modBus);

        DOUBLE_BLOCK_ENTITIES.add((DeferredBlockEntity<? extends FramedDoubleBlockEntity>) BE_TYPE_FRAMED_DOUBLE_BLOCK);
    }

    public static Collection<DeferredHolder<Block, ? extends Block>> getRegisteredBlocks()
    {
        return BLOCKS.getEntries();
    }

    public static Block byType(BlockType type)
    {
        return BLOCKS_BY_TYPE.get(type).value();
    }

    public static Collection<DeferredHolder<Item, ? extends Item>> getRegisteredItems()
    {
        return ITEMS.getEntries();
    }

    public static Item toolByType(FramedToolType type)
    {
        return TOOLS_BY_TYPE.get(type).value();
    }

    public static List<DeferredBlockEntity<? extends FramedBlockEntity>> getBlockEntities()
    {
        return FRAMED_BLOCK_ENTITIES;
    }

    public static List<DeferredBlockEntity<? extends FramedDoubleBlockEntity>> getDoubleBlockEntities()
    {
        return DOUBLE_BLOCK_ENTITIES;
    }

    public static BlockEntityType.BlockEntitySupplier<FramedBlockEntity> getDefaultBlockEntityFactory()
    {
        return (pos, state) -> new FramedBlockEntity(BE_TYPE_FRAMED_BLOCK.value(), pos, state);
    }

    public static BlockEntityType.BlockEntitySupplier<FramedBlockEntity> getDefaultDoubleBlockEntityFactory()
    {
        return (pos, state) -> new FramedDoubleBlockEntity(BE_TYPE_FRAMED_DOUBLE_BLOCK.value(), pos, state);
    }

    private static Supplier<Set<Block>> getDefaultEntityBlocks()
    {
        return () -> BLOCKS.getEntries()
                .stream()
                .map(Holder::value)
                .filter(block -> block instanceof IFramedBlock)
                .filter(makeBlockEntityBlockPredicate(false))
                .collect(Collectors.toSet());
    }

    private static Supplier<Set<Block>> getDefaultDoubleEntityBlocks()
    {
        return () -> BLOCKS.getEntries()
                .stream()
                .map(Holder::value)
                .filter(block -> block instanceof IFramedBlock)
                .filter(makeBlockEntityBlockPredicate(true))
                .collect(Collectors.toSet());
    }

    private static Predicate<Block> makeBlockEntityBlockPredicate(boolean _double)
    {
        return block ->
        {
            IBlockType type = ((IFramedBlock) block).getBlockType();
            return !type.hasSpecialTile() && (type.isDoubleBlock() == _double);
        };
    }

    private static <T extends Block & IFramedBlock> Holder<Block> registerBlock(
            FramedBlockFactory<T> blockFactory, BlockType type
    )
    {
        return registerBlock(props -> blockFactory.create(type, props), type);
    }

    private static <T extends Block & IFramedBlock> Holder<Block> registerBlock(
            Function<BlockBehaviour.Properties, T> blockFactory, BlockType type
    )
    {
        Holder<Block> result = BLOCKS.registerBlock(type.getName(), props ->
        {
            T block = blockFactory.apply(props);
            Preconditions.checkArgument(block.getBlockType() == type);
            return block;
        }, BlockBehaviour.Properties.of());
        BLOCKS_BY_TYPE.put(type, result);

        if (type.hasBlockItem())
        {
            ITEMS.registerItem(type.getName(), props ->
                    ((IFramedBlock) result.value()).createBlockItem(props.useBlockDescriptionPrefix())
            );
        }

        return result;
    }

    @SuppressWarnings("SameParameterValue")
    private static Holder<Block> registerBlock(String name, Function<BlockBehaviour.Properties, ? extends Block> blockFactory)
    {
        Holder<Block> result = BLOCKS.registerBlock(name, blockFactory, BlockBehaviour.Properties.of());
        ITEMS.registerSimpleBlockItem(result);
        return result;
    }

    private static Holder<Item> registerToolItem(ToolItemFactory itemFactory, FramedToolType type)
    {
        Holder<Item> result = ITEMS.registerItem(type.getName(), props -> itemFactory.create(type, props));
        TOOLS_BY_TYPE.put(type, result);
        return result;
    }

    private static <T extends BlockEntity> DeferredBlockEntity<T> registerBlockEntity(
            BlockEntityType.BlockEntitySupplier<T> factory, BlockType... types
    )
    {
        return registerBlockEntity(factory, false, types);
    }

    private static <T extends BlockEntity> DeferredBlockEntity<T> registerBlockEntity(
            BlockEntityType.BlockEntitySupplier<T> factory, boolean opOnlyNbt, BlockType... types
    )
    {
        Supplier<Set<Block>> blocks = () -> Arrays.stream(types)
                .map(BLOCKS_BY_TYPE::get)
                .map(Holder::value)
                .collect(Collectors.toSet());

        DeferredBlockEntity<T> result = registerBlockEntity(factory, types[0].getName(), blocks, true, opOnlyNbt);
        if (!FMLEnvironment.production && Arrays.stream(types).anyMatch(BlockType::isDoubleBlock))
        {
            storeBlockEntityType(DOUBLE_BLOCK_ENTITIES, result);
        }
        return result;
    }

    private static <T extends BlockEntity> DeferredBlockEntity<T> registerBlockEntity(
            BlockEntityType.BlockEntitySupplier<T> factory, String name, Supplier<Set<Block>> blocks, boolean isFramedBE
    )
    {
        return registerBlockEntity(factory, name, blocks, isFramedBE, false);
    }

    private static <T extends BlockEntity> DeferredBlockEntity<T> registerBlockEntity(
            BlockEntityType.BlockEntitySupplier<T> factory, String name, Supplier<Set<Block>> blocks, boolean isFramedBE, boolean opOnlyNbt
    )
    {
        DeferredBlockEntity<T> result = BE_TYPES.registerBlockEntity(name, factory, blocks, opOnlyNbt);
        if (isFramedBE)
        {
            storeBlockEntityType(FRAMED_BLOCK_ENTITIES, result);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private static <T extends BlockEntity> void storeBlockEntityType(List<DeferredBlockEntity<? extends T>> list, DeferredBlockEntity<?> type)
    {
        list.add((DeferredBlockEntity<T>) type);
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends Recipe<?>> DeferredRecipeType<T> registerRecipeType(String name)
    {
        return RECIPE_TYPES.registerRecipeType(name);
    }

    private static <T extends Recipe<?>> DeferredRecipeSerializer<T> registerRecipeSerializer(
            String name, MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec
    )
    {
        return RECIPE_SERIALIZERS.registerRecipeSerializer(name, codec, streamCodec);
    }

    @SuppressWarnings("SameParameterValue")
    private static Holder<LootItemConditionType> registerLootCondition(String name, MapCodec<? extends LootItemCondition> codec)
    {
        return LOOT_CONDITIONS.register(name, () -> new LootItemConditionType(codec));
    }

    private static Holder<LootNumberProviderType> registerLootNumberProvider(String name, MapCodec<? extends NumberProvider> codec)
    {
        return LOOT_NUMBER_PROVIDERS.register(name, () -> new LootNumberProviderType(codec));
    }

    private static <T> DeferredRegister<T> register(ResourceKey<Registry<T>> key)
    {
        return DeferredRegister.create(key, FramedConstants.MOD_ID);
    }

    @FunctionalInterface
    private interface FramedBlockFactory<T extends Block & IFramedBlock>
    {
        T create(BlockType type, BlockBehaviour.Properties properties);
    }

    @FunctionalInterface
    private interface ToolItemFactory
    {
        Item create(FramedToolType type, Item.Properties properties);
    }



    private FBContent() { }
}
