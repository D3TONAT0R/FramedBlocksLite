package xfacthd.framedblockslite.api.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import xfacthd.framedblockslite.api.block.blockentity.FramedBlockEntity;

public record FrameConfig(boolean glowing)
{
    public static final Codec<FrameConfig> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.BOOL.fieldOf("glowing").forGetter(FrameConfig::glowing)
    ).apply(inst, FrameConfig::new));
    public static final StreamCodec<ByteBuf, FrameConfig> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            FrameConfig::glowing,
            FrameConfig::new
    );
    public static final FrameConfig DEFAULT = new FrameConfig(false);

    public void apply(FramedBlockEntity be)
    {

    }
}
