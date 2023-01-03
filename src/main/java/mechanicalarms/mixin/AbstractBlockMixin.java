package mechanicalarms.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.CompletableFuture;

@Mixin(targets = "net.minecraft.block.AbstractBlock$AbstractBlockState")
public abstract class AbstractBlockMixin {

    @Shadow
    protected AbstractBlock.AbstractBlockState.ShapeCache shapeCache;
    private CompletableFuture<Void> sc;

    @Inject(
            method = "initShapeCache",  // the jvm bytecode signature for the constructor
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;hasDynamicBounds()Z", by = 1,shift = At.Shift.AFTER
            ), cancellable = true)
    protected void a(CallbackInfo ci) {
        sc = CompletableFuture.supplyAsync(() -> new AbstractBlock.AbstractBlockState.ShapeCache(this.asBlockState())).thenAcceptAsync(as -> this.shapeCache = as);
        ci.cancel();
    }

    @Shadow
    protected abstract BlockState asBlockState();

}
