package xfacthd.framedblocks.common.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.crafting.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class FramingSawRecipeProvider extends RecipeProvider
{
    public FramingSawRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> providerFuture)
    {
        super(output, providerFuture);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer)
    {
        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_CUBE)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_SLAB)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 2)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_DOUBLE_SLAB)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_SLAB_EDGE)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 4)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_SLAB_CORNER)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 8)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_PANEL)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 2)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_DOUBLE_PANEL)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_CORNER_PILLAR)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 4)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_STAIRS)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 4 * 3)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_HALF_STAIRS)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 4 * 3 / 2)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_VERTICAL_STAIRS)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 4 * 3)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_VERTICAL_HALF_STAIRS)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 4 * 3 / 2)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_WALL)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 4)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_FENCE)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 8)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_FENCE_GATE)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 4)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_DOOR)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 2)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_IRON_DOOR)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 2)
                .additive(FramingSawRecipeAdditive.of(Tags.Items.INGOTS_IRON, 2))
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_TRAP_DOOR)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 4)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 4)
                .additive(FramingSawRecipeAdditive.of(Tags.Items.INGOTS_IRON))
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_PRESSURE_PLATE)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 4)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_STONE_PRESSURE_PLATE)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 4)
                .additive(FramingSawRecipeAdditive.of(Tags.Items.STONES))
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_BUTTON)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 8)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_STONE_BUTTON)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 8)
                .additive(FramingSawRecipeAdditive.of(Tags.Items.STONES))
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_PILLAR)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 4)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_HALF_PILLAR)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE / 8)
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_BOOKSHELF)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE * 2)
                .additive(FramingSawRecipeAdditive.of(Items.BOOK, 3))
                .save(consumer);

        FramingSawRecipeBuilder.builder(FBContent.BLOCK_FRAMED_CHISELED_BOOKSHELF)
                .material(FramingSawRecipe.CUBE_MATERIAL_VALUE * 3)
                .save(consumer);
    }

    @Override
    public String getName()
    {
        return "Framing Saw Recipes";
    }
}
