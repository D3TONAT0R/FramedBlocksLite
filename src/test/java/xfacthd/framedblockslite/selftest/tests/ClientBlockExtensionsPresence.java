package xfacthd.framedblockslite.selftest.tests;

import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientBlockExtensions;
import xfacthd.framedblockslite.api.block.render.FramedBlockRenderProperties;
import xfacthd.framedblockslite.selftest.SelfTestReporter;

import java.util.List;

public final class ClientBlockExtensionsPresence
{
    public static void checkClientExtensionsPresent(SelfTestReporter reporter, List<Block> blocks)
    {
        reporter.startTest("client block extension presence");

        blocks.forEach(block ->
        {
            if (!(IClientBlockExtensions.of(block) instanceof FramedBlockRenderProperties))
            {
                reporter.warn("Block '{}' does not provide FramedBlockRenderProperties", block);
            }
        });

        reporter.endTest();
    }



    private ClientBlockExtensionsPresence() { }
}
