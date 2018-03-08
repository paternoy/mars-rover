package com.blueliv.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.blueliv.TestConfiguration;
import com.blueliv.model.Coordinates;
import com.blueliv.model.Instruction;
import com.blueliv.model.Orientation;
import com.blueliv.model.Plateau;
import com.blueliv.model.Rover;
import com.blueliv.service.exception.PlateauServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class PlateauServiceTest {
	@Autowired
	PlateauService plateauService;

	@Test
	public void testPlateauCreationOK() {
		try {
			Plateau plateau = plateauService.createPlateau(new Coordinates(5, 4));
			assertEquals(5, plateau.getSize().getX());
			assertEquals(4, plateau.getSize().getY());
			assertEquals(0, plateau.getRovers().size());
		} catch (PlateauServiceException e) {
			fail("Unexpected PlateauServiceException thrown in Plateau creation");
		}
	}

	@Test
	public void testPlateauCreationNullKO() {
		try {
			plateauService.createPlateau(null);
			fail("A PlateauServiceException was expected");
		} catch (PlateauServiceException e) {
		}
	}

	@Test
	public void testPlateauCreationZeroDimensionKO() {
		try {
			plateauService.createPlateau(new Coordinates(5, 0));
			fail("A PlateauServiceException was expected");
		} catch (PlateauServiceException e) {
		}
	}

	@Test
	public void testRoverCreationOK() {
		try {
			Plateau plateau = new Plateau(new Coordinates(5, 5));
			Coordinates position = new Coordinates(3, 2);
			Orientation orientation = Orientation.S;

			Rover rover = plateauService.addRoverToPlateau(plateau, position, orientation);

			assertEquals(3, rover.getPosition().getX());
			assertEquals(2, rover.getPosition().getY());
			assertEquals(orientation, rover.getOrientation());
		} catch (PlateauServiceException e) {
			fail("Unexpected PlateauServiceException thrown in Plateau creation");
		}
	}

	@Test
	public void testRoverCreationOutOfBoundsKO() {
		try {
			Plateau plateau = new Plateau(new Coordinates(4, 4));
			Coordinates position = new Coordinates(5, 5);
			Orientation orientation = Orientation.S;

			plateauService.addRoverToPlateau(plateau, position, orientation);

			fail("A PlateauServiceException was expected");
		} catch (PlateauServiceException e) {
		}
	}

	@Test
	public void testRoverCreationInOccupiedPositionKO() {
		try {
			Plateau plateau = new Plateau(new Coordinates(4, 4));
			Coordinates position = new Coordinates(3, 3);
			Orientation orientation = Orientation.S;
			plateau.addRover(new Rover(position, orientation));

			plateauService.addRoverToPlateau(plateau, position, orientation);

			fail("A PlateauServiceException was expected");
		} catch (PlateauServiceException e) {
		}
	}

	@Test
	public void testRoverMoveInstructionOK() {
		try {
			Plateau plateau = new Plateau(new Coordinates(4, 4));
			Coordinates position = new Coordinates(3, 3);
			Orientation orientation = Orientation.S;
			Rover rover = new Rover(position, orientation);
			plateau.addRover(rover);
			Instruction[] instructions = new Instruction[] { Instruction.M };

			plateauService.executeRoverInstructions(plateau, rover, instructions);

			assertEquals(3, rover.getPosition().getX());
			assertEquals(2, rover.getPosition().getY());
		} catch (PlateauServiceException e) {
			fail("Unexpected PlateauServiceException thrown when processing move instruction");
		}
	}

	@Test
	public void testRoverMoveInstructionOffLimitsOK() {
		try {
			Plateau plateau = new Plateau(new Coordinates(4, 4));
			Coordinates position = new Coordinates(3, 4);
			Orientation orientation = Orientation.N;
			Rover rover = new Rover(position, orientation);
			plateau.addRover(rover);
			Instruction[] instructions = new Instruction[] { Instruction.M };

			plateauService.executeRoverInstructions(plateau, rover, instructions);

			assertEquals(3, rover.getPosition().getX());
			assertEquals(4, rover.getPosition().getY());
		} catch (PlateauServiceException e) {
			fail("Unexpected PlateauServiceException thrown when processing move instruction");
		}
	}

	@Test
	public void testRoverMoveInstructionToOccupiedPositionOK() {
		try {
			Plateau plateau = new Plateau(new Coordinates(4, 4));
			Orientation orientation = Orientation.N;
			// Rover 1
			Coordinates position1 = new Coordinates(3, 4);
			Rover rover1 = new Rover(position1, orientation);
			plateau.addRover(rover1);

			// Rover 2
			Coordinates position2 = new Coordinates(3, 3);
			Rover rover2 = new Rover(position2, orientation);
			plateau.addRover(rover2);
			Instruction[] instructions = new Instruction[] { Instruction.M };

			plateauService.executeRoverInstructions(plateau, rover2, instructions);

			assertEquals(position2, rover2.getPosition());
			assertEquals(Orientation.N, rover2.getOrientation());
		} catch (PlateauServiceException e) {
			fail("Unexpected PlateauServiceException thrown when processing MOVE instruction");
		}
	}

	@Test
	public void testRoverTurnLeftInstructionOK() {
		try {
			Plateau plateau = new Plateau(new Coordinates(4, 4));
			Coordinates position = new Coordinates(3, 4);
			Orientation orientation = Orientation.N;
			Rover rover = new Rover(position, orientation);
			plateau.addRover(rover);
			Instruction[] instructions = new Instruction[] { Instruction.L };

			plateauService.executeRoverInstructions(plateau, rover, instructions);

			assertEquals(position, rover.getPosition());
			assertEquals(Orientation.W, rover.getOrientation());
		} catch (PlateauServiceException e) {
			fail("Unexpected PlateauServiceException thrown when processing LEFT instruction");
		}
	}

	@Test
	public void testRoverTurnRightInstructionOK() {
		try {
			Plateau plateau = new Plateau(new Coordinates(4, 4));
			Coordinates position = new Coordinates(3, 4);
			Orientation orientation = Orientation.N;
			Rover rover = new Rover(position, orientation);
			plateau.addRover(rover);
			Instruction[] instructions = new Instruction[] { Instruction.R, Instruction.R, Instruction.R,
					Instruction.R };

			plateauService.executeRoverInstructions(plateau, rover, instructions);

			assertEquals(position, rover.getPosition());
			assertEquals(orientation, rover.getOrientation());
		} catch (PlateauServiceException e) {
			fail("Unexpected PlateauServiceException thrown when processing LEFT instruction");
		}
	}

	@Test
	public void testUndeployedRoverInstructionsKO() {
		try {
			Plateau plateau = new Plateau(new Coordinates(4, 4));
			Coordinates position = new Coordinates(3, 4);
			Orientation orientation = Orientation.N;
			Rover rover = new Rover(position, orientation);
			Instruction[] instructions = new Instruction[] { Instruction.M };

			plateauService.executeRoverInstructions(plateau, rover, instructions);

			fail("A PlateauServiceException was expected");
		} catch (PlateauServiceException e) {
		}
	}

}
