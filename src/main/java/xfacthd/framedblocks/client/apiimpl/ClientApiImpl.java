package xfacthd.framedblocks.client.apiimpl;

import xfacthd.framedblocks.api.FramedBlocksClientAPI;

@SuppressWarnings("unused")
public final class ClientApiImpl implements FramedBlocksClientAPI
{
    @Override
    @SuppressWarnings("removal")
    public void addConTexProperty(ModelProperty<?> ctProperty)
    {
        addConTexProperty("<unknown>", ctProperty);
    }

    @Override
    public void addConTexProperty(String modId, ModelProperty<?> ctProperty)
    {
        ConTexDataHandler.addConTexProperty(modId, ctProperty);
    }

}
