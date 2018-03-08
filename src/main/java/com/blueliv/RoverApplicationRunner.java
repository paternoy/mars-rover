package com.blueliv;

import java.util.List;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.blueliv.controller.MarsRoverController;
import com.blueliv.controller.PlateauResponse;
import com.blueliv.controller.command.CommandFactoryProvider;
import com.blueliv.controller.command.CompositeCommand;
import com.blueliv.controller.command.exception.CommandFormatException;
import com.blueliv.service.exception.PlateauServiceException;

@Component
@Profile("local")
/**
 * Main ApplicationRunner for command-line usage. This Runner is associated to a
 * Spring profile called "local". When specifying this profile in the
 * command-line, the application will run this class after preparing whole
 * application context. Through specific application-local.properties file, web
 * environment is disabled when using this profile.
 * 
 * @author Jaume Paternoy
 *
 */
public class RoverApplicationRunner implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(RoverApplicationRunner.class);

	@Autowired
	MarsRoverController marsRoverController;

	@Autowired
	CommandFactoryProvider commandFactoryProvider;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		try {
			List<String> arguments = args.getNonOptionArgs();
			logger.debug("RoverApplication started with args : {}", arguments);

			StringJoiner stringJoiner = new StringJoiner("\n");
			for (String argument : arguments) {
				stringJoiner.add(argument);
			}

			CompositeCommand compositeCommand = (CompositeCommand) commandFactoryProvider
					.parseCommand(CompositeCommand.class, stringJoiner.toString());
			PlateauResponse response = marsRoverController.processCommands(compositeCommand);
			System.out.println(response.getResponse());
		} catch (CommandFormatException | PlateauServiceException e) {
			logger.error(e.getMessage());
			System.exit(1);
		}

	}

}
