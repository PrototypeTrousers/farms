package mechanicalarms.common.tile;

import mechanicalarms.MechanicalArmsMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BeltEntity extends BlockEntity {
    public BeltEntity(BlockPos pos, BlockState state) {
        super(MechanicalArmsMod.BELT_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BeltEntity be) {
        be.update();
    }

    void update() {

    }

}
