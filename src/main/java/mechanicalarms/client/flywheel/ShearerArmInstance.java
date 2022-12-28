package mechanicalarms.client.flywheel;

import com.jozufozu.flywheel.api.MaterialManager;
import mechanicalarms.common.tile.AbstractArmEntity;

public class ShearerArmInstance extends AbstractArmInstance {
    public ShearerArmInstance(MaterialManager materialManager, AbstractArmEntity blockEntity) {
        super(materialManager, blockEntity);
    }
}
