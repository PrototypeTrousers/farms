package mechanicalarms.mixin;

import net.minecraft.util.thread.ThreadExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ThreadExecutor.class)
public class ThreadExecutorMixin {
    /**
     * @author
     * @reason
     */
    @Overwrite
    public void waitForTasks() {
    }
}
