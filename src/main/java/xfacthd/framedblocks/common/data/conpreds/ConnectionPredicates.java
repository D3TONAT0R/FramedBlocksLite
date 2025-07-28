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
        put(BlockType.FRAMED_ADJ_DOUBLE_SLAB, DoubleSlabConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_ADJ_DOUBLE_COPYCAT_SLAB, DoubleSlabConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_SLAB_EDGE, new SlabEdgeConnectionPredicate());
        put(BlockType.FRAMED_SLAB_CORNER, new SlabCornerConnectionPredicate());
        put(BlockType.FRAMED_PANEL, new PanelConnectionPredicate());
        put(BlockType.FRAMED_DOUBLE_PANEL, DoublePanelConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_ADJ_DOUBLE_PANEL, DoublePanelConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_ADJ_DOUBLE_COPYCAT_PANEL, DoublePanelConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_CORNER_PILLAR, new CornerPillarConnectionPredicate());
        put(BlockType.FRAMED_STAIRS, new StairsConnectionPredicate());
        put(BlockType.FRAMED_DOUBLE_STAIRS, new DoubleStairsConnectionPredicate());
        put(BlockType.FRAMED_HALF_STAIRS, new HalfStairsConnectionPredicate());
        put(BlockType.FRAMED_DIVIDED_STAIRS, new DividedStairsConnectionPredicate());
        put(BlockType.FRAMED_DOUBLE_HALF_STAIRS, new DoubleHalfStairsConnectionPredicate());
        put(BlockType.FRAMED_SLICED_STAIRS_SLAB, new SlicedStairsSlabConnectionPredicate());
        put(BlockType.FRAMED_SLICED_STAIRS_PANEL, new SlicedStairsPanelConnectionPredicate());
        put(BlockType.FRAMED_SLOPED_STAIRS, new SlopedStairsConnectionPredicate());
        put(BlockType.FRAMED_SLOPED_DOUBLE_STAIRS, new SlopedDoubleStairsConnectionPredicate());
        put(BlockType.FRAMED_SLICED_SLOPED_STAIRS_SLAB, new SlicedSlopedStairsSlabConnectionPredicate());
        put(BlockType.FRAMED_SLICED_SLOPED_STAIRS_SLOPE, new SlicedSlopedStairsSlopeConnectionPredicate());
        put(BlockType.FRAMED_VERTICAL_STAIRS, new VerticalStairsConnectionPredicate());
        put(BlockType.FRAMED_VERTICAL_DOUBLE_STAIRS, new VerticalDoubleStairsConnectionPredicate());
        put(BlockType.FRAMED_VERTICAL_HALF_STAIRS, new VerticalHalfStairsConnectionPredicate());
        put(BlockType.FRAMED_VERTICAL_DIVIDED_STAIRS, new VerticalDividedStairsConnectionPredicate());
        put(BlockType.FRAMED_VERTICAL_DOUBLE_HALF_STAIRS, new VerticalDoubleHalfStairsConnectionPredicate());
        put(BlockType.FRAMED_VERTICAL_SLICED_STAIRS, new VerticalSlicedStairsConnectionPredicate());
        put(BlockType.FRAMED_VERTICAL_SLOPED_STAIRS, new VerticalSlopeStairsConnectionPredicate());
        put(BlockType.FRAMED_VERTICAL_SLOPED_DOUBLE_STAIRS, new VerticalSlopedDoubleStairsConnectionPredicate());
        put(BlockType.FRAMED_VERTICAL_SLICED_SLOPED_STAIRS_PANEL, new VerticalSlicedSlopedStairsPanelConnectionPredicate());
        put(BlockType.FRAMED_VERTICAL_SLICED_SLOPED_STAIRS_SLOPE, new VerticalSlicedSlopedStairsSlopeConnectionPredicate());
        put(BlockType.FRAMED_THREEWAY_CORNER_PILLAR, new ThreewayCornerPillarConnectionPredicate());
        put(BlockType.FRAMED_DOUBLE_THREEWAY_CORNER_PILLAR, new DoubleThreewayCornerPillarConnectionPredicate());
        put(BlockType.FRAMED_WALL, new WallConnectionPredicate());
        put(BlockType.FRAMED_FENCE, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_FENCE_GATE, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_DOOR, DoorConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_IRON_DOOR, DoorConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_TRAPDOOR, TrapdoorConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_IRON_TRAPDOOR, TrapdoorConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_PRESSURE_PLATE, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_WATERLOGGABLE_PRESSURE_PLATE, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_STONE_PRESSURE_PLATE, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_WATERLOGGABLE_STONE_PRESSURE_PLATE, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_OBSIDIAN_PRESSURE_PLATE, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_WATERLOGGABLE_OBSIDIAN_PRESSURE_PLATE, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_GOLD_PRESSURE_PLATE, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_WATERLOGGABLE_GOLD_PRESSURE_PLATE, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_IRON_PRESSURE_PLATE, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_WATERLOGGABLE_IRON_PRESSURE_PLATE, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_BUTTON, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_STONE_BUTTON, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_LARGE_BUTTON, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_LARGE_STONE_BUTTON, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_LEVER, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_SIGN, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_WALL_SIGN, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_HANGING_SIGN, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_WALL_HANGING_SIGN, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_TORCH, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_WALL_TORCH, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_SOUL_TORCH, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_SOUL_WALL_TORCH, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_REDSTONE_TORCH, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_REDSTONE_WALL_TORCH, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_LATTICE_BLOCK, LatticeConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_THICK_LATTICE, LatticeConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_SECRET_STORAGE, ConnectionPredicate.FULL_EDGE);
        put(BlockType.FRAMED_TANK, ConnectionPredicate.FULL_FACE);
        put(BlockType.FRAMED_BARS, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_FANCY_RAIL, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_FANCY_POWERED_RAIL, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_FANCY_DETECTOR_RAIL, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_FANCY_ACTIVATOR_RAIL, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_FLOWER_POT, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_PILLAR, PillarConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_HALF_PILLAR, new HalfPillarConnectionPredicate());
        put(BlockType.FRAMED_POST, PillarConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_BOUNCY_CUBE, ConnectionPredicate.FULL_EDGE);
        put(BlockType.FRAMED_REDSTONE_BLOCK, ConnectionPredicate.FULL_EDGE);
        put(BlockType.FRAMED_PYRAMID_SLAB, ConnectionPredicate.FULL_FACE);
        put(BlockType.FRAMED_TARGET, ConnectionPredicate.FULL_EDGE);
        put(BlockType.FRAMED_GATE, DoorConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_IRON_GATE, DoorConnectionPredicate.INSTANCE);
        put(BlockType.FRAMED_ITEM_FRAME, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_GLOWING_ITEM_FRAME, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_MINI_CUBE, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_ONE_WAY_WINDOW, ConnectionPredicate.FULL_FACE);
        put(BlockType.FRAMED_BOOKSHELF, new BookshelfConnectionPredicate());
        put(BlockType.FRAMED_CHISELED_BOOKSHELF, new ChiseledBookshelfConnectionPredicate());
        put(BlockType.FRAMED_CHECKERED_CUBE, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_CHECKERED_SLAB, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_CHECKERED_PANEL, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_CHAIN, new ChainConnectionPredicate());
        put(BlockType.FRAMED_LANTERN, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_SOUL_LANTERN, ConnectionPredicate.FALSE);
        put(BlockType.FRAMED_LIGHTNING_ROD, ConnectionPredicate.FALSE);
    }
}
