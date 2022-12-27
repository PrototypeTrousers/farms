package mechanicalarms;

import mechanicalarms.client.flywheel.Instances;
import mechanicalarms.client.renderer.ArmRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

import static mechanicalarms.MechanicalArmsMod.ARM_BASIC_ENTITY;
import static mechanicalarms.MechanicalArmsMod.ARM_SHEARER_ENTITY;

public class ModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ARM_BASIC_ENTITY, ArmRenderer::new);
        BlockEntityRendererFactories.register(ARM_SHEARER_ENTITY, ArmRenderer::new);

        Instances.init();
    }
}
