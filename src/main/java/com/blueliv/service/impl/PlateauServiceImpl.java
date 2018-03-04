package com.blueliv.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.blueliv.model.Coordinates;
import com.blueliv.model.Instruction;
import com.blueliv.model.Orientation;
import com.blueliv.model.Plateau;
import com.blueliv.model.Rover;
import com.blueliv.service.PlateauService;

@Service
public class PlateauServiceImpl implements PlateauService {

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
	}

}
