package xfacthd.framedblocks.common.datagen.providers;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.util.*;
import xfacthd.framedblocks.client.loader.overlay.OverlayLoaderBuilder;
import xfacthd.framedblocks.client.model.cube.FramedMarkedCubeGeometry;
import xfacthd.framedblocks.client.model.cube.FramedTargetGeometry;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.data.PropertyHolder;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class FramedBlockStateProvider extends BlockStateProvider
{
    private final ResourceLocation TEXTURE;
    private final ResourceLocation TEXTURE_ALT;
    private final ResourceLocation TEXTURE_UNDERLAY;

    public FramedBlockStateProvider(PackOutput output, ExistingFileHelper fileHelper)
    {
        super(output, FramedConstants.MOD_ID, fileHelper);
        TEXTURE = modLoc("block/framed_block");
        TEXTURE_ALT = modLoc("block/framed_block_alt");
        TEXTURE_UNDERLAY = mcLoc("block/stripped_dark_oak_log");
    }

    @Override
    protected void registerStatesAndModels()
    {
        ModelFile cube = models().cubeAll("framed_cube", TEXTURE).renderType("cutout");
        ModelFile stoneCube = makeUnderlayedCube("framed_stone_cube", mcLoc("block/stone"));
        ModelFile obsidianCube = makeUnderlayedCube("framed_obsidian_cube", mcLoc("block/obsidian"));
        ModelFile ironCube = makeUnderlayedCube("framed_iron_cube", mcLoc("block/iron_block"));
        ModelFile goldCube = makeUnderlayedCube("framed_gold_cube", mcLoc("block/gold_block"));
        ModelFile snowCube = makeUnderlayedCube("framed_snow_cube", mcLoc("block/snow"));

        simpleBlockWithItem(FBContent.BLOCK_FRAMED_DOUBLE_SLAB, cube, "cutout");
        simpleBlockWithItem(FBContent.BLOCK_FRAMED_SLAB_EDGE, cube, "cutout");
        simpleBlockWithItem(FBContent.BLOCK_FRAMED_SLAB_CORNER, cube, "cutout");
        simpleBlockWithItem(FBContent.BLOCK_FRAMED_PANEL, cube, "cutout");
        simpleBlockWithItem(FBContent.BLOCK_FRAMED_DOUBLE_PANEL, cube, "cutout");
        simpleBlockWithItem(FBContent.BLOCK_FRAMED_CORNER_PILLAR.value(), cube);
        simpleBlockWithItem(FBContent.BLOCK_FRAMED_HALF_STAIRS, cube, "cutout");
        simpleBlockWithItem(FBContent.BLOCK_FRAMED_VERTICAL_STAIRS, cube, "cutout");
        simpleBlockWithItem(FBContent.BLOCK_FRAMED_VERTICAL_HALF_STAIRS, cube, "cutout");
        simpleBlockWithItem(FBContent.BLOCK_FRAMED_FENCE_GATE, cube, "cutout");
        simpleBlockWithItem(FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR, ironCube, "cutout");
        simpleBlockWithItem(FBContent.BLOCK_FRAMED_STONE_BUTTON, stoneCube, "cutout");
        simpleBlockWithItem(FBContent.BLOCK_FRAMED_PILLAR, cube, "cutout");
        simpleBlockWithItem(FBContent.BLOCK_FRAMED_HALF_PILLAR, cube, "cutout");

        registerFramedCube(cube);
        registerFramedSlab(cube);
        registerFramedStairs(cube);
        registerFramedWall(cube);
        registerFramedFence(cube);
        registerFramedDoor(cube);
        registerFramedIronDoor(ironCube);
        registerFramedTrapDoor(cube);
        registerFramedPressurePlate(cube);
        registerFramedStonePressurePlate(stoneCube);
        registerFramedBookshelf();
        registerFramedChiseledBookshelf(cube);

        registerFramingSaw();
    }

    private void registerFramedCube(ModelFile cube)
    {
        ModelFile solidUnderlay = models().cubeAll("framed_underlay", TEXTURE_UNDERLAY)
                .texture("particle", TEXTURE)
                .renderType("cutout");
        ModelFile altCube = models().cubeAll("framed_cube_alt", modLoc("block/framed_block_alt"))
                .renderType("cutout");
        ModelFile reinforcement = models().cubeAll("framed_reinforcement", modLoc("block/framed_reinforcement"))
                .renderType("cutout");

        getMultipartBuilder(FBContent.BLOCK_FRAMED_CUBE.value())
                .part()
                    .modelFile(solidUnderlay)
                    .addModel()
                    .condition(PropertyHolder.SOLID_BG, true)
                    .end()
                .part()
                    .modelFile(cube)
                    .addModel()
                    .condition(PropertyHolder.ALT, false)
                    .end()
                .part()
                    .modelFile(altCube)
                    .addModel()
                    .condition(PropertyHolder.ALT, true)
                    .end()
                .part()
                    .modelFile(reinforcement)
                    .addModel()
                    .condition(PropertyHolder.REINFORCED, true)
                    .end();

        simpleBlockItem(FBContent.BLOCK_FRAMED_CUBE, cube);
    }

    private void registerFramedSlab(ModelFile cube)
    {
        simpleBlock(FBContent.BLOCK_FRAMED_SLAB.value(), cube);
        itemModels().slab("framed_slab", TEXTURE, TEXTURE, TEXTURE).renderType("cutout");
    }

    private void registerFramedStairs(ModelFile cube)
    {
        simpleBlock(FBContent.BLOCK_FRAMED_STAIRS.value(), cube);
        itemModels().stairs("framed_stairs", TEXTURE, TEXTURE, TEXTURE).renderType("cutout");
    }

    private void registerFramedWall(ModelFile cube)
    {
        simpleBlock(FBContent.BLOCK_FRAMED_WALL.value(), cube);
        itemModels().getBuilder("framed_wall")
                .parent(models().getExistingFile(mcLoc("block/wall_inventory")))
                .texture("wall", TEXTURE)
                .renderType("cutout");
    }

    private void registerFramedFence(ModelFile cube)
    {
        getMultipartBuilder(FBContent.BLOCK_FRAMED_FENCE.value())
                .part()
                .modelFile(cube)
                .addModel();

        itemModels().getBuilder("framed_fence")
                .parent(models().getExistingFile(modLoc("item/framed_fence_inventory")))
                .texture("texture", TEXTURE)
                .texture("underlay", TEXTURE_UNDERLAY)
                .renderType("cutout");
    }

    private void registerFramedDoor(ModelFile cube)
    {
        simpleBlock(FBContent.BLOCK_FRAMED_DOOR.value(), cube);
        simpleItem(FBContent.BLOCK_FRAMED_DOOR, "cutout");
    }

    private void registerFramedIronDoor(ModelFile cube)
    {
        simpleBlock(FBContent.BLOCK_FRAMED_IRON_DOOR.value(), cube);
        simpleItem(FBContent.BLOCK_FRAMED_IRON_DOOR, "cutout");
    }

    private void registerFramedTrapDoor(ModelFile cube)
    {
        simpleBlock(FBContent.BLOCK_FRAMED_TRAP_DOOR.value(), cube);
        itemModels().withExistingParent("framed_trapdoor", mcLoc("block/template_orientable_trapdoor_bottom"))
                .texture("texture", TEXTURE)
                .renderType("cutout");
    }

    private void registerFramedPressurePlate(ModelFile cube)
    {
        simpleBlock(FBContent.BLOCK_FRAMED_PRESSURE_PLATE.value(), cube);

        itemModels().withExistingParent("framed_pressure_plate", mcLoc("block/pressure_plate_up"))
                .texture("texture", TEXTURE).renderType("cutout");
    }

    private void registerFramedStonePressurePlate(ModelFile cube)
    {
        simpleBlock(FBContent.BLOCK_FRAMED_STONE_PRESSURE_PLATE.value(), cube);

        itemModels().withExistingParent("framed_stone_pressure_plate", modLoc("block/framed_pressure_plate_up"))
                .texture("background", mcLoc("block/stone"))
                .renderType("cutout");
    }

    private void registerFramedBookshelf()
    {
        ModelFile model = models().getExistingFile(modLoc("block/framed_bookshelf"));
        simpleBlockWithItem(FBContent.BLOCK_FRAMED_BOOKSHELF, model);
    }

    private void registerFramedChiseledBookshelf(ModelFile cube)
    {
        String template = "block/template_framed_chiseled_bookshelf_slot_";
        String baseName = "framed_chiseled_bookshelf";
        String[] slots = new String[] { "top_left", "top_mid", "top_right", "bottom_left", "bottom_mid", "bottom_right" };
        ModelFile[] modelsEmpty = new ModelFile[6];
        ModelFile[] modelsFilled = new ModelFile[6];
        for (int i = 0; i < ChiseledBookShelfBlockEntity.MAX_BOOKS_IN_STORAGE; i++)
        {
            String slot = slots[i];
            modelsEmpty[i] = models().withExistingParent(baseName + "_empty_slot_" + slot, modLoc(template + slot))
                    .texture("texture", mcLoc("block/chiseled_bookshelf_empty"));
            modelsFilled[i] = models().withExistingParent(baseName + "_occupied_slot_" + slot, modLoc(template + slot))
                    .texture("texture", "minecraft:block/chiseled_bookshelf_occupied");
        }

        MultiPartBlockStateBuilder builder = getMultipartBuilder(FBContent.BLOCK_FRAMED_CHISELED_BOOKSHELF.value());
        builder.part().modelFile(models().getExistingFile(mcLoc("block/block"))).addModel().end();
        for (Direction dir : Direction.Plane.HORIZONTAL)
        {
            for (int i = 0; i < ChiseledBookShelfBlockEntity.MAX_BOOKS_IN_STORAGE; i++)
            {
                BooleanProperty prop = ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(i);
                int rot = (int) ((dir.toYRot() + 180F) % 360F);
                builder.part()
                        .modelFile(modelsEmpty[i])
                        .rotationY(rot)
                        .addModel()
                        .nestedGroup()
                            .condition(prop, false)
                            .condition(FramedProperties.FACING_HOR, dir)
                            .end()
                        .end();

                builder.part()
                        .modelFile(modelsFilled[i])
                        .rotationY(rot)
                        .addModel()
                        .nestedGroup()
                            .condition(prop, true)
                            .condition(FramedProperties.FACING_HOR, dir)
                            .end()
                        .end();
            }
        }

        simpleBlockItem(FBContent.BLOCK_FRAMED_CHISELED_BOOKSHELF, cube);
    }



    private void registerFramingSaw()
    {
        ModelFile model = models().getExistingFile(modLoc("block/framing_saw"));
        ModelFile modelEncoder = models().getExistingFile(modLoc("block/framing_saw_encoder"));
        getVariantBuilder(FBContent.BLOCK_FRAMING_SAW.value()).forAllStates(state ->
        {
            int rotY = (int) state.getValue(FramedProperties.FACING_HOR).toYRot();
            boolean encoder = state.getValue(PropertyHolder.SAW_ENCODER);
            return ConfiguredModel.builder()
                    .rotationY(rotY)
                    .modelFile(encoder ? modelEncoder : model)
                    .build();
        });
        simpleBlockItem(FBContent.BLOCK_FRAMING_SAW, model);
    }



    @SuppressWarnings("unused")
    private BlockModelBuilder block(Holder<Block> block)
    {
        return block(block, "");
    }

    private BlockModelBuilder block(Holder<Block> block, String suffix)
    {
        String name = Utils.getKeyOrThrow(block).location().getPath();
        String path = "block/" + name;
        if (!suffix.isBlank())
        {
            path += "_" + suffix;
        }
        return models().getBuilder(path);
    }

    private ModelFile existingBlock(Holder<Block> block)
    {
        return existingBlock(block, "");
    }

    private ModelFile existingBlock(Holder<Block> block, String suffix)
    {
        ResourceLocation name = Utils.getKeyOrThrow(block).location();
        String path = "block/" + name.getPath();
        if (!suffix.isBlank())
        {
            path += "_" + suffix;
        }
        return models().getExistingFile(Utils.rl(name.getNamespace(), path));
    }

    @SuppressWarnings({ "UnusedReturnValue", "SameParameterValue" })
    private ItemModelBuilder simpleBlockWithItem(Holder<Block> block, ModelFile model, String itemRenderType)
    {
        return simpleBlockWithItem(block, model).renderType(itemRenderType);
    }

    private ItemModelBuilder simpleBlockWithItem(Holder<Block> block, ModelFile model)
    {
        simpleBlock(block.value(), model);
        return simpleBlockItem(block, model);
    }

    @SuppressWarnings({ "UnusedReturnValue", "SameParameterValue" })
    private ItemModelBuilder simpleBlockItem(Holder<Block> block, ModelFile model, String renderType)
    {
        return simpleBlockItem(block, model).renderType(renderType);
    }

    private ItemModelBuilder simpleBlockItem(Holder<Block> block, ModelFile model)
    {
        return itemModels().getBuilder(Utils.getKeyOrThrow(block).location().getPath()).parent(model);
    }

    @SuppressWarnings({ "UnusedReturnValue", "SameParameterValue" })
    private ItemModelBuilder simpleItem(Holder<Block> block, String renderType)
    {
        return simpleItem(Utils.getKeyOrThrow(block).location().getPath(), renderType);
    }

    @SuppressWarnings({ "UnusedReturnValue", "SameParameterValue" })
    private ItemModelBuilder simpleItem(Holder<Block> block, String texture, String renderType)
    {
        return simpleItem(Utils.getKeyOrThrow(block).location().getPath(), texture, renderType);
    }

    private ItemModelBuilder simpleItem(String name, String renderType)
    {
        return simpleItem(name, "item/" + name, renderType);
    }

    private ItemModelBuilder simpleItem(String name, String texture, String renderType)
    {
        return itemModels().singleTexture(name, mcLoc("item/generated"), "layer0", modLoc(texture)).renderType(renderType);
    }

    private BlockModelBuilder makeUnderlayedCube(String name, ResourceLocation underlayTex)
    {
        return makeUnderlayedCube(name, TEXTURE, underlayTex);
    }

    private BlockModelBuilder makeUnderlayedCube(String name, ResourceLocation frameTex, ResourceLocation underlayTex)
    {
        return models().withExistingParent(name, "block/block")
                .element()
                    .cube("#underlay")
                    .end()
                .element()
                    .cube("#frame")
                    .end()
                .texture("frame", frameTex)
                .texture("underlay", underlayTex)
                .texture("particle", frameTex)
                .renderType("cutout");
    }

    @SuppressWarnings("SameParameterValue")
    private void makeOverlayModel(ResourceLocation name, ResourceLocation parent, String textureKey, ResourceLocation texture)
    {
        models().getBuilder(name.getPath())
                .customLoader(OverlayLoaderBuilder::new)
                .model(models()
                        .nested()
                        .parent(models().getExistingFile(parent))
                        .texture(textureKey, texture)
                );
    }
}
