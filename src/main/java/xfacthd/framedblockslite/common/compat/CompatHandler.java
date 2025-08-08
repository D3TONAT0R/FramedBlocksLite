package xfacthd.framedblockslite.common.compat;

import net.neoforged.bus.api.IEventBus;
import xfacthd.framedblockslite.common.compat.additionalplacements.AdditionalPlacementsCompat;
import xfacthd.framedblockslite.common.compat.amendments.AmendmentsCompat;
import xfacthd.framedblockslite.common.compat.athena.AthenaCompat;
import xfacthd.framedblockslite.common.compat.atlasviewer.AtlasViewerCompat;
import xfacthd.framedblockslite.common.compat.buildinggadgets.BuildingGadgetsCompat;
import xfacthd.framedblockslite.common.compat.create.CreateCompat;
import xfacthd.framedblockslite.common.compat.diagonalblocks.DiagonalBlocksCompat;
import xfacthd.framedblockslite.common.compat.searchables.SearchablesCompat;

public final class CompatHandler
{
    public static void init(IEventBus modBus)
    {
        AdditionalPlacementsCompat.init();
        AmendmentsCompat.init();
        AthenaCompat.init();
        AtlasViewerCompat.init(modBus);
        BuildingGadgetsCompat.init(modBus);
        CreateCompat.init();
        DiagonalBlocksCompat.init(modBus);
        SearchablesCompat.init();
    }

    public static void commonSetup()
    {
        CreateCompat.commonSetup();
    }



    private CompatHandler() { }
}
