package com.blueliv.service;

import com.blueliv.model.Coordinates;
import com.blueliv.model.Instruction;
import com.blueliv.model.Orientation;
import com.blueliv.model.Plateau;
import com.blueliv.model.Rover;
import com.blueliv.service.exception.PlateauServiceException;

public interface PlateauService {
	public Plateau createPlateau(Coordinates size) throws PlateauServiceException;

	public Rover addRoverToPlateau(Plateau plateau, Coordinates position, Orientation orientation)
			throws PlateauServiceException;

	public void executeRoverInstructions(Plateau plateau, Rover rover, Instruction[] instructions)
			throws PlateauServiceException;
}
