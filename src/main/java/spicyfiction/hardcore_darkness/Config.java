package spicyfiction.hardcore_darkness;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.world.Dimension;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {

    private static File configFile = new File((FabricLoader.getInstance().getConfigDir()) + "/HardcoreDarkness.cfg");
    private static Path configPath = FabricLoader.getInstance().getConfigDir();

    public Config() {
        if (!configFile.exists()) {
            try {
                Files.createDirectory(configPath);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            writeConfig();
        }
    }

    public static void writeConfig() {
        try {
            BufferedWriter configWriter = new BufferedWriter(new FileWriter(configFile));
            configWriter.write(
                    "// Hardcore Darkness configuration file. Configure options here." + System.getProperty("line.separator") +
                        "//Configure minimum light level in dimensions here. Note: null means vanilla light level will be used"
            );

            for (Dimension dimension : Dimension.getDimensionList().values()) {
                try {
                    if (dimension.defaultWorldType.hasCeiling()) {
                        configWriter.write(System.getProperty("line.separator") + dimension.languageKey
                                + "=" + "null");
                    } else {
                        configWriter.write(System.getProperty("line.separator") + dimension.languageKey
                                + "=" + "0.0");
                    }

                } catch (Exception exception) {
                    HardcoreDarkness.LOGGER.info("Failed to write config line for dimension : " + dimension.languageKey);
                }
            }
            HardcoreDarkness.LOGGER.info("Successfully created config file");
            configWriter.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    public static float getFromConfig(String dimKey, float base) {
        try {
            BufferedReader configReader = new BufferedReader(new FileReader(configFile));
            for (String line : Files.readAllLines(configFile.toPath())) {
                if (line.startsWith("//")) {
                    // Ignore comments
                } else if (line.contains("=")) {
                    String[] splitLine = line.split("=");
                    String key = splitLine[0];

                    if (key.equals(dimKey)) {
                        if(splitLine[1].equals("null")) {
                            HardcoreDarkness.LOGGER.info("config read successfully, returning default for dimension " + dimKey);
                            return base;
                        }
                        HardcoreDarkness.LOGGER.info("config read successfully, got min light level : " + Float.parseFloat(splitLine[1]) + " for dimension " + dimKey);
                        return Float.parseFloat(splitLine[1]);

                    }

                }
            }

            configReader.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        HardcoreDarkness.LOGGER.info("config read unsuccessfully, returning : " + base + " for dimension " + dimKey);
        return base;
    }




}