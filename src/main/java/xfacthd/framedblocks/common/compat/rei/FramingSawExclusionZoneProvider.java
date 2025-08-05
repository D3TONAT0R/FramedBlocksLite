package xfacthd.framedblocks.common.compat.rei;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.registry.screen.ExclusionZonesProvider;
import xfacthd.framedblocks.client.screen.FramingSawScreen;

import java.util.Collection;
import java.util.List;

public final class FramingSawExclusionZoneProvider implements ExclusionZonesProvider<FramingSawScreen>
{
    @Override
    public Collection<Rectangle> provide(FramingSawScreen screen)
    {
        return List.of();
    }
}
