package mechanicalarms.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.PalettedContainer;
import net.minecraft.world.chunk.ReadableContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkSection.class)
public class ChunkSectionMixin {

    private boolean initialized;

    @Redirect(
            method = "<init>(ILnet/minecraft/world/chunk/PalettedContainer;Lnet/minecraft/world/chunk/ReadableContainer;)V",  // the jvm bytecode signature for the constructor
            at = @At(value = "INVOKE", target = "net/minecraft/world/chunk/ChunkSection.calculateCounts ()V"
            ))
    private void construct(ChunkSection instance) {

    }

    @Inject(method = "setBlockState(IIILnet/minecraft/block/BlockState;Z)Lnet/minecraft/block/BlockState;", at = @At(value = "HEAD"))
    private void init1(int x, int y, int z, BlockState state, boolean lock, CallbackInfoReturnable<BlockState> cir) {
        if (!initialized) {
            initialized = true;
            calculateCounts();
        }
    }

    @Inject(method = "toPacket", at =@At("HEAD"))
    private void initPacket(PacketByteBuf buf, CallbackInfo ci){
        if (!initialized) {
            initialized = true;
            calculateCounts();
        }
    }

    @Inject(method = "isEmpty", at =@At("HEAD"))
    private void initIsEmpty(CallbackInfoReturnable<Boolean> cir){
        if (!initialized) {
            initialized = true;
            calculateCounts();
        }
    }

    @Inject(method = "hasRandomTicks", at =@At("HEAD"))
    private void initHasRandomTicks(CallbackInfoReturnable<Boolean> cir){
        if (!initialized) {
            initialized = true;
            calculateCounts();
        }
    }

    @Inject(method = "hasRandomBlockTicks", at =@At("HEAD"))
    private void initHasRandomBlockTicks(CallbackInfoReturnable<Boolean> cir){
        if (!initialized) {
            initialized = true;
            calculateCounts();
        }
    }

    @Inject(method = "hasRandomFluidTicks", at =@At("HEAD"))
    private void initHasRandomFluidTicks(CallbackInfoReturnable<Boolean> cir){
        if (!initialized) {
            initialized = true;
            calculateCounts();
        }
    }



    @Shadow
    public void calculateCounts() {

    }

}
