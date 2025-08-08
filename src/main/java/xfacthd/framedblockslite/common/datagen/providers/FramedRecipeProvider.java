package xfacthd.framedblockslite.common.datagen.providers;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import xfacthd.framedblockslite.api.util.Utils;
import xfacthd.framedblockslite.common.FBContent;
import xfacthd.framedblockslite.common.crafting.CamoApplicationRecipe;
import xfacthd.framedblockslite.common.crafting.ShapeRotationRecipeBuilder;
import xfacthd.framedblockslite.common.datagen.builders.recipe.ExtShapedRecipeBuilder;
import xfacthd.framedblockslite.common.datagen.builders.recipe.ExtShapelessRecipeBuilder;

import java.util.concurrent.CompletableFuture;

public final class FramedRecipeProvider extends RecipeProvider
{
    private PackOutput output;

    public FramedRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> providerFuture)
    {
        super(output, providerFuture);
        this.output = output;
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer)
    {
        shapedBuildingBlock(FBContent.BLOCK_FRAMED_CUBE.value(), 4)
                .pattern("PSP")
                .pattern("S S")
                .pattern("PSP")
                .define('P', ItemTags.PLANKS)
                .define('S', Items.STICK)
                .unlockedBy(ItemTags.PLANKS)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SLAB.value(), 6)
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOUBLE_SLAB.value())
                .pattern("F")
                .pattern("F")
                .define('F', FBContent.BLOCK_FRAMED_SLAB.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SLAB_EDGE.value(), 6)
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_SLAB.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SLAB_CORNER.value(), 8)
                .pattern("FF")
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_SLAB.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_PANEL.value(), 6)
                .pattern("F")
                .pattern("F")
                .pattern("F")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOUBLE_PANEL.value())
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_PANEL.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_CORNER_PILLAR.value(), 4)
                .pattern("F")
                .pattern("F")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_STAIRS.value(), 4)
                .pattern("F  ")
                .pattern("FF ")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_HALF_STAIRS.value(), 2)
                .requires(FBContent.ITEM_FRAMED_HAMMER.value())
                .requires(FBContent.BLOCK_FRAMED_STAIRS.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_STAIRS)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_VERTICAL_STAIRS.value(), 4)
                .pattern("FFF")
                .pattern("FF ")
                .pattern("F  ")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_VERTICAL_HALF_STAIRS.value(), 2)
                .requires(FBContent.BLOCK_FRAMED_VERTICAL_STAIRS.value())
                .requires(FBContent.ITEM_FRAMED_HAMMER.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_VERTICAL_STAIRS)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_WALL.value(), 6)
                .pattern("FFF")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FENCE.value(), 3)
                .pattern("FSF")
                .pattern("FSF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .define('S', Items.STICK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FENCE_GATE.value())
                .pattern("SFS")
                .pattern("SFS")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .define('S', Items.STICK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOOR.value(), 3)
                .pattern("FF")
                .pattern("FF")
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_IRON_DOOR.value())
                .pattern("IDI")
                .define('D', FBContent.BLOCK_FRAMED_DOOR.value())
                .define('I', Items.IRON_INGOT)
                .unlockedBy(FBContent.BLOCK_FRAMED_DOOR)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_TRAP_DOOR.value())
                .pattern("FFF")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_SLAB.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR.value())
                .requires(FBContent.BLOCK_FRAMED_TRAP_DOOR.value())
                .requires(Items.IRON_INGOT)
                .unlockedBy(FBContent.BLOCK_FRAMED_TRAP_DOOR)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_PRESSURE_PLATE.value())
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_STONE_PRESSURE_PLATE.value())
                .pattern("FF")
                .pattern("SS")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .define('S', Tags.Items.STONES)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_BUTTON.value())
                .requires(FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_STONE_BUTTON.value())
                .requires(FBContent.BLOCK_FRAMED_CUBE.value())
                .requires(Tags.Items.STONES)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_PILLAR.value(), 1)
                .requires(FBContent.BLOCK_FRAMED_CORNER_PILLAR.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CORNER_PILLAR)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_CORNER_PILLAR.value(), 1)
                .requires(FBContent.BLOCK_FRAMED_PILLAR.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_PILLAR)
                .save(consumer, Utils.rl("framed_corner_pillar_from_pillar"));

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_HALF_PILLAR.value(), 1)
                .requires(FBContent.BLOCK_FRAMED_SLAB_CORNER.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB_CORNER)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_SLAB_CORNER.value(), 1)
                .requires(FBContent.BLOCK_FRAMED_HALF_PILLAR.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_HALF_PILLAR)
                .save(consumer, Utils.rl("framed_slab_corner_from_half_pillar"));

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_BOOKSHELF.value())
                .pattern("FFF")
                .pattern("BBB")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .define('B', Items.BOOK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_CHISELED_BOOKSHELF.value())
                .pattern("FFF")
                .pattern("SSS")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .define('S', FBContent.BLOCK_FRAMED_SLAB.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);



        shapedRecipe(RecipeCategory.TOOLS, FBContent.ITEM_FRAMED_HAMMER.value())
                .pattern(" F ")
                .pattern(" SF")
                .pattern("S  ")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .define('S', Items.STICK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedRecipe(RecipeCategory.TOOLS, FBContent.ITEM_FRAMED_WRENCH.value())
                .pattern("F F")
                .pattern(" S ")
                .pattern(" S ")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .define('S', Items.STICK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedRecipe(RecipeCategory.TOOLS, FBContent.ITEM_FRAMED_BLUEPRINT.value())
                .pattern(" F ")
                .pattern("FPF")
                .pattern(" F ")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .define('P', Items.PAPER)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedRecipe(RecipeCategory.TOOLS, FBContent.ITEM_FRAMED_KEY.value())
                .pattern("SSF")
                .pattern("NN ")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .define('N', Tags.Items.NUGGETS_IRON)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedRecipe(RecipeCategory.TOOLS, FBContent.ITEM_FRAMED_SCREWDRIVER.value())
                .pattern("S ")
                .pattern(" F")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('F', FBContent.BLOCK_FRAMED_CUBE.value())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);




        makeRotationRecipe(FBContent.BLOCK_FRAMED_SLAB, FBContent.BLOCK_FRAMED_PANEL, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_STAIRS, FBContent.BLOCK_FRAMED_VERTICAL_STAIRS, consumer);



        SpecialRecipeBuilder.special(category -> new CamoApplicationRecipe(category, Ingredient.of(Items.BRUSH)))
                .save(consumer, Utils.rl("camo_application"));
    }

    private static void makeRotationRecipe(Holder<Block> first, Holder<Block> second, RecipeOutput consumer)
    {
        String firstName = Utils.getKeyOrThrow(first).location().getPath();
        String secondName = Utils.getKeyOrThrow(second).location().getPath();

        String name = firstName + "_rotate_to_" + secondName;
        new ShapeRotationRecipeBuilder(second.value())
                .tool(Ingredient.of(Utils.TOOL_WRENCH))
                .block(first.value())
                .unlockedBy(first)
                .save(consumer, Utils.rl(name));

        name = secondName + "_rotate_to_" + firstName;
        new ShapeRotationRecipeBuilder(first.value())
                .tool(Ingredient.of(Utils.TOOL_WRENCH))
                .block(second.value())
                .unlockedBy(second)
                .save(consumer, Utils.rl(name));
    }

    private static ExtShapedRecipeBuilder shapedBuildingBlock(ItemLike output)
    {
        return shapedBuildingBlock(output, 1);
    }

    private static ExtShapedRecipeBuilder shapedBuildingBlock(ItemLike output, int count)
    {
        return shapedRecipe(RecipeCategory.BUILDING_BLOCKS, output, count);
    }

    private static ExtShapedRecipeBuilder shapedRecipe(RecipeCategory category, ItemLike output)
    {
        return shapedRecipe(category, output, 1);
    }

    private static ExtShapedRecipeBuilder shapedRecipe(RecipeCategory category, ItemLike output, int count)
    {
        return new ExtShapedRecipeBuilder(category, output, count);
    }

    private static ExtShapelessRecipeBuilder shapelessBuildingBlock(ItemLike output)
    {
        return shapelessBuildingBlock(output, 1);
    }

    private static ExtShapelessRecipeBuilder shapelessBuildingBlock(ItemLike output, int count)
    {
        return shapelessRecipe(RecipeCategory.BUILDING_BLOCKS, output, count);
    }

    private static ExtShapelessRecipeBuilder shapelessRecipe(RecipeCategory category, ItemLike output)
    {
        return shapelessRecipe(category, output, 1);
    }

    private static ExtShapelessRecipeBuilder shapelessRecipe(RecipeCategory category, ItemLike output, int count)
    {
        return new ExtShapelessRecipeBuilder(category, output, count);
    }
}
