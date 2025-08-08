package xfacthd.framedblockslite.common.data.skippreds;

import xfacthd.framedblockslite.api.predicate.cull.SideSkipPredicate;
import xfacthd.framedblockslite.common.data.BlockType;
import xfacthd.framedblockslite.common.data.skippreds.door.*;
import xfacthd.framedblockslite.common.data.skippreds.pillar.*;
import xfacthd.framedblockslite.common.data.skippreds.slab.*;
import xfacthd.framedblockslite.common.data.skippreds.stairs.*;
import xfacthd.framedblockslite.common.data.skippreds.door.DoorSkipPredicate;
import xfacthd.framedblockslite.common.data.skippreds.door.FenceGateSkipPredicate;
import xfacthd.framedblockslite.common.data.skippreds.door.TrapdoorSkipPredicate;
import xfacthd.framedblockslite.common.data.skippreds.pillar.*;
import xfacthd.framedblockslite.common.data.skippreds.slab.PanelSkipPredicate;
import xfacthd.framedblockslite.common.data.skippreds.slab.SlabCornerSkipPredicate;
import xfacthd.framedblockslite.common.data.skippreds.slab.SlabEdgeSkipPredicate;
import xfacthd.framedblockslite.common.data.skippreds.slab.SlabSkipPredicate;
import xfacthd.framedblockslite.common.data.skippreds.stairs.HalfStairsSkipPredicate;
import xfacthd.framedblockslite.common.data.skippreds.stairs.StairsSkipPredicate;
import xfacthd.framedblockslite.common.data.skippreds.stairs.VerticalHalfStairsSkipPredicate;
import xfacthd.framedblockslite.common.data.skippreds.stairs.VerticalStairsSkipPredicate;
import xfacthd.framedblockslite.common.util.BlockTypeMap;

public final class SideSkipPredicates extends BlockTypeMap<SideSkipPredicate>
{
    public static final SideSkipPredicates PREDICATES = new SideSkipPredicates();

    private SideSkipPredicates()
    {
        super(SideSkipPredicate.FALSE);
    }

    @Override
    protected void fill()
    {
        put(BlockType.FRAMED_CUBE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_SLAB, new SlabSkipPredicate());
        put(BlockType.FRAMED_DOUBLE_SLAB, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_SLAB_EDGE, new SlabEdgeSkipPredicate());
        put(BlockType.FRAMED_SLAB_CORNER, new SlabCornerSkipPredicate());
        put(BlockType.FRAMED_PANEL, new PanelSkipPredicate());
        put(BlockType.FRAMED_DOUBLE_PANEL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_CORNER_PILLAR, new CornerPillarSkipPredicate());
        put(BlockType.FRAMED_STAIRS, new StairsSkipPredicate());
        put(BlockType.FRAMED_HALF_STAIRS, new HalfStairsSkipPredicate());
        put(BlockType.FRAMED_VERTICAL_STAIRS, new VerticalStairsSkipPredicate());
        put(BlockType.FRAMED_VERTICAL_HALF_STAIRS, new VerticalHalfStairsSkipPredicate());
        put(BlockType.FRAMED_WALL, new WallSkipPredicate());
        put(BlockType.FRAMED_FENCE, new FenceSkipPredicate());
        put(BlockType.FRAMED_FENCE_GATE, new FenceGateSkipPredicate());
        put(BlockType.FRAMED_DOOR, DoorSkipPredicate.INSTANCE);
        put(BlockType.FRAMED_IRON_DOOR, DoorSkipPredicate.INSTANCE);
        put(BlockType.FRAMED_TRAPDOOR, TrapdoorSkipPredicate.INSTANCE);
        put(BlockType.FRAMED_IRON_TRAPDOOR, TrapdoorSkipPredicate.INSTANCE);
        put(BlockType.FRAMED_PRESSURE_PLATE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_STONE_PRESSURE_PLATE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_BUTTON, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_STONE_BUTTON, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_PILLAR, new PillarSkipPredicate());
        put(BlockType.FRAMED_HALF_PILLAR, new HalfPillarSkipPredicate());
        put(BlockType.FRAMED_BOOKSHELF, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_CHISELED_BOOKSHELF, SideSkipPredicate.FALSE);
    }
}
