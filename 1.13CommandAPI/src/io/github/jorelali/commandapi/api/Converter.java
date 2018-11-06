package io.github.jorelali.commandapi.api;

import java.util.LinkedHashMap;
import java.util.Set;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.jorelali.commandapi.api.arguments.Argument;
import io.github.jorelali.commandapi.api.arguments.GreedyStringArgument;

/**
 * For lazy developers who want to convert their regular plugin commands to minecraft plugin commands
 * Useful for functions maybe?
 * @author Jorel
 *
 */
public class Converter {

	public static void convert(Plugin p) throws Exception {
		Set<String> commands = p.getDescription().getCommands().keySet();
		JavaPlugin plugin = (JavaPlugin) p;
		
		for(String cmdName : commands) {
			
			LinkedHashMap<String, Argument> arguments = new LinkedHashMap<>();
			
			//TODO: Parse PluginDescriptionFile (from commands variable) to add aliases + permissions
			
			//Register the command twice: once for no arguments and once for any number of arguments
			CommandAPI.getInstance().register(cmdName, arguments, (sender, args) -> {
				plugin.getCommand(cmdName).execute(sender, cmdName, new String[0]);
			});
			arguments.put("args", new GreedyStringArgument());
			CommandAPI.getInstance().register(cmdName, arguments, (sender, args) -> {
				plugin.getCommand(cmdName).execute(sender, cmdName, ((String) args[0]).split(" "));
			});
			
		}
	}
	
	
}