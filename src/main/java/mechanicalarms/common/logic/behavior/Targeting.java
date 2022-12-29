package mechanicalarms.common.logic.behavior;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.tuple.Pair;

public class Targeting {

    Pair<Vec3d, Direction> source;
    Pair<Vec3d, Direction> target;
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

    public Pair<Vec3d, Direction> getSource() {
        return source;
    }

    public Pair<Vec3d, Direction> getTarget() {
        return target;
    }

    public Direction getTargetFacing() {
        return target.getRight();
    }

    public Direction getSourceFacing() {
        return source.getRight();
    }

    public void setSource(Vec3d sourcePos, Direction sourceFacing) {
        this.source = Pair.of(sourcePos, sourceFacing);
        Vec3d vecPos = sourcePos.add(Vec3d.ofCenter(sourceFacing.getVector()));
        this.sourceVec = new Vec3d(vecPos.getX() + 0.5, vecPos.getY() + 0.5, vecPos.getZ() + 0.5);
    }

    public void setTarget(Vec3d targetPos, Direction targetFacing) {
        this.target = Pair.of(targetPos, targetFacing);
        Vec3d vecPos = targetPos.add(new Vec3d(targetFacing.getVector().getX(), targetFacing.getVector().getY(), targetFacing.getVector().getZ()));
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
            NbtList sp = new NbtList();
            sp.add(NbtDouble.of(source.getKey().x));
            sp.add(NbtDouble.of(source.getKey().y));
            sp.add(NbtDouble.of(source.getKey().z));
            compound.put("sourcePos", sp);
            compound.putInt("sourceFacing", source.getValue().ordinal());
        }
        if (target != null) {
            NbtList sp = new NbtList();
            sp.add(NbtDouble.of(target.getKey().x));
            sp.add(NbtDouble.of(target.getKey().y));
            sp.add(NbtDouble.of(target.getKey().z));
            compound.put("targetPos", sp);
            compound.putInt("targetFacing", target.getValue().ordinal());
        }
        return compound;
    }

    public void deserializeNBT(NbtCompound compound) {
        NbtList s = compound.getList("sourcePos", NbtElement.DOUBLE_TYPE);
        if (s.size() > 0)
            setSource(new Vec3d(s.getDouble(0), s.getDouble(1), s.getDouble(2)), Direction.byId(compound.getInt("sourceFacing")));
        NbtList t = compound.getList("targetPos", NbtElement.DOUBLE_TYPE);
        if (t.size() > 0)
            setTarget(new Vec3d(t.getDouble(0), t.getDouble(1), t.getDouble(2)), Direction.byId(compound.getInt("targetFacing")));
    }
}
