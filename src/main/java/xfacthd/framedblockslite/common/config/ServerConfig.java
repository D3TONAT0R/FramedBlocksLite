package xfacthd.framedblockslite.common.config;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import xfacthd.framedblockslite.api.util.ConfigView;
import xfacthd.framedblockslite.api.util.Utils;

public final class ServerConfig
{
    public static final ExtConfigView.Server VIEW = (ExtConfigView.Server) ConfigView.Server.INSTANCE;
    private static final ModConfigSpec SPEC;

    private static final String KEY_ALLOW_BLOCK_ENTITIES = "allowBlockEntities";
    private static final String KEY_ONE_WAY_WINDOW_OWNABLE = "oneWayWindowOwnable";
    private static final String KEY_CONSUME_CAMO_ITEM = "consumeCamoItem";
    private static final String KEY_GLOWSTONE_LIGHT_LEVEL = "glowstoneLightLevel";
    private static final String KEY_FIREPROOF_BLOCKS = "fireproofBlocks";
    private static final String KEY_POWERED_SAW_ENERGY_CAPACITY = "energyCapacity";
    private static final String KEY_POWERED_SAW_MAX_RECEIVE = "maxReceive";
    private static final String KEY_POWERED_SAW_CONSUMPTION = "consumption";
    private static final String KEY_POWERED_SAW_RECIPE_DURATION = "craftingDuration";

    public static final String TRANSLATION_ALLOW_BLOCK_ENTITIES = translate(KEY_ALLOW_BLOCK_ENTITIES);
    public static final String TRANSLATION_ONE_WAY_WINDOW_OWNABLE = translate(KEY_ONE_WAY_WINDOW_OWNABLE);
    public static final String TRANSLATION_CONSUME_CAMO_ITEM = translate(KEY_CONSUME_CAMO_ITEM);
    public static final String TRANSLATION_GLOWSTONE_LIGHT_LEVEL = translate(KEY_GLOWSTONE_LIGHT_LEVEL);
    public static final String TRANSLATION_FIREPROOF_BLOCKS = translate(KEY_FIREPROOF_BLOCKS);
    public static final String TRANSLATION_POWERED_SAW_ENERGY_CAPACITY = translate(KEY_POWERED_SAW_ENERGY_CAPACITY);
    public static final String TRANSLATION_POWERED_SAW_MAX_RECEIVE = translate(KEY_POWERED_SAW_MAX_RECEIVE);
    public static final String TRANSLATION_POWERED_SAW_CONSUMPTION = translate(KEY_POWERED_SAW_CONSUMPTION);
    public static final String TRANSLATION_POWERED_SAW_RECIPE_DURATION = translate(KEY_POWERED_SAW_RECIPE_DURATION);

    private static boolean allowBlockEntities = false;
    private static boolean oneWayWindowOwnable = true;
    private static boolean consumeCamoItem = true;
    private static int glowstoneLightLevel = 15;
    private static boolean fireproofBlocks = false;

    private static final ModConfigSpec.BooleanValue ALLOW_BLOCK_ENTITIES_VALUE;
    private static final ModConfigSpec.BooleanValue ONE_WAY_WINDOW_OWNABLE_VALUE;
    private static final ModConfigSpec.BooleanValue CONSUME_CAMO_ITEM_VALUE;
    private static final ModConfigSpec.IntValue GLOWSTONE_LIGHT_LEVEL_VALUE;
    private static final ModConfigSpec.BooleanValue FIREPROOF_BLOCKS_VALUE;

    public static void init(IEventBus modBus, ModContainer modContainer)
    {
        modBus.addListener((ModConfigEvent.Loading event) -> onConfigReloaded(event));
        modBus.addListener((ModConfigEvent.Reloading event) -> onConfigReloaded(event));
        modContainer.registerConfig(ModConfig.Type.SERVER, SPEC);
    }

    static
    {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("general");
        ALLOW_BLOCK_ENTITIES_VALUE = builder
                .comment("Whether blocks with block entities can be placed in framed blocks")
                .translation(TRANSLATION_ALLOW_BLOCK_ENTITIES)
                .define(KEY_ALLOW_BLOCK_ENTITIES, false);
        ONE_WAY_WINDOW_OWNABLE_VALUE = builder
                .comment("If true, only the player who placed the Framed One-Way Window can modify the window direction")
                .translation(TRANSLATION_ONE_WAY_WINDOW_OWNABLE)
                .define(KEY_ONE_WAY_WINDOW_OWNABLE, true);
        CONSUME_CAMO_ITEM_VALUE = builder
                .comment("If true, applying a camo will consume the item and removing the camo will drop it again")
                .translation(TRANSLATION_CONSUME_CAMO_ITEM)
                .define(KEY_CONSUME_CAMO_ITEM, true);
        GLOWSTONE_LIGHT_LEVEL_VALUE = builder
                .comment("The light level to emit when glowstone dust is applied to a framed block")
                .translation(TRANSLATION_GLOWSTONE_LIGHT_LEVEL)
                .defineInRange(KEY_GLOWSTONE_LIGHT_LEVEL, 15, 0, 15);
        FIREPROOF_BLOCKS_VALUE = builder
                .comment("If true, framed blocks are completely fire proof")
                .translation(TRANSLATION_FIREPROOF_BLOCKS)
                .define(KEY_FIREPROOF_BLOCKS, false);
        builder.pop();

        SPEC = builder.build();
    }

    private static String translate(String key)
    {
        return Utils.translateConfig("server", key);
    }

    private static void onConfigReloaded(ModConfigEvent event)
    {
        if (event.getConfig().getType() == ModConfig.Type.SERVER && event.getConfig().getSpec() == SPEC)
        {
            allowBlockEntities = ALLOW_BLOCK_ENTITIES_VALUE.get();
            oneWayWindowOwnable = ONE_WAY_WINDOW_OWNABLE_VALUE.get();
            consumeCamoItem = CONSUME_CAMO_ITEM_VALUE.get();
            glowstoneLightLevel = GLOWSTONE_LIGHT_LEVEL_VALUE.get();
            fireproofBlocks = FIREPROOF_BLOCKS_VALUE.get();
        }
    }

    private ServerConfig() { }



    public static final class ViewImpl implements ExtConfigView.Server
    {
        @Override
        public boolean allowBlockEntities()
        {
            return allowBlockEntities;
        }

        @Override
        public boolean isOneWayWindowOwnable()
        {
            return oneWayWindowOwnable;
        }

        @Override
        public boolean shouldConsumeCamoItem()
        {
            return consumeCamoItem;
        }

        @Override
        public int getGlowstoneLightLevel()
        {
            return glowstoneLightLevel;
        }

        @Override
        public boolean areBlocksFireproof()
        {
            return fireproofBlocks;
        }
    }
}
