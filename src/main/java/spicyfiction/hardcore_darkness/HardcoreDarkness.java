package spicyfiction.hardcore_darkness;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.client.Minecraft;
import spicyfiction.hardcore_darkness.interfaces.IWorldType;


public class HardcoreDarkness implements ModInitializer {
    public static final String MOD_ID = "hardcore_darkness";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static String name(String name) {
        return HardcoreDarkness.MOD_ID + "." + name;
    }

    public static final Config config = new Config();

    @Override
    public void onInitialize() {
        LOGGER.info("Hardcore darkness initialized.");

    }
}
