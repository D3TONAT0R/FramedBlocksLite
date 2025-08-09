package xfacthd.framedblockslite.common.config;

import org.jetbrains.annotations.Nullable;
import xfacthd.framedblockslite.api.util.ConfigView;
import xfacthd.framedblockslite.client.model.SolidFrameMode;
import xfacthd.framedblockslite.client.screen.overlay.OverlayDisplayMode;

import java.util.regex.Pattern;

public final class ExtConfigView
{
    public interface Server extends ConfigView.Server
    {
    }

    public interface Client extends ConfigView.Client
    {
        int getGhostRenderOpacity();

        boolean isConTexDisabledFor(String modId);

        /**
         * If true, all recipe permutations will be added to EMI, otherwise only cube->any variants will be added
         */
        boolean showAllRecipePermutationsInEmi();

        SolidFrameMode getSolidFrameMode();

        boolean showButtonPlateOverlay();

        boolean showSpecialCubeOverlay();

        boolean shouldRenderCamoInJade();

        OverlayDisplayMode getStateLockMode();

        OverlayDisplayMode getToggleWaterlogMode();

        OverlayDisplayMode getToggleYSlopeMode();

        OverlayDisplayMode getSplitLineMode();

        OverlayDisplayMode getFrameBackgroundMode();

        OverlayDisplayMode getCamoRotationMode();

        OverlayDisplayMode getTrapdoorTextureRotationMode();
    }

    public interface DevTools extends ConfigView.DevTools
    {
        boolean isDoubleBlockPartHitDebugRendererEnabled();

        boolean isConnectionDebugRendererEnabled();

        boolean isQuadWindingDebugRendererEnabled();

        boolean isStateMergerDebugLoggingEnabled();

        @Nullable
        Pattern getStateMergerDebugFilter();

        boolean isOcclusionShapeDebugRenderingEnabled();
    }



    private ExtConfigView() { }
}
