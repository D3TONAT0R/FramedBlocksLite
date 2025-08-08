package xfacthd.framedblockslite.client.util;

import net.minecraft.client.Minecraft;
import xfacthd.framedblockslite.api.model.AbstractFramedBlockModel;

public final class FramedClientUtils
{
    public static void clearModelCaches()
    {
        Minecraft.getInstance()
                .getModelManager()
                .getModelBakery()
                .getBakedTopLevelModels()
                .values()
                .stream()
                .filter(AbstractFramedBlockModel.class::isInstance)
                .map(AbstractFramedBlockModel.class::cast)
                .forEach(AbstractFramedBlockModel::clearCache);

    }



    private FramedClientUtils() { }
}
