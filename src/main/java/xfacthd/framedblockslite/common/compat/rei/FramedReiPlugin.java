package xfacthd.framedblockslite.common.compat.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ExclusionZones;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.forge.REIPluginClient;
import net.minecraft.resources.ResourceLocation;
import xfacthd.framedblockslite.api.util.Utils;

@REIPluginClient
public final class FramedReiPlugin implements REIClientPlugin
{
    public static final ResourceLocation SAW_ID = Utils.rl("framing_saw");

    @Override
    public void registerCategories(CategoryRegistry registry)
    {
    }

    @Override
    public void registerDisplays(DisplayRegistry registry)
    {
    }

    @Override
    public void registerTransferHandlers(TransferHandlerRegistry registry)
    {
    }

    @Override
    public void registerScreens(ScreenRegistry registry)
    {
    }

    @Override
    public void registerExclusionZones(ExclusionZones zones)
    {
    }
}
