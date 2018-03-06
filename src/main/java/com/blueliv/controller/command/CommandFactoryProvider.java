package com.blueliv.controller.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blueliv.controller.command.exception.CommandFormatException;


@Component
public class CommandFactoryProvider {
	private static final Logger logger = LoggerFactory.getLogger(CommandFactoryProvider.class);
	
	@Autowired
    private List<CommandFactory> factories;
	
	private Map<Class<? extends Command>,CommandFactory> factoryRegistry = new HashMap<>();
	
	public CommandFactory getCommandFactory(Class<? extends Command> targetClass){
		if (!factoryRegistry.containsKey(targetClass)) throw new RuntimeException(String.format("No CommandFactory found for class %s",targetClass.getSimpleName()));
		return factoryRegistry.get(targetClass);
	}
	
	public Command parseCommand(Class<? extends Command> targetClass, String commandString) throws CommandFormatException{
		return getCommandFactory(targetClass).parseCommand(commandString);
	}
	
	@PostConstruct
	protected void registerFactories() {
		for (CommandFactory commandFactory : factories) {
			logger.debug("Found factory: {}",commandFactory);
			factoryRegistry.put(commandFactory.getFactoryClass(), commandFactory);
		}
	}
	
}
