package xfacthd.framedblocks.common.data.facepreds;

import xfacthd.framedblocks.api.predicate.fullface.FullFacePredicate;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.facepreds.door.*;
import xfacthd.framedblocks.common.data.facepreds.stairs.*;
import xfacthd.framedblocks.common.util.BlockTypeMap;

public final class FullFacePredicates extends BlockTypeMap<FullFacePredicate>
{
    public static final FullFacePredicates PREDICATES = new FullFacePredicates();

    private FullFacePredicates()
    {
        super(FullFacePredicate.FALSE);
    }

    @Override
    protected void fill()
    {
        put(BlockType.FRAMED_CUBE, FullFacePredicate.TRUE);
        put(BlockType.FRAMED_SLAB, FullFacePredicate.TOP);
        put(BlockType.FRAMED_DOUBLE_SLAB, FullFacePredicate.Y_AXIS);
        put(BlockType.FRAMED_SLAB_EDGE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_SLAB_CORNER, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_PANEL, FullFacePredicate.HOR_DIR);
        put(BlockType.FRAMED_DOUBLE_PANEL, FullFacePredicate.HOR_DIR_AXIS);
        put(BlockType.FRAMED_CORNER_PILLAR, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_STAIRS, new StairsFullFacePredicate());
        put(BlockType.FRAMED_HALF_STAIRS, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_VERTICAL_STAIRS, new VerticalStairsFullFacePredicate());
        put(BlockType.FRAMED_VERTICAL_HALF_STAIRS, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_WALL, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_FENCE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_FENCE_GATE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_DOOR, DoorFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_IRON_DOOR, DoorFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_TRAPDOOR, TrapdoorFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_IRON_TRAPDOOR, TrapdoorFullFacePredicate.INSTANCE);
        put(BlockType.FRAMED_PRESSURE_PLATE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_STONE_PRESSURE_PLATE, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_BUTTON, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_STONE_BUTTON, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_PILLAR, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_HALF_PILLAR, FullFacePredicate.FALSE);
        put(BlockType.FRAMED_BOOKSHELF, FullFacePredicate.Y_AXIS);
        put(BlockType.FRAMED_CHISELED_BOOKSHELF, FullFacePredicate.NOT_HOR_DIR);
    }
}
