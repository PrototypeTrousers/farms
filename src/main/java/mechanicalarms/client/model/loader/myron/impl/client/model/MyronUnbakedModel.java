package mechanicalarms.client.model.loader.myron.impl.client.model;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjSplitting;
import mechanicalarms.Myron;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

public class MyronUnbakedModel implements UnbakedModel {
    private final Obj obj;
    private final Map<String, MyronMaterial> materials;
    private final SpriteIdentifier sprite;
    private final ModelTransformation transform;
    private final boolean isSideLit;
    private final boolean isBlock;

    public MyronUnbakedModel(@Nullable Obj obj, @Nullable Map<String, MyronMaterial> materials, SpriteIdentifier sprite, ModelTransformation modelTransformation, boolean isSideLit, boolean isBlock) {
        this.obj = obj;
        this.materials = materials;
        this.sprite = sprite;
        this.transform = modelTransformation;
        this.isSideLit = isSideLit;
        this.isBlock = isBlock;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return Collections.emptyList();
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {

    }

    @Override
    public @Nullable BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings bakeSettings, Identifier modelId) {
        Mesh mesh;

        if (obj == null)
            // Try to load the obj (previous behavior)
            mesh = Myron.load(modelId, textureGetter, bakeSettings, isBlock);
        else
            // We already loaded the obj earlier in AbstractObjLoader. Don't use the external utility to re-load the obj
            // (it works only on absolute identifiers, not ModelIdentifiers like 'myron:torus#inventory')
            mesh = Myron.build(obj, materials, textureGetter, bakeSettings, isBlock);

        Myron.MESHES.put(modelId, mesh);
        MyronBakedModel bakedModel = new MyronBakedModel(mesh, this.transform, textureGetter.apply(this.sprite), this.isSideLit);
        Myron.BAKED_MODEL_MAP.put(modelId, bakedModel);

        for (Map.Entry<String, Obj> entry : ObjSplitting.splitByGroups(obj).entrySet()) {
            Mesh mesh2 = Myron.build(entry.getValue(), materials, textureGetter, bakeSettings, isBlock);
            MyronBakedModel partBakedModel = new MyronBakedModel(mesh2, this.transform, textureGetter.apply(this.sprite), this.isSideLit);
            Myron.BAKED_MODEL_MAP.put(new Identifier(modelId.getNamespace(), modelId.getPath() + "_" + entry.getKey()), partBakedModel);
        }

        return bakedModel;
    }
}
