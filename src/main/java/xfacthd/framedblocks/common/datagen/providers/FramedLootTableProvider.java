package xfacthd.framedblocks.common.datagen.providers;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.datagen.loot.FramedBlockLootSubProvider;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.data.loot.LayeredCubeAdditionalItemCountNumberProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public final class FramedLootTableProvider extends LootTableProvider
{
    public FramedLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> providerFuture)
    {
        super(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(BlockLootTable::new, LootContextParamSets.BLOCK)
        ), providerFuture);
    }

    private static class BlockLootTable extends FramedBlockLootSubProvider
    {
        public BlockLootTable(HolderLookup.Provider lookupProvider)
        {
            super(lookupProvider);
        }

        @Override
        protected Iterable<Block> getKnownBlocks()
        {
            return FBContent.getRegisteredBlocks()
                    .stream()
                    .map(Holder::value)
                    .collect(Collectors.toList());
        }

        @Override
        protected void generate()
        {
            dropDoorWithCamo(FBContent.BLOCK_FRAMED_DOOR.value());
            dropDoorWithCamo(FBContent.BLOCK_FRAMED_IRON_DOOR.value());
            dropMultipleWithCamo(FBContent.BLOCK_FRAMED_DOUBLE_SLAB.value(), FBContent.BLOCK_FRAMED_SLAB.value(), 2);
            dropMultipleWithCamo(FBContent.BLOCK_FRAMED_DOUBLE_PANEL.value(), FBContent.BLOCK_FRAMED_PANEL.value(), 2);

            FBContent.getRegisteredBlocks()
                    .stream()
                    .map(Holder::value)
                    .filter(IFramedBlock.class::isInstance)
                    .filter(block -> !map.containsKey(block.getLootTable()))
                    .forEach(this::dropSelfWithCamo);

        }
    }
}
