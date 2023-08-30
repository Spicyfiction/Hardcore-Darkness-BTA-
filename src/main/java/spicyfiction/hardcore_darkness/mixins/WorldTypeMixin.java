package spicyfiction.hardcore_darkness.mixins;

import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import spicyfiction.hardcore_darkness.Config;
import spicyfiction.hardcore_darkness.interfaces.IWorldType;


@Mixin(value = net.minecraft.core.world.type.WorldType.class, remap = false)
public abstract class WorldTypeMixin implements IWorldType {


    public float[] generateBrightnessTable(World world) {
                float[] brightnessTable = world.getWorldType().getBrightnessRamp();
                float f = Config.getFromConfig(world.dimension.languageKey, brightnessTable[0]);

        for(int i = 0; i <= 15; ++i) {
            float f1 = 1.0F - (float)i / 15.0F;
            brightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
        }
        return brightnessTable;
    }

    public float[] generateBrightnessTable(Dimension dimension) {
        float[] brightnessTable = dimension.defaultWorldType.getBrightnessRamp();
        float f = Config.getFromConfig(dimension.languageKey, brightnessTable[0]);

        for(int i = 0; i <= 15; ++i) {
            float f1 = 1.0F - (float)i / 15.0F;
            brightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
        }
        return brightnessTable;
    }



}

