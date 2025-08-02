package xfacthd.framedblocks.client.net;

import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xfacthd.framedblocks.common.data.cullupdate.ClientCullingUpdateTracker;
import xfacthd.framedblocks.common.net.payload.clientbound.ClientboundCullingUpdatePayload;

public final class ClientNetworkHandler
{
    public static void onRegisterPayloadHandlers(RegisterClientPayloadHandlersEvent event)
    {
        event.register(ClientboundCullingUpdatePayload.TYPE, ClientNetworkHandler::handleCullingUpdate);
    }

    private static void handleCullingUpdate(ClientboundCullingUpdatePayload payload, IPayloadContext ctx)
    {
        ClientCullingUpdateTracker.handleCullingUpdates(payload.chunk(), payload.positions());
    }

    private ClientNetworkHandler() { }
}
