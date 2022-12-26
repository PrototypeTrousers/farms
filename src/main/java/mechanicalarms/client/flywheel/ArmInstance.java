package mechanicalarms.client.flywheel;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.jozufozu.flywheel.core.model.BakedModelBuilder;
import com.jozufozu.flywheel.core.model.BlockModel;
import com.jozufozu.flywheel.core.model.Model;
import com.jozufozu.flywheel.util.AnimationTickHolder;
import com.jozufozu.flywheel.util.transform.TransformStack;
import mechanicalarms.Myron;
import mechanicalarms.common.tile.BlockEntityArm;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ArmInstance extends BlockEntityInstance<BlockEntityArm> implements DynamicInstance {
    private final ModelData arm = armModelData();
    private final ModelData arm2 = armModelData();
    private final ModelData hand = armHandData();
    private final ModelData claw = armClawData();

    public ArmInstance(MaterialManager materialManager, BlockEntityArm blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    protected void remove() {
        arm.delete();
        arm2.delete();
        hand.delete();
        claw.delete();
    }

    @Override
    public void updateLight() {
        relight(getWorldPosition().up(), arm, arm2, hand, claw);
    }

    private ModelData armModelData() {
        return materialManager.solid(RenderLayer.getSolid())
                .material(Materials.TRANSFORMED)
                .model("arm", this::getArmModel)
                .createInstance();
    }

    private ModelData armHandData() {
        return materialManager.solid(RenderLayer.getSolid())
                .material(Materials.TRANSFORMED)
                .model("hand", this::getHandModel)
                .createInstance();
    }

    private ModelData armClawData() {
        return materialManager.solid(RenderLayer.getSolid())
                .material(Materials.TRANSFORMED)
                .model("claw", this::getClawModel)
                .createInstance();
    }

    private Model getArmModel() {
        BakedModel bakedModel = Myron.BAKED_MODEL_MAP.get(new Identifier("mechanicalarms", "block/arm_arm"));
        BakedModelBuilder modelBuilder = new BakedModelBuilder(bakedModel);
        return new BlockModel(modelBuilder.build(), "arm");
    }

    private Model getHandModel() {
        BakedModel bakedModel = Myron.BAKED_MODEL_MAP.get(new Identifier("mechanicalarms", "block/arm_hand"));
        BakedModelBuilder modelBuilder = new BakedModelBuilder(bakedModel);
        return new BlockModel(modelBuilder.build(), "hand");
    }

    private Model getClawModel() {
        BakedModel bakedModel = Myron.BAKED_MODEL_MAP.get(new Identifier("mechanicalarms", "block/arm_claw"));
        BakedModelBuilder modelBuilder = new BakedModelBuilder(bakedModel);
        return new BlockModel(modelBuilder.build(), "claw");
    }

    @Override
    public void beginFrame() {

        float[] firstArmRotation = blockEntity.getRotation(0);
        float[] firstArmAnimationAngle = blockEntity.getAnimationRotation(0);

        float[] secondArmRotation = blockEntity.getRotation(1);
        float[] secondArmAnimationAngle = blockEntity.getAnimationRotation(1);

        float[] handRotation = blockEntity.getRotation(2);
        float[] handRotationAnimationAngle = blockEntity.getAnimationRotation(2);

        MatrixStack matrixStack = new MatrixStack();
        TransformStack ts = TransformStack.cast(matrixStack);
        ts.pushPose();

        ts.translate(getInstancePosition())
                .translate(.5F, 1 + 7 / 16F, .5F)
                .rotateYRadians(-Math.PI / 2)
                .rotateYRadians(lerp(firstArmAnimationAngle[1], firstArmRotation[1], AnimationTickHolder.getPartialTicks()))
                .rotateXRadians(lerp(firstArmAnimationAngle[0], firstArmRotation[0], AnimationTickHolder.getPartialTicks()))
                .translateBack(.5F, 1 + 7 / 16F, .5F);
        arm.setTransform((MatrixStack) ts);

        ts.pushPose();
        ts.translate(0, 0, -(1 + 12 / 16F))
                .translate(.5, 1 + 7 / 16F, .5).
                rotateXRadians(lerp(secondArmAnimationAngle[0], secondArmRotation[0], AnimationTickHolder.getPartialTicks()))
                .translateBack(.5, 1 + 7 / 16F, .5);

        arm2.setTransform((MatrixStack) ts);

        ts.pushPose();
        ts.translate(0, 0, -(1 + 13 / 16F)).
                translate(0.5F, 1 + 8 / 16F, 0.5F)
                .rotateXRadians(lerp(handRotationAnimationAngle[0], handRotation[0], AnimationTickHolder.getPartialTicks()))
                .rotateYRadians(lerp(handRotationAnimationAngle[1], handRotation[1], AnimationTickHolder.getPartialTicks()))
                .translateBack(0.5F, 1 + 5 / 16F, 0.5F);
        hand.setTransform((MatrixStack) ts);

        ts.pushPose();
        ts.translate(0, 2 / 16F, -0.5F);
        claw.setTransform((MatrixStack) ts);

        ts.popPose();
        ts.popPose();
        ts.popPose();
        ts.popPose();

        arm.translate(-0.5, -0.5, -0.5);
        arm2.translate(-0.5, -0.5, -0.5);
        hand.translate(-0.5, -0.5, -0.5);
        claw.translate(-0.5, -0.5, -0.5);

    }

    private float lerp(float previous, float current, float partialTick) {
        var diff = Math.abs(previous) - Math.abs(current);
        if (diff > Math.PI) {
            previous = 0;
        } else if (diff < -Math.PI) {
            current = 0;
        }
        return (previous * (1.0F - partialTick)) + (current * partialTick);
    }
}
