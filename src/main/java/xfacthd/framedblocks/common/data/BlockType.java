package xfacthd.framedblocks.common.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import xfacthd.framedblocks.api.predicate.contex.ConTexMode;
import xfacthd.framedblocks.api.predicate.contex.ConnectionPredicate;
import xfacthd.framedblocks.api.predicate.cull.SideSkipPredicate;
import xfacthd.framedblocks.api.predicate.fullface.FullFacePredicate;
import xfacthd.framedblocks.api.shapes.CommonShapes;
import xfacthd.framedblocks.api.shapes.ReloadableShapeProvider;
import xfacthd.framedblocks.api.shapes.ShapeGenerator;
import xfacthd.framedblocks.api.shapes.ShapeProvider;
import xfacthd.framedblocks.api.block.IBlockType;
import xfacthd.framedblocks.common.data.conpreds.ConnectionPredicates;
import xfacthd.framedblocks.common.data.facepreds.FullFacePredicates;
import xfacthd.framedblocks.common.data.shapes.MoreCommonShapes;
import xfacthd.framedblocks.common.data.shapes.SplitShapeGenerator;
import xfacthd.framedblocks.common.data.shapes.door.*;
import xfacthd.framedblocks.common.data.shapes.interactive.*;
import xfacthd.framedblocks.common.data.shapes.pillar.*;
import xfacthd.framedblocks.common.data.shapes.slab.*;
import xfacthd.framedblocks.common.data.shapes.stairs.standard.*;
import xfacthd.framedblocks.common.data.shapes.stairs.vertical.*;
import xfacthd.framedblocks.common.data.skippreds.SideSkipPredicates;

import java.util.Locale;
import java.util.Objects;

@SuppressWarnings("SameParameterValue")
public enum BlockType implements IBlockType
{
    FRAMED_CUBE                                     ( true, false, false, false,  true,  true, false, false, ConTexMode.FULL_FACE, Shapes.block()),
    FRAMED_SLAB                                     ( true, false, false,  true,  true,  true, false, false, ConTexMode.FULL_FACE, CommonShapes.SLAB_GENERATOR),
    FRAMED_DOUBLE_SLAB                              ( true, false, false, false,  true,  true,  true, false, ConTexMode.FULL_FACE, Shapes.block()),
    FRAMED_SLAB_EDGE                                (false, false, false,  true,  true,  true, false, false, ConTexMode.FULL_EDGE, SlabEdgeShapes::generate),
    FRAMED_SLAB_CORNER                              (false, false, false,  true,  true,  true, false, false, ConTexMode.DETAILED, SlabCornerShapes::generate),
    FRAMED_PANEL                                    ( true, false, false,  true,  true,  true, false, false, ConTexMode.FULL_FACE, CommonShapes.PANEL_GENERATOR),
    FRAMED_DOUBLE_PANEL                             ( true, false, false, false,  true,  true,  true, false, ConTexMode.FULL_FACE, Shapes.block()),
    FRAMED_CORNER_PILLAR                            (false, false, false,  true,  true,  true, false, false, ConTexMode.FULL_EDGE, CornerPillarShapes::generate),
    FRAMED_STAIRS                                   ( true, false, false,  true,  true,  true, false,  true, ConTexMode.FULL_FACE),
    FRAMED_HALF_STAIRS                              (false, false, false,  true,  true,  true, false, false, ConTexMode.FULL_EDGE, HalfStairsShapes::generate),
    FRAMED_VERTICAL_STAIRS                          ( true, false, false,  true,  true,  true, false,  true, ConTexMode.FULL_FACE, VerticalStairsShapes::generate),
    FRAMED_VERTICAL_HALF_STAIRS                     (false, false, false,  true,  true, false, false, false, ConTexMode.FULL_EDGE, VerticalHalfStairsShapes::generate),
    FRAMED_WALL                                     (false, false, false,  true,  true, false, false,  true, ConTexMode.DETAILED),
    FRAMED_FENCE                                    (false, false, false,  true,  true, false, false,  true, ConTexMode.DETAILED),
    FRAMED_FENCE_GATE                               (false, false, false, false,  true, false, false, false, ConTexMode.DETAILED),
    FRAMED_DOOR                                     ( true, false,  true, false,  true, false, false, false, ConTexMode.FULL_FACE),
    FRAMED_IRON_DOOR                                ( true, false,  true, false,  true, false, false, false, ConTexMode.FULL_FACE),
    FRAMED_TRAPDOOR                                 ( true, false, false,  true,  true, false, false, false, ConTexMode.FULL_FACE),
    FRAMED_IRON_TRAPDOOR                            ( true, false, false,  true,  true, false, false, false, ConTexMode.FULL_FACE),
    FRAMED_PRESSURE_PLATE                           (false, false, false, false,  true, false, false, false, null),
    FRAMED_STONE_PRESSURE_PLATE                     (false, false, false, false,  true, false, false, false, null),
    FRAMED_BUTTON                                   (false, false, false, false,  true, false, false, false, null),
    FRAMED_STONE_BUTTON                             (false, false, false, false,  true, false, false, false, null),
    FRAMED_LARGE_BUTTON                             (false, false, false, false,  true, false, false, false, null),
    FRAMED_LARGE_STONE_BUTTON                       (false, false, false, false,  true, false, false, false, null),
    FRAMED_PILLAR                                   (false, false, false,  true,  true,  true, false, false, ConTexMode.DETAILED, PillarShapes.PILLAR),
    FRAMED_HALF_PILLAR                              (false, false, false,  true,  true,  true, false, false, ConTexMode.DETAILED, HalfPillarShapes::generate),
    FRAMED_GATE                                     ( true, false, false, false,  true, false, false, false, ConTexMode.FULL_FACE, GateShapes::generate),
    FRAMED_IRON_GATE                                ( true, false, false, false,  true, false, false, false, ConTexMode.FULL_FACE, GateShapes::generate),
    FRAMED_BOOKSHELF                                ( true, false, false, false,  true,  true, false, false, ConTexMode.FULL_FACE, Shapes.block()),
    FRAMED_CHISELED_BOOKSHELF                       ( true, false,  true, false,  true,  true, false, false, ConTexMode.FULL_FACE, Shapes.block()),
    ;

