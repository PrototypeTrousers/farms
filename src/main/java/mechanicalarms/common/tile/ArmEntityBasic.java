package mechanicalarms.common.tile;

import mechanicalarms.common.logic.behavior.InteractionType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import static mechanicalarms.MechanicalArmsMod.ARM_BASIC_ENTITY;

public class ArmEntityBasic extends AbstractArmEntity {
    public ArmEntityBasic(BlockPos pos, BlockState blockState) {
        super(ARM_BASIC_ENTITY, pos, blockState, InteractionType.BLOCK);
    }
}
