package xfacthd.framedblockslite.common.compat.emi;

import dev.emi.emi.api.*;
import dev.emi.emi.api.recipe.EmiRecipe;
import xfacthd.framedblockslite.common.crafting.*;

@EmiEntrypoint
public final class FramedEmiPlugin implements EmiPlugin
{
    @Override
    public void register(EmiRegistry registry)
    {
        registerRecipes(registry);
    }

    private static void registerRecipes(EmiRegistry registry)
    {
    }

    static int compareRecipes(EmiRecipe recipeOne, EmiRecipe recipeTwo)
    {
        return 0;
    }
}
