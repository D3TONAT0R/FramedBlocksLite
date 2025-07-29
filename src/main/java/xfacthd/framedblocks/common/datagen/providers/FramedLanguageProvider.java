package xfacthd.framedblocks.common.datagen.providers;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.neoforged.neoforge.common.data.LanguageProvider;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.camo.empty.EmptyCamoContainer;
import xfacthd.framedblocks.api.util.FramedConstants;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.client.screen.FramingSawScreen;
import xfacthd.framedblocks.client.screen.overlay.impl.CamoRotationOverlay;
import xfacthd.framedblocks.client.screen.overlay.impl.FrameBackgroundOverlay;
import xfacthd.framedblocks.client.screen.overlay.impl.ReinforcementOverlay;
import xfacthd.framedblocks.client.screen.overlay.impl.SplitLineOverlay;
import xfacthd.framedblocks.client.screen.overlay.impl.StateLockOverlay;
import xfacthd.framedblocks.client.screen.overlay.impl.ToggleYSlopeOverlay;
import xfacthd.framedblocks.client.screen.overlay.impl.TrapdoorTextureRotationOverlay;
import xfacthd.framedblocks.common.compat.atlasviewer.AtlasViewerCompat;
import xfacthd.framedblocks.common.compat.jade.JadeCompat;
import xfacthd.framedblocks.common.compat.jei.JeiConstants;
import xfacthd.framedblocks.common.config.*;
import xfacthd.framedblocks.client.util.KeyMappings;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.block.special.FramingSawBlock;
import xfacthd.framedblocks.common.config.ClientConfig;
import xfacthd.framedblocks.common.config.DevToolsConfig;
import xfacthd.framedblocks.common.config.ServerConfig;
import xfacthd.framedblocks.common.crafting.saw.FramingSawRecipeMatchResult;
import xfacthd.framedblocks.common.data.property.NullableDirection;
import xfacthd.framedblocks.common.item.FramedBlueprintItem;
import xfacthd.framedblocks.common.item.block.FramedMirroringBlockItem;

import java.util.Objects;

