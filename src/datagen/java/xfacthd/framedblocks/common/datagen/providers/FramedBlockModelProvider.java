package xfacthd.framedblocks.common.datagen.providers;

import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.MissingBlockModel;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;
import net.neoforged.neoforge.common.conditions.NeoForgeConditions;
import org.jetbrains.annotations.Nullable;
import xfacthd.framedblocks.api.datagen.models.AbstractFramedBlockModelProvider;
import xfacthd.framedblocks.api.model.item.tint.FramedBlockItemTintProvider;
import xfacthd.framedblocks.api.util.ClientUtils;
import xfacthd.framedblocks.api.util.FramedConstants;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.client.model.item.FramedBlockItemModel;
import xfacthd.framedblocks.client.model.item.modelprovider.FenceBlockItemModelProvider;
import xfacthd.framedblocks.client.model.loader.fallback.FallbackLoaderBuilder;
import xfacthd.framedblocks.client.model.geometry.cube.FramedMarkedCubeGeometry;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.compat.amendments.AmendmentsCompat;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.property.ChainType;

import java.util.Arrays;
import java.util.Objects;

@SuppressWarnings({ "MethodMayBeStatic", "SameParameterValue" })
public final class FramedBlockModelProvider extends AbstractFramedBlockModelProvider
{
    private static final ResourceLocation TEXTURE = Utils.rl("block/framed_block");
    private static final ResourceLocation TEXTURE_ALT = Utils.rl("block/framed_block_alt");
    private static final ResourceLocation TEXTURE_UNDERLAY = ResourceLocation.withDefaultNamespace("block/stripped_dark_oak_log");
    private static final ModelTemplate TEMPLATE_CUTOUT_CUBE = ModelTemplates.CUBE_ALL.extend().renderType("cutout").build();
    private static final ResourceLocation TRAPDOOR_TEMPLATE_LOC = ResourceLocation.withDefaultNamespace("block/template_orientable_trapdoor_bottom");
    private static final ResourceLocation THIN_BLOCK_LOC = ResourceLocation.withDefaultNamespace("block/thin_block");

