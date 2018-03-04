package com.blueliv.service.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.blueliv.RoverApplication;
import com.blueliv.model.Coordinates;
import com.blueliv.model.Instruction;
import com.blueliv.model.Orientation;
import com.blueliv.model.Plateau;
import com.blueliv.model.Rover;
import com.blueliv.service.PlateauService;

@Service
public class PlateauServiceImpl implements PlateauService{

	private static final Logger logger = LoggerFactory.getLogger(PlateauServiceImpl.class);
	
	@Override
	public Plateau createPlateau(Coordinates size) {
		return new Plateau(size);
	}

	@Override
	public Rover addRoverToPlateau(Plateau plateau, Coordinates position, Orientation orientation) {
		Rover r = new Rover(position, orientation);
		plateau.getRovers().add(r);
		return r;
	}

	@Override
	public void executeRoverInstructions(Plateau plateau, Rover rover, Instruction[] instructions) {
		Coordinates position = rover.getPosition();
		Orientation orientation = rover.getOrientation();
		
		for (Instruction instruction : instructions) {
			switch (instruction) {
			case M:
				Coordinates nextPosition=move(position, orientation);
				if(checkPosition(plateau, nextPosition)) {
					position = nextPosition;
				}
				break;
			case L:
			case R:
				orientation = turn(orientation,instruction);
			}
			logger.debug("Rover executed instruction \"{}\" and current position is {} {} ",instruction,position,orientation);
		}
		rover.setPosition(position);
		rover.setOrientation(orientation);
	}
	
	private boolean checkPosition(Plateau plateau, Coordinates position) {
		if(position.getX()<0 || position.getX()>=plateau.getSize().getX()) return false;
		if(position.getY()<0 || position.getY()>=plateau.getSize().getY()) return false;
		return true;
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
		Orientation result = values[Math.floorMod(index,4)];
		return result;
	}
	
	private Coordinates move(Coordinates position, Orientation orientation) {
		int deltaX=0;
		int deltaY=0;
		switch(orientation) {
		case E:
			deltaX=1;
			break;
		case N:
			deltaY=1;
			break;
		case S:
			deltaY=-1;
			break;
		case W:
			deltaX=-1;
			break;
		}
		Coordinates result = new Coordinates(position.getX()+deltaX,position.getY()+deltaY);
		return result;
	}


}