public final class FramedLanguageProvider extends LanguageProvider
{
    public FramedLanguageProvider(PackOutput output)
    {
        super(output, FramedConstants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations()
    {
        addFramedBlockTranslations();
        addSpecialBlockTranslations();
        addItemTranslations();
        addSpecialTranslations();
        addStatusMessageTranslations();
        addScreenTranslations();
        addTooltipTranslations();
        addOverlayTranslations();
        addConfigTranslations();
    }

    private void addFramedBlockTranslations()
    {
        add(FBContent.BLOCK_FRAMED_CUBE.value(), "Framed Cube");
        add(FBContent.BLOCK_FRAMED_SLAB.value(), "Framed Slab");
        add(FBContent.BLOCK_FRAMED_DOUBLE_SLAB.value(), "Framed Double Slab");
        add(FBContent.BLOCK_FRAMED_SLAB_EDGE.value(), "Framed Slab Edge");
        add(FBContent.BLOCK_FRAMED_SLAB_CORNER.value(), "Framed Slab Corner");
        add(FBContent.BLOCK_FRAMED_PANEL.value(), "Framed Panel");
        add(FBContent.BLOCK_FRAMED_DOUBLE_PANEL.value(), "Framed Double Panel");
        add(FBContent.BLOCK_FRAMED_CORNER_PILLAR.value(), "Framed Corner Pillar");
        add(FBContent.BLOCK_FRAMED_STAIRS.value(), "Framed Stairs");
        add(FBContent.BLOCK_FRAMED_HALF_STAIRS.value(), "Framed Half Stairs");
        add(FBContent.BLOCK_FRAMED_VERTICAL_STAIRS.value(), "Framed Vertical Stairs");
        add(FBContent.BLOCK_FRAMED_VERTICAL_HALF_STAIRS.value(), "Framed Vertical Half Stairs");
        add(FBContent.BLOCK_FRAMED_WALL.value(), "Framed Wall");
        add(FBContent.BLOCK_FRAMED_FENCE.value(), "Framed Fence");
        add(FBContent.BLOCK_FRAMED_FENCE_GATE.value(), "Framed Fence Gate");
        add(FBContent.BLOCK_FRAMED_DOOR.value(), "Framed Door");
        add(FBContent.BLOCK_FRAMED_IRON_DOOR.value(), "Framed Iron Door");
        add(FBContent.BLOCK_FRAMED_TRAP_DOOR.value(), "Framed Trapdoor");
        add(FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR.value(), "Framed Iron Trapdoor");
        add(FBContent.BLOCK_FRAMED_PRESSURE_PLATE.value(), "Framed Pressure Plate");
        add(FBContent.BLOCK_FRAMED_STONE_PRESSURE_PLATE.value(), "Framed Stone Pressure Plate");
        add(FBContent.BLOCK_FRAMED_BUTTON.value(), "Framed Button");
        add(FBContent.BLOCK_FRAMED_STONE_BUTTON.value(), "Framed Stone Button");
        add(FBContent.BLOCK_FRAMED_LARGE_BUTTON.value(), "Large Framed Button");
        add(FBContent.BLOCK_FRAMED_LARGE_STONE_BUTTON.value(), "Large Framed Stone Button");
        add(FBContent.BLOCK_FRAMED_PILLAR.value(), "Framed Pillar");
        add(FBContent.BLOCK_FRAMED_HALF_PILLAR.value(), "Framed Half Pillar");
        add(FBContent.BLOCK_FRAMED_GATE.value(), "Framed Gate");
        add(FBContent.BLOCK_FRAMED_IRON_GATE.value(), "Framed Iron Gate");
        add(FBContent.BLOCK_FRAMED_BOOKSHELF.value(), "Framed Bookshelf");
        add(FBContent.BLOCK_FRAMED_CHISELED_BOOKSHELF.value(), "Framed Chiseled Bookshelf");
    }

    private void addSpecialBlockTranslations()
    {
        add(FBContent.BLOCK_FRAMING_SAW.value(), "Framing Saw");
    }

    private void addItemTranslations()
    {
        add(FBContent.ITEM_FRAMED_HAMMER.value(), "Framed Hammer");
        add(FBContent.ITEM_FRAMED_WRENCH.value(), "Framed Wrench");
        add(FBContent.ITEM_FRAMED_BLUEPRINT.value(), "Framed Blueprint");
        add(FBContent.ITEM_FRAMED_KEY.value(), "Framed Key");
        add(FBContent.ITEM_FRAMED_SCREWDRIVER.value(), "Framed Screwdriver");
        add(FBContent.ITEM_FRAMED_REINFORCEMENT.value(), "Framed Reinforcement");
    }

    private void addSpecialTranslations()
    {
        add(KeyMappings.KEY_CATEGORY, "FramedBlocks");
        add(KeyMappings.KEYMAPPING_UPDATE_CULLING.get().getName(), "Update culling cache");
        add(KeyMappings.KEYMAPPING_WIPE_CACHE.get().getName(), "Clear model cache");

        add(FBContent.MAIN_TAB.value().getDisplayName(), "FramedBlocks");

        add(EmptyCamoContainer.CAMO_NAME, "Empty");

        add(JeiConstants.MSG_INVALID_RECIPE, "Invalid recipe");
        add(JeiConstants.MSG_TRANSFER_NOT_IMPLEMENTED, "Transfer not implemented, no items will be transferred");
        add(JeiConstants.MSG_SUPPORTS_MOST_CAMOS, "Supports most items that can be used to apply camos by block interaction");

        add(AtlasViewerCompat.LABEL_TEXTURE, "Texture");
        add(AtlasViewerCompat.LABEL_FRAMES, "Frames");

        add(JadeCompat.configTranslation(JadeCompat.ID_FRAMED_BLOCK), "FramedBlocks camo");
        add(JadeCompat.configTranslation(JadeCompat.ID_ITEM_FRAME), "Framed Item Frame");
        add(JadeCompat.LABEL_CAMO, "Camo: %s");
        add(JadeCompat.LABEL_CAMO_ONE, "Camo one: %s");
        add(JadeCompat.LABEL_CAMO_TWO, "Camo two: %s");

        add(Utils.TOOL_WRENCH, "Wrenches");
        add(Utils.DISABLE_INTANGIBLE, "Disable Intangibility");
        add(Utils.GROUP_FULL_CUBE, "Full Framed Blocks");
    }

    private void addStatusMessageTranslations()
    {
        add(FramedBlockEntity.MSG_BLACKLISTED, "This block is disallowed as a camo!");
        add(FramedBlockEntity.MSG_BLOCK_ENTITY, "Blocks with BlockEntities cannot be inserted into framed blocks!");
        add(FramedBlockEntity.MSG_NON_SOLID, "Untagged non-solid blocks cannot be inserted into framed blocks!");

        add(IFramedBlock.LOCK_MESSAGE, "The state of this block is now %s");
    }

    private void addScreenTranslations()
    {
        add(FramingSawBlock.SAW_MENU_TITLE, "Framing Saw");
        add(FramingSawScreen.TOOLTIP_MATERIAL, "Material value: %s");
        add(FramingSawScreen.TOOLTIP_LOOSE_ADDITIVE, "Item was crafted with additive ingredients, these will be lost");
        add(FramingSawScreen.TOOLTIP_HAVE_X_BUT_NEED_Y_ITEM, "Have %s, but need %s");
        add(FramingSawScreen.TOOLTIP_HAVE_X_BUT_NEED_Y_ITEM_MULTI, "Have %s, but need %s or listed alternatives");
        add(FramingSawScreen.TOOLTIP_HAVE_X_BUT_NEED_Y_TAG, "Have %s, but need any %s");
        add(FramingSawScreen.TOOLTIP_HAVE_X_BUT_NEED_Y_ITEM_COUNT, "Have %s item(s), but need at least %s item(s)");
        add(FramingSawScreen.TOOLTIP_HAVE_X_BUT_NEED_Y_MATERIAL_COUNT, "Have %s material, but need at least %s material");
        add(FramingSawScreen.TOOLTIP_OUTPUT_COUNT, "Result size: %s, max size: %s");
        add(FramingSawScreen.TOOLTIP_HAVE_ITEM_NONE, "none");
        add(FramingSawScreen.TOOLTIP_PRESS_TO_SHOW, "Press [%s] to show all possible items");
        add(FramingSawScreen.MSG_HINT_SEARCH, "Search...");
        add(FramingSawRecipeMatchResult.SUCCESS.translation(), "Craftable");
        add(FramingSawRecipeMatchResult.CAMO_PRESENT.translation(), "Input item must not have any camo");
        add(FramingSawRecipeMatchResult.MATERIAL_VALUE.translation(), "Insufficient input material available");
        add(FramingSawRecipeMatchResult.MATERIAL_LCM.translation(), "Too few input items to evenly convert to this output");
        add(FramingSawRecipeMatchResult.OUTPUT_SIZE.translation(), "Result count exceeds maximum result stack size");
        add(FramingSawRecipeMatchResult.MISSING_ADDITIVE_0.translation(), "Missing additive ingredient in the first slot");
        add(FramingSawRecipeMatchResult.MISSING_ADDITIVE_1.translation(), "Missing additive ingredient in the second slot");
        add(FramingSawRecipeMatchResult.MISSING_ADDITIVE_2.translation(), "Missing additive ingredient in the third slot");
        add(FramingSawRecipeMatchResult.UNEXPECTED_ADDITIVE_0.translation(), "Unexpected additive ingredient present in the first slot");
        add(FramingSawRecipeMatchResult.UNEXPECTED_ADDITIVE_1.translation(), "Unexpected additive ingredient present in the second slot");
        add(FramingSawRecipeMatchResult.UNEXPECTED_ADDITIVE_2.translation(), "Unexpected additive ingredient present in the third slot");
        add(FramingSawRecipeMatchResult.INCORRECT_ADDITIVE_0.translation(), "Incorrect additive ingredient present in the first slot");
        add(FramingSawRecipeMatchResult.INCORRECT_ADDITIVE_1.translation(), "Incorrect additive ingredient present in the second slot");
        add(FramingSawRecipeMatchResult.INCORRECT_ADDITIVE_2.translation(), "Incorrect additive ingredient present in the third slot");
        add(FramingSawRecipeMatchResult.INSUFFICIENT_ADDITIVE_0.translation(), "Insufficient amount of additive ingredient present in the first slot");
        add(FramingSawRecipeMatchResult.INSUFFICIENT_ADDITIVE_1.translation(), "Insufficient amount of additive ingredient present in the second slot");
        add(FramingSawRecipeMatchResult.INSUFFICIENT_ADDITIVE_2.translation(), "Insufficient amount of additive ingredient present in the third slot");
    }

    private void addTooltipTranslations()
    {
        add(FramedBlueprintItem.CONTAINED_BLOCK, "Contained Block: %s");
        add(FramedBlueprintItem.CAMO_BLOCK, "Camo Block: %s");
        add(FramedBlueprintItem.IS_ILLUMINATED, "Illuminated: %s");
        add(FramedBlueprintItem.IS_INTANGIBLE, "Intangible: %s");
        add(FramedBlueprintItem.IS_REINFORCED, "Reinforced: %s");
        add(FramedBlueprintItem.MISSING_MATERIALS, "[Framed Blueprint] Missing required materials:");
        add(FramedBlueprintItem.BLOCK_NONE, "None");
        add(FramedBlueprintItem.BLOCK_INVALID, "Invalid");
        add(FramedBlueprintItem.FALSE, "false");
        add(FramedBlueprintItem.TRUE, "true");
        add(FramedBlueprintItem.CANT_COPY, "[Framed Blueprint] This block can currently not be copied!");
        add(FramedBlueprintItem.CANT_PLACE_FLUID_CAMO, "[Framed Blueprint] Copying blocks with fluid camos is currently not possible!");
        add(IFramedBlock.CAMO_LABEL, "Camo: %s");
        add(IFramedBlock.CAMO_LABEL_MULTI, "Camos: %s");
        add(FramedMirroringBlockItem.PLACE_UPSIDE_DOWN, "Hold sneak key to place upside down");
    }

    private void addOverlayTranslations()
    {
        add(StateLockOverlay.LOCK_MESSAGE, "State %s");
        add(IFramedBlock.STATE_LOCKED, "locked");
        add(IFramedBlock.STATE_UNLOCKED, "unlocked");

        add(ToggleYSlopeOverlay.SLOPE_MESSAGE, "Block uses %s faces for vertical sloped faces.");
        add(ToggleYSlopeOverlay.TOGGLE_MESSAGE, "Hit with a Framed Wrench to switch to %s faces");
        add(ToggleYSlopeOverlay.SLOPE_HOR, "horizontal");
        add(ToggleYSlopeOverlay.SLOPE_VERT, "vertical");
        add(ToggleYSlopeOverlay.SLOPE_MESSAGE_ALT, "Block uses the %s face for horizontal sloped faces.");
        add(ToggleYSlopeOverlay.TOGGLE_MESSAGE_ALT, "Hit with a Framed Wrench to switch to the %s face");
        add(ToggleYSlopeOverlay.SLOPE_FRONT, "front");
        add(ToggleYSlopeOverlay.SLOPE_SIDE, "right");

        add(ReinforcementOverlay.REINFORCE_MESSAGE, "Block is %s.");
        add(ReinforcementOverlay.STATE_NOT_REINFORCED, "not reinforced");
        add(ReinforcementOverlay.STATE_REINFORCED, "reinforced");

        add(SplitLineOverlay.SPLIT_LINE_FALSE, "Split-line of the deformed face runs along the steep diagonal.");
        add(SplitLineOverlay.SPLIT_LINE_TRUE, "Split-line of the deformed face runs along the shallow diagonal.");
        add(SplitLineOverlay.MSG_SWITCH_SPLIT_LINE, "Hit with a Framed Wrench to switch the orientation of the split-line");

        add(FrameBackgroundOverlay.LINE_USE_CAMO_BG, "Framed Item Frame uses the camo as background");
        add(FrameBackgroundOverlay.LINE_USE_LEATHER_BG, "Framed Item Frame uses leather as background");
        add(FrameBackgroundOverlay.LINE_SET_CAMO_BG, "Hit with a Framed Hammer to use the camo as background");
        add(FrameBackgroundOverlay.LINE_SET_LEATHER_BG, "Hit with a Framed Hammer to use leather as background");

        add(CamoRotationOverlay.ROTATEABLE_FALSE, "The targetted camo cannot be rotated");
        add(CamoRotationOverlay.ROTATEABLE_TRUE, "The targetted camo can be rotated");

        add(TrapdoorTextureRotationOverlay.ROTATING_FALSE, "Camo texture will not rotate when opening the trapdoor");
        add(TrapdoorTextureRotationOverlay.ROTATING_TRUE, "Camo texture will rotate when opening the trapdoor");
        add(TrapdoorTextureRotationOverlay.ROTATING_TOGGLE, "Hit with a Framed Hammer to toggle texture rotation");
    }

    private void addConfigTranslations()
    {
        add("framedblocks.configuration.general", "General");
        add("framedblocks.configuration.powered_framing_saw", "Powered Framing Saw");
        add(ServerConfig.TRANSLATION_ALLOW_BLOCK_ENTITIES, "Allow BlockEntities");
        add(ServerConfig.TRANSLATION_ENABLE_INTANGIBILITY, "Enable intangibility feature");
        add(ServerConfig.TRANSLATION_ONE_WAY_WINDOW_OWNABLE, "One-Way Window ownability");
        add(ServerConfig.TRANSLATION_CONSUME_CAMO_ITEM, "Consume camo item");
        add(ServerConfig.TRANSLATION_GLOWSTONE_LIGHT_LEVEL, "Glowstone Light Level");
        add(ServerConfig.TRANSLATION_FIREPROOF_BLOCKS, "Fireproof blocks");
        add(ServerConfig.TRANSLATION_POWERED_SAW_ENERGY_CAPACITY, "Energy Capacity");
        add(ServerConfig.TRANSLATION_POWERED_SAW_MAX_RECEIVE, "Max input");
        add(ServerConfig.TRANSLATION_POWERED_SAW_CONSUMPTION, "Consumption");
        add(ServerConfig.TRANSLATION_POWERED_SAW_RECIPE_DURATION, "Crafting Duration");

        //add("framedblocks.configuration.general", "General");
        add("framedblocks.configuration.overlay", "Overlays");
        add(ClientConfig.TRANSLATION_SHOW_GHOST_BLOCKS, "Show ghost blocks");
        add(ClientConfig.TRANSLATION_ALT_GHOST_RENDERER, "Use alternative placement preview renderer");
        add(ClientConfig.TRANSLATION_GHOST_RENDER_OPACITY, "Placement preview opacity");
        add(ClientConfig.TRANSLATION_FANCY_HITBOXES, "Fancy hitboxes");
        add(ClientConfig.TRANSLATION_DETAILED_CULLING, "Detailed culling");
        add(ClientConfig.TRANSLATION_USE_DISCRETE_UV_STEPS, "Use discrete UV steps");
        add(ClientConfig.TRANSLATION_CON_TEX_MODE, "Connected textures mode");
        add(ClientConfig.TRANSLATION_CON_TEX_DISABLE_LIST, "Disable connected textures support");
        add(ClientConfig.TRANSLATION_CAMO_MESSAGE_VERBOSITY, "Disallowed camo message verbosity");
        add(ClientConfig.TRANSLATION_FORCE_AO_ON_GLOWING_BLOCKS, "Force ambient occlusion on glowing framed blocks");
        add(ClientConfig.TRANSLATION_RENDER_ITEM_MODELS_WITH_CAMO, "Render item models with camo");
        add(ClientConfig.TRANSLATION_SHOW_ALL_RECIPE_PERMUTATIONS_IN_EMI, "Show all Framing Saw recipe permutations in EMI");
        add(ClientConfig.TRANSLATION_SOLID_FRAME_MODE, "Solid frame mode");
        add(ClientConfig.TRANSLATION_SHOW_BUTTON_PLATE_OVERLAY, "Show button and pressure plate type overlay");
        add(ClientConfig.TRANSLATION_SHOW_SPECIAL_CUBE_OVERLAY, "Show special cube type overlay");
        add(ClientConfig.TRANSLATION_RENDER_CAMO_IN_JADE, "Render camo in Jade overlay");
        add(ClientConfig.TRANSLATION_STATE_LOCK_MODE, "State lock overlay: Display mode");
        add(ClientConfig.TRANSLATION_TOGGLE_WATERLOG_MODE, "Toggle waterloggable overlay: Display mode");
        add(ClientConfig.TRANSLATION_TOGGLE_Y_SLOPE_MODE, "Toggle Y slope overlay: Display mode");
        add(ClientConfig.TRANSLATION_REINFORCEMENT_MODE, "Reinforcement overlay: Display mode");
        add(ClientConfig.TRANSLATION_PRISM_OFFSET_MODE, "Prism offset overlay: Display mode");
        add(ClientConfig.TRANSLATION_SPLIT_LINES_MODE, "Collapsible block split lines overlay: Display mode");
        add(ClientConfig.TRANSLATION_ONE_WAY_WINDOW_MODE, "One-Way Window overlay: Display mode");
        add(ClientConfig.TRANSLATION_FRAME_BACKGROUND_MODE, "Item Frame Background overlay: Display mode");
        add(ClientConfig.TRANSLATION_CAMO_ROTATION_MODE, "Camo Rotation overlay: Display mode");
        add(ClientConfig.TRANSLATION_TRAPDOOR_TEXTURE_ROTATION_MODE, "Trapdoor Texture Rotation overlay: Display mode");

        add("framedblocks.configuration.section.framedblocks.devtools.toml", "Dev Tools Settings");
        add("framedblocks.configuration.section.framedblocks.devtools.toml.title", "FramedBlocks Dev Tools Configuration");
        add(DevToolsConfig.TRANSLATION_DOUBLE_BLOCK_PART_DEBUG, "Double-block part debug");
        add(DevToolsConfig.TRANSLATION_CONNECTION_DEBUG, "ConnectionPredicate debug");
        add(DevToolsConfig.TRANSLATION_QUAD_WINDING_DEBUG, "Quad-winding debug");
        add(DevToolsConfig.TRANSLATION_STATE_MERGER_DEBUG, "StateMerger debug");
        add(DevToolsConfig.TRANSLATION_STATE_MERGER_DEBUG_FILTER, "StateMerger debug filter");
        add(DevToolsConfig.TRANSLATION_OCCLUSION_SHAPE_DEBUG, "Occlusion shape debug");
    }

    private void add(Component key, String value)
    {
        ComponentContents contents = key.getContents();
        if (contents instanceof TranslatableContents translatable)
        {
            add(translatable.getKey(), value);
        }
        else
        {
            add(key.getString(), value);
        }
    }
}