package xfacthd.framedblockslite.common.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import xfacthd.framedblockslite.api.datagen.recipes.AbstractFramingSawRecipeProvider;
import xfacthd.framedblockslite.common.FBContent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class FramingSawRecipeProvider extends AbstractFramingSawRecipeProvider
{
    private FramingSawRecipeProvider(HolderLookup.Provider registries, RecipeOutput output)
    {
        super(registries, output);
    }

    @Override
    protected void buildRecipes()
    {
        sawRecipe(FBContent.BLOCK_FRAMED_CUBE)
                .material(CUBE_MATERIAL_VALUE)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_SLAB)
                .material(CUBE_MATERIAL_VALUE / 2)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_DOUBLE_SLAB)
                .material(CUBE_MATERIAL_VALUE)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_SLAB_EDGE)
                .material(CUBE_MATERIAL_VALUE / 4)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_SLAB_CORNER)
                .material(CUBE_MATERIAL_VALUE / 8)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_PANEL)
                .material(CUBE_MATERIAL_VALUE / 2)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_DOUBLE_PANEL)
                .material(CUBE_MATERIAL_VALUE)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_CORNER_PILLAR)
                .material(CUBE_MATERIAL_VALUE / 4)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_STAIRS)
                .material(CUBE_MATERIAL_VALUE / 4 * 3)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_HALF_STAIRS)
                .material(CUBE_MATERIAL_VALUE / 4 * 3 / 2)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_VERTICAL_STAIRS)
                .material(CUBE_MATERIAL_VALUE / 4 * 3)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_VERTICAL_HALF_STAIRS)
                .material(CUBE_MATERIAL_VALUE / 4 * 3 / 2)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_WALL)
                .material(CUBE_MATERIAL_VALUE / 4)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_FENCE)
                .material(CUBE_MATERIAL_VALUE / 8)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_FENCE_GATE)
                .material(CUBE_MATERIAL_VALUE / 4)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_DOOR)
                .material(CUBE_MATERIAL_VALUE / 2)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_IRON_DOOR)
                .material(CUBE_MATERIAL_VALUE / 2)
                .additive(additive(Tags.Items.INGOTS_IRON, 2))
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_TRAP_DOOR)
                .material(CUBE_MATERIAL_VALUE / 4)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR)
                .material(CUBE_MATERIAL_VALUE / 4)
                .additive(additive(Tags.Items.INGOTS_IRON))
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_PRESSURE_PLATE)
                .material(CUBE_MATERIAL_VALUE / 4)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_STONE_PRESSURE_PLATE)
                .material(CUBE_MATERIAL_VALUE / 4)
                .additive(additive(Tags.Items.STONES))
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_BUTTON)
                .material(CUBE_MATERIAL_VALUE / 8)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_STONE_BUTTON)
                .material(CUBE_MATERIAL_VALUE / 8)
                .additive(additive(Tags.Items.STONES))
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_PILLAR)
                .material(CUBE_MATERIAL_VALUE / 4)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_HALF_PILLAR)
                .material(CUBE_MATERIAL_VALUE / 8)
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_BOOKSHELF)
                .material(CUBE_MATERIAL_VALUE * 2)
                .additive(additive(Items.BOOK, 3))
                .save(output);

        sawRecipe(FBContent.BLOCK_FRAMED_CHISELED_BOOKSHELF)
                .material(CUBE_MATERIAL_VALUE * 3)
                .save(output);

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
            return new FramingSawRecipeProvider(registries, output);
        }

        @Override
        public String getName()
        {
            return "Framing Saw Recipes";
        }
    }
}
