package xfacthd.framedblockslite.common.compat.jei;

import me.shedaniel.rei.plugincompatibilities.api.REIPluginCompatIgnore;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import xfacthd.framedblockslite.api.util.Utils;
import xfacthd.framedblockslite.common.compat.jei.camo.CamoCraftingHelper;
import xfacthd.framedblockslite.common.compat.jei.camo.CamoCraftingRecipeExtension;
import xfacthd.framedblockslite.common.compat.jei.camo.CamoRecipeManagerPlugin;
import xfacthd.framedblockslite.common.compat.jei.camo.JeiCamoApplicationRecipe;

@JeiPlugin
@REIPluginCompatIgnore
public final class FramedJeiPlugin implements IModPlugin
{
    private static final ResourceLocation ID = Utils.rl("jei_plugin");
    @Nullable
    private CamoCraftingHelper camoCraftingHelperInstance;

    private CamoCraftingHelper getCamoCraftingHelper()
    {
        if (camoCraftingHelperInstance == null)
        {
            camoCraftingHelperInstance = new CamoCraftingHelper();
        }
        return camoCraftingHelperInstance;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration)
    {
        CamoCraftingHelper camoCraftingHelper = getCamoCraftingHelper();
        camoCraftingHelper.scanForItems(registration.getJeiHelpers().getIngredientManager());

        registration.getCraftingCategory().addExtension(
                JeiCamoApplicationRecipe.class,
                new CamoCraftingRecipeExtension(camoCraftingHelper)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration)
    {
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration)
    {
    }

    @Override
    public void registerAdvanced(IAdvancedRegistration registration)
    {
        registration.addTypedRecipeManagerPlugin(
                RecipeTypes.CRAFTING,
                new CamoRecipeManagerPlugin(getCamoCraftingHelper())
        );
    }

    @Override
    public void onRuntimeUnavailable()
    {
        camoCraftingHelperInstance = null;
    }

    @Override
    public ResourceLocation getPluginUid()
    {
        return ID;
    }
}
