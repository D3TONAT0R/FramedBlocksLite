package xfacthd.framedblockslite.selftest.tests;

import net.minecraft.world.level.block.Block;
import xfacthd.framedblockslite.api.block.FramedProperties;
import xfacthd.framedblockslite.api.block.IFramedBlock;
import xfacthd.framedblockslite.selftest.SelfTestReporter;

import java.util.List;

public final class OcclusionPropertyConsistency
{
    public static void checkOcclusionProperty(SelfTestReporter reporter, List<Block> blocks)
    {
        reporter.startTest("occlusion property");

        blocks.forEach(block ->
        {
            boolean onType = ((IFramedBlock) block).getBlockType().canOccludeWithSolidCamo();
            boolean onBlock = block.defaultBlockState().hasProperty(FramedProperties.SOLID);
            if (onType != onBlock)
            {
                reporter.warn("Block '{}' has inconsistent occlusion configuration", block);
            }
        });

        reporter.endTest();
    }



    private OcclusionPropertyConsistency() { }
}
