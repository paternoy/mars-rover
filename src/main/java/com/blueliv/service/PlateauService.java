package com.blueliv.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.blueliv.model.Coordinates;
import com.blueliv.model.Instruction;
import com.blueliv.model.Orientation;
import com.blueliv.model.Plateau;
import com.blueliv.model.Rover;
import com.blueliv.service.exception.PlateauServiceException;

@Service
public class PlateauService {

	private static final Logger logger = LoggerFactory.getLogger(PlateauService.class);

	/**
	 * Creates a new Plateau with the dimensions specified in argument size.
	 * 
	 * @return the created Plateau
	 * @throws PlateauServiceException
	 *             if size argument is null or contains any dimension lower than 1.
	 */
	public Plateau createPlateau(Coordinates size) throws PlateauServiceException {
		if (size == null)
			throw new PlateauServiceException("Plateau size cannot be null");
		if (size.getX() <= 0 || size.getY() <= 0)
			throw new PlateauServiceException("None of plateau dimensions can be lower than 1");
		return new Plateau(size);
	}

	/**
	 * Creates a new Rover and deploys it in the specified Plateau, according to
	 * initial position and orientation passed by parameter.
	 * 
	 * @return the created Rover
	 * @throws PlateauServiceException
	 *             if initial position is outside Plateau bounds or conflicts with
	 *             another rovers position.
	 */
	public Rover addRoverToPlateau(Plateau plateau, Coordinates position, Orientation orientation)
			throws PlateauServiceException {
		if (!plateau.isPositionInBounds(position))
			throw new PlateauServiceException(
					String.format("Initial rover position %s out of plateau bounds", position));
		if (!plateau.isPositionEmpty(position))
			throw new PlateauServiceException(String.format("Initial rover position %s is already occupied", position));
		Rover rover = new Rover(position, orientation);
		plateau.addRover(rover);
		return rover;
	}

	/**
	 * Executes the instructions for the specified rover and updates its orientation
	 * and position within the Plateau.
	 * 
	 * @throws PlateauServiceException
	 *             if rover was not initially deployed in the Plateau.
	 */
	public void executeRoverInstructions(Plateau plateau, Rover rover, Instruction[] instructions)
			throws PlateauServiceException {
		if (!plateau.getRovers().contains(rover))
			throw new PlateauServiceException("Rover is not deployed in the specified plateau.");
		for (Instruction instruction : instructions) {
			switch (instruction) {
			case M:
				Coordinates nextPosition = move(rover.getPosition(), rover.getOrientation());
				if (plateau.isPositionInBounds(nextPosition) && plateau.isPositionEmpty(nextPosition)) {
					rover.setPosition(nextPosition);
				}
				break;
			case L:
			case R:
				rover.setOrientation(turn(rover.getOrientation(), instruction));
			}
			logger.debug("Rover executed instruction \"{}\" and current position is {} {} ", instruction,
					rover.getPosition(), rover.getOrientation());
		}
	}

	private Orientation turn(Orientation orientation, Instruction instruction) {
		Orientation[] values = Orientation.values();
		int index = Arrays.asList(values).indexOf(orientation);
		switch (instruction) {
		case M:
			break;
		case L:
			index--;
			break;
		case R:
			index++;
			break;
		}
		Orientation result = values[Math.floorMod(index, 4)];
		return result;
	}

	private Coordinates move(Coordinates position, Orientation orientation) {
		int deltaX = 0;
		int deltaY = 0;
		switch (orientation) {
		case E:
			deltaX = 1;
			break;
		case N:
			deltaY = 1;
			break;
		case S:
			deltaY = -1;
			break;
		case W:
			deltaX = -1;
			break;
		}
		Coordinates result = new Coordinates(position.getX() + deltaX, position.getY() + deltaY);
		return result;
	}

}
