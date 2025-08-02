package xfacthd.framedblocks.client.render.debug.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.model.data.ModelData;
import org.joml.Vector3f;
import xfacthd.framedblocks.api.block.blockentity.FramedBlockEntity;
import xfacthd.framedblocks.api.model.quad.QuadData;
import xfacthd.framedblocks.api.render.debug.BlockDebugRenderer;
import xfacthd.framedblocks.api.util.SingleBlockFakeLevel;
import xfacthd.framedblocks.api.util.Triangle;
import xfacthd.framedblocks.common.config.DevToolsConfig;

import java.util.Arrays;
import java.util.Objects;

public class QuadWindingDebugRenderer implements BlockDebugRenderer<FramedBlockEntity>
{
    public static final QuadWindingDebugRenderer INSTANCE = new QuadWindingDebugRenderer();
    private static final Direction[] DIRECTIONS = Arrays.copyOf(Direction.values(), 7);
    private static final int[] VERT_INDEX_COLORS = { 0xFFFFFFFF, 0xFFFF0000, 0xFF00FF00, 0xFF0000FF };
    private static final RandomSource RANDOM = RandomSource.create();

    @Override
    public void render(FramedBlockEntity be, BlockHitResult blockHit, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay)
    {
        BlockPos pos = be.getBlockPos();
        BlockState state = be.getBlockState();
        LocalPlayer player = Objects.requireNonNull(Minecraft.getInstance().player);

        ModelData beModelData = Objects.requireNonNull(be.getLevel()).getModelData(be.getBlockPos());
        ModelData modelData = model.getModelData(be.getLevel(), be.getBlockPos(), be.getBlockState(), beModelData);

        ModelData modelData = Objects.requireNonNull(be.getLevel()).getModelData(pos);
        BlockAndTintGetter level = new SingleBlockFakeLevel(Objects.requireNonNull(be.getLevel()), pos, pos, state, be, modelData);
        Vector3f vertPos = new Vector3f();
        Vector3f vertNorm = new Vector3f();
        for (BlockModelPart part : model.collectParts(level, pos, state, RANDOM))
        {
            for (RenderType renderType : model.getRenderTypes(be.getBlockState(), RANDOM, modelData))
            {
                for (BakedQuad quad : model.getQuads(be.getBlockState(), side, RANDOM, modelData, renderType))
                {
                    QuadData data = new QuadData(quad);

                    norm.set(data.normal(0, 0), data.normal(0, 1), data.normal(0, 2)).normalize();
                    float dot = norm.dot((float) viewVector.x, (float) viewVector.y, (float) viewVector.z);
                    if (dot > -.75F) continue;

                    for (int i = 0; i < 4; i++)
                    {
                        data.pos(i, pos);

                        poseStack.pushPose();
                        poseStack.translate(pos.x, pos.y, pos.z);
                        poseStack.mulPose(Minecraft.getInstance().gameRenderer.getMainCamera().rotation());
                        poseStack.mulPose(Axis.YP.rotationDegrees(180));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(180));
                        poseStack.scale(1F / 16F, 1F / 16F, 1F / 16F);

                        Minecraft.getInstance().font.drawInBatch(
                                Integer.toString(i),
                                -2.5F,
                                -3.5F,
                                0xFFFFFFFF,
                                false,
                                poseStack.last().pose(),
                                Minecraft.getInstance().renderBuffers().bufferSource(),
                                Font.DisplayMode.NORMAL,
                                0x00000000,
                                LightTexture.FULL_BRIGHT
                        );

                        poseStack.popPose();
                    }
                }
            }
        }
    }

    private static boolean checkViewIntersectsQuad(QuadData quadData, Vec3 eyePos, Vec3 viewVector)
    {
        Vector3f posVec = new Vector3f();

        Triangle triOne = new Triangle(
                new Vec3(quadData.pos(0, posVec)),
                new Vec3(quadData.pos(1, posVec)),
                new Vec3(quadData.pos(2, posVec))
        );
        if (triOne.intersects(eyePos, viewVector)) return true;

        Triangle triTwo = new Triangle(
                new Vec3(quadData.pos(2, posVec)),
                new Vec3(quadData.pos(3, posVec)),
                new Vec3(quadData.pos(0, posVec))
        );
        return triTwo.intersects(eyePos, viewVector);
    }

    @Override
    public boolean isEnabled()
    {
        return DevToolsConfig.VIEW.isQuadWindingDebugRendererEnabled();
    }
}
