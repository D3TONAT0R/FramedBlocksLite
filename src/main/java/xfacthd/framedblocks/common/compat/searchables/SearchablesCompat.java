package xfacthd.framedblocks.common.compat.searchables;

import com.blamejared.searchables.api.SearchableComponent;
import com.blamejared.searchables.api.SearchableType;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import xfacthd.framedblocks.FramedBlocks;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class SearchablesCompat
{
    private static boolean loaded = false;

    public static void init()
    {
        if (ModList.get().isLoaded("searchables"))
        {
            try
            {
                if (FMLEnvironment.dist.isClient())
                {
                    loaded = true;
                }
            }
            catch (Throwable t)
            {
                FramedBlocks.LOGGER.warn("An error occured while initializing Searchables integration!", t);
            }
        }
    }

    public static boolean isLoaded()
    {
        return loaded;
    }

    private SearchablesCompat() { }
}
