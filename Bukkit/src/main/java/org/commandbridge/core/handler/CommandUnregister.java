package org.commandbridge.core.handler;

import dev.jorel.commandapi.CommandAPI;
import org.commandbridge.CommandBridge;
import org.commandbridge.core.utilities.VerboseLogger;

public class CommandUnregister {
    private final VerboseLogger verboseLogger;

    public CommandUnregister(CommandBridge plugin) {
        this.verboseLogger = plugin.getVerboseLogger();
    }

//    public void unregisterCommand(String command) {
//        try {
//            verboseLogger.info("Attempting to unregister command: " + command);
//
//            Field commandMapField = plugin.getServer().getClass().getDeclaredField("commandMap");
//            commandMapField.setAccessible(true);
//            CommandMap commandMap = (CommandMap) commandMapField.get(plugin.getServer());
//
//            if (commandMap instanceof SimpleCommandMap) {
//                SimpleCommandMap simpleCommandMap = (SimpleCommandMap) commandMap;
//
//                Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
//                knownCommandsField.setAccessible(true);
//                Map<String, Command> knownCommands = (Map<String, Command>) knownCommandsField.get(simpleCommandMap);
//
//                if (knownCommands.containsKey(command)) {
//                    verboseLogger.info("Command found in knownCommands: " + command);
//
//                    knownCommands.remove(command);
//                    verboseLogger.info("Removed command: " + command + " from knownCommands.");
//
//                    knownCommands.values().removeIf(cmd -> cmd instanceof PluginCommand && ((PluginCommand) cmd).getPlugin() == plugin);
//                    verboseLogger.info("Removed any lingering PluginCommand instances associated with the plugin.");
//
//                    verboseLogger.forceInfo("Successfully unregistered command: " + command);
//                } else {
//                    verboseLogger.warn("Command not found: " + command);
//                }
//            } else {
//                verboseLogger.warn("CommandMap is not an instance of SimpleCommandMap.");
//            }
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            verboseLogger.error("Failed to unregister command: " + command, e);
//        }
//    }

    public void unregisterCommand(String command) {
        CommandAPI.unregister(command);
        verboseLogger.info("Unregistered command: " + command);
    }
}
