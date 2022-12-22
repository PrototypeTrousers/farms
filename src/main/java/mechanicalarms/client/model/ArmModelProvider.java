package mechanicalarms.client.model;

import net.fabricmc.fabric.api.client.model.ExtraModelProvider;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ArmModelProvider implements ExtraModelProvider {

    public static final Identifier BASE = new Identifier("mechanicalarms:block/arm_base");
    public static final Identifier ARM = new Identifier("mechanicalarms:block/arm_arm");
    public static final Identifier HAND = new Identifier("mechanicalarms:block/arm_hand");
    public static final Identifier CLAW = new Identifier("mechanicalarms:block/arm_claw");


    @Override
    public void provideExtraModels(ResourceManager manager, Consumer<Identifier> out) {
        out.accept(BASE);
        out.accept(ARM);
        out.accept(HAND);
        out.accept(CLAW);
    }
}
