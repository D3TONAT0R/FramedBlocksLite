package xfacthd.framedblockslite;

import com.mojang.logging.LogUtils;
import net.neoforged.fml.*;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import xfacthd.framedblockslite.common.config.*;
import xfacthd.framedblockslite.common.config.ClientConfig;
import xfacthd.framedblockslite.common.config.DevToolsConfig;
import xfacthd.framedblockslite.common.config.ServerConfig;
import xfacthd.framedblockslite.common.data.BlueprintBehaviours;
import xfacthd.framedblockslite.common.data.capabilities.CapabilitySetup;
import xfacthd.framedblockslite.common.data.FramedDataMaps;
import xfacthd.framedblockslite.common.data.cullupdate.CullingUpdateTracker;
import xfacthd.framedblockslite.common.data.shapes.ShapeReloader;
import xfacthd.framedblockslite.api.util.FramedConstants;
import xfacthd.framedblockslite.common.FBContent;
import xfacthd.framedblockslite.common.compat.CompatHandler;
import xfacthd.framedblockslite.common.data.StateCacheBuilder;
import xfacthd.framedblockslite.common.data.camo.CamoContainerFactories;
import xfacthd.framedblockslite.common.data.conpreds.ConnectionPredicates;
import xfacthd.framedblockslite.common.data.facepreds.FullFacePredicates;
import xfacthd.framedblockslite.common.data.skippreds.SideSkipPredicates;
import xfacthd.framedblockslite.common.item.FramedBlueprintItem;
import xfacthd.framedblockslite.common.net.NetworkHandler;
import xfacthd.framedblockslite.common.util.EventHandler;

@Mod(FramedConstants.MOD_ID)
@SuppressWarnings("UtilityClassWithPublicConstructor")
public final class FramedBlocks
{
    public static final Logger LOGGER = LogUtils.getLogger();

    public FramedBlocks(IEventBus modBus, ModContainer modContainer)
    {
        FBContent.init(modBus);

        ClientConfig.init(modBus, modContainer);
        ServerConfig.init(modBus, modContainer);
        DevToolsConfig.init(modBus, modContainer);

        modBus.addListener(CapabilitySetup::onRegisterCapabilities);
        modBus.addListener(FramedBlocks::onCommonSetup);
        modBus.addListener(NetworkHandler::onRegisterPayloads);
        modBus.addListener(BlueprintBehaviours::onRegisterBlueprintCopyBehaviours);
        modBus.addListener(FramedDataMaps::onRegisterDataMapTypes);

        IEventBus forgeBus = NeoForge.EVENT_BUS;
        forgeBus.addListener(EventHandler::onBlockLeftClick);
        forgeBus.addListener(EventHandler::onServerShutdown);
        forgeBus.addListener(CullingUpdateTracker::onServerLevelTick);
        forgeBus.addListener(CullingUpdateTracker::onServerShutdown);
        forgeBus.addListener(FramedDataMaps::onDataMapsUpdated);

        if (!FMLEnvironment.production)
        {
            forgeBus.addListener(FramedBlocks::onAddDebugReloadListener);
        }

        FullFacePredicates.PREDICATES.initialize();
        SideSkipPredicates.PREDICATES.initialize();
        ConnectionPredicates.PREDICATES.initialize();

        CompatHandler.init(modBus);

        CrashReportCallables.registerCrashCallable(
                "FramedBlocks BlockEntity Warning",
                FramedBlocks::getBlockEntityWarning,
                ServerConfig.VIEW::allowBlockEntities
        );
    }

    private static void onCommonSetup(final FMLCommonSetupEvent event)
    {
        StateCacheBuilder.ensureStateCachesInitialized();
        FramedBlueprintItem.init();
        CompatHandler.commonSetup();
        CamoContainerFactories.registerCamoFactories();
    }

    private static void onAddDebugReloadListener(final AddReloadListenerEvent event)
    {
        event.addListener(ShapeReloader.INSTANCE);
        event.addListener(StateCacheBuilder.CacheReloader.INSTANCE);
    }

    private static String getBlockEntityWarning()
    {
        return """
               
               \t\tThe 'allowBlockEntities' setting in the framedblockslite-server.toml config file is enabled.
               \t\tIf this crash happened in FramedBlocks code, please try the following solutions before reporting:
               \t\t- If you can identify the block that was used as a camo and resulted in the crash, add the block to the blacklist tag
               \t\t- If you can't identify the block or the crash wasn't fixed, make a backup of the world and disable the mentioned config setting
               \t\tIf the crash still happens, please report it on the FramedBlocks GitHub repository
               """;
    }
}