    public FramedBlockModelProvider(PackOutput output)
    {
        super(output, FramedConstants.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels)
    {
        ResourceLocation cube = TEMPLATE_CUTOUT_CUBE.create(FBContent.BLOCK_FRAMED_CUBE.value(), TextureMapping.cube(TEXTURE), blockModels.modelOutput);
        ResourceLocation stoneCube = makeUnderlayedCube(blockModels, Utils.rl("block/framed_stone_cube"), TEXTURE, mcLocation("block/stone"), $ -> {});
        ResourceLocation ironCube = makeUnderlayedCube(blockModels, Utils.rl("block/framed_iron_cube"), TEXTURE, mcLocation("block/iron_block"), $ -> {});

        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_SLAB, cube);
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_DOUBLE_SLAB, cube);
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_SLAB_EDGE, cube);
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_SLAB_CORNER, cube, builder -> builder.itemBaseModel(THIN_BLOCK_LOC));
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_PANEL, cube);
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_DOUBLE_PANEL, cube);
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_CORNER_PILLAR, cube);
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_STAIRS, cube);
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_HALF_STAIRS, cube);
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_VERTICAL_STAIRS, cube);
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_VERTICAL_HALF_STAIRS, cube, builder -> builder.itemBaseModel(THIN_BLOCK_LOC));
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_WALL, cube);
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_FENCE_GATE, cube);
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_TRAP_DOOR, cube, builder -> builder.itemBaseModel(TRAPDOOR_TEMPLATE_LOC));
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR, ironCube, builder -> builder.itemBaseModel(TRAPDOOR_TEMPLATE_LOC));
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_BUTTON, cube, builder -> builder.itemBaseModel(THIN_BLOCK_LOC));
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_STONE_BUTTON, stoneCube, builder -> builder.itemBaseModel(THIN_BLOCK_LOC));
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_PILLAR, cube);
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_HALF_PILLAR, cube, builder -> builder.itemBaseModel(THIN_BLOCK_LOC));

        registerFramedCube(blockModels, cube);
        registerFramedFence(blockModels, cube);
        registerFramedDoor(blockModels, cube);
        registerFramedIronDoor(blockModels, ironCube);
        registerFramedPressurePlate(blockModels, cube);
        registerFramedStonePressurePlate(blockModels, stoneCube);
        registerFramedBookshelf(blockModels);
        registerFramedChiseledBookshelf(blockModels);
        registerFramingSaw(blockModels);
    }

    private void registerFramedCube(BlockModelGenerators blockModels, ResourceLocation cube)
    {
        ResourceLocation solidUnderlay = TEMPLATE_CUTOUT_CUBE.create(
                Utils.rl("block/framed_underlay"),
                new TextureMapping()
                        .put(TextureSlot.ALL, TEXTURE_UNDERLAY)
                        .putForced(TextureSlot.PARTICLE, TEXTURE),
                blockModels.modelOutput
        );
        ResourceLocation altCube = TEMPLATE_CUTOUT_CUBE.create(
                Utils.rl("block/framed_cube_alt"),
                TextureMapping.cube(TEXTURE_ALT),
                blockModels.modelOutput
        );
        ResourceLocation reinforcement = TEMPLATE_CUTOUT_CUBE.create(
                Utils.rl("block/framed_reinforcement"),
                TextureMapping.cube(Utils.rl("block/framed_reinforcement")),
                blockModels.modelOutput
        );

        framedMultiPart(blockModels, FBContent.BLOCK_FRAMED_CUBE, gen -> gen
                .with(
                        BlockModelGenerators.condition().term(PropertyHolder.SOLID_BG, true),
                        BlockModelGenerators.plainVariant(solidUnderlay)
                )
                .with(
                        BlockModelGenerators.condition().term(PropertyHolder.ALT, false),
                        BlockModelGenerators.plainVariant(cube)
                )
                .with(
                        BlockModelGenerators.condition().term(PropertyHolder.ALT, true),
                        BlockModelGenerators.plainVariant(altCube)
                )
                .with(
                        BlockModelGenerators.condition().term(PropertyHolder.REINFORCED, true),
                        BlockModelGenerators.plainVariant(reinforcement)
                )
        );

        framedBlockItemModel(blockModels, FBContent.BLOCK_FRAMED_CUBE);
    }

    private void registerFramedFence(BlockModelGenerators blockModels, ResourceLocation cube)
    {
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_FENCE, cube, builder ->
                builder.modelProvider(FenceBlockItemModelProvider.INSTANCE)
                        .itemBaseModel(mcLocation("block/fence_inventory"))
        );
    }

    private void registerFramedDoor(BlockModelGenerators blockModels, ResourceLocation cube)
    {
        simpleFramedBlock(blockModels, FBContent.BLOCK_FRAMED_DOOR, cube);
        blockModels.registerSimpleFlatItemModel(FBContent.BLOCK_FRAMED_DOOR.value().asItem());
    }

    private void registerFramedIronDoor(BlockModelGenerators blockModels, ResourceLocation cube)
    {
        simpleFramedBlock(blockModels, FBContent.BLOCK_FRAMED_IRON_DOOR, cube);
        blockModels.registerSimpleFlatItemModel(FBContent.BLOCK_FRAMED_IRON_DOOR.value().asItem());
    }

    private void registerFramedPressurePlate(BlockModelGenerators blockModels, ResourceLocation cube)
    {
        simpleFramedBlock(blockModels, FBContent.BLOCK_FRAMED_PRESSURE_PLATE, cube);

        framedBlockItemModel(blockModels, FBContent.BLOCK_FRAMED_PRESSURE_PLATE, builder -> builder.itemBaseModel(THIN_BLOCK_LOC));
    }

    private void registerFramedStonePressurePlate(BlockModelGenerators blockModels, ResourceLocation cube)
    {
        simpleFramedBlock(blockModels, FBContent.BLOCK_FRAMED_STONE_PRESSURE_PLATE, cube);

        framedBlockItemModel(blockModels, FBContent.BLOCK_FRAMED_STONE_PRESSURE_PLATE, builder -> builder.itemBaseModel(THIN_BLOCK_LOC));
    }

    private void registerFramedBookshelf(BlockModelGenerators blockModels)
    {
        simpleFramedBlockWithItem(blockModels, FBContent.BLOCK_FRAMED_BOOKSHELF, Utils.rl("block/framed_bookshelf"));
    }

    private void registerFramedChiseledBookshelf(BlockModelGenerators blockModels)
    {
        String[] bookSlots = new String[] { "top_left", "top_mid", "top_right", "bottom_left", "bottom_mid", "bottom_right" };
        ModelTemplate[] bookSlotTemplates = Arrays.stream(bookSlots)
                .map(slot -> ModelTemplates.create("framedblocks:template_framed_chiseled_bookshelf_slot_" + slot, TextureSlot.TEXTURE))
                .toArray(ModelTemplate[]::new);

        String baseName = "block/framed_chiseled_bookshelf";
        ResourceLocation[] modelsEmpty = new ResourceLocation[6];
        ResourceLocation[] modelsFilled = new ResourceLocation[6];
        for (int i = 0; i < ChiseledBookShelfBlockEntity.MAX_BOOKS_IN_STORAGE; i++)
        {
            String slot = bookSlots[i];

            modelsEmpty[i] = bookSlotTemplates[i].create(
                    Utils.rl(baseName + "_empty_slot_" + slot),
                    TextureMapping.defaultTexture(mcLocation("block/chiseled_bookshelf_empty")),
                    blockModels.modelOutput
            );
            modelsFilled[i] = bookSlotTemplates[i].create(
                    Utils.rl(baseName + "_occupied_slot_" + slot),
                    TextureMapping.defaultTexture(mcLocation("block/chiseled_bookshelf_occupied")),
                    blockModels.modelOutput
            );
        }
        ResourceLocation baseModel = blockModelFromTemplate(
                blockModels,
                FBContent.BLOCK_FRAMED_CHISELED_BOOKSHELF,
                ModelTemplates.create("block", TextureSlot.PARTICLE),
                TextureMapping.particle(TEXTURE)
        );

        framedMultiPart(blockModels, FBContent.BLOCK_FRAMED_CHISELED_BOOKSHELF, gen ->
        {
            gen.with(BlockModelGenerators.plainVariant(baseModel));
            for (Direction dir : Direction.Plane.HORIZONTAL)
            {
                for (int i = 0; i < ChiseledBookShelfBlockEntity.MAX_BOOKS_IN_STORAGE; i++)
                {
                    BooleanProperty prop = ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(i);
                    ConditionBuilder facingCondition = BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, dir);

                    gen.with(
                            and(facingCondition, BlockModelGenerators.condition().term(prop, false)),
                            BlockModelGenerators.plainVariant(modelsEmpty[i]).with(horDirToVariant(dir.getOpposite()))
                    );
                    gen.with(
                            and(facingCondition, BlockModelGenerators.condition().term(prop, true)),
                            BlockModelGenerators.plainVariant(modelsFilled[i]).with(horDirToVariant(dir.getOpposite()))
                    );
                }
            }
            return gen;
        });

        framedBlockItemModel(blockModels, FBContent.BLOCK_FRAMED_CHISELED_BOOKSHELF);
    }

    private void registerFramingSaw(BlockModelGenerators blockModels)
    {
        ResourceLocation model = Utils.rl("block/framing_saw");
        ResourceLocation modelEncoder = Utils.rl("block/framing_saw_encoder");
        variant(blockModels, FBContent.BLOCK_FRAMING_SAW, gen ->
                gen.with(BlockModelGenerators.createBooleanModelDispatch(
                        PropertyHolder.SAW_ENCODER,
                        BlockModelGenerators.plainVariant(modelEncoder),
                        BlockModelGenerators.plainVariant(model)
                ))
                .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING_ALT)
        );
        blockModels.registerSimpleItemModel(FBContent.BLOCK_FRAMING_SAW.value(), model);
    }
}
