package mechanicalarms.mixin;

import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Util.class)
public class UtilMixin {

    /**
     * @author
     * @reason
     */
    @Overwrite
    public static long getMeasuringTimeNano() {
        return System.nanoTime();
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public static long getMeasuringTimeMs() {
        return System.nanoTime() / 1000000;
    }
}
