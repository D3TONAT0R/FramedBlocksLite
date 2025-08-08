package xfacthd.framedblockslite.common.data.skippreds.pane;

import net.minecraft.core.Direction;
import xfacthd.framedblockslite.api.util.Utils;
import xfacthd.framedblockslite.common.data.property.SlopeType;
import xfacthd.framedblockslite.common.data.skippreds.CornerDir;
import xfacthd.framedblockslite.common.data.skippreds.HalfDir;

@SuppressWarnings("JavaExistingMethodCanBeUsed")
public final class PaneDirs
{
    public static final class FloorBoard
    {
        public static HalfDir getHalfDir(boolean top, Direction side)
        {
            if (!Utils.isY(side))
            {
                return HalfDir.fromDirections(side, top ? Direction.UP : Direction.DOWN);
            }
            return HalfDir.NULL;
        }

        private FloorBoard() { }
    }

    public static final class WallBoard
    {
        public static HalfDir getHalfDir(Direction dir, Direction side)
        {
            if (side.getAxis() != dir.getAxis())
            {
                return HalfDir.fromDirections(side, dir);
            }
            return HalfDir.NULL;
        }

        private WallBoard() { }
    }

    public static final class CornerStrip
    {
        public static HalfDir getHalfDir(Direction dir, SlopeType type, Direction side)
        {
            Direction dirTwo = switch (type)
            {
                case TOP -> Direction.UP;
                case BOTTOM -> Direction.DOWN;
                case HORIZONTAL -> dir.getCounterClockWise();
            };
            if (side == dir)
            {
                return HalfDir.fromDirections(side, dirTwo);
            }
            else if (side == dirTwo)
            {
                return HalfDir.fromDirections(side, dir);
            }
            return HalfDir.NULL;
        }

        public static CornerDir getCornerDir(Direction dir, SlopeType type, Direction side)
        {
            Direction dirTwo = switch (type)
            {
                case TOP -> Direction.UP;
                case BOTTOM -> Direction.DOWN;
                case HORIZONTAL -> dir.getCounterClockWise();
            };
            if (side.getAxis() != dir.getAxis() && side.getAxis() != dirTwo.getAxis())
            {
                return CornerDir.fromDirections(side, dir, dirTwo);
            }
            return CornerDir.NULL;
        }

        private CornerStrip() { }
    }

    private PaneDirs() { }
}
