package mechanicalarms.client.renderer;

import com.jozufozu.flywheel.Flywheel;
import mechanicalarms.Myron;
import mechanicalarms.common.tile.BlockEntityArm;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

@Environment(EnvType.CLIENT)
public class ArmRenderer implements BlockEntityRenderer<BlockEntityArm> {

    BlockEntityRendererFactory.Context ctx;
    private final Random random = Random.create();


    public ArmRenderer(BlockEntityRendererFactory.Context ctx){
        this.ctx = ctx;
    }
    @Override
    public void render(BlockEntityArm entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        BakedModel model = ctx.getRenderManager().getModel(entity.getCachedState());
        var quads = model.getQuads(entity.getCachedState(), Direction.UP, random);
        for (int i = 0; i < quads.size(); i++) {
            BakedQuad q = quads.get(i);


            vertexConsumers.getBuffer(RenderLayer.getSolid()).quad(matrices.peek(), q, 255, 255, 255, light, overlay);
        }
        matrices.pop();
    }
}
