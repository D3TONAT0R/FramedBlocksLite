package xfacthd.framedblockslite.common.compat.searchables;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import xfacthd.framedblockslite.FramedBlocks;

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
