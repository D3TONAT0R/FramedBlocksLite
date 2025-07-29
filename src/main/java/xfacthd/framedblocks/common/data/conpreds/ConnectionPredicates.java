package xfacthd.framedblocks.common.data.conpreds;

import xfacthd.framedblocks.api.predicate.contex.ConnectionPredicate;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.conpreds.door.*;
import xfacthd.framedblocks.common.data.conpreds.misc.*;
import xfacthd.framedblocks.common.data.conpreds.pillar.*;
import xfacthd.framedblocks.common.data.conpreds.slab.*;
import xfacthd.framedblocks.common.data.conpreds.stairs.*;
import xfacthd.framedblocks.common.util.BlockTypeMap;

public final class ConnectionPredicates extends BlockTypeMap<ConnectionPredicate>
{
    public static final ConnectionPredicates PREDICATES = new ConnectionPredicates();

    private ConnectionPredicates()
    {
        super(ConnectionPredicate.FALSE);
    }

    @Override
    protected void fill()
    {
        put(BlockType.FRAMED_CUBE, ConnectionPredicate.FULL_EDGE);
        put(BlockType.FRAMED_SLAB, new SlabConnectionPredicate());
        put(BlockType.FRAMED_DOUBLE_SLAB, DoubleSlabConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_SLAB_EDGE, new SlabEdgeConnectionPredicate());
        put(BlockType.FRAMED_SLAB_CORNER, new SlabCornerConnectionPredicate());
        put(BlockType.FRAMED_PANEL, new PanelConnectionPredicate());
        put(BlockType.FRAMED_DOUBLE_PANEL, DoublePanelConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_CORNER_PILLAR, new CornerPillarConnectionPredicate());
        put(BlockType.FRAMED_STAIRS, new StairsConnectionPredicate());
        put(BlockType.FRAMED_HALF_STAIRS, new HalfStairsConnectionPredicate());
        put(BlockType.FRAMED_VERTICAL_STAIRS, new VerticalStairsConnectionPredicate());
        put(BlockType.FRAMED_VERTICAL_HALF_STAIRS, new VerticalHalfStairsConnectionPredicate());
        put(BlockType.FRAMED_WALL, new WallConnectionPredicate());
        put(BlockType.FRAMED_FENCE, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_FENCE_GATE, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_DOOR, DoorConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_IRON_DOOR, DoorConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_TRAPDOOR, TrapdoorConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_IRON_TRAPDOOR, TrapdoorConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_PRESSURE_PLATE, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_STONE_PRESSURE_PLATE, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_BUTTON, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_STONE_BUTTON, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_PILLAR, PillarConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_HALF_PILLAR, new HalfPillarConnectionPredicate());
        put(BlockType.FRAMED_BOOKSHELF, new BookshelfConnectionPredicate());
        put(BlockType.FRAMED_CHISELED_BOOKSHELF, new ChiseledBookshelfConnectionPredicate());
    }
}
