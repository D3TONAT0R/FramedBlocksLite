package xfacthd.framedblocks.common.datagen.providers;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import xfacthd.framedblocks.api.datagen.recipes.AbstractFramedRecipeProvider;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.crafting.camo.CamoApplicationRecipe;
import xfacthd.framedblocks.common.crafting.rotation.ShapeRotationRecipeBuilder;

import java.util.concurrent.CompletableFuture;

public final class FramedRecipeProvider extends AbstractFramedRecipeProvider
{
    private FramedRecipeProvider(HolderLookup.Provider registries, RecipeOutput output)
    {
        super(registries, output);
    }

    @Override
    protected void buildRecipes()
    {
        shapedBuildingBlock(FBContent.BLOCK_FRAMED_CUBE.value(), 4)
                .pattern("PSP")
                .pattern("S S")
                .pattern("PSP")
                .define('P', ItemTags.PLANKS)
                .define('S', Items.STICK)
                .unlockedBy(ItemTags.PLANKS)
                .save(output);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SLAB.value(), 6)
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOUBLE_SLAB.value())
                .pattern("F")
                .pattern("F")
                .define('F', FBContent.BLOCK_FRAMED_SLAB.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB)
                .save(output);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SLAB_EDGE.value(), 6)
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_SLAB.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB)
                .save(output);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SLAB_CORNER.value(), 8)
                .pattern("FF")
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_SLAB.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB)
                .save(output);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_PANEL.value(), 6)
                .pattern("F")
                .pattern("F")
                .pattern("F")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOUBLE_PANEL.value())
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_PANEL.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_PANEL)
                .save(output);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_CORNER_PILLAR.value(), 4)
                .pattern("F")
                .pattern("F")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_STAIRS.value(), 4)
                .pattern("F  ")
                .pattern("FF ")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_HALF_STAIRS.value(), 2)
                .requires(FBContent.ITEM_FRAMED_HAMMER.value())
                .requires(FBContent.BLOCK_FRAMED_STAIRS.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_STAIRS)
                .save(output);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_VERTICAL_STAIRS.value(), 4)
                .pattern("FFF")
                .pattern("FF ")
                .pattern("F  ")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_VERTICAL_HALF_STAIRS.value(), 2)
                .requires(FBContent.BLOCK_FRAMED_VERTICAL_STAIRS.value())
                .requires(FBContent.ITEM_FRAMED_HAMMER.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_VERTICAL_STAIRS)
                .save(output);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_WALL.value(), 6)
                .pattern("FFF")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FENCE.value(), 3)
                .pattern("FSF")
                .pattern("FSF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .define('S', Items.STICK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FENCE_GATE.value())
                .pattern("SFS")
                .pattern("SFS")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .define('S', Items.STICK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOOR.value(), 3)
                .pattern("FF")
                .pattern("FF")
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_IRON_DOOR.value())
                .pattern("IDI")
                .define('D', FBContent.BLOCK_FRAMED_DOOR.value())
                .define('I', Items.IRON_INGOT)
                .unlockedBy(FBContent.BLOCK_FRAMED_DOOR)
                .save(output);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_TRAP_DOOR.value())
                .pattern("FFF")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_SLAB.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB)
                .save(output);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR.value())
                .requires(FBContent.BLOCK_FRAMED_TRAP_DOOR.value())
                .requires(Items.IRON_INGOT)
                .unlockedBy(FBContent.BLOCK_FRAMED_TRAP_DOOR)
                .save(output);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_PRESSURE_PLATE.value())
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_STONE_PRESSURE_PLATE.value())
                .pattern("FF")
                .pattern("SS")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .define('S', Tags.Items.STONES)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_BUTTON.value())
                .requires(FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_STONE_BUTTON.value())
                .requires(FBContent.BLOCK_FRAMED_CUBE.value())
                .requires(Tags.Items.STONES)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_PILLAR.value(), 1)
                .requires(FBContent.BLOCK_FRAMED_CORNER_PILLAR.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CORNER_PILLAR)
                .save(output);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_CORNER_PILLAR.value(), 1)
                .requires(FBContent.BLOCK_FRAMED_PILLAR.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_PILLAR)
                .save(output, key("framed_corner_pillar_from_pillar"));

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_HALF_PILLAR.value(), 1)
                .requires(FBContent.BLOCK_FRAMED_SLAB_CORNER.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB_CORNER)
                .save(output);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_SLAB_CORNER.value(), 1)
                .requires(FBContent.BLOCK_FRAMED_HALF_PILLAR.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_HALF_PILLAR)
                .save(output, key("framed_slab_corner_from_half_pillar"));

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_BOOKSHELF.value())
                .pattern("FFF")
                .pattern("BBB")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .define('B', Items.BOOK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_CHISELED_BOOKSHELF.value())
                .pattern("FFF")
                .pattern("SSS")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .define('S', FBContent.BLOCK_FRAMED_SLAB.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_SLAB.value())
                .requires(FBContent.BLOCK_FRAMED_CUBE.value())
                .requires(FBContent.ITEM_FRAMED_HAMMER.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output, key("framed_slab_from_framed_centered_slab"));

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_PANEL.value())
                .requires(FBContent.BLOCK_FRAMED_CUBE.value())
                .requires(FBContent.ITEM_FRAMED_HAMMER.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output, key("framed_panel_from_framed_centered_panel"));



        shapedRecipe(RecipeCategory.TOOLS, FBContent.BLOCK_FRAMING_SAW.value())
                .pattern(" I ")
                .pattern("FFF")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);



        shapedRecipe(RecipeCategory.TOOLS, FBContent.ITEM_FRAMED_HAMMER.value())
                .pattern(" F ")
                .pattern(" SF")
                .pattern("S  ")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .define('S', Items.STICK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapedRecipe(RecipeCategory.TOOLS, FBContent.ITEM_FRAMED_WRENCH.value())
                .pattern("F F")
                .pattern(" S ")
                .pattern(" S ")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .define('S', Items.STICK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapedRecipe(RecipeCategory.TOOLS, FBContent.ITEM_FRAMED_BLUEPRINT.value())
                .pattern(" F ")
                .pattern("FPF")
                .pattern(" F ")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .define('P', Items.PAPER)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapedRecipe(RecipeCategory.TOOLS, FBContent.ITEM_FRAMED_KEY.value())
                .pattern("SSF")
                .pattern("NN ")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .define('N', Tags.Items.NUGGETS_IRON)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapedRecipe(RecipeCategory.TOOLS, FBContent.ITEM_FRAMED_SCREWDRIVER.value())
                .pattern("S ")
                .pattern(" F")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);

        shapedRecipe(RecipeCategory.MISC, FBContent.ITEM_FRAMED_REINFORCEMENT.value(), 16)
                .pattern("OSO")
                .pattern("SFS")
                .pattern("OSO")
                .define('O', Tags.Items.OBSIDIANS)
                .define('S', Items.STICK)
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(output);




        makeRotationRecipe(FBContent.BLOCK_FRAMED_SLAB, FBContent.BLOCK_FRAMED_PANEL, output);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_STAIRS, FBContent.BLOCK_FRAMED_VERTICAL_STAIRS, output);



        SpecialRecipeBuilder.special(category -> new CamoApplicationRecipe(category, Ingredient.of(Items.BRUSH)))
                .save(output, key("camo_application"));
    }

    private void makeRotationRecipe(Holder<Block> first, Holder<Block> second, RecipeOutput consumer)
    {
        String firstName = Utils.getKeyOrThrow(first).location().getPath();
        String secondName = Utils.getKeyOrThrow(second).location().getPath();

        String name = firstName + "_rotate_to_" + secondName;
        new ShapeRotationRecipeBuilder(this, itemRegistry, second.value())
                .tool(tag(Utils.TOOL_WRENCH))
                .block(first.value())
                .unlockedBy(first)
                .save(consumer, key(name));

        name = secondName + "_rotate_to_" + firstName;
        new ShapeRotationRecipeBuilder(this, itemRegistry, first.value())
                .tool(tag(Utils.TOOL_WRENCH))
                .block(second.value())
                .unlockedBy(second)
                .save(consumer, key(name));
    }

    private static ResourceKey<Recipe<?>> key(String name)
    {
        return ResourceKey.create(Registries.RECIPE, Utils.rl(name));
    }

    public static final class Runner extends RecipeProvider.Runner
    {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
        {
            super(output, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output)
        {
            return new FramedRecipeProvider(registries, output);
        }

        @Override
        public String getName()
        {
            return "FramedBlocks Recipes";
        }
    }
}
