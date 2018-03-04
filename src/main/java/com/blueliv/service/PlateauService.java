package com.blueliv.service;

import com.blueliv.model.Coordinates;
import com.blueliv.model.Instruction;
import com.blueliv.model.Orientation;
import com.blueliv.model.Plateau;
import com.blueliv.model.Rover;

public interface PlateauService {
	public Plateau createPlateau(Coordinates size);

	public Rover addRoverToPlateau(Plateau plateau, Coordinates position, Orientation orientation);

	public void executeRoverInstructions(Plateau plateau, Rover rover, Instruction[] instructions);
}
