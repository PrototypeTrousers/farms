package mechanicalarms.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.chunk.PalettedContainer;

class BlockStateCounter implements PalettedContainer.Counter<BlockState> {
    public int nonEmptyBlockCount;
    public int randomTickableBlockCount;
    public int nonEmptyFluidCount;

    BlockStateCounter() {
    }

    public void accept(BlockState blockState, int i) {
        if (!blockState.isAir()) {
            this.nonEmptyBlockCount += i;
            if (blockState.hasRandomTicks()) {
                this.randomTickableBlockCount += i;
            }
        }
        FluidState fluidState = blockState.getFluidState();
        if (fluidState != Fluids.EMPTY.getDefaultState()) {
            this.nonEmptyFluidCount += i;
            if (fluidState.hasRandomTicks()) {
                this.randomTickableBlockCount += i;
            }
        }
    }
}
