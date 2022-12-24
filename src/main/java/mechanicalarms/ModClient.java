package mechanicalarms;

import mechanicalarms.client.flywheel.Instances;
import mechanicalarms.client.renderer.ArmRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

import static mechanicalarms.MechanicalArmsMod.ARM_BLOCK_ENTITY;

public class ModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        /* Other client-specific initialization */
        BlockEntityRendererRegistry.register(ARM_BLOCK_ENTITY, ArmRenderer::new);
        Instances.init();
    }
}
