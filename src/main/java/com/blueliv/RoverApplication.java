package com.blueliv;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.blueliv.command.CommandFactoryProvider;
import com.blueliv.command.PlateauCreationCommand;
import com.blueliv.command.RoverCreationCommand;
import com.blueliv.command.RoverInstructionsCommand;
import com.blueliv.command.exception.CommandFormatException;
import com.blueliv.model.Coordinates;
import com.blueliv.model.Plateau;
import com.blueliv.model.Rover;
import com.blueliv.service.PlateauService;
import com.blueliv.service.exception.PlateauServiceException;

@SpringBootApplication
public class RoverApplication implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(RoverApplication.class);

	@Autowired
	PlateauService plateauService;

	@Autowired
	CommandFactoryProvider commandFactoryProvider;

	public static void main(String[] args) {
		SpringApplication.run(RoverApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		try {
			String[] arguments = args.getSourceArgs();
			logger.debug("RoverApplication started with args : {}", Arrays.toString(arguments));
			if (arguments.length < 3 || arguments.length % 2 == 0) {
				throw new CommandFormatException("Wrong number of input lines, must be odd");
			}

			PlateauCreationCommand plateauCreationCommand = (PlateauCreationCommand) commandFactoryProvider
					.parseCommand(PlateauCreationCommand.class, arguments[0]);
			Plateau plateau = plateauService.createPlateau(
					new Coordinates(plateauCreationCommand.getX() + 1, plateauCreationCommand.getY() + 1));
			logger.debug("Plateau created : {}", plateau);

			int i = 1;
			while (i < arguments.length - 1) {
				RoverCreationCommand roverCreationCommand = (RoverCreationCommand) commandFactoryProvider
						.parseCommand(RoverCreationCommand.class, arguments[i]);
				Rover r = plateauService.addRoverToPlateau(plateau, roverCreationCommand.getPosition(),
						roverCreationCommand.getOrientation());
				logger.debug("Rover created : {}", r);

				RoverInstructionsCommand roverInstructionsCommand = (RoverInstructionsCommand) commandFactoryProvider
						.parseCommand(RoverInstructionsCommand.class, arguments[i + 1]);
				logger.debug("Rover instructions parsed : {}",
						Arrays.toString(roverInstructionsCommand.getInstructions()));
				plateauService.executeRoverInstructions(plateau, r, roverInstructionsCommand.getInstructions());
				logger.debug("Rover moved : {}", r);
				i += 2;
			}

			String response = createResponse(plateau);
			System.out.println(response);
		} catch (CommandFormatException | PlateauServiceException e) {
			logger.error(e.getMessage());
			System.exit(1);
		}

	}

	private String createResponse(Plateau plateau) {
		StringBuilder sb = new StringBuilder();
		for (Rover rover : plateau.getRovers()) {
			sb.append(
					rover.getPosition().getX() + " " + rover.getPosition().getY() + " " + rover.getOrientation() + " ");
		}
		sb.deleteCharAt(sb.length() - 1);
		String response = sb.toString();
		return response;
	}
}
