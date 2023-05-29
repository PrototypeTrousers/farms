package mechanicalarms.common.tile;

import mechanicalarms.MechanicalArmsMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class BeltEntity extends BlockEntity {
    public BeltEntity(BlockPos pos, BlockState state) {
        super(MechanicalArmsMod.BELT_ENTITY, pos, state);
    }

    public void update() {
        List<Entity> entities = this.getWorld().getNonSpectatingEntities(Entity.class, new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 0.5, pos.getZ() + 1));
        for (Entity e : entities) {
            if (e.isAlive()) {
                double xCenter = pos.getX() + 0.5f;
                double zCenter = pos.getZ() + 0.5f;

                double diffx;
                double diffz;

                if (e.getX() - xCenter > 0.1) {
                    diffx = -1 / 16f;
                } else if (e.getX() - xCenter < -0.1) {
                    diffx = 1 / 16f;
                } else {
                    diffx = 0;
                }

                if (e.getZ() - zCenter > 0.1) {
                    diffz = -1 / 16f;
                } else if (e.getZ() - zCenter < -0.1) {
                    diffz = 1 / 16f;
                } else {
                    diffz = 0;
                }

                switch (world.getBlockState(this.pos).get(Properties.HORIZONTAL_FACING)) {
                    case NORTH -> e.move(MovementType.SELF, new Vec3d(diffx, 0, -0.05));
                    case SOUTH -> e.move(MovementType.SELF, new Vec3d(diffx, 0, +0.05));
                    case WEST -> e.move(MovementType.SELF, new Vec3d(+0.05, 0, diffz));
                    case EAST -> e.move(MovementType.SELF, new Vec3d(-0.05, 0, diffz));
                }
                e.setOnGround(true);
            }
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, BeltEntity blockEntity) {
        if (world.isClient()) {
            //return;
        }
        blockEntity.update();
    }

}
