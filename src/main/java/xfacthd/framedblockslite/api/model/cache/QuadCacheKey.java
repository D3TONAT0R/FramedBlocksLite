package xfacthd.framedblockslite.api.model.cache;

import org.jetbrains.annotations.Nullable;
import xfacthd.framedblockslite.api.camo.CamoContent;

@SuppressWarnings("unused")
public interface QuadCacheKey
{
    CamoContent<?> camo();

    @Nullable
    Object ctCtx();
}
