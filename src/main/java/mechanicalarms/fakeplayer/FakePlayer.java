package mechanicalarms.fakeplayer;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FakePlayer extends PlayerEntity {

    private static final GameProfile fakeGP = new GameProfile(null, "armsfakeplayer");
    public static FakePlayer fakePlayer;

    public FakePlayer(World world) {
        super(world, BlockPos.ORIGIN, 0, fakeGP);
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }

    public static FakePlayer getFakePlayer(BlockEntity blockEntity) {
        if (fakePlayer == null) {
            fakePlayer = new FakePlayer(blockEntity.getWorld());
        }
        fakePlayer.world = blockEntity.getWorld();
        fakePlayer.setPos(blockEntity.getPos().getX(), blockEntity.getPos().getY(), blockEntity.getPos().getZ());
        return fakePlayer;
    }
}