    private final String name = toString().toLowerCase(Locale.ROOT);
    private final boolean canOcclude;
    private final boolean specialHitbox;
    private final boolean specialTile;
    private final boolean waterloggable;
    private final boolean blockItem;
    private final boolean allowIntangible;
    private final boolean doubleBlock;
    private final boolean lockable;
    private final boolean supportsCT;
    private final ConTexMode minCTMode;
    private final ShapeGenerator shapeGen;
    private final boolean separateOcclusionShapes;

    BlockType(boolean canOcclude, boolean specialHitbox, boolean specialTile, boolean waterloggable, boolean blockItem, boolean allowIntangible, boolean doubleBlock, boolean lockable, @Nullable ConTexMode minCTMode)
    {
        this(canOcclude, specialHitbox, specialTile, waterloggable, blockItem, allowIntangible, doubleBlock, lockable, minCTMode, ShapeGenerator.EMPTY);
    }

    BlockType(boolean canOcclude, boolean specialHitbox, boolean specialTile, boolean waterloggable, boolean blockItem, boolean allowIntangible, boolean doubleBlock, boolean lockable, @Nullable ConTexMode minCTMode, VoxelShape shape)
    {
        this(canOcclude, specialHitbox, specialTile, waterloggable, blockItem, allowIntangible, doubleBlock, lockable, minCTMode, ShapeGenerator.singleShape(shape));
        Preconditions.checkArgument(!waterloggable || !Shapes.joinUnoptimized(shape, Shapes.block(), BooleanOp.NOT_SAME).isEmpty(), "Blocks with full cube shape can't be waterloggable");
    }

    BlockType(boolean canOcclude, boolean specialHitbox, boolean specialTile, boolean waterloggable, boolean blockItem, boolean allowIntangible, boolean doubleBlock, boolean lockable, @Nullable ConTexMode minCTMode, ShapeGenerator shapeGen)
    {
        this.canOcclude = canOcclude;
        this.specialHitbox = specialHitbox;
        this.specialTile = specialTile;
        this.waterloggable = waterloggable;
        this.blockItem = blockItem;
        this.allowIntangible = allowIntangible;
        this.doubleBlock = doubleBlock;
        this.lockable = lockable;
        this.supportsCT = minCTMode != null;
        this.minCTMode = Objects.requireNonNullElse(minCTMode, ConTexMode.NONE);
        this.shapeGen = shapeGen;
        this.separateOcclusionShapes = shapeGen instanceof SplitShapeGenerator;
    }

    @Override
    public boolean canOccludeWithSolidCamo()
    {
        return canOcclude;
    }

    @Override
    public boolean hasSpecialHitbox()
    {
        return specialHitbox;
    }

    @Override
    public FullFacePredicate getFullFacePredicate()
    {
        return FullFacePredicates.PREDICATES.get(this);
    }

    @Override
    public SideSkipPredicate getSideSkipPredicate()
    {
        return SideSkipPredicates.PREDICATES.get(this);
    }

    @Override
    public ConnectionPredicate getConnectionPredicate()
    {
        return ConnectionPredicates.PREDICATES.get(this);
    }

    @Override
    public ShapeProvider generateShapes(ImmutableList<BlockState> states)
    {
        return ReloadableShapeProvider.of(shapeGen, states);
    }

    @Override
    public ShapeProvider generateOcclusionShapes(ImmutableList<BlockState> states, ShapeProvider shapes)
    {
        if (separateOcclusionShapes)
        {
            SplitShapeGenerator splitShapeGen = (SplitShapeGenerator) shapeGen;
            return ReloadableShapeProvider.of(splitShapeGen::generateOcclusionShapes, states);
        }
        return shapes;
    }

    @Override
    public boolean hasSpecialTile()
    {
        return specialTile;
    }

    @Override
    public boolean hasBlockItem()
    {
        return blockItem;
    }

    @Override
    public boolean supportsWaterLogging()
    {
        return waterloggable;
    }

    @Override
    public boolean supportsConnectedTextures()
    {
        return supportsCT;
    }

    @Override
    public ConTexMode getMinimumConTexMode()
    {
        return minCTMode;
    }

    @Override
    public boolean allowMakingIntangible()
    {
        return allowIntangible;
    }

    @Override
    public boolean isDoubleBlock()
    {
        return doubleBlock;
    }

    @Override
    public boolean consumesTwoCamosInCamoApplicationRecipe()
    {
        return doubleBlock || this == FRAMED_DOOR || this == FRAMED_IRON_DOOR;
    }

    @Override
    public boolean canLockState()
    {
        return lockable;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public int compareTo(IBlockType other)
    {
        if (!(other instanceof BlockType type))
        {
            return 1;
        }
        return compareTo(type);
    }
}