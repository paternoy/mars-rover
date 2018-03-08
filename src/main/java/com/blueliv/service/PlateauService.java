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

	public Plateau createPlateau(Coordinates size) throws PlateauServiceException {
		if (size == null)
			throw new PlateauServiceException("Plateau size cannot be null");
		return new Plateau(size);
	}

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

	public void executeRoverInstructions(Plateau plateau, Rover rover, Instruction[] instructions)
			throws PlateauServiceException {

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
