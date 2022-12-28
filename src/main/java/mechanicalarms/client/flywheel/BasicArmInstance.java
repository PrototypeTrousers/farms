package mechanicalarms.client.flywheel;

import com.jozufozu.flywheel.api.MaterialManager;
import mechanicalarms.common.tile.AbstractArmEntity;

public class BasicArmInstance extends AbstractArmInstance {
    public BasicArmInstance(MaterialManager materialManager, AbstractArmEntity blockEntity) {
        super(materialManager, blockEntity);
    }
}
