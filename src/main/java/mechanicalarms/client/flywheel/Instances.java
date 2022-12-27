package mechanicalarms.client.flywheel;

import com.jozufozu.flywheel.backend.instancing.InstancedRenderRegistry;

import static mechanicalarms.MechanicalArmsMod.ARM_BASIC_ENTITY;
import static mechanicalarms.MechanicalArmsMod.ARM_SHEARER_ENTITY;


public class Instances {
    public static void init() {
        InstancedRenderRegistry.configure(ARM_BASIC_ENTITY)
                .alwaysSkipRender() // Completely skip the BlockEntityRenderer.
                .factory(BasicArmInstance::new) // Use our BlockEntityInstance instead.
                .apply(); // Apply the instancing configuration.

        InstancedRenderRegistry.configure(ARM_SHEARER_ENTITY)
                .alwaysSkipRender() // Completely skip the BlockEntityRenderer.
                .factory(ShearerArmInstance::new) // Use our BlockEntityInstance instead.
                .apply(); // Apply the instancing configuration.
    }
}
