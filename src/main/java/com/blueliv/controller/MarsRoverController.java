package com.blueliv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.blueliv.controller.command.CompositeCommand;
import com.blueliv.controller.command.PlateauCreationCommand;
import com.blueliv.controller.command.RoverCreationCommand;
import com.blueliv.controller.command.RoverInstructionsCommand;
import com.blueliv.model.Coordinates;
import com.blueliv.model.Plateau;
import com.blueliv.model.Rover;
import com.blueliv.service.PlateauService;
import com.blueliv.service.exception.PlateauServiceException;

@Controller
public class MarsRoverController {

	@Autowired
	PlateauService plateauService;

	public PlateauResponse processCommands(CompositeCommand compositeCommand) throws PlateauServiceException {
		// Create Plateau
		PlateauCreationCommand plateauCreationCommand = compositeCommand.getPlateauCreationCommand();
		Plateau plateau = plateauService
				.createPlateau(new Coordinates(plateauCreationCommand.getX() + 1, plateauCreationCommand.getY() + 1));

		// Iterate per each rover related command
		int i = 0;
		for (RoverCreationCommand roverCreationCommand : compositeCommand.getRoverCreationCommands()) {
			Rover r = plateauService.addRoverToPlateau(plateau, roverCreationCommand.getPosition(),
					roverCreationCommand.getOrientation());
			RoverInstructionsCommand roverInstructionsCommand = compositeCommand.getInstructionsCommands().get(i);
			plateauService.executeRoverInstructions(plateau, r, roverInstructionsCommand.getInstructions());
			i++;
		}
		return PlateauResponse.createResponse(plateau);

	}
}
