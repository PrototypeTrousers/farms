package mechanicalarms.common.tile;

import mechanicalarms.MechanicalArmsMod;
import mechanicalarms.common.logic.behavior.Action;
import mechanicalarms.common.logic.behavior.ActionResult;
import mechanicalarms.common.logic.behavior.ActionTypes;
import mechanicalarms.common.logic.behavior.InteractionType;
import mechanicalarms.common.logic.behavior.Targeting;
import mechanicalarms.common.logic.behavior.WorkStatus;
import mechanicalarms.common.logic.movement.MotorCortex;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static mechanicalarms.common.logic.behavior.Action.*;


public class BlockEntityArm extends BlockEntity{
    private final Targeting targeting = new Targeting();
    private final MotorCortex motorCortex;
    private final WorkStatus workStatus = new WorkStatus();
    private Vec3d armPoint;

    public BlockEntityArm(BlockPos pos, BlockState blockState) {
        super(MechanicalArmsMod.ARM_BLOCK_ENTITY, pos, blockState);
        motorCortex = new MotorCortex(this, 4, InteractionType.ENTITY);
        targeting.setSource(this.getPos().east(3), Direction.SOUTH);
        targeting.setTarget(this.getPos().west(3), Direction.NORTH);
    }

    public float[] getAnimationRotation(int idx) {
        return motorCortex.getAnimationRotation(idx);
    }

    public float[] getRotation(int idx) {
        return motorCortex.getRotation(idx);
    }

    @Override
    protected void writeNbt(NbtCompound compound) {
        super.writeNbt(compound);
        compound.put("rotation", motorCortex.serializeNBT());
        compound.put("targeting", targeting.serializeNBT());
        compound.put("workStatus", workStatus.serializeNBT());
    }

    @Override
    public void readNbt(NbtCompound compound) {
        super.readNbt(compound);
        motorCortex.deserializeNBT(compound.getList("rotation", NbtElement.LIST_TYPE));
        targeting.deserializeNBT(compound.getCompound("targeting"));
        workStatus.deserializeNBT(compound.getCompound("workStatus"));
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public void setWorld(World world) {
        armPoint = new Vec3d(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
        super.setWorld(world);
    }

    public ActionResult interact(Action action, Pair<BlockPos, Direction> blkFace) {
        BlockEntity te = world.getBlockEntity(blkFace.getKey());
        /*if (te != null) {
            IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, blkFace.getRight());
            if (itemHandler != null) {
                if (action == Action.RETRIEVE) {
                    if (this.itemHandler.getStackInSlot(0).isEmpty()) {
                        for (int i = 0; i < itemHandler.getSlots(); i++) {
                            if (!itemHandler.extractItem(i, 1, true).isEmpty()) {
                                ItemStack itemStack = itemHandler.extractItem(i, 1, false);
                                if (!itemStack.isEmpty()) {
                                    this.itemHandler.insertItem(0, itemStack, false);
                                    return ActionResult.SUCCESS;
                                }
                            }
                        }
                    }
                } else if (action == Action.DELIVER) {
                    ItemStack itemStack = this.itemHandler.extractItem(0, 1, true);
                    if (!itemStack.isEmpty()) {
                        itemStack = this.itemHandler.extractItem(0, 1, false);
                        if (!itemStack.isEmpty()) {
                            for (int i = 0; i < itemHandler.getSlots(); i++) {
                                if (itemHandler.insertItem(i, itemStack, false).isEmpty()) {
                                    return ActionResult.SUCCESS;
                                }
                            }
                        }
                    }
                }
            }
        }
         */
        return ActionResult.SUCCESS;
    }

    void update() {
        switch (motorCortex.getInteractionType()) {
            case BLOCK -> {
                if (workStatus.getType() == ActionTypes.IDLING) {
                    if (hasInput() && hasOutput()) {
                        updateWorkStatus(ActionTypes.MOVEMENT, RETRIEVE);
                    }
                } else if (workStatus.getType() == ActionTypes.MOVEMENT) {
                    if (workStatus.getAction() == Action.RETRIEVE) {
                        ActionResult result = motorCortex.move(armPoint, targeting.getSourceVec(), targeting.getSourceFacing());
                        if (result == ActionResult.SUCCESS) {
                            updateWorkStatus(ActionTypes.INTERACTION, RETRIEVE);
                        }
                    } else if (workStatus.getAction() == DELIVER) {
                        ActionResult result = motorCortex.move(armPoint, targeting.getTargetVec(), targeting.getTargetFacing());
                        if (result == ActionResult.SUCCESS) {
                            updateWorkStatus(ActionTypes.INTERACTION, DELIVER);
                        }
                    }
                } else if (workStatus.getType() == ActionTypes.INTERACTION) {
                    if (workStatus.getAction() == Action.RETRIEVE) {
                        ActionResult result = interact(RETRIEVE, targeting.getSource());
                        if (result == ActionResult.SUCCESS) {
                            updateWorkStatus(ActionTypes.MOVEMENT, DELIVER);
                        }
                    } else if (workStatus.getAction() == DELIVER) {
                        ActionResult result = interact(DELIVER, targeting.getTarget());
                        if (result == ActionResult.SUCCESS) {
                            updateWorkStatus(ActionTypes.MOVEMENT, RETRIEVE);
                        }
                    }
                }
            }
            case ENTITY -> {
                List<Entity> entities = this.getWorld().getNonSpectatingEntities(Entity.class, new Box(this.pos.east(2).south(2).up(6), this.pos.north(2).west(2).down()));
                if (!entities.isEmpty()) {
                    targeting.setTarget(entities.get(0).getBlockPos(), Direction.UP);
                }
                    ActionResult result = motorCortex.move(armPoint, targeting.getTargetVec(), targeting.getTargetFacing());
                }
            }
        }


    public static void tick(World world, BlockPos pos, BlockState state, BlockEntityArm be) {
        be.update();
    }

    private void updateWorkStatus(ActionTypes type, Action action) {
        workStatus.setType(type);
        workStatus.setAction(action);
        world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        markDirty();
    }

    public WorkStatus getWorkStatus() {
        return workStatus;
    }

    public void setSource(BlockPos sourcePos, Direction sourceFacing) {
        targeting.setSource(sourcePos, sourceFacing);
        markDirty();
    }

    public void setTarget(BlockPos targetPos, Direction targetFacing) {
        targeting.setTarget(targetPos, targetFacing);
        markDirty();
    }

    public boolean hasInput() {
        return targeting.hasInput();
    }

    public boolean hasOutput() {
        return targeting.hasOutput();
    }
}
