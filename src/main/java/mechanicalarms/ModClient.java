package mechanicalarms;

import mechanicalarms.client.model.ArmModelProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ExtraModelProvider;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;

public class ModClient implements ClientModInitializer {

    ExtraModelProvider extraModelProvider = new ArmModelProvider();
    @Override
    public void onInitializeClient() {
        ModelLoadingRegistry.INSTANCE.registerModelProvider(extraModelProvider);

        /* Other client-specific initialization */
    }
}
