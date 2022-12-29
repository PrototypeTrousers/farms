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
import mechanicalarms.client.model.loader.myron.impl.client.model.MyronBakedModel;
import mechanicalarms.common.tile.AbstractArmEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public abstract class AbstractArmInstance extends BlockEntityInstance<AbstractArmEntity> implements DynamicInstance {
    private final ModelData arm = armModelData();
    private final ModelData arm2 = arm2ModelData();
    private final ModelData hand = armHandData();
    private final ModelData claw = armClawData();

    public AbstractArmInstance(MaterialManager materialManager, AbstractArmEntity blockEntity) {
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

    private ModelData arm2ModelData() {
        return materialManager.solid(RenderLayer.getSolid())
                .material(Materials.TRANSFORMED)
                .model("arm2", this::getArm2Model)
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
        MyronBakedModel bakedModel = Myron.BAKED_MODEL_MAP.get(new Identifier("mechanicalarms", "block/arm_basic.obj_arm1"));
        BakedModelBuilder modelBuilder = new BakedModelBuilder(bakedModel);
        return new BlockModel(modelBuilder.build(), "arm");
    }

    private Model getArm2Model() {
        MyronBakedModel bakedModel = Myron.BAKED_MODEL_MAP.get(new Identifier("mechanicalarms", "block/arm_basic.obj_arm2"));
        BakedModelBuilder modelBuilder = new BakedModelBuilder(bakedModel);
        return new BlockModel(modelBuilder.build(), "arm2");
    }

    private Model getHandModel() {
        MyronBakedModel bakedModel = Myron.BAKED_MODEL_MAP.get(new Identifier("mechanicalarms", "block/arm_basic.obj_hand"));
        BakedModelBuilder modelBuilder = new BakedModelBuilder(bakedModel);
        return new BlockModel(modelBuilder.build(), "hand");
    }

    private Model getClawModel() {
        MyronBakedModel bakedModel = Myron.BAKED_MODEL_MAP.get(new Identifier("mechanicalarms", "block/arm_basic.obj_claw"));
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

        ts.translate(getInstancePosition());
        ts.translate(0, -0.5, 0);

        var arm1pivot = new Vec3d(0.5, 2, 0.5);
        ts
                .translate(arm1pivot)
                .rotateYRadians(lerp(firstArmAnimationAngle[1], firstArmRotation[1], AnimationTickHolder.getPartialTicks()))
                .rotateZRadians(lerp(firstArmAnimationAngle[0], firstArmRotation[0], AnimationTickHolder.getPartialTicks()))
                .translateBack(arm1pivot);
        arm.setTransform((MatrixStack) ts);

        ts.pushPose();
        var arm2pivot = new Vec3d(2.5, 2, 0.5);

        ts.translate(arm2pivot)
                .rotateZRadians(lerp(secondArmAnimationAngle[0], secondArmRotation[0], AnimationTickHolder.getPartialTicks()))
                .translateBack(arm2pivot);
        arm2.setTransform((MatrixStack) ts);

        ts.pushPose();

        var hand2pivot = new Vec3d(4 + 4 / 16f, 1 + 15f/16, 0.5f);
        ts
                .translate(hand2pivot)
                .rotateZRadians(lerp(handRotationAnimationAngle[0], handRotation[0], AnimationTickHolder.getPartialTicks()))
                .rotateYRadians(lerp(handRotationAnimationAngle[1], handRotation[1], AnimationTickHolder.getPartialTicks()))
                .translateBack(hand2pivot);
        hand.setTransform((MatrixStack) ts);
        claw.setTransform((MatrixStack) ts);

        ts.popPose();
        ts.popPose();
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
