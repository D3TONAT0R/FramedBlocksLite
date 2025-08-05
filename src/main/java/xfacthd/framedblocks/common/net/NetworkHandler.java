package xfacthd.framedblocks.common.net;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import xfacthd.framedblocks.common.data.cullupdate.ClientCullingUpdateTracker;
import xfacthd.framedblocks.common.net.payload.*;

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