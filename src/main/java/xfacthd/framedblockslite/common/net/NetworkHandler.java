package xfacthd.framedblockslite.common.net;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import xfacthd.framedblockslite.common.data.cullupdate.ClientCullingUpdateTracker;
import xfacthd.framedblockslite.common.net.payload.*;
import xfacthd.framedblockslite.common.net.payload.ClientboundCullingUpdatePayload;

public final class NetworkHandler
{
    private static final String PROTOCOL_VERSION = "3";

    public static void onRegisterPayloads(final RegisterPayloadHandlersEvent event)
    {
        event.registrar(PROTOCOL_VERSION)
                .executesOn(HandlerThread.NETWORK)
                .playToClient(
                        ClientboundCullingUpdatePayload.TYPE,
                        ClientboundCullingUpdatePayload.CODEC,
                        ClientCullingUpdateTracker::handleCullingUpdates
                );
    }



    private NetworkHandler() { }
}