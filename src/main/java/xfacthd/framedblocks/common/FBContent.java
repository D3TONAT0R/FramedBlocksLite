package xfacthd.framedblocks.common;

import com.google.common.base.Preconditions;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xfacthd.framedblocks.api.block.blockentity.FramedBlockEntity;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.blueprint.AuxBlueprintData;
import xfacthd.framedblocks.api.camo.*;
import xfacthd.framedblocks.api.camo.empty.EmptyCamoContainerFactory;
import xfacthd.framedblocks.api.component.FrameConfig;
import xfacthd.framedblocks.api.util.*;
import xfacthd.framedblocks.api.util.registration.*;
import xfacthd.framedblocks.common.block.interactive.button.*;
import xfacthd.framedblocks.common.block.interactive.pressureplate.*;
import xfacthd.framedblocks.common.block.rail.fancy.*;
import xfacthd.framedblocks.common.block.rail.vanillaslope.*;
import xfacthd.framedblocks.common.block.sign.*;
import xfacthd.framedblocks.common.block.slopeedge.*;
import xfacthd.framedblocks.common.block.special.FramingSawBlock;
import xfacthd.framedblocks.common.block.special.PoweredFramingSawBlock;
import xfacthd.framedblocks.common.block.cube.*;
import xfacthd.framedblocks.common.block.door.*;
import xfacthd.framedblocks.common.block.interactive.*;
import xfacthd.framedblocks.common.block.interactive.button.*;
import xfacthd.framedblocks.common.block.interactive.pressureplate.*;
import xfacthd.framedblocks.common.block.pillar.*;
import xfacthd.framedblocks.common.block.prism.*;
import xfacthd.framedblocks.common.block.slab.*;
import xfacthd.framedblocks.common.block.slope.*;
import xfacthd.framedblocks.common.block.slopepanel.*;
import xfacthd.framedblocks.common.block.slopepanelcorner.*;
import xfacthd.framedblocks.common.block.slopeslab.*;
import xfacthd.framedblocks.common.block.special.*;
import xfacthd.framedblocks.common.block.stairs.standard.*;
import xfacthd.framedblocks.common.block.stairs.vertical.*;
import xfacthd.framedblocks.common.blockentity.special.*;
import xfacthd.framedblocks.common.compat.jei.camo.JeiCamoApplicationRecipe;
import xfacthd.framedblocks.common.crafting.CamoApplicationRecipe;
import xfacthd.framedblocks.common.crafting.FramingSawRecipe;
import xfacthd.framedblocks.common.crafting.ShapeRotationRecipe;
import xfacthd.framedblocks.common.data.*;
import xfacthd.framedblocks.common.data.blueprint.auxdata.DoorAuxBlueprintData;
import xfacthd.framedblocks.common.data.camo.block.BlockCamoContainerFactory;
import xfacthd.framedblocks.common.data.camo.fluid.FluidCamoContainerFactory;
import xfacthd.framedblocks.api.blueprint.BlueprintData;
import xfacthd.framedblocks.common.data.component.*;
import xfacthd.framedblocks.api.datagen.loot.objects.NonTrivialCamoLootCondition;
import xfacthd.framedblocks.api.datagen.loot.objects.SplitCamoLootFunction;
import xfacthd.framedblocks.common.data.component.FramedMap;
import xfacthd.framedblocks.common.data.loot.BoardAdditionalItemCountNumberProvider;
import xfacthd.framedblocks.common.data.loot.LayeredCubeAdditionalItemCountNumberProvider;
import xfacthd.framedblocks.common.item.*;
import xfacthd.framedblocks.common.menu.*;
import xfacthd.framedblocks.common.particle.BasicParticleType;
import xfacthd.framedblocks.common.item.FramedBlueprintItem;
import xfacthd.framedblocks.common.item.FramedToolItem;
import xfacthd.framedblocks.common.menu.FramingSawMenu;
import xfacthd.framedblocks.common.particle.FluidParticleOptions;
import xfacthd.framedblocks.common.util.FramedCreativeTab;
import xfacthd.framedblocks.common.util.registration.*;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class FBContent
{
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FramedConstants.MOD_ID);
    private static final DeferredDataComponentTypeRegister DATA_COMPONENTS = DeferredDataComponentTypeRegister.create(FramedConstants.MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FramedConstants.MOD_ID);
    private static final DeferredBlockEntityRegister BE_TYPES = DeferredBlockEntityRegister.create(FramedConstants.MOD_ID);
    private static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, FramedConstants.MOD_ID);
    private static final DeferredRecipeTypeRegister RECIPE_TYPES = DeferredRecipeTypeRegister.create(FramedConstants.MOD_ID);
    private static final DeferredRecipeSerializerRegister RECIPE_SERIALIZERS = DeferredRecipeSerializerRegister.create(FramedConstants.MOD_ID);
    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FramedConstants.MOD_ID);
    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, FramedConstants.MOD_ID);
    private static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, FramedConstants.MOD_ID);
    private static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTIONS = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, FramedConstants.MOD_ID);
    private static final DeferredRegister<LootNumberProviderType> LOOT_NUMBER_PROVIDERS = DeferredRegister.create(Registries.LOOT_NUMBER_PROVIDER_TYPE, FramedConstants.MOD_ID);

    private static final DeferredRegister<CamoContainerFactory<?>> CAMO_CONTAINER_FACTORIES = DeferredRegister.create(
            FramedConstants.CAMO_CONTAINER_FACTORY_REGISTRY_NAME,
            FramedConstants.MOD_ID
    );
    public static final Registry<CamoContainerFactory<?>> CAMO_CONTAINER_FACTORY_REGISTRY = CAMO_CONTAINER_FACTORIES.makeRegistry(
            builder -> builder.sync(true)
    );

    private static final DeferredAuxDataTypeRegister AUX_BLUEPRINT_DATA_TYPES = DeferredAuxDataTypeRegister.create(FramedConstants.MOD_ID);
    public static final Registry<AuxBlueprintData.Type<?>> AUX_BLUEPRINT_DATA_TYPE_REGISTRY = AUX_BLUEPRINT_DATA_TYPES.makeRegistry(
            builder -> builder.sync(true)
    );

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
    // endregion

    // region Items
    public static final Holder<Item> ITEM_FRAMED_HAMMER = registerToolItem(FramedToolItem::new, FramedToolType.HAMMER);
    public static final Holder<Item> ITEM_FRAMED_WRENCH = registerToolItem(FramedToolItem::new, FramedToolType.WRENCH);
    public static final Holder<Item> ITEM_FRAMED_BLUEPRINT = registerToolItem(FramedBlueprintItem::new, FramedToolType.BLUEPRINT);
    public static final Holder<Item> ITEM_FRAMED_KEY = registerToolItem(FramedToolItem::new, FramedToolType.KEY);
    public static final Holder<Item> ITEM_FRAMED_SCREWDRIVER = registerToolItem(FramedToolItem::new, FramedToolType.SCREWDRIVER);
    public static final Holder<Item> ITEM_FRAMED_REINFORCEMENT = ITEMS.registerSimpleItem("framed_reinforcement");
    // endregion

    // region BlockEntityTypes
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_BLOCK = registerBlockEntity(
            FramedBlockEntity::new,
            "framed_tile",
            getDefaultEntityBlocks(),
            true
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_DOUBLE_BLOCK = registerBlockEntity(
            FramedDoubleBlockEntity::new,
            "framed_double_tile",
            getDefaultDoubleEntityBlocks(),
            true
    );
    public static final Holder<BlockEntityType<?>> BE_TYPE_FRAMED_DOOR = registerBlockEntity(
            FramedDoorBlockEntity::new,
            BlockType.FRAMED_DOOR, BlockType.FRAMED_IRON_DOOR
    );
    public static final DeferredBlockEntity<FramedChiseledBookshelfBlockEntity> BE_TYPE_FRAMED_CHISELED_BOOKSHELF = registerBlockEntity(
            FramedChiseledBookshelfBlockEntity::new,
            BlockType.FRAMED_CHISELED_BOOKSHELF
    );
    // endregion

    // region Special BlockEntities
    // endregion

    // region MenuTypes
    public static final DeferredMenuType<FramingSawMenu> MENU_TYPE_FRAMING_SAW = MENU_TYPES.registerSimpleMenuType(
            "framing_saw", FramingSawMenu::createClient
    );
    // endregion

    // region RecipeTypes
    public static final DeferredHolder<RecipeType<?>, RecipeType<FramingSawRecipe>> RECIPE_TYPE_FRAMING_SAW_RECIPE = registerRecipeType("frame");
    // endregion

    // region RecipeSerializers
    public static final Holder<RecipeSerializer<?>> RECIPE_SERIALIZER_FRAMING_SAW_RECIPE = registerRecipeSerializer(
            "frame", FramingSawRecipe.CODEC, FramingSawRecipe.STREAM_CODEC
    );
    public static final Holder<RecipeSerializer<?>> RECIPE_SERIALIZER_APPLY_CAMO = registerRecipeSerializer(
            "apply_camo", CamoApplicationRecipe.CODEC, CamoApplicationRecipe.STREAM_CODEC
    );
    public static final Holder<RecipeSerializer<?>> RECIPE_SERIALIZER_JEI_CAMO = registerRecipeSerializer(
            "jei_camo", JeiCamoApplicationRecipe.CODEC, JeiCamoApplicationRecipe.STREAM_CODEC
    );
    public static final Holder<RecipeSerializer<?>> RECIPE_SERIALIZER_SHAPE_ROTATION = registerRecipeSerializer(
            "rotate_shape", ShapeRotationRecipe.CODEC, ShapeRotationRecipe.STREAM_CODEC
    );
    // endregion

    // region CreativeModeTabs
    public static final Holder<CreativeModeTab> MAIN_TAB = CREATIVE_TABS.register(
            "framed_blocks", FramedCreativeTab::makeTab
    );
    // endregion

    // region ParticleTypes
    public static final DeferredHolder<ParticleType<?>, ParticleType<FluidParticleOptions>> FLUID_PARTICLE = registerParticle(
            "fluid", false, FluidParticleOptions.CODEC, FluidParticleOptions.STREAM_CODEC
    );
    // endregion

    // region LootItemConditions
    public static final Holder<LootItemConditionType> NON_TRIVIAL_CAMO_LOOT_CONDITION = LOOT_CONDITIONS.register(
            "non_trivial_camo", () -> new LootItemConditionType(NonTrivialCamoLootCondition.MAP_CODEC)
    );
    // endregion

    // region LootItemFunctions
    public static final DeferredHolder<LootItemFunctionType<?>, LootItemFunctionType<SplitCamoLootFunction>> SPLIT_CAMO_LOOT_FUNCTION = LOOT_FUNCTIONS.register(
            "split_camo", () -> new LootItemFunctionType<>(SplitCamoLootFunction.MAP_CODEC)
    );
    // endregion

    // region LootNumberProviderTypes
    public static final Holder<LootNumberProviderType> LAYERED_CUBE_ADDITIONAL_ITEM_COUNT_NUMBER_PROVIDER = LOOT_NUMBER_PROVIDERS.register(
            "layered_cube", () -> new LootNumberProviderType(MapCodec.unit(LayeredCubeAdditionalItemCountNumberProvider.INSTANCE))
    );
    // endregion

    // region CamoContainer.Factories
    public static final DeferredHolder<CamoContainerFactory<?>, EmptyCamoContainerFactory> FACTORY_EMPTY = CAMO_CONTAINER_FACTORIES.register(
            "empty",
            EmptyCamoContainerFactory::new
    );
    public static final DeferredHolder<CamoContainerFactory<?>, BlockCamoContainerFactory> FACTORY_BLOCK = CAMO_CONTAINER_FACTORIES.register(
            "block",
            BlockCamoContainerFactory::new
    );
    public static final DeferredHolder<CamoContainerFactory<?>, FluidCamoContainerFactory> FACTORY_FLUID = CAMO_CONTAINER_FACTORIES.register(
            "fluid",
            FluidCamoContainerFactory::new
    );
    // endregion

    // region AuxBlueprintData.Types
    public static final DeferredAuxDataType<DoorAuxBlueprintData> AUX_TYPE_DOOR_DATA = AUX_BLUEPRINT_DATA_TYPES.registerAuxDataType(
            "door", DoorAuxBlueprintData.CODEC, DoorAuxBlueprintData.STREAM_CODEC
    );
    // endregion



    public static void init(IEventBus modBus)
    {
        BLOCKS.register(modBus);
        DATA_COMPONENTS.register(modBus);
        ITEMS.register(modBus);
        BE_TYPES.register(modBus);
        CONTAINER_TYPES.register(modBus);
        RECIPE_TYPES.register(modBus);
        RECIPE_SERIALIZERS.register(modBus);
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

    private static Supplier<Block[]> getDefaultEntityBlocks()
    {
        //noinspection SuspiciousToArrayCall
        return () -> BLOCKS.getEntries()
                .stream()
                .map(Holder::value)
                .filter(block -> block instanceof IFramedBlock)
                .map(IFramedBlock.class::cast)
                .filter(block -> !block.getBlockType().isDoubleBlock())
                .filter(block -> !block.getBlockType().hasSpecialTile())
                .toArray(Block[]::new);
    }

    private static Supplier<Block[]> getDefaultDoubleEntityBlocks()
    {
        //noinspection SuspiciousToArrayCall
        return () -> BLOCKS.getEntries()
                .stream()
                .map(Holder::value)
                .filter(block -> block instanceof IFramedBlock)
                .map(IFramedBlock.class::cast)
                .filter(block -> block.getBlockType().isDoubleBlock())
                .filter(block -> !block.getBlockType().hasSpecialTile())
                .toArray(Block[]::new);
    }

    private static <T extends Block & IFramedBlock> Holder<Block> registerBlock(
            Function<BlockType, T> blockFactory, BlockType type
    )
    {
        return registerBlock(() -> blockFactory.apply(type), type);
    }

    private static <T extends Block & IFramedBlock> Holder<Block> registerBlock(
            Supplier<T> blockFactory, BlockType type
    )
    {
        Holder<Block> result = BLOCKS.register(type.getName(), () ->
        {
            T block = blockFactory.get();
            Preconditions.checkArgument(block.getBlockType() == type);
            return block;
        });
        BLOCKS_BY_TYPE.put(type, result);

        if (type.hasBlockItem())
        {
            ITEMS.register(type.getName(), () ->
                    ((IFramedBlock) result.value()).createBlockItem()
            );
        }

        return result;
    }

    @SuppressWarnings("SameParameterValue")
    private static Holder<Block> registerBlock(String name, Supplier<? extends Block> blockFactory)
    {
        Holder<Block> result = BLOCKS.register(name, blockFactory);
        ITEMS.register(name, () -> new BlockItem(result.value(), new Item.Properties()));
        return result;
    }

    private static Holder<Item> registerToolItem(Function<FramedToolType, Item> itemFactory, FramedToolType type)
    {
        Holder<Item> result = ITEMS.register(type.getName(), () -> itemFactory.apply(type));
        TOOLS_BY_TYPE.put(type, result);
        return result;
    }

    private static <T extends BlockEntity> DeferredBlockEntity<T> registerBlockEntity(
            BlockEntityType.BlockEntitySupplier<T> factory, BlockType... types
    )
    {
        Supplier<Block[]> blocks = () -> Arrays.stream(types)
                .map(BLOCKS_BY_TYPE::get)
                .map(Holder::value)
                .toArray(Block[]::new);

        DeferredBlockEntity<T> result = registerBlockEntity(factory, types[0].getName(), blocks, true);
        if (!FMLEnvironment.production && Arrays.stream(types).anyMatch(BlockType::isDoubleBlock))
        {
            //noinspection unchecked
            DOUBLE_BLOCK_ENTITIES.add((DeferredBlockEntity<? extends FramedDoubleBlockEntity>) result);
        }
        return result;
    }

    private static <T extends BlockEntity> DeferredBlockEntity<T> registerBlockEntity(
            BlockEntityType.BlockEntitySupplier<T> factory, String name, Supplier<Block[]> blocks, boolean isFramedBE
    )
    {
        DeferredBlockEntity<T> result = BE_TYPES.registerBlockEntity(name, factory, blocks);
        if (isFramedBE)
        {
            //noinspection unchecked
            FRAMED_BLOCK_ENTITIES.add((DeferredBlockEntity<? extends FramedBlockEntity>) result);
        }
        return result;
    }

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerSimpleMenuType(
            MenuType.MenuSupplier<T> factory, String name
    )
    {
        return CONTAINER_TYPES.register(name, () -> new MenuType<>(factory, FeatureFlags.VANILLA_SET));
    }

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(
            IContainerFactory<T> factory, String name
    )
    {
        return CONTAINER_TYPES.register(name, () -> IMenuTypeExtension.create(factory));
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
    private static <O extends ParticleOptions> DeferredHolder<ParticleType<?>, ParticleType<O>> registerParticle(
            String name, boolean overrideLimiter, MapCodec<O> codec, StreamCodec<? super RegistryFriendlyByteBuf, O> streamCodec
    )
    {
        return PARTICLE_TYPES.register(name, () -> new BasicParticleType<>(overrideLimiter, codec, streamCodec));
    }



    private FBContent() { }
}
