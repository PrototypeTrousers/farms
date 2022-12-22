package mechanicalarms.client.model.loader.myron.impl.mixin;

import mechanicalarms.client.model.loader.myron.impl.client.obj.ObjLoader;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = ModelLoader.class, priority = 100)
public abstract class MixinModelLoader {
    @Unique
    private ModelResourceProvider objModelProvider;

    @Shadow
    protected abstract void putModel(Identifier id, UnbakedModel unbakedModel);

    @Inject(method = "loadModel", at = @At("HEAD"), cancellable = true)
    private void addObjModel(Identifier id, CallbackInfo ci) {
        if (this.objModelProvider == null) {
            this.objModelProvider = new ObjLoader(MinecraftClient.getInstance().getResourceManager());
        }

        try {
            @Nullable UnbakedModel model = this.objModelProvider.loadModelResource(id, null);

            if (model != null) {
                this.putModel(id, model);
                ci.cancel();
            }
        } catch (ModelProviderException e) {
            e.printStackTrace();
        }
    }
}
