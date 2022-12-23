package mechanicalarms.client.flywheel;


import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import mechanicalarms.common.tile.BlockEntityArm;
import net.minecraft.client.render.RenderLayer;

import static mechanicalarms.MechanicalArmsMod.BLOCK_ARM;

public class ArmInstance extends BlockEntityInstance<BlockEntityArm> {
    private final ModelData model;
    public ArmInstance(MaterialManager materialManager, BlockEntityArm blockEntity) {
        super(materialManager, blockEntity);

        model = materialManager.defaultSolid()
                .material(Materials.TRANSFORMED)
                .getModel(BLOCK_ARM.getDefaultState())
                .createInstance();

        // translate the model to make it appear at your BlockEntity
        model.loadIdentity()
                .translate(getInstancePosition());
    }

    @Override
    protected void remove() {
        model.delete();
    }

    @Override
    public void updateLight() {
        relight(getWorldPosition(), model);
    }

    private ModelData baseInstance() {

        return materialManager.solid(RenderLayer.getSolid())
                .material(Materials.TRANSFORMED)
                .getModel(BLOCK_ARM.getDefaultState())
                .createInstance();
    }
}
