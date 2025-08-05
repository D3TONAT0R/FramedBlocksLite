package xfacthd.framedblocks.client.util;

import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;

public final class ClientEventHandler
{
    public static void onRecipesUpdated(final RecipesUpdatedEvent event)
    {
    }

    public static void onClientDisconnect(@SuppressWarnings("unused") final ClientPlayerNetworkEvent.LoggingOut event)
    {
    }



    private ClientEventHandler() { }
}
