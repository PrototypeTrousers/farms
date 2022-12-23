package mechanicalarms.common.logic.behavior;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.tuple.Pair;

public class Targeting {

    Pair<BlockPos, Direction> source;
    Pair<BlockPos, Direction> target;
    private Vec3d sourceVec;
    private Vec3d targetVec;

    public Targeting() {

    }

    public Vec3d getSourceVec() {
        return sourceVec;
    }

    public Vec3d getTargetVec() {
        return targetVec;
    }

    public Pair<BlockPos, Direction> getSource() {
        return source;
    }

    public Pair<BlockPos, Direction> getTarget() {
        return target;
    }

    public Direction getTargetFacing() {
        return target.getRight();
    }

    public Direction getSourceFacing() {
        return source.getRight();
    }

    public void setSource(BlockPos sourcePos, Direction sourceFacing) {
        this.source = Pair.of(sourcePos, sourceFacing);
        BlockPos vecPos = sourcePos.offset(sourceFacing, 1);
        this.sourceVec = new Vec3d(vecPos.getX() + 0.5, vecPos.getY() + 0.5, vecPos.getZ() + 0.5);
    }

    public void setTarget(BlockPos targetPos, Direction targetFacing) {
        this.target = Pair.of(targetPos, targetFacing);
        BlockPos vecPos = targetPos.offset(targetFacing, 1);
        this.targetVec = new Vec3d(vecPos.getX() + 0.5, vecPos.getY() + 0.5, vecPos.getZ() + 0.5);
    }

    public boolean hasInput() {
        return source != null;
    }

    public boolean hasOutput() {
        return target != null;
    }

    public NbtCompound serializeNBT() {
        NbtCompound compound = new NbtCompound();
        if (source != null) {
            compound.put("sourcePos", NbtHelper.fromBlockPos(source.getKey()));
            compound.putInt("sourceFacing", source.getValue().ordinal());
        }
        if (target != null) {
            compound.put("targetPos", NbtHelper.fromBlockPos(target.getKey()));
            compound.putInt("targetFacing", target.getValue().ordinal());
        }
        return compound;
    }

    public void deserializeNBT(NbtCompound compound) {
        setSource(NbtHelper.toBlockPos(compound.getCompound("sourcePos")), Direction.byId(compound.getInt("sourceFacing")));
        setTarget(NbtHelper.toBlockPos(compound.getCompound("targetPos")), Direction.byId(compound.getInt("targetFacing")));
    }
}
