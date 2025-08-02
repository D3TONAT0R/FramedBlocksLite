package xfacthd.framedblocks.api;

import org.jetbrains.annotations.ApiStatus;
import xfacthd.framedblocks.api.util.Utils;

@ApiStatus.NonExtendable
@SuppressWarnings({ "unused" })
public interface FramedBlocksClientAPI
{
    FramedBlocksClientAPI INSTANCE = Utils.loadService(FramedBlocksClientAPI.class);



    /**
     * Returns the default block color implementation used by FramedBlocks for {@link BlockColor} proxying
     * @deprecated Use {@link FramedBlockColor#INSTANCE} directly instead
     */
    @Deprecated(forRemoval = true)
    default BlockColor defaultBlockColor()
    {
        return FramedBlockColor.INSTANCE;
    }

    /**
     * Add a {@link ModelProperty} for connected textures data to allow FramedBlocks to look up the data for use
     * in the caching of generated quads in the model
     *
     * @deprecated Use overload with mod ID parameter instead
     */
    @Deprecated(forRemoval = true)
    void addConTexProperty(ModelProperty<?> ctProperty);

    /**
     * Add a {@link ModelProperty} for connected textures data to allow FramedBlocks to look up the data for use
     * in the caching of generated quads in the model
     */
    void addConTexProperty(String modId, ModelProperty<?> ctProperty);

    /**
     * Generate overlay quads with the given texture based on all quads on the given side and insert them in the given
     * quad map after the existing quads on the given side of the active render type
     * @param quadMap The {@link QuadMap} containing all transformed quads
     * @param side The side (or {@code null} whose quads shall be operated on
     * @param sprite The texture to be applied to the overlay quads
     */
    void generateOverlayQuads(QuadMap quadMap, @Nullable Direction side, TextureAtlasSprite sprite);

    /**
     * Generate overlay quads with the given texture based on all quads on the given side filtered by the given predicate
     * and insert them in the given quad map after the existing quads on the given side of the active render type
     * @param quadMap The {@link QuadMap} containing all transformed quads
     * @param side The side (or {@code null} whose quads shall be operated on
     * @param sprite The texture to be applied to the overlay quads
     * @param filter The predicate to filter the quads with by their nearest normal direction
     */
    void generateOverlayQuads(QuadMap quadMap, @Nullable Direction side, TextureAtlasSprite sprite, Predicate<Direction> filter);

    /**
     * Generate overlay quads with the given texture based on all quads on the given side filtered by the given predicate
     * and insert them in the given quad map after the existing quads on the given side of the active render type
     * @param quadMap The {@link QuadMap} containing all transformed quads
     * @param side The side (or {@code null} whose quads shall be operated on
     * @param spriteGetter A function returning the texture to be applied to the overlay quad generated from a quad with the given nearest normal direction
     * @param filter The predicate to filter the quads with by their nearest normal direction
     * @apiNote
     */
    void generateOverlayQuads(QuadMap quadMap, @Nullable Direction side, Function<Direction, TextureAtlasSprite> spriteGetter, Predicate<Direction> filter);
}
