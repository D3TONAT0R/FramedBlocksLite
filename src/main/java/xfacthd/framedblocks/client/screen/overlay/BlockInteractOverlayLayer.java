package xfacthd.framedblocks.client.screen.overlay;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModLoader;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.GuiLayer;
import xfacthd.framedblocks.api.screen.overlay.BlockInteractOverlay;
import xfacthd.framedblocks.api.screen.overlay.RegisterBlockInteractOverlaysEvent;
import xfacthd.framedblocks.api.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public final class BlockInteractOverlayLayer implements GuiLayer
{
    private static final List<BlockInteractOverlay> OVERLAYS = List.of(
            new StateLockOverlay(),
            new ToggleWaterloggableOverlay(),
            new ToggleYSlopeOverlay(),
            new ReinforcementOverlay(),
            new PrismOffsetOverlay(),
            new SplitLineOverlay(),
            new OneWayWindowOverlay(),
            new FrameBackgroundOverlay(),
            new CamoRotationOverlay(),
            new TrapdoorTextureRotationOverlay()
    );

    @Override
    public void render(GuiGraphics graphics, DeltaTracker delta)
    {
        Player player = Objects.requireNonNull(Minecraft.getInstance().player);
        if (player.isSpectator() || Minecraft.getInstance().options.hideGui) return;

        String renderedOverlay = null;
        for (BlockInteractOverlayWrapper overlay : OVERLAYS)
        {
            if (overlay.render(graphics, player))
            {
                if (FMLEnvironment.production) break;

                if (renderedOverlay != null)
                {
                    String msg = "Only one overlay may be active at any time, encountered collision between '%s' and '%s'"
                            .formatted(renderedOverlay, overlay.getName());
                    throw new IllegalStateException(msg);
                }
                renderedOverlay = overlay.getName();
            }
        }
    }

    public static void init()
    {
        Map<String, BlockInteractOverlay> overlays = new HashMap<>();
        ModLoader.postEvent(new RegisterBlockInteractOverlaysEvent((name, overlay) ->
        {
            BlockInteractOverlay prevOverlay = overlays.put(name, overlay);
            if (prevOverlay != null)
            {
                throw new IllegalStateException(String.format(
                        Locale.ROOT, "Duplicate overlay registration for name: %s (old: %s, new: %s)", name, prevOverlay, overlay
                ));
            }
            OVERLAYS.add(new BlockInteractOverlayWrapper(name, overlay));
        }
        ));
    }

    public static void onResourceReload(@SuppressWarnings("unused") ResourceManager manager)
    {
        OVERLAYS.forEach(overlay -> overlay.textWidthValid = false);
    }
}
