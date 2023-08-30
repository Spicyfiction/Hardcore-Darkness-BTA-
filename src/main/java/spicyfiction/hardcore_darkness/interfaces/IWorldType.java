package spicyfiction.hardcore_darkness.interfaces;


import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;

public interface IWorldType {

    float[] generateBrightnessTable(World world);

    float[] generateBrightnessTable(Dimension dimension);

}
