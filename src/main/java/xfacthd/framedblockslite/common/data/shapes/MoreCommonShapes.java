package xfacthd.framedblockslite.common.data.shapes;

import xfacthd.framedblockslite.api.block.FramedProperties;
import xfacthd.framedblockslite.api.shapes.CommonShapes;
import xfacthd.framedblockslite.api.shapes.ShapeGenerator;
import xfacthd.framedblockslite.common.data.PropertyHolder;

public final class MoreCommonShapes
{
    public static final ShapeGenerator TOP_HALF_SLAB_GENERATOR = CommonShapes.createSlabGenerator(PropertyHolder.TOP_HALF);
    public static final ShapeGenerator FRONT_INV_PANEL_GENERATOR = CommonShapes.createPanelGenerator(FramedProperties.FACING_HOR, PropertyHolder.FRONT);



    private MoreCommonShapes() { }
}
