package mechanicalarms.common.block;

import mechanicalarms.MechanicalArmsMod;
import mechanicalarms.common.tile.ArmEntityBasic;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlockBasicArm extends AbstractBlockArm {

    public BlockBasicArm() {
        super(FabricBlockSettings.of(Material.METAL).strength(4.0f));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ArmEntityBasic(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, MechanicalArmsMod.ARM_BASIC_ENTITY, ArmEntityBasic::tick);
    }
}
