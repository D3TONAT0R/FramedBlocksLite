package xfacthd.framedblockslite;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import xfacthd.framedblockslite.api.util.FramedConstants;
import xfacthd.framedblockslite.cmdtests.SpecialTestCommand;
import xfacthd.framedblockslite.cmdtests.tests.ChunkBanTest;
import xfacthd.framedblockslite.selftest.SelfTest;

@Mod(value = FramedConstants.MOD_ID, dist = Dist.CLIENT)
public final class FramedBlocksTest
{
    public FramedBlocksTest(IEventBus modBus)
    {
        //modBus.addListener(SelfTest::runStartupSelfTest);

        NeoForge.EVENT_BUS.addListener(SpecialTestCommand::registerCommands);
        NeoForge.EVENT_BUS.addListener(ChunkBanTest::onLevelTick);
        NeoForge.EVENT_BUS.addListener(SelfTest::runInWorldSelfTest);
    }
}
