package xfacthd.framedblockslite.selftest.tests;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.*;
import xfacthd.framedblockslite.api.block.FramedProperties;
import xfacthd.framedblockslite.api.util.Utils;
import xfacthd.framedblockslite.common.block.IFramedDoubleBlock;
import xfacthd.framedblockslite.common.data.doubleblock.DoubleBlockStateCache;
import xfacthd.framedblockslite.common.data.doubleblock.SolidityCheck;
import xfacthd.framedblockslite.selftest.SelfTestReporter;

import java.util.List;

public final class DoubleBlockSolidSideConsistency
{
    public static void checkSolidSideConsistency(SelfTestReporter reporter, List<Block> blocks)
    {
        reporter.startTest("solid side consistency");

        blocks.stream()
                .filter(IFramedDoubleBlock.class::isInstance)
                .map(IFramedDoubleBlock.class::cast)
                .forEach(block -> ((Block) block).getStateDefinition().getPossibleStates().forEach(state ->
                {
                    if (!state.hasProperty(FramedProperties.SOLID) || !state.getValue(FramedProperties.SOLID)) return;

                    DoubleBlockStateCache cache = block.getCache(state);
                    Utils.forAllDirections(false, side ->
                    {
                        VoxelShape faceShape = state.getFaceOcclusionShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO, side);
                        boolean solidShape = !Shapes.joinIsNotEmpty(faceShape, Shapes.block(), BooleanOp.ONLY_SECOND);
                        boolean solidCache = cache.getSolidityCheck(side) != SolidityCheck.NONE;

                        if (solidShape != solidCache)
                        {
                            reporter.warn(
                                    "Block '{}' has inconsistent side solidity for state {} on side {} (shape: {}, cache: {})",
                                    block, state, side, solidShape, solidCache
                            );
                        }
                    });
                }));

        reporter.endTest();
    }



    private DoubleBlockSolidSideConsistency() { }
}
