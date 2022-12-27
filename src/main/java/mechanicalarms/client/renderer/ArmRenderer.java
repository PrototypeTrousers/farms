package mechanicalarms.client.renderer;

import mechanicalarms.common.tile.AbstractArmEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.random.Random;

@Environment(EnvType.CLIENT)
public class ArmRenderer implements BlockEntityRenderer<AbstractArmEntity> {

    BlockEntityRendererFactory.Context ctx;
    private final Random random = Random.create();


    public ArmRenderer(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void render(AbstractArmEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
    }
}
