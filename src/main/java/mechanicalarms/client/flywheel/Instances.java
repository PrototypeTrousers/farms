package mechanicalarms.client.flywheel;

import com.jozufozu.flywheel.backend.instancing.InstancedRenderRegistry;

import static mechanicalarms.MechanicalArmsMod.ARM_BLOCK_ENTITY;


public class Instances {
    public static void init() {
        InstancedRenderRegistry.configure(ARM_BLOCK_ENTITY)
                .alwaysSkipRender() // Completely skip the BlockEntityRenderer.
                .factory(ArmInstance::new) // Use our BlockEntityInstance instead.
                .apply(); // Apply the instancing configuration.
    }
}
