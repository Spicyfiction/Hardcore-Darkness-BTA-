package spicyfiction.hardcore_darkness.mixins;


import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.core.world.save.DimensionData;
import net.minecraft.core.world.save.LevelData;
import net.minecraft.core.world.save.LevelStorage;
import net.minecraft.core.world.type.WorldType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spicyfiction.hardcore_darkness.interfaces.IWorldType;
import spicyfiction.hardcore_darkness.mixins.Accessors.WorldTypeAccessor;

@Mixin(value = World.class, remap = false)
public class WorldMixin {


    @Shadow public DimensionData dimensionData;
    @Shadow public Dimension dimension;
    @Shadow public LevelData levelData;
    @Mutable
    @Final
    @Shadow public WorldType worldType;

    @Inject(method = "<init>(Lnet/minecraft/core/world/save/LevelStorage;Ljava/lang/String;JLnet/minecraft/core/world/Dimension;Lnet/minecraft/core/world/type/WorldType;)V", at = @At("TAIL"), remap = false)
    public void replaceBrightnessTable(LevelStorage saveHandler, String name, long seed, Dimension dimension, WorldType worldType, CallbackInfo ci) {


        if (worldType != null) {
            ((WorldTypeAccessor) (this.worldType)).setBrightnessRamp(((IWorldType) worldType).generateBrightnessTable(this.dimension));
        } else if (this.dimensionData != null) {
            ((WorldTypeAccessor) (this.worldType)).setBrightnessRamp(((IWorldType) this.dimensionData.getWorldType()).generateBrightnessTable(this.dimension));
        } else {
            ((WorldTypeAccessor) (this.worldType)).setBrightnessRamp(((IWorldType) this.dimension.defaultWorldType).generateBrightnessTable(this.dimension));
        }
    }

    @Inject(method = "<init>(Lnet/minecraft/core/world/World;Lnet/minecraft/core/world/Dimension;)V", at = @At("TAIL"), remap = false)
    public void replaceBrightnessTable1(World world, Dimension dimension, CallbackInfo ci) {
        if (dimension != null) {
            ((WorldTypeAccessor) (world.worldType)).setBrightnessRamp(((IWorldType) dimension.defaultWorldType).generateBrightnessTable(dimension));
        } else if (world.dimensionData != null) {
            ((WorldTypeAccessor) (world.worldType)).setBrightnessRamp(((IWorldType) world.dimensionData.getWorldType()).generateBrightnessTable(world.dimension));
        } else {
            ((WorldTypeAccessor) (world.worldType)).setBrightnessRamp(((IWorldType) world.dimension.defaultWorldType).generateBrightnessTable(world.dimension));
        }
    }

}
