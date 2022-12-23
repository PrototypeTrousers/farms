package mechanicalarms.common.logic.behavior;

import net.minecraft.nbt.NbtCompound;

public class WorkStatus {
    private ActionTypes type;
    private Action action;

    public WorkStatus() {
        this.type = ActionTypes.IDLING;
        this.action = Action.IDLING;
    }

    public void idle() {
        this.type = ActionTypes.IDLING;
        this.action = Action.IDLING;
    }

    public ActionTypes getType() {
        return type;
    }

    public Action getAction() {
        return action;
    }

    public Action setAction(Action value) {
        this.action = value;
        return value;
    }

    public ActionTypes setType(ActionTypes value) {
        this.type = value;
        return type;
    }

    public NbtCompound serializeNBT() {
        NbtCompound compound = new NbtCompound();
        compound.putString("type", type.name());
        compound.putString("action", action.name());
        return compound;
    }

    public void deserializeNBT(NbtCompound nbt) {
        this.type = ActionTypes.valueOf(nbt.getString("type"));
        this.action = Action.valueOf(nbt.getString("action"));
    }
}
