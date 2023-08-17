package spicyfiction.hardcore_darkness.mixins.Accessors;

import net.minecraft.core.world.type.WorldType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = WorldType.class, remap = false)
public interface WorldTypeAccessor {

    @Accessor
    void setBrightnessRamp(float[] table);
}
