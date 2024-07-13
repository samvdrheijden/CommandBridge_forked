package org.commandbridge.runtime;

import org.commandbridge.CommandBridge;
import org.commandbridge.utilities.VerboseLogger;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;

public class Scripts {

    private final File scriptsFolder = new File("plugins/CommandBridge/bukkit-scripts");
    private final CommandBridge plugin;
    private final VerboseLogger verboseLogger;

    public Scripts(CommandBridge plugin) {
        this.plugin = plugin;
        this.verboseLogger = plugin.getVerboseLogger();
    }

    public void loadScripts() {
        verboseLogger.info("Loading scripts...");
        unloadScripts();

        if (!scriptsFolder.exists() || !scriptsFolder.isDirectory()) {
            verboseLogger.warn("Scripts folder does not exist or is not a directory: " + scriptsFolder.getPath());
            return;
        }

        File[] files = scriptsFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null || files.length == 0) {
            verboseLogger.warn("No scripts found in folder: " + scriptsFolder.getPath());
            return;
        }

        for (File file : files) {
            try (InputStream input = Files.newInputStream(file.toPath())) {
                verboseLogger.info("Loading script file: " + file.getName());

                Yaml yaml = new Yaml();
                Map<String, Object> data = yaml.load(input);

                if (Boolean.TRUE.equals(data.get("enabled"))) {
                    plugin.getCommandRegister().registerCommands(data);
                    verboseLogger.forceInfo("Command registered successfully from script: " + file.getName());
                } else {
                    verboseLogger.info("Script disabled, skipping file: " + file.getName());
                }
            } catch (Exception e) {
                verboseLogger.error("Failed to load or parse script file: " + file.getName(), e);
            }
        }

        verboseLogger.info("Scripts loading process completed.");
    }

    public void unloadScripts() {
        verboseLogger.info("Unloading all registered scripts...");

        ArrayList<String> registeredCommands = new ArrayList<>(plugin.getRegisteredCommands());
        for (String command : registeredCommands) {
            plugin.getCommandUnregister().unregisterCommand(command);
            verboseLogger.info("Command unregistered successfully: " + command);
        }

        plugin.clearRegisteredCommands();
        verboseLogger.info("All commands have been unregistered and cleared.");
    }
}
