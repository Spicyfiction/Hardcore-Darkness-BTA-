package spicyfiction.hardcore_darkness.mixins;


import net.minecraft.client.Minecraft;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
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


    @Final
    @Shadow
    public Dimension dimension;
    @Mutable
    @Final
    @Shadow
    public WorldType worldType;


    @Inject(method = "<init>(Lnet/minecraft/core/world/save/LevelStorage;Ljava/lang/String;JLnet/minecraft/core/world/Dimension;Lnet/minecraft/core/world/type/WorldType;)V", at = @At("TAIL"), remap = false)
    public void replaceBrightnessTable(LevelStorage saveHandler, String name, long seed, Dimension dimension, WorldType worldType, CallbackInfo ci) {

        if (worldType != null) {
            ((WorldTypeAccessor) (this.worldType)).setBrightnessRamp(((IWorldType) worldType).generateBrightnessTable(Dimension.getDimensionList().get(saveHandler.getLevelData().getDimension())));
        } else if (saveHandler.getLevelData() != null) {
            ((WorldTypeAccessor) (this.worldType)).setBrightnessRamp(((IWorldType) saveHandler.getDimensionData(saveHandler.getLevelData().getDimension()).getWorldType()).generateBrightnessTable(Dimension.getDimensionList().get(saveHandler.getLevelData().getDimension())));
        } else if (dimension != null) {
            ((WorldTypeAccessor) (this.worldType)).setBrightnessRamp(((IWorldType) dimension.defaultWorldType).generateBrightnessTable(dimension));
        } else {
            ((WorldTypeAccessor) (this.worldType)).setBrightnessRamp(((IWorldType) this.dimension.defaultWorldType).generateBrightnessTable(this.dimension));
        }
    }

    //this one is for the player switching worlds
    @Inject(method = "<init>(Lnet/minecraft/core/world/World;Lnet/minecraft/core/world/Dimension;)V", at = @At("TAIL"), remap = false)
    public void replaceBrightnessTable1(World world, Dimension dimension, CallbackInfo ci) {
            if (dimension != null) {
                ((WorldTypeAccessor) (world.worldType)).setBrightnessRamp(((IWorldType) world.getWorldType()).generateBrightnessTable(dimension));
            } else if (world.dimensionData != null) {
                ((WorldTypeAccessor) (world.worldType)).setBrightnessRamp(((IWorldType) world.dimensionData.getWorldType()).generateBrightnessTable(world.dimension));
            } else {
                ((WorldTypeAccessor) (world.worldType)).setBrightnessRamp(((IWorldType) this.dimension.defaultWorldType).generateBrightnessTable(this.dimension));
            }
    }

    /*
    @Inject(method = "<init>(Lnet/minecraft/core/world/save/LevelStorage;Ljava/lang/String;Lnet/minecraft/core/world/Dimension;Lnet/minecraft/core/world/type/WorldType;J)V", at = @At("TAIL"), remap = false)
    public void replaceBrightnessTable2(LevelStorage saveHandler, String name, Dimension dimension, WorldType worldType, long seed, CallbackInfo ci) {
        if (worldType != null) {
            ((WorldTypeAccessor) (this.worldType)).setBrightnessRamp(((IWorldType) worldType).generateBrightnessTable(Dimension.getDimensionList().get(saveHandler.getLevelData().getDimension())));
        } else if (saveHandler.getLevelData() != null) {
            ((WorldTypeAccessor) (this.worldType)).setBrightnessRamp(((IWorldType) saveHandler.getDimensionData(saveHandler.getLevelData().getDimension()).getWorldType()).generateBrightnessTable(Dimension.getDimensionList().get(saveHandler.getLevelData().getDimension())));
        } else if (dimension != null) {
            ((WorldTypeAccessor) (this.worldType)).setBrightnessRamp(((IWorldType) dimension.defaultWorldType).generateBrightnessTable(dimension));
        } else {
            ((WorldTypeAccessor) (this.worldType)).setBrightnessRamp(((IWorldType) this.dimension.defaultWorldType).generateBrightnessTable(this.dimension));
        }
    }

     */

}
