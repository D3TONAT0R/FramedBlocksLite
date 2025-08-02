package xfacthd.framedblocks.common.net;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import xfacthd.framedblocks.common.net.payload.clientbound.ClientboundCullingUpdatePayload;
import xfacthd.framedblocks.common.net.payload.serverbound.ServerboundSelectFramingSawRecipePayload;

public final class NetworkHandler
{
    private static final String PROTOCOL_VERSION = "3";

    public static void onRegisterPayloads(RegisterPayloadHandlersEvent event)
    {
        PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
        registerNetworkThreadPayloads(registrar);
        registerMainThreadPayloads(registrar);
    }

    private static void registerNetworkThreadPayloads(PayloadRegistrar registrar)
    {
    }

    private static void registerMainThreadPayloads(PayloadRegistrar registrar)
    {
        registrar.executesOn(HandlerThread.MAIN)
                .playToClient(
                        ClientboundCullingUpdatePayload.TYPE,
                        ClientboundCullingUpdatePayload.CODEC
                )
                .playToServer(
                        ServerboundSelectFramingSawRecipePayload.TYPE,
                        ServerboundSelectFramingSawRecipePayload.CODEC,
                        ServerboundSelectFramingSawRecipePayload::handle
                );
    }



    private NetworkHandler() { }
}
