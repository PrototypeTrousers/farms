package mechanicalarms.common.tile;

import mechanicalarms.common.logic.behavior.InteractionType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import static mechanicalarms.MechanicalArmsMod.ARM_SHEARER_ENTITY;

public class ArmEntityShearer extends AbstractArmEntity {
    public ArmEntityShearer(BlockPos pos, BlockState blockState) {
        super(ARM_SHEARER_ENTITY, pos, blockState, InteractionType.ENTITY);
    }
}
