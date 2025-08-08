package xfacthd.framedblockslite.common.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import xfacthd.framedblockslite.common.FBContent;
import xfacthd.framedblockslite.common.data.BlockType;
import xfacthd.framedblockslite.common.data.FramedToolType;

public final class FramedCreativeTab
{
    public static CreativeModeTab makeTab()
    {
        return CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.framed_blocks"))
                .icon(() -> new ItemStack(FBContent.BLOCK_FRAMED_CUBE.value()))
                .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                .displayItems((params, output) ->
                {
                    for (BlockType type : BlockType.values())
                    {
                        if (type.hasBlockItem())
                        {
                            output.accept(FBContent.byType(type));
                        }
                    }

                    for (FramedToolType tool : FramedToolType.values())
                    {
                        output.accept(FBContent.toolByType(tool));
                    }
                })
                .build();
    }

    private FramedCreativeTab() { }
}
